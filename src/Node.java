public class Node {
    private Vertex vertex;
    private Circle circle;

    public Node(Vertex vertex, Circle circle) {
        this.vertex = vertex;
        this.circle = circle;
    }

    public float getNumberX() {
        return (float) (circle.getX() + circle.getRADIUS() / 3.0);
    }

    public float getNumberY() {
        return (float) (circle.getY() + circle.getRADIUS() / 1.5);
    }

    public Vertex getVertex() {
        return vertex;
    }

    public Circle getCircle() {
        return circle;
    }

    @Override
    public String toString() {
        return "Node: " + vertex.toString() + " (" + circle.getX() + ", " + circle.getY() + ") ";
    }
}
