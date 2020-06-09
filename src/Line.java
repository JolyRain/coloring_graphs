//import java.awt.geom.Line2D;
//import java.awt.geom.Point2D;
//import java.awt.geom.Rectangle2D;
//
//public class Line extends Line2D.Double {
//    private double startX;
//    private double startY;
//    private double endX;
//    private double endY;
//
//    public Line(double startX, double startY, double endX, double endY) {
//        this.startX = startX;
//        this.startY = startY;
//        this.endX = endX;
//        this.endY = endY;
//    }
//
//    public void setStartX(double startX) {
//        this.startX = startX;
//    }
//
//    public void setStartY(double startY) {
//        this.startY = startY;
//    }
//
//    public void setEndX(double endX) {
//        this.endX = endX;
//    }
//
//    public void setEndY(double endY) {
//        this.endY = endY;
//    }
//
//    @Override
//    public double getX1() {
//        return startX;
//    }
//
//    @Override
//    public double getY1() {
//        return startY;
//    }
//
//    @Override
//    public Point2D getP1() {
//        throw new UnsupportedOperationException("Not supported");
//    }
//
//    @Override
//    public double getX2() {
//        return endX;
//    }
//
//    @Override
//    public double getY2() {
//        return endY;
//    }
//
//    @Override
//    public Point2D getP2() {
//        throw new UnsupportedOperationException("Not supported");
//    }
//
//    @Override
//    public void setLine(double x1, double y1, double x2, double y2) {
//        this.startX = x1;
//        this.startY = y1;
//        this.endX = x2;
//        this.endY = y2;
//    }
//
//    @Override
//    public Rectangle2D getBounds2D() {
//        throw new UnsupportedOperationException("Not supported");
//    }
//}
