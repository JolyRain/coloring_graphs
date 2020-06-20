package graph;

import java.util.Objects;

public class Edge {
    private Vertex startVertex;
    private Vertex endVertex;

    public Edge(Vertex startVertex, Vertex endVertex) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
    }

    public Vertex getStartVertex() {
        return startVertex;
    }

    public Vertex getEndVertex() {
        return endVertex;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Edge edge = (Edge) object;
        return (startVertex.equals(edge.startVertex) && endVertex.equals(edge.endVertex)) ||
                (startVertex.equals(edge.endVertex) && endVertex.equals(edge.startVertex));
    }

    @Override
    public int hashCode() {
        return Objects.hash(startVertex, endVertex);
    }

    @Override
    public String toString() {
        return "<" + startVertex + ", " + endVertex + ">";
    }
}
