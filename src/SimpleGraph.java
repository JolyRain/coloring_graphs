import java.util.*;
import java.util.function.Consumer;

/**
 * Реализация графа на основе списков смежности
 */
public class SimpleGraph implements Graph {
    private HashMap<Vertex, List<Vertex>> adjacentVerticesMap = new HashMap<>();
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
    private void colorizer(Consumer<Vertex> visitor) {
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
        colorizer(vertex -> {
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
        adjacentVerticesMap.put(vertex, new LinkedList<>());
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

//    List<List<Vertex>> getAdjacentVerticesMap() {
//        return adjacentVerticesMap;
//    }
//
//    public void setAdjacentVerticesMap(List<List<Vertex>> adjacentVerticesMap) {
//        this.adjacentVerticesMap = adjacentVerticesMap;
//    }

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
//        for (; vertexCount <= maxVertex; vertexCount++) {
//            adjacentVerticesMap.add(null);
//        }
        if (!isAdjacent(startVertex, endVertex)) {
            adjacentVerticesMap.get(startVertex).add(endVertex);
            adjacentVerticesMap.get(endVertex).add(startVertex);
            edges.add(new Edge(startVertex, endVertex));
            edgeCount++;
        }
    }

    //исправить баги с иднексами - исправлено
    public void deleteVertex(Vertex deletingVertex) {
        vertices.remove(deletingVertex);
        for (Vertex adjacentVertex : adjacentVerticesMap.get(deletingVertex)) {
            adjacentVerticesMap.get(adjacentVertex).removeIf(currentVertex -> currentVertex.equals(deletingVertex));
        }
        edges.removeIf(deletingEdge -> deletingEdge.getStartVertex().equals(deletingVertex) || deletingEdge.getEndVertex().equals(deletingVertex));
        adjacentVerticesMap.remove(deletingVertex);
        recountIndex(deletingVertex);
        vertexCount--;
    }

    private void recountIndex(Vertex deletingVertex){
        for (Vertex currentVertex : vertices) {
            if (deletingVertex.getNumber() < currentVertex.getNumber()) {
                currentVertex.setNumber(currentVertex.getNumber() - 1);
            }
        }
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
        edgeCount -= countingRemove(adjacentVerticesMap.get(startVertex.getNumber()), endVertex);
    }

    void clear() {
        if (adjacentVerticesMap != null) adjacentVerticesMap.clear();
        if (defaultColors != null) defaultColors.clear();
        if (vertices != null) vertices.clear();
        vertexCount = 0;
        edgeCount = 0;
        chromaticNumber = 0;
    }

    @Override
    public Iterable<Vertex> adjacent(Vertex vertex) {
        return adjacentVerticesMap.get(vertex) == null ? nullIterable : adjacentVerticesMap.get(vertex);
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
