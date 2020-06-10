import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.LinkedList;

public class PaintPanel extends JPanel {

    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<Line2D> lines = new ArrayList<>();
    private ModeCreatingVertex modeCreatingVertex = new ModeCreatingVertex();
    private ModeDeleting modeDeleting = new ModeDeleting();
    private ModeConnectingVertex modeConnectingVertex = new ModeConnectingVertex();
    private SimpleGraph graph = new SimpleGraph();

    public PaintPanel() {
        setCreatingMode();
    }

    public SimpleGraph getGraph() {
        return graph;
    }

    public void colorize() {
        graph.colorize();
        repaint();
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        graphics2D.setColor(Color.BLACK);
        for (Line2D line : lines) {
            graphics2D.draw(line);
        }
        for (Node node : nodes) {
            Circle circle = node.getCircle();
            Vertex vertex = node.getVertex();
            if (node.equals(modeConnectingVertex.startNode)) {
                graphics2D.setColor(Color.RED);
            } else graphics2D.setColor(Color.BLACK);
            graphics2D.draw(circle);
            if (vertex.getColor() != null) {
                graphics2D.setColor(vertex.getColor());
            } else graphics2D.setColor(Color.WHITE);
            graphics2D.fill(node.getCircle());
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString(String.valueOf(vertex.getNumber()), (float) (circle.getX() + circle.getRADIUS() / 3),  //убрать хардкод
                    (float) (circle.getY() + circle.getRADIUS() / 1.5));
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
            Vertex newVertex = new Vertex(graph.getVertexCount());
            graph.addVertex(newVertex);
            nodes.add(new Node(newVertex, new Circle(event.getX(), event.getY())));
            graph.getAdjacentVertexList().add(new LinkedList<>());
            repaint();
        }
    }

    private class ModeDeleting extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            Node deletingCircle = null;
            ArrayList<Line2D> deletingLines = new ArrayList<>();
            for (Node node : nodes) {
                if (node.getCircle().contains(event.getX(), event.getY())) {
                    deletingCircle = node;
                }
            }
            for (Line2D line : lines) {
                if (deletingCircle != null) {
                    if (deletingCircle.getCircle().contains(line.getX1(), line.getY1()) ||
                            deletingCircle.getCircle().contains(line.getX2(), line.getY2())) {
                        deletingLines.add(line);
                    }
                } else if (line.intersects(event.getX(), event.getY(), 35, 1)) { //basicStroke * 7
                    deletingLines.add(line);
                }
            }
            //сделать нахуй
            deleteLines(deletingLines);
            nodes.remove(deletingCircle);
            repaint();
        }

        private void deleteLines(ArrayList<Line2D> deletingLines) {
            for (Line2D line : deletingLines) {
                lines.remove(line);
            }
        }
    }

    private class ModeConnectingVertex extends MouseAdapter {
        Node startNode;
        boolean clicked = false;

        @Override
        public void mouseClicked(MouseEvent e) {
            for (Node node : nodes) {
                if (!clicked) {
                    if (node.getCircle().contains(e.getX(), e.getY())) {
                        startNode = node;
                        clicked = true;
                        break;
                    }
                } else if (node.getCircle().contains(e.getX(), e.getY()) && !startNode.getCircle().contains(e.getX(), e.getY())) {
                    lines.add(new Line2D.Double(startNode.getCircle().getX() + startNode.getCircle().getRADIUS() / 2,
                            startNode.getCircle().getY() + startNode.getCircle().getRADIUS() / 2,
                            node.getCircle().getX() + node.getCircle().getRADIUS() / 2,
                            node.getCircle().getY() + node.getCircle().getRADIUS() / 2));
                    graph.addEdge(graph.getVertices().get(startNode.getVertex().getNumber()), graph.getVertices().get(node.getVertex().getNumber()));
                    startNode = null;
                    clicked = false;
                    break;
                }
            }
            repaint();
        }
    }
}
