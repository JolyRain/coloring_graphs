public class Node {
    private Vertex vertex;
    private Circle circle;

    Node(Vertex vertex, Circle circle) {
        this.vertex = vertex;
        this.circle = circle;
    }

    float getNumberX() {
        return (float) (circle.getX() + Circle.RADIUS / 3.0);
    }

    float getNumberY() {
        return (float) (circle.getY() + Circle.RADIUS / 1.5);
    }

    Vertex getVertex() {
        return vertex;
    }

    Circle getCircle() {
        return circle;
    }

    @Override
    public String toString() {
        return "Node: " + vertex.toString() + " (" + circle.getX() + ", " + circle.getY() + ") ";
    }
}
