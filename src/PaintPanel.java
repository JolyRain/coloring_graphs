import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class PaintPanel extends JPanel {

    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<Line> lines = new ArrayList<>();
    private CreatingVertexMode creatingVertexMode = new CreatingVertexMode();
    private DeletingMode deletingMode = new DeletingMode();
    private ConnectingVertexMode connectingVertexMode = new ConnectingVertexMode();
    private SimpleGraph graph = new SimpleGraph();

    PaintPanel() {
        setCreatingMode();
    }

//    public SimpleGraph getGraph() {
//        return graph;
//    }

    void clear() {
        graph.clear();
        nodes.clear();
        lines.clear();
        repaint();
    }

    void colorize() {
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
        paintLines(graphics2D);
        paintVertices(graphics2D);
        graphics2D.drawString("Хроматическое число графа: " + graph.getChromaticNumber(), 10, this.getHeight() - 30);
    }

    private void paintLines(Graphics2D graphics2D) {
        for (Line line : lines) {
            graphics2D.draw(line.getLine());
        }
    }

    private void paintVertices(Graphics2D graphics2D) {
        for (Node node : nodes) {
            Circle circle = node.getCircle();
            Vertex vertex = node.getVertex();
            if (node.equals(connectingVertexMode.startNode)) {
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
        removeMouseListener(connectingVertexMode);
        removeMouseListener(deletingMode);
        addMouseListener(creatingVertexMode);
    }

    void setDeletingMode() {
        removeMouseListener(connectingVertexMode);
        removeMouseListener(creatingVertexMode);
        addMouseListener(deletingMode);
    }

    void setModeConnecting() {
        removeMouseListener(deletingMode);
        removeMouseListener(creatingVertexMode);
        addMouseListener(connectingVertexMode);
    }


    private class CreatingVertexMode extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            Vertex newVertex = new Vertex();
            graph.addVertex(newVertex);
            nodes.add(new Node(newVertex, new Circle(event.getX(), event.getY())));
            repaint();
        }
    }

    private class DeletingMode extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            Node deletingNode = null;
            boolean vertexClicked = false;
            ArrayList<Line> deletingLines = new ArrayList<>();
            for (Node node : nodes) {
                if (node.getCircle().contains(event.getX(), event.getY())) {
                    deletingNode = node;
                    vertexClicked = true;
                }
            }
            for (Line line : lines) {
                if (vertexClicked) {
                    if (deletingNode.getCircle().contains(line.getLine().getX1(), line.getLine().getY1()) ||
                            deletingNode.getCircle().contains(line.getLine().getX2(), line.getLine().getY2())) {
                        deletingLines.add(line);
                    }
                } else if (line.getLine().intersects(event.getX(), event.getY(), 35, 1)) { //basicStroke * 7
                    deletingLines.add(line);
                }
            }
            //сделать нахуй
            if (vertexClicked) {
                graph.removeVertex(deletingNode.getVertex());
                nodes.remove(deletingNode);
                deleteLines(deletingLines);
            } else {
                graph.removeEdge(deletingLines.get(0).getEdge().getStartVertex(), deletingLines.get(0).getEdge().getEndVertex());
                deleteLines(deletingLines);
            }
            repaint();
        }

        private void deleteLines(ArrayList<Line> deletingLines) {
            for (Line line : deletingLines) {
                lines.remove(line);
            }
        }
    }

    private class ConnectingVertexMode extends MouseAdapter {
        Node startNode;
        Line newLine;
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
                } else if (node.getCircle().contains(e.getX(), e.getY())) {
                    newLine = new Line(new Line2D.Double(startNode.getCircle().getX() + startNode.getCircle().getRADIUS() / 2,
                            startNode.getCircle().getY() + startNode.getCircle().getRADIUS() / 2,
                            node.getCircle().getX() + node.getCircle().getRADIUS() / 2,
                            node.getCircle().getY() + node.getCircle().getRADIUS() / 2), new Edge(startNode.getVertex(), node.getVertex()));
                            lines.add(newLine);
                    graph.addEdge(startNode.getVertex(), node.getVertex());
                    startNode = null;
                    newLine = null;
                    clicked = false;
                    break;
                } else if (startNode.getCircle().contains(e.getX(), e.getY())) {
                    startNode = null;
                    newLine = null;
                    clicked = false;
                    break;
                }
            }
            repaint();
        }
    }
}
