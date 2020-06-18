import java.awt.geom.Line2D;

public class Line {
    private Line2D line;
    private Edge edge;

    Line(Line2D line, Edge edge) {
        this.line = line;
        this.edge = edge;
    }

    Line2D getLine() {
        return line;
    }

    Edge getEdge() {
        return edge;
    }

    @Override
    public String toString() {
        return "Line: " + edge;
    }
}