import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Consumer;

/**
 * Интерфейс для описания неориентированного графа (н-графа)
 * с реализацией некоторых методов графа
 */
public interface Graph {
    /**
     * Кол-во вершин в графе
     *
     * @return
     */
    int vertexCount();

    /**
     * Кол-во ребер в графе
     *
     * @return
     */
    int edgeCount();

    /**
     * Добавление ребра между вершинами с номерами startVertex и endVertex
     *
     * @param startVertex
     * @param endVertex
     */
    void addEdge(Vertex startVertex, Vertex endVertex);

    /**
     * Удаление ребра/ребер между вершинами с номерами startVertex и endVertex
     *
     * @param startVertex
     * @param endVertex
     */
    void removeEdge(Vertex startVertex, Vertex endVertex);

    /**
     * @param vertex Номер вершины, смежные с которой необходимо найти
     * @return Объект, поддерживающий итерацию по номерам связанных с vertex вершин
     */
    Iterable<Vertex> adjacent(Vertex vertex);

    /**
     * Проверка смежности двух вершин
     *
     * @param startVertex
     * @param endVertex
     * @return
     */
    default boolean isAdjacent(Vertex startVertex, Vertex endVertex) {
        for (Vertex adjacent : adjacent(startVertex)) {
            if (adjacent.equals(endVertex)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Поиск в глубину, реализованный рекурсивно
     * (начальная вершина также включена)
     *
     * @param startVertex Вершина, с которой начинается поиск
     * @param visitor     Посетитель
     */
    default void dfsRecursionImpl(Vertex startVertex, Consumer<Vertex> visitor) {
        boolean[] visited = new boolean[vertexCount()];

        class Inner {
            private void visit(Vertex currentVertex) {
                visitor.accept(currentVertex);
                visited[currentVertex.getNumber()] = true;
                for (Vertex adjacentVertex : adjacent(currentVertex)) {
                    if (!visited[adjacentVertex.getNumber()]) {
                        visit(adjacentVertex);
                    }
                }
            }
        }
        new Inner().visit(startVertex);
    }

    /**
     * Поиск в глубину, реализованный с помощью стека
     * (не совсем "правильный"/классический, т.к. "в глубину" реализуется только "план" обхода, а не сам обход)
     *
     * @param startVertex Вершина, с которой начинается поиск
     * @param visitor     Посетитель
     */
    default void dfsStackImpl(Vertex startVertex, Consumer<Vertex> visitor) {
        boolean[] visited = new boolean[vertexCount()];
        Stack<Vertex> stack = new Stack<>();
        stack.push(startVertex);
        visited[startVertex.getNumber()] = true;
        while (!stack.empty()) {
            Vertex currentVertex = stack.pop();
            visitor.accept(currentVertex);
            for (Vertex vertex : adjacent(currentVertex)) {
                if (!visited[vertex.getNumber()]) {
                    stack.push(vertex);
                    visited[vertex.getNumber()] = true;
                }
            }
        }
    }

    /**
     * Поиск в ширину, реализованный с помощью очереди
     * (начальная вершина также включена)
     *
     * @param startVertex Вершина, с которой начинается поиск
     * @param visitor     Посетитель
     */
    default void bfsQueueImpl(Vertex startVertex, Consumer<Vertex> visitor) {
        boolean[] visited = new boolean[vertexCount()];
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(startVertex);
        visited[startVertex.getNumber()] = true;
        while (queue.size() > 0) {
            Vertex currentVertex = queue.remove();
            visitor.accept(currentVertex);
            for (Vertex vertex : adjacent(currentVertex)) {
                if (!visited[vertex.getNumber()]) {
                    queue.add(vertex);
                    visited[vertex.getNumber()] = true;
                }
            }
        }
    }

    /**
     * Поиск в глубину в виде итератора
     * (начальная вершина также включена)
     *
     * @param startVertex Вершина, с которой начинается поиск
     * @return Итератор
     */
    default Iterable<Vertex> dfs(Vertex startVertex) {
        return new Iterable<>() {
            private Stack<Vertex> stack = null;
            private boolean[] visited = null;

            @Override
            public Iterator<Vertex> iterator() {
                stack = new Stack<>();
                stack.push(startVertex);
                visited = new boolean[Graph.this.vertexCount()];
                visited[startVertex.getNumber()] = true;

                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return !stack.isEmpty();
                    }

                    @Override
                    public Vertex next() {
                        Vertex result = stack.pop();
                        for (Vertex adjacent : Graph.this.adjacent(result)) {
                            if (!visited[adjacent.getNumber()]) {
                                visited[adjacent.getNumber()] = true;
                                stack.add(adjacent);
                            }
                        }
                        return result;
                    }
                };
            }
        };
    }

    /**
     * Поиск в ширину в виде итератора
     * (начальная вершина также включена)
     *
     * @param startVertex Вершина, с которой начинается поиск
     * @return Итератор
     */
    default Iterable<Vertex> bfs(Vertex startVertex) {
        return new Iterable<>() {
            private Queue<Vertex> queue = null;
            private boolean[] visited = null;

            @Override
            public Iterator<Vertex> iterator() {
                queue = new LinkedList<>();
                queue.add(startVertex);
                visited = new boolean[Graph.this.vertexCount()];
                visited[startVertex.getNumber()] = true;

                return new Iterator<Vertex>() {
                    @Override
                    public boolean hasNext() {
                        return !queue.isEmpty();
                    }

                    @Override
                    public Vertex next() {
                        Vertex result = queue.remove();
                        for (Vertex adjacentVertex : Graph.this.adjacent(result)) {
                            if (!visited[adjacentVertex.getNumber()]) {
                                visited[adjacentVertex.getNumber()] = true;
                                queue.add(adjacentVertex);
                            }
                        }
                        return result;
                    }
                };
            }
        };
    }
}
