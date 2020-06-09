import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class PaintPanel extends JPanel {
    private ArrayList<Circle> circles = new ArrayList<>();
    private ArrayList<Line2D> lines = new ArrayList<>();
    private ModeCreatingVertex modeCreatingVertex = new ModeCreatingVertex();
    private ModeDeleting modeDeleting = new ModeDeleting();
    private ModeConnectingVertex modeConnectingVertex = new ModeConnectingVertex();

    public PaintPanel() {
        setCreatingMode();
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setStroke(new BasicStroke(7));
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        int counter = 0;
        graphics2D.setColor(Color.BLACK);
        for (Line2D line : lines) {
            graphics2D.draw(line);
        }
        for (Circle circle : circles) {
            if (circle.equals(modeConnectingVertex.startCircle)) {
                graphics2D.setColor(Color.RED);
                graphics2D.draw(circle);
            }
            graphics2D.setColor(Color.BLACK);
            graphics2D.fill(circle);
            graphics2D.setColor(Color.white);
            graphics2D.drawString(String.valueOf(counter), (float) (circle.getX() + circle.getWidth() / 3),  //убрать хардкод
                    (float) (circle.getY() + circle.getHeight() / 1.5));
            counter++;
        }
    }

    void setCreatingMode() {
        removeMouseListener(modeConnectingVertex);
        removeMouseListener(modeDeleting);
        addMouseListener(modeCreatingVertex);
    }

    void setDeletingMode() {
        removeMouseListener(modeConnectingVertex);
        removeMouseListener(modeCreatingVertex);
        addMouseListener(modeDeleting);
    }

    void setModeConnecting() {
        removeMouseListener(modeDeleting);
        removeMouseListener(modeCreatingVertex);
        addMouseListener(modeConnectingVertex);
    }

    private class ModeCreatingVertex extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            circles.add(new Circle(event.getX(), event.getY()));
            repaint();
        }
    }

    private class ModeDeleting extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            Circle deletingCircle = null;
            ArrayList<Line2D> deletingLines = new ArrayList<>();
            for (Circle circle : circles) {
                if (circle.contains(event.getX(), event.getY())) {
                    deletingCircle = circle;
                }
            }
            for (Line2D line : lines) {
                if (deletingCircle != null) {
                    if (deletingCircle.contains(line.getX1(), line.getY1()) || deletingCircle.contains(line.getX2(), line.getY2())) {
                        deletingLines.add(line);
                    }
                } else if (line.intersects(event.getX(), event.getY(), 35, 1)) { //basicStroke * 7
                    System.out.println("yea");
                    deletingLines.add(line);
                }
            }
            deleteLines(deletingLines);
            circles.remove(deletingCircle);
            repaint();
        }

        private void deleteLines(ArrayList<Line2D> deletingLines) {
            for (Line2D line : deletingLines) {
                lines.remove(line);
            }
        }
    }

    private class ModeConnectingVertex extends MouseAdapter {
        Circle startCircle;
        boolean clicked = false;

        @Override
        public void mouseClicked(MouseEvent e) {
            for (Circle circle : circles) {
                if (!clicked) {
                    if (circle.contains(e.getX(), e.getY())) {
                        startCircle = circle;
                        clicked = true;
                        break;
                    }
                } else if (circle.contains(e.getX(), e.getY()) && !startCircle.contains(e.getX(), e.getY())) {
                    lines.add(new Line2D.Double(startCircle.getX() + startCircle.getRADIUS() / 2,
                            startCircle.getY() + startCircle.getRADIUS() / 2,
                            circle.getX() + circle.getRADIUS() / 2,
                            circle.getY() + circle.getRADIUS() / 2));
                    startCircle = null;
                    clicked = false;
                    break;
                }
            }
            repaint();
        }
    }
}
