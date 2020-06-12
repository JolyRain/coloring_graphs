import java.util.*;
import java.util.function.Consumer;

/**
 * Реализация графа на основе списков смежности
 */
public class SimpleGraph implements Graph {
    private List<List<Vertex>> adjacentVertexList = new ArrayList<>();
    private ArrayList<Vertex> vertices = new ArrayList<>();
    private ArrayList<Edge> edges = new ArrayList<>();
    private int vertexCount = 0;
    private int edgeCount = 0;
    private int chromaticNumber = 0;
    private Queue<DefaultColors> defaultColors;


    /**
     * Альтернативный алгоритм
     *
     * @param visitor
     */
    private void colorist(Consumer<Vertex> visitor) {
        for (Vertex currentVertex : vertices) {
            visitor.accept(currentVertex);
        }
    }

    public void colorize() {
        if (vertices.isEmpty()) return;
//        ArrayList<Vertex> coloredVertices = new ArrayList<>();
        defaultColors = toQueue(DefaultColors.values());
        clearColor();
        chromaticNumber = 0;
        colorist(vertex -> {
            if (!isColored(vertex)) countChromaticNumber(vertex);
//                coloredVertices.add(vertex);
        });
    }

    private void countChromaticNumber(Vertex vertex) {
        int bufferChromaticNumber = 0;
        for (Vertex currentVertex : vertices) {
            if (currentVertex.equals(vertex)) continue;
            if (isAdjacent(currentVertex, vertex)) bufferChromaticNumber++;
            else {
                vertex.setColor(currentVertex.getColor());
                if (!isColorOfAdjacentVertex(vertex)) break;
            }
        }
        if (bufferChromaticNumber >= chromaticNumber) {
            colorizeVertex(vertex);
            chromaticNumber++;
        }
    }

    private void colorizeVertex(Vertex vertex) {
        if (!defaultColors.isEmpty()) {
            vertex.setColor(defaultColors.poll());
        } else vertex.setRandomColor();
    }

    /**
     * Альтернативный алгоритм
     */

    private Queue<DefaultColors> toQueue(DefaultColors[] defaultColors) {
        return new LinkedList<>(Arrays.asList(defaultColors));
    }

    private void clearColor() {
        for (Vertex vertex : vertices) {
            vertex.setNullColor();
        }
    }

    void addVertex(Vertex vertex) {
        vertex.setNumber(vertexCount);
        adjacentVertexList.add(new LinkedList<>());
        vertices.add(vertex);
        vertexCount++;
    }

//    private Vertex vertexMaxDegree() {
//        int bufferSize = 0;
//        int index = 0;
//        for (int i = 0; i < adjacentVertexList.size(); i++) {
//            if (adjacentVertexList.get(i).size() > bufferSize) {
//                bufferSize = adjacentVertexList.get(i).size();
//                index = i;
//            }
//        }
//        return vertices.get(index);
//    }

//    void colorizeShitVersion() {
//        if (vertices.isEmpty() || adjacentVertexList.isEmpty()) return;
//        ArrayList<Vertex> coloredVertices = new ArrayList<>();
//        defaultColors = toQueue(DefaultColors.values());
//        clearColor();
//        chromaticNumber = 0;
//        this.dfsRecursionImpl(vertexMaxDegree(), vertex -> {
//            if (!isColored(vertex)) {
//                countChromaticNumber(coloredVertices, vertex);
//                coloredVertices.add(vertex);
//            }
//        });
//    }

    private boolean isColored(Vertex vertex) {
        return vertex.getColor() != null;
    }

    private boolean isColorOfAdjacentVertex(Vertex otherColoredVertex) {
        for (Vertex currentVertex : adjacent(otherColoredVertex)) {
            if (currentVertex.getColor() == null) return false;
            if (currentVertex.getColor().equals(otherColoredVertex.getColor())) return true;
        }
        return false;
    }

//    private void countChromaticNumber(ArrayList<Vertex> coloredVertices, Vertex vertex) {
//        int bufferChromaticNumber = 0;
//        for (Vertex currentVertex : coloredVertices) {
//            if (currentVertex.equals(vertex)) continue;
//            if (isAdjacent(currentVertex, vertex)) {
//                bufferChromaticNumber++;
//            } else {
//                vertex.setColor(currentVertex.getColor());
//                if (!isColorOfAdjacentVertex(vertex)) break;
//            }
//        }
//        if (bufferChromaticNumber >= chromaticNumber) {
//            if (!defaultColors.isEmpty()) {
//                vertex.setColor(defaultColors.poll());
//            } else vertex.setRandomColor();
//            chromaticNumber++;
//        }
//    }

    List<List<Vertex>> getAdjacentVertexList() {
        return adjacentVertexList;
    }

    public void setAdjacentVertexList(List<List<Vertex>> adjacentVertexList) {
        this.adjacentVertexList = adjacentVertexList;
    }

    public int getChromaticNumber() {
        return chromaticNumber;
    }

    ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices = vertices;
    }

    int getVertexCount() {
        return vertexCount;
    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public void setEdgeCount(int edgeCount) {
        this.edgeCount = edgeCount;
    }

    @Override
    public void addEdge(Vertex startVertex, Vertex endVertex) {
        int maxVertex = Math.max(startVertex.getNumber(), endVertex.getNumber());
        // добавляем вершин в список списков связности
        for (; vertexCount <= maxVertex; vertexCount++) {
            adjacentVertexList.add(null);
        }
        if (!isAdjacent(startVertex, endVertex)) {
            adjacentVertexList.get(startVertex.getNumber()).add(endVertex);
            adjacentVertexList.get(endVertex.getNumber()).add(startVertex);
            edges.add(new Edge(startVertex, endVertex));
            edgeCount++;
        }
    }

    public void deleteVertex(Vertex vertex) {
        vertices.remove(vertex);
        for (Vertex adjacentVertex : adjacentVertexList.get(vertex.getNumber())) {
            adjacentVertexList.get(adjacentVertex.getNumber()).removeIf(deletingVertex -> deletingVertex.equals(vertex));
        }
        edges.removeIf(deletingEdge -> deletingEdge.getStartVertex().equals(vertex) || deletingEdge.getEndVertex().equals(vertex));
        adjacentVertexList.remove(vertex.getNumber());
    }

    private void findDeletingVertex() {

    }

    private int countingRemove(List<Vertex> adjacentVertexList, Vertex vertex) {
        int count = 0;
        if (adjacentVertexList != null) {
            for (Iterator<Vertex> iterator = adjacentVertexList.iterator(); iterator.hasNext(); ) {
                if (iterator.next().equals(vertex)) {
                    iterator.remove();
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public void removeEdge(Vertex startVertex, Vertex endVertex) {
        edgeCount -= countingRemove(adjacentVertexList.get(startVertex.getNumber()), endVertex);
    }

    void clear() {
        adjacentVertexList.clear();
        defaultColors.clear();
        defaultColors = toQueue(DefaultColors.values());
        vertices.clear();
        vertexCount = 0;
        edgeCount = 0;
        chromaticNumber = 0;
    }

    @Override
    public Iterable<Vertex> adjacent(Vertex vertex) {
        return adjacentVertexList.get(vertex.getNumber()) == null ? nullIterable : adjacentVertexList.get(vertex.getNumber());
    }

    private static Iterable<Vertex> nullIterable = () -> new Iterator<>() {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Vertex next() {
            return null;
        }
    };

    @Override
    public int vertexCount() {
        return vertexCount;
    }

    @Override
    public int edgeCount() {
        return edgeCount;
    }
}
