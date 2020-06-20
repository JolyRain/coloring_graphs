package frame;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Circle extends Ellipse2D.Double {
    static final double RADIUS = 40.0;
    private double x;
    private double y;

    Circle(double x, double y) {
        this.x = x;
        this.y = y;
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
        return RADIUS;
    }

    @Override
    public double getHeight() {
        return RADIUS;
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