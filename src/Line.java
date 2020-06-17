import java.awt.geom.Line2D;

public class Line {
    private Line2D line;
    private Edge edge;

    public Line(Line2D line, Edge edge) {
        this.line = line;
        this.edge = edge;
    }

    public Line2D getLine() {
        return line;
    }

    public Edge getEdge() {
        return edge;
    }

    @Override
    public String toString() {
        return "Line: " + edge;
    }
}
