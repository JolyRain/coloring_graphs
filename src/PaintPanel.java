import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class PaintPanel extends JPanel implements MouseListener, MouseMotionListener {

    private boolean createVertex = true;
    private boolean deleteVertex;
    private boolean connectVertex = false;
    private ArrayList<Circle> circles = new ArrayList<>();
    private ArrayList<Line> lines = new ArrayList<>();
    private double startX;
    private double startY;
    private double endX;
    private double endY;

    public PaintPanel() {
        addMouseListener(this);
        addMouseMotionListener(this);
        setFixedSize(this, this.getWidth(), this.getHeight());
    }

    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        int counter = 0;
        graphics2D.setColor(Color.BLACK);
        for (Line line : lines) {
            graphics2D.draw(line);
        }
        graphics2D.draw(new Line(startX, startY, endX, endY));
        for (Circle circle : circles) {
            graphics2D.setColor(Color.BLACK);
            graphics2D.fill(circle);
            graphics2D.setColor(Color.white);
            graphics2D.drawString(String.valueOf(counter), (float) (circle.getX() + circle.getWidth() / 3),  //убрать хардкод
                    (float) (circle.getY() + circle.getHeight() / 1.5));
            counter++;
        }

    }


    private static void setFixedSize(JComponent component, int width, int height) {
        Dimension dimension = new Dimension(width, height);
        component.setMaximumSize(dimension);
        component.setMinimumSize(dimension);
        component.setPreferredSize(dimension);
        component.setSize(dimension);
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if (circles.isEmpty()) {
            circles.add(new Circle(event.getX(), event.getY()));
            repaint();
            return;
        }
        if (event.getButton() == MouseEvent.BUTTON1) {
            circles.add(new Circle(event.getX(), event.getY()));
            repaint();
            return;
        }
        if (event.getButton() == MouseEvent.BUTTON3) {
            for (Circle circle : circles) {
                if (circle.contains(event.getX(), event.getY())) {
                    circles.remove(circle);
                    repaint();
                    return;
                }
            }
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            for (Circle circle : circles) {
                if (circle.contains(e.getX(), e.getY())) {
                    startX = circle.getX() + circle.getRADIUS() / 2;
                    startY = circle.getY() + circle.getRADIUS() / 2;
                    connectVertex = true;
                }
            }
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            for (Circle circle : circles) {
                if (circle.contains(e.getX(), e.getY()) && !circle.contains(startX, startY)) {
                    endX = circle.getX() + circle.getRADIUS() / 2;
                    endY = circle.getY() + circle.getRADIUS() / 2;
                    lines.add(new Line(startX, startY, endX, endY));
                }
            }
            repaint();
        }
        startX = startY = endX = endY = 0;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
            if (connectVertex) {
                endX = e.getX();
                endY = e.getY();
                repaint();
            }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}