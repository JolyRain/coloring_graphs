import java.util.*;
import java.util.function.Consumer;

class Graph {
    private TreeMap<Vertex, List<Vertex>> adjacentVerticesMap = new TreeMap<>(Comparator.comparingInt(Vertex::getNumber));
    private List<Vertex> vertices = new LinkedList<>();
    private List<Edge> edges = new LinkedList<>();
    private Queue<DefaultColors> defaultColors;
    private int chromaticNumber = 0;

    private void colorizer(Consumer<Vertex> visitor) {
        for (Vertex currentVertex : vertices) {
            visitor.accept(currentVertex);
        }
    }

    void colorize() {
        if (vertices.isEmpty()) return;
        defaultColors = toQueue(DefaultColors.values());
        clearColor();
        chromaticNumber = 0;
        colorizer(vertex -> {
            if (!isColored(vertex)) colorizeVertex(vertex);
        });
    }

    private void colorizeVertex(Vertex vertex) {
        int bufferChromaticNumber = 0;
        for (Vertex currentVertex : vertices) {
            if (currentVertex.equals(vertex)) continue;
            if (isAdjacent(currentVertex, vertex)) bufferChromaticNumber++;
            else {
                vertex.setColor(currentVertex.getColor());
                if (!isColorOfAdjacentVertex(vertex)) break;
            }
            if (bufferChromaticNumber >= chromaticNumber) {
                setVertexColor(vertex);
                chromaticNumber++;
                break;
            }
        }
    }

    private void setVertexColor(Vertex vertex) {
        if (!defaultColors.isEmpty()) vertex.setColor(defaultColors.poll());
        else vertex.setRandomColor();
    }

    private Queue<DefaultColors> toQueue(DefaultColors[] defaultColors) {
        return new LinkedList<>(Arrays.asList(defaultColors));
    }

    private void clearColor() {
        for (Vertex vertex : vertices) {
            vertex.setNullColor();
        }
    }

    private boolean isColored(Vertex vertex) {
        return vertex.getColor() != null;
    }

    private boolean isColorOfAdjacentVertex(Vertex coloredVertex) {
        for (Vertex adjacentVertex : adjacent(coloredVertex)) {
            if (adjacentVertex.getColor() == null) continue;
            if (adjacentVertex.getColor().equals(coloredVertex.getColor())) return true;
        }
        return false;
    }

    void addVertex(Vertex vertex) {
        vertex.setNumber(vertices.size());
        adjacentVerticesMap.put(vertex, new LinkedList<>());
        vertices.add(vertex);
    }

    private boolean isAdjacent(Vertex firstVertex, Vertex secondVertex) {
        for (Vertex adjacent : adjacent(firstVertex)) {
            if (adjacent.equals(secondVertex)) {
                return true;
            }
        }
        return false;
    }

    void addEdge(Vertex startVertex, Vertex endVertex) {
        if (!isAdjacent(startVertex, endVertex)) {
            adjacentVerticesMap.get(startVertex).add(endVertex);
            adjacentVerticesMap.get(endVertex).add(startVertex);
            edges.add(new Edge(startVertex, endVertex));
        }
    }

    void removeVertex(Vertex deletingVertex) {
        vertices.remove(deletingVertex);
        for (Vertex adjacentVertex : adjacentVerticesMap.get(deletingVertex)) {
            List<Vertex> adjacentVertices = adjacentVerticesMap.get(adjacentVertex);
            adjacentVertices.removeIf(currentVertex -> currentVertex.equals(deletingVertex));
        }
        edges.removeIf(deletingEdge -> deletingEdge.getStartVertex().equals(deletingVertex) ||
                deletingEdge.getEndVertex().equals(deletingVertex));
        adjacentVerticesMap.remove(deletingVertex);
        recountIndex(deletingVertex);
    }

    private void recountIndex(Vertex deletingVertex) {
        for (Vertex currentVertex : vertices) {
            if (deletingVertex.getNumber() < currentVertex.getNumber()) {
                currentVertex.setNumber(currentVertex.getNumber() - 1);
            }
        }
    }

    void removeEdge(Vertex startVertex, Vertex endVertex) {
        Edge deletingEdge = new Edge(startVertex, endVertex);
        edges.removeIf(edge -> edge.equals(deletingEdge));
        adjacentVerticesMap.get(startVertex).removeIf(adjacentVertex -> adjacentVertex.equals(endVertex));
        adjacentVerticesMap.get(endVertex).removeIf(adjacentVertex -> adjacentVertex.equals(startVertex));
    }

    void clear() {
        if (adjacentVerticesMap != null) adjacentVerticesMap.clear();
        if (defaultColors != null) defaultColors.clear();
        if (vertices != null) vertices.clear();
        chromaticNumber = 0;
    }

    private Iterable<Vertex> adjacent(Vertex vertex) {
        return adjacentVerticesMap.get(vertex);
    }

    int getChromaticNumber() {
        return chromaticNumber;
    }

    public TreeMap<Vertex, List<Vertex>> getAdjacentVerticesMap() {
        return adjacentVerticesMap;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
