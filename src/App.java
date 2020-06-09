import javax.swing.*;
import java.awt.*;

class App {
    private JFrame frame;
    private PaintPanel graphicPanel;

    App() {
        createFrame();
        initElements();
    }

    private void createFrame() {
        frame = new JFrame("Раскраска графа");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
    }

    void show() {
        frame.setVisible(true);
    }

    private void initElements() {
        JPanel panel = createLeftPanel();
        panel.setBounds(0, 0, 250, frame.getHeight());
        graphicPanel = new PaintPanel();
        graphicPanel.setLayout(null);
        JScrollPane scrollPanel = new JScrollPane(graphicPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPanel.setBounds(250, 0, frame.getWidth() - 250, frame.getHeight());
        frame.add(scrollPanel);
        frame.add(panel);
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        Font fontButton = new Font(Font.SANS_SERIF, Font.BOLD, 18);

//        JLabel title = new JLabel("Раскраска графа");
////        title.setBounds(10, 10, 100, 50);
//        title.setFont(font);
//        panel.add(title);



        JRadioButton creatingButton = new JRadioButton("Режим создания вершин", true);
//        creatingButton.setBounds(10, 100, 100, 50);
        creatingButton.setFont(fontButton);
        creatingButton.addActionListener(e -> {
            graphicPanel.setCreatingMode();
        });
        panel.add(creatingButton);
        JRadioButton connectingButton = new JRadioButton("Режим присоединения", false);
//        creatingButton.setBounds(10, 100, 100, 50);
        connectingButton.setFont(fontButton);
        connectingButton.addActionListener(e -> {
            graphicPanel.setModeConnecting();
        });
        panel.add(connectingButton);
        JRadioButton deletingButton = new JRadioButton("Режим удаления", false);
//        creatingButton.setBounds(10, 100, 100, 50);
        deletingButton.setFont(fontButton);
        deletingButton.addActionListener(e -> {
            graphicPanel.setDeletingMode();
        });
        panel.add(deletingButton);


        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(creatingButton);
        buttonGroup.add(connectingButton);
        buttonGroup.add(deletingButton);

        return panel;
    }
}
