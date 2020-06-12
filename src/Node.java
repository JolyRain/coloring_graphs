public class Node {
    private Vertex vertex;
    private Circle circle;

    public Node(Vertex vertex, Circle circle) {
        this.vertex = vertex;
        this.circle = circle;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }
}
