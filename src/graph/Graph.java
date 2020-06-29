package graph;

import java.util.*;

public class Graph {
    private Map<Vertex, List<Vertex>> adjacencyMap = new TreeMap<>(Comparator.comparingInt(Vertex::getNumber));
    private List<Vertex> vertices = new LinkedList<>();
    private List<Edge> edges = new LinkedList<>();
    private Queue<DefaultColors> defaultColors;
    private int chromaticNumber = 0;

    public void colorize() {
        if (vertices.isEmpty()) return;
        defaultColors = toQueue(DefaultColors.values());
        clearColors();
        chromaticNumber = 0;
        sortVertices();
        colorizer();
    }

    private void colorizer() {
        for (Vertex vertex : vertices) {
            if (!isColored(vertex)) colorizeVertex(vertex);
        }
    }

    private void colorizeVertex(Vertex vertex) {
        for (Vertex currentVertex : vertices) {
            if (currentVertex.equals(vertex)) continue;
            if (!isAdjacent(currentVertex, vertex)) {
                vertex.setColor(currentVertex.getColor());
                if (isColorOfAdjacentVertex(vertex)) vertex.setNullColor();
                else break;
            }
        }
        if (vertex.getColor() == null) {
            setVertexColor(vertex);
            chromaticNumber++;
        }
    }

    private void sortVertices() {
//        vertices.sort((vertex, otherVertex) ->
//                Integer.compare(adjacencyMap.get(otherVertex).size(), adjacencyMap.get(vertex).size()));
        vertices.sort(new Comparator<Vertex>() {
            @Override
            public int compare(Vertex vertex, Vertex other) {
                if (adjacencyMap.get(vertex).size() < adjacencyMap.get(other).size()) return 1;
                else if (adjacencyMap.get(vertex).size() > adjacencyMap.get(other).size()) return -1;
                else return 0;
            }
        });
    }

    private void setVertexColor(Vertex vertex) {
        if (!defaultColors.isEmpty()) vertex.setColor(defaultColors.poll());
        else vertex.setRandomColor();
    }

    private Queue<DefaultColors> toQueue(DefaultColors[] defaultColors) {
        return new LinkedList<>(Arrays.asList(defaultColors));
    }

    private void clearColors() {
        for (Vertex vertex : vertices) {
            vertex.setNullColor();
        }
    }

    private boolean isColored(Vertex vertex) {
        return vertex.getColor() != null;
    }

    private boolean isColorOfAdjacentVertex(Vertex coloredVertex) {
        if (coloredVertex.getColor() == null) return true;
        for (Vertex adjacentVertex : adjacent(coloredVertex)) {
            if (adjacentVertex.getColor() == null) continue;
            if (adjacentVertex.getColor().equals(coloredVertex.getColor())) return true;
        }
        return false;
    }

    public void addVertex(Vertex vertex) {
        vertex.setNumber(vertices.size());
        adjacencyMap.put(vertex, new LinkedList<>());
        vertices.add(vertex);
    }

    private boolean isAdjacent(Vertex firstVertex, Vertex secondVertex) {
        for (Vertex adjacent : adjacent(firstVertex)) {
            if (adjacent.equals(secondVertex)) return true;
        }
        return false;
    }

    public void addEdge(Vertex startVertex, Vertex endVertex) {
        if (!isAdjacent(startVertex, endVertex)) {
            adjacencyMap.get(startVertex).add(endVertex);
            adjacencyMap.get(endVertex).add(startVertex);
            edges.add(new Edge(startVertex, endVertex));
        }
    }

    public void removeVertex(Vertex deletingVertex) {
        vertices.remove(deletingVertex);
        for (Vertex adjacentVertex : adjacencyMap.get(deletingVertex)) {
            List<Vertex> adjacentVertices = adjacencyMap.get(adjacentVertex);
            adjacentVertices.removeIf(currentVertex -> currentVertex.equals(deletingVertex));
        }
        edges.removeIf(deletingEdge -> deletingEdge.getStartVertex().equals(deletingVertex)
                || deletingEdge.getEndVertex().equals(deletingVertex));
        adjacencyMap.remove(deletingVertex);
        recountIndex(deletingVertex);
    }

    private void recountIndex(Vertex deletingVertex) {
        for (Vertex currentVertex : vertices) {
            if (deletingVertex.getNumber() < currentVertex.getNumber()) {
                currentVertex.setNumber(currentVertex.getNumber() - 1);
            }
        }
    }

    public void removeEdge(Vertex startVertex, Vertex endVertex) {
        Edge deletingEdge = new Edge(startVertex, endVertex);
        edges.removeIf(edge -> edge.equals(deletingEdge));
        adjacencyMap.get(startVertex).removeIf(adjacentVertex -> adjacentVertex.equals(endVertex));
        adjacencyMap.get(endVertex).removeIf(adjacentVertex -> adjacentVertex.equals(startVertex));
    }

    public void clear() {
        if (adjacencyMap != null) adjacencyMap.clear();
        if (defaultColors != null) defaultColors.clear();
        if (vertices != null) vertices.clear();
        if (edges != null) edges.clear();
        chromaticNumber = 0;
    }

    private Iterable<Vertex> adjacent(Vertex vertex) {
        return adjacencyMap.get(vertex);
    }

    public int getChromaticNumber() {
        return chromaticNumber;
    }

    public Map<Vertex, List<Vertex>> getAdjacencyMap() {
        return adjacencyMap;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }
}
