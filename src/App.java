import javax.swing.*;
import java.awt.*;

class App {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int ZERO = 0;
    private static final int LEFT_PANEL_WIDTH = 250;
    private static final Font FONT_BUTTON = new Font(Font.SANS_SERIF, Font.BOLD, 18);
    private JFrame frame;
    private PaintGraphPanel graphicPanel;
    private JPanel leftPanel;
    private FileManager fileManager;

    App() {
        createFrame();
        initElements();
        fileManager = new FileManager(graphicPanel);

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
        saveButton.addActionListener(e -> fileManager.saveImage());
        leftPanel.add(saveButton);

        JButton saveFileButton = new JButton("Save file");
        saveFileButton.setFont(FONT_BUTTON);
        saveFileButton.addActionListener(e -> fileManager.saveGraphToFile());
        leftPanel.add(saveFileButton);

        JButton readFileButton = new JButton("Read file");
        readFileButton.setFont(FONT_BUTTON);
        readFileButton.addActionListener(e -> fileManager.readGraphFromFile());
        leftPanel.add(readFileButton);
    }

}
