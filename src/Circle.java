import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Circle extends Ellipse2D.Double {
    private final double RADIUS = 40.0;
    private double x;
    private double y;

    Circle(double x, double y) {
        this.x = x - RADIUS / 2;
        this.y = y - RADIUS / 2;
    }

    double getRADIUS() {
        return RADIUS;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getWidth() {
        return this.RADIUS;
    }

    @Override
    public double getHeight() {
        return this.RADIUS;
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void setFrame(double x, double y, double w, double h) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public Rectangle2D getBounds2D() {
        throw new UnsupportedOperationException("Not supported");
    }
}