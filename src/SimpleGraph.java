import java.util.*;

/**
 * Реализация графа на основе списков смежности
 */
public class SimpleGraph implements Graph {
    private List<List<Vertex>> adjacentVertexList = new ArrayList<>();
    private ArrayList<Vertex> vertices = new ArrayList<>();
    private int vertexCount = 0;
    private int edgeCount = 0;
    private int chromaticNumber = 0;

    private void colorize() {
        Stack<Vertex> coloredVertex = new Stack<>();
        this.dfsRecursionImpl(vertices.get(0), vertex -> {
            countChromaticNumber(coloredVertex, vertex);
            coloredVertex.push(vertex);
        });
    }

    private boolean isColorAdjacentVertex(Vertex otherColoredVertex) {
        for (Vertex currentVertex : adjacent(otherColoredVertex)) {
            if (currentVertex.getColor().equals(otherColoredVertex.getColor())) {
                return true;
            }
        }
        return false;
    }

    private void countChromaticNumber(Stack<Vertex> coloredVertices, Vertex vertex) {
        int bufferChromaticNumber = 0;
        if (!coloredVertices.isEmpty()) {
            for (Vertex currentVertex : coloredVertices) {
                if (isAdjacent(currentVertex, vertex)) {
                    bufferChromaticNumber++;
                } else {
                    vertex.setColor(currentVertex.getColor());
                    if (!isColorAdjacentVertex(vertex)) break;
                }
            }
        } else bufferChromaticNumber++;
        if (bufferChromaticNumber >= chromaticNumber) {
            vertex.setColor();
            chromaticNumber++;
        }
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

    @Override
    public void addEdge(Vertex startVertex, Vertex endVertex) {
        int maxVertex = Math.max(startVertex.getNumber(), endVertex.getNumber());
        // добавляем вершин в список списков связности
        for (; vertexCount <= maxVertex; vertexCount++) {
            adjacentVertexList.add(null);
        }
        if (!isAdjacent(startVertex, endVertex)) {
            if (adjacentVertexList.get(startVertex.getNumber()) == null) {
                adjacentVertexList.set(startVertex.getNumber(), new LinkedList<>());
            }
            adjacentVertexList.get(startVertex.getNumber()).add(endVertex);
            edgeCount++;
        }
        if (!vertices.contains(startVertex)) {
            vertices.add(startVertex);
        }
        if (!vertices.contains(endVertex)) {
            vertices.add(endVertex);
        }
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

    @Override
    public Iterable<Vertex> adjacent(Vertex vertex) {
        return adjacentVertexList.get(vertex.getNumber()) == null ? nullIterable : adjacentVertexList.get(vertex.getNumber());
    }
}
