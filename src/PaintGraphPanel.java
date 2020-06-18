import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;

public class PaintGraphPanel extends JPanel {
    private static final Font FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 15);
    private static final int STROKE_WIDTH = 5;
    private static final int LINE_WIDTH = 35;
    private static final int LINE_HEIGHT = 5;
    private static final int LABEL_CHROMATIC_NUMBER_X = 10;
    private static final int LABEL_CHROMATIC_NUMBER_Y = 660;
    private static final int ZERO = 0;

    private List<Node> nodes = new LinkedList<>();
    private List<Line> lines = new LinkedList<>();
    private CreatingVertexMode creatingVertexMode = new CreatingVertexMode();
    private DeletingMode deletingMode = new DeletingMode();
    private ConnectingVertexMode connectingVertexMode = new ConnectingVertexMode();
    private Graph graph = new Graph();

    PaintGraphPanel() {
        setCreatingMode();
    }

    public void paint(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        setPaintSettings(graphics2D);
        paintLines(graphics2D);
        paintVertices(graphics2D);
        graphics2D.drawString("Chromatic number: " + graph.getChromaticNumber(),
                LABEL_CHROMATIC_NUMBER_X, LABEL_CHROMATIC_NUMBER_Y);
    }

    private void setPaintSettings(Graphics2D graphics2D) {
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setStroke(new BasicStroke(STROKE_WIDTH));
        graphics2D.setFont(FONT);
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(ZERO, ZERO, this.getWidth(), this.getHeight());
        graphics2D.setColor(Color.BLACK);
    }

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

    private void paintLines(Graphics2D graphics2D) {
        for (Line line : lines) {
            graphics2D.draw(line.getLine());
        }
    }

    private void paintVertices(Graphics2D graphics2D) {
        Circle circle;
        Vertex vertex;
        for (Node node : nodes) {
            circle = node.getCircle();
            vertex = node.getVertex();
            if (node.equals(connectingVertexMode.startNode)) graphics2D.setColor(Color.RED);
            else graphics2D.setColor(Color.BLACK);
            graphics2D.draw(circle);
            if (vertex.getColor() != null) graphics2D.setColor(vertex.getColor());
            else graphics2D.setColor(Color.WHITE);
            graphics2D.fill(node.getCircle());
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString(String.valueOf(vertex.getNumber()), node.getNumberX(), node.getNumberY());
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
        Node deletingNode = null;
        Line deletingLine = null;
        boolean vertexClicked = false;
        List<Line> deletingLines = new LinkedList<>();

        @Override
        public void mouseClicked(MouseEvent event) {
            for (Node node : nodes) {
                if (node.getCircle().contains(event.getX(), event.getY())) {
                    deletingNode = node;
                    vertexClicked = true;
                }
            }
            for (Line line : lines) {
                if (vertexClicked) {
                    if (containsLine(deletingNode, line)) deletingLines.add(line);
                } else if (line.getLine().intersects(event.getX(), event.getY(), LINE_WIDTH, LINE_HEIGHT))
                    deletingLine = line;
            }
            if (vertexClicked) removeNode();
            else if (deletingLine != null) removeLine();
            repaint();
            clear();
        }

        private void clear() {
            deletingNode = null;
            deletingLine = null;
            deletingLines.clear();
            vertexClicked = false;
        }

        private void removeNode() {
            graph.removeVertex(deletingNode.getVertex());
            nodes.remove(deletingNode);
            deleteLines(deletingLines);
        }

        private void removeLine() {
            graph.removeEdge(deletingLine.getEdge().getStartVertex(), deletingLine.getEdge().getEndVertex());
            lines.remove(deletingLine);
        }

        private void deleteLines(List<Line> deletingLines) {
            for (Line line : deletingLines) {
                lines.remove(line);
            }
        }

        private boolean containsLine(Node node, Line line) {
            return node.getCircle().contains(line.getLine().getX1(), line.getLine().getY1())
                    || node.getCircle().contains(line.getLine().getX2(), line.getLine().getY2());
        }
    }

    private class ConnectingVertexMode extends MouseAdapter {
        Node startNode;
        Line newLine;
        boolean vertexClicked = false;

        @Override
        public void mouseClicked(MouseEvent e) {
            for (Node node : nodes) {
                if (!vertexClicked) {
                    if (node.getCircle().contains(e.getX(), e.getY())) {
                        startNode = node;
                        vertexClicked = true;
                        break;
                    }
                } else if (startNode.getCircle().contains(e.getX(), e.getY())) {
                    clear();
                    break;
                } else if (node.getCircle().contains(e.getX(), e.getY())) {
                    setNewLine(node);
                    lines.add(newLine);
                    graph.addEdge(startNode.getVertex(), node.getVertex());
                    clear();
                    break;
                }
            }
            repaint();
        }

        private void setNewLine(Node node) {
            Circle circleStart = startNode.getCircle();
            Circle circleEnd = node.getCircle();
            double radius = toHalve(circleStart.getRADIUS());
            newLine = new Line(new Line2D.Double(
                    circleStart.getX() + radius,
                    circleStart.getY() + radius,
                    circleEnd.getX() + radius,
                    circleEnd.getY() + radius),
                    new Edge(startNode.getVertex(), node.getVertex()));
        }

        private double toHalve(double value) {
            UnaryOperator<Double> operator = number -> number / 2.0;
            return operator.apply(value);
        }

        private void clear() {
            startNode = null;
            newLine = null;
            vertexClicked = false;
        }
    }
}

