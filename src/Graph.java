import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Интерфейс для описания неориентированного графа (н-графа)
 * с реализацией некоторых методов графа
 */
public interface Graph {
    /**
     * Кол-во вершин в графе
     * @return
     */
    int vertexCount();

    /**
     * Кол-во ребер в графе
     * @return
     */
    int edgeCount();

    /**
     * Добавление ребра между вершинами с номерами v1 и v2
     * @param firstVertex
     * @param secondVertex
     */
    void addEdge(int firstVertex, int secondVertex);

    /**
     * Удаление ребра/ребер между вершинами с номерами v1 и v2
     * @param firstVertex
     * @param secondVertex
     */
    void removeEdge(int firstVertex, int secondVertex);

    /**
     * @param vertex Номер вершины, смежные с которой необходимо найти
     * @return Объект, поддерживающий итерацию по номерам связанных с v вершин
     */
    Iterable<Integer> adjacent(int vertex);

    /**
     * Проверка смежности двух вершин
     * @param firstVertex
     * @param secondVertex
     * @return
     */
    default boolean isAdjacent(int firstVertex, int secondVertex) {
        for (Integer adjacentVertex : adjacent(firstVertex)) {
            if (adjacentVertex == secondVertex) {
                return true;
            }
        }
        return false;
    }

    /**
     * Поиск в глубину в виде итератора, который проходит по связным вершинам
     * (начальная вершина также включена)
     * @param vertex Вершина, с которой начинается поиск
     * @return Итератор
     */
    default Iterable<Integer> depthFirstSearch(int vertex) {
        return new Iterable<>() {
            private Stack<Integer> stack = null;
            private boolean[] visited = null;

            @Override
            public Iterator<Integer> iterator() {
                stack = new Stack<>();
                stack.push(vertex);
                visited = new boolean[Graph.this.vertexCount()];
                visited[vertex] = true;

                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return !stack.isEmpty();
                    }

                    @Override
                    public Integer next() {
                        Integer result = stack.pop();
                        for (Integer adjacentVertex : Graph.this.adjacent(result)) {
                            if (!visited[adjacentVertex]) {
                                visited[adjacentVertex] = true;
                                stack.add(adjacentVertex);
                            }
                        }
                        return result;
                    }
                };
            }
        };
    }

    /**
     * Поиск в ширину в виде итератора, который проходит по связным вершинам
     * (начальная вершина также включена)
     * @param v Вершина, с которой начинается поиск
     * @return Итератор
     */
    default Iterable<Integer> breadthFirstSearch(int v) {
        return new Iterable<Integer>() {
            private Queue<Integer> queue = null;
            private boolean[] visited = null;

            @Override
            public Iterator<Integer> iterator() {
                queue = new LinkedList<Integer>();
                queue.add(v);
                visited = new boolean[Graph.this.vertexCount()];
                visited[v] = true;

                return new Iterator<Integer>() {
                    @Override
                    public boolean hasNext() {
                        return ! queue.isEmpty();
                    }

                    @Override
                    public Integer next() {
                        Integer result = queue.poll();
                        for (Integer adj : Graph.this.adjacent(result)) {
                            if (!visited[adj]) {
                                visited[adj] = true;
                                queue.add(adj);
                            }
                        }
                        return result;
                    }
                };
            }
        };
    }

    /**
     * Получение dot-описяния графа (для GraphViz)
     * @return
     */
    default String toDot() {
        StringBuilder sb = new StringBuilder();
        String nl = System.getProperty("line.separator");
        sb.append("strict graph").append(" {").append(nl);
        for (int v1 = 0; v1 < vertexCount(); v1++) {
            int count = 0;
            for (Integer v2 : this.adjacent(v1)) {
                sb.append(String.format("  %d %s %d", v1, ("--"), v2)).append(nl);
                count++;
            }
            if (count == 0) {
                sb.append(v1).append(nl);
            }
        }
        sb.append("}").append(nl);

        return sb.toString();
    }
}
