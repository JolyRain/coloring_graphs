import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class App {
    private JFrame frame;
    private PaintPanel graphicPanel;

    App() throws ClassNotFoundException, UnsupportedLookAndFeelException,
            InstantiationException, IllegalAccessException {
        createFrame();
        initElements();
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }

    private void createFrame() {
        frame = new JFrame("Coloring graph");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(true);
    }

    void show() {
        frame.setVisible(true);
    }

    private void initElements() {
        JPanel leftPanel = createLeftPanel();
        leftPanel.setBounds(0, 0, 250, frame.getHeight());
        graphicPanel = new PaintPanel();
        graphicPanel.setLayout(null);
        graphicPanel.setBounds(250, 0, frame.getWidth() - 250, frame.getHeight() - 30);
        frame.add(graphicPanel);
        frame.add(leftPanel);
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        Font fontButton = new Font(Font.SANS_SERIF, Font.BOLD, 18);

        JRadioButton creatingButton = new JRadioButton("Create vertex", true);
        creatingButton.setFont(fontButton);
        creatingButton.addActionListener(e -> graphicPanel.setCreatingMode());
        panel.add(creatingButton);

        JRadioButton connectingButton = new JRadioButton("Connect vertex", false);
        connectingButton.setFont(fontButton);
        connectingButton.addActionListener(e -> graphicPanel.setModeConnecting());
        panel.add(connectingButton);

        JRadioButton deletingButton = new JRadioButton("Deleting mode", false);
        deletingButton.setFont(fontButton);
        deletingButton.addActionListener(e -> graphicPanel.setDeletingMode());
        panel.add(deletingButton);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(creatingButton);
        buttonGroup.add(connectingButton);
        buttonGroup.add(deletingButton);

        JButton colorizeButton = new JButton("Colorize");
        colorizeButton.setFont(fontButton);
        colorizeButton.addActionListener(e -> graphicPanel.colorize());
        panel.add(colorizeButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(fontButton);
        clearButton.addActionListener(e -> graphicPanel.clear());
        panel.add(clearButton);

        JButton saveButton = new JButton("Save image");
        saveButton.setFont(fontButton);
        saveButton.addActionListener(e -> saveImage());
        panel.add(saveButton);

        JButton button = new JButton("Save");
        button.setFont(fontButton);
        button.addActionListener(e -> graphicPanel.saveToFile());
        panel.add(button);

        return panel;
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
        BufferedImage image = (BufferedImage) graphicPanel.createImage(graphicPanel.getWidth(), graphicPanel.getHeight());
        Graphics2D g2 = image.createGraphics();
        graphicPanel.paint(g2);
        g2.dispose();
        try {
            ImageIO.write(image, "png", new File(checkFileName(fileName)));
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
