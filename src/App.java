import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class App {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int ZERO = 0;
    private static final int LEFT_PANEL_WIDTH = 250;
    private static final Font FONT_BUTTON = new Font(Font.SANS_SERIF, Font.BOLD, 18);
    private JFrame frame;
    private PaintGraphPanel graphicPanel;
    private JPanel leftPanel;

    App()  {
        createFrame();
        initElements();
    }

    private void createFrame() {
        frame = new JFrame("Coloring graph");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
    }

    void show() {
        frame.setVisible(true);
    }

    private void initElements() {
        createLeftPanel();
        leftPanel.setBounds(ZERO, ZERO, LEFT_PANEL_WIDTH, frame.getHeight());
        graphicPanel = new PaintGraphPanel();
        graphicPanel.setLayout(null);
        graphicPanel.setBounds(LEFT_PANEL_WIDTH, ZERO, WIDTH - LEFT_PANEL_WIDTH, HEIGHT);
        frame.add(graphicPanel);
        frame.add(leftPanel);
    }

    private void createLeftPanel() {
        leftPanel = new JPanel();
        JRadioButton creatingButton = new JRadioButton("Create vertex", true);
        creatingButton.setFont(FONT_BUTTON);
        creatingButton.addActionListener(e -> graphicPanel.setCreatingMode());
        leftPanel.add(creatingButton);

        JRadioButton connectingButton = new JRadioButton("Connect vertex", false);
        connectingButton.setFont(FONT_BUTTON);
        connectingButton.addActionListener(e -> graphicPanel.setModeConnecting());
        leftPanel.add(connectingButton);

        JRadioButton deletingButton = new JRadioButton("Deleting mode", false);
        deletingButton.setFont(FONT_BUTTON);
        deletingButton.addActionListener(e -> graphicPanel.setDeletingMode());
        leftPanel.add(deletingButton);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(creatingButton);
        buttonGroup.add(connectingButton);
        buttonGroup.add(deletingButton);

        JButton colorizeButton = new JButton("Colorize");
        colorizeButton.setFont(FONT_BUTTON);
        colorizeButton.addActionListener(e -> graphicPanel.colorize());
        leftPanel.add(colorizeButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(FONT_BUTTON);
        clearButton.addActionListener(e -> graphicPanel.clear());
        leftPanel.add(clearButton);

        JButton saveButton = new JButton("Save image");
        saveButton.setFont(FONT_BUTTON);
        saveButton.addActionListener(e -> saveImage());
        leftPanel.add(saveButton);
    }

    private void saveImage() {
        FileFilter filter = new FileNameExtensionFilter("Image", "png");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("./src/images"));
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            saveImageGraph(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void saveImageGraph(String fileName) {
        BufferedImage imageGraph = (BufferedImage)
                graphicPanel.createImage(graphicPanel.getWidth(), graphicPanel.getHeight());
        Graphics2D graphics2D = imageGraph.createGraphics();
        graphicPanel.paint(graphics2D);
        graphics2D.dispose();
        try {
            ImageIO.write(imageGraph, "png", new File(checkFileName(fileName)));
        } catch (IOException io) {
            JOptionPane.showMessageDialog(null, "Error!!!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String checkFileName(String fileName) {
        if (!fileName.endsWith(".png")) {
            fileName += ".png";
        }
        return fileName;
    }
}
