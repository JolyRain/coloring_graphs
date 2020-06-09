import javax.swing.*;
import java.awt.*;

class App {
    private JFrame frame;
    private JTextField heightTextFiled;
    private PaintPanel graphicPanel;

    App() {
        createFrame();
        initElements();
    }

    private void createFrame() {
        frame = new JFrame("Моё страдание");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
    }

    void show() {
        frame.setVisible(true);
    }

    private void initElements() {
        JPanel leftPanel = createLeftPanel();
        leftPanel.setBounds(0, 0, 100, 250);
        graphicPanel = new PaintPanel();
//        JScrollPane scrollPanel = new JScrollPane(graphicPanel,
//                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        graphicPanel.setBounds(250, 0, frame.getWidth(), frame.getHeight());
        frame.add(graphicPanel);
        frame.add(leftPanel);

    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(null);

        JLabel title = new JLabel("Раскраска дерева");
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        title.setFont(font);
        panel.add(title);

        panel.add(Box.createVerticalStrut(40));

        JLabel inputLabel = new JLabel("Введите высоту дерева");
        inputLabel.setFont(font);
        panel.add(inputLabel);

        panel.add(Box.createVerticalStrut(10));

        heightTextFiled = new JTextField();
        heightTextFiled.setMaximumSize(new Dimension(panel.getWidth(), 30));
        heightTextFiled.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 20));
        panel.add(heightTextFiled);

        panel.add(Box.createVerticalStrut(10));

        JCheckBox randomCheck = new JCheckBox("Рандомное число потомков");
        randomCheck.setFont(new Font(null, Font.BOLD, 15));
        panel.add(randomCheck);

        panel.add(Box.createVerticalStrut(10));

        JButton buttonPaint = new JButton("Нарисовать");
        buttonPaint.setMaximumSize(new Dimension(400, 50));
        buttonPaint.setFont(font);
        panel.add(buttonPaint);
        buttonPaint.addActionListener(e -> {

        });
        panel.add(Box.createVerticalGlue());
        return panel;
    }
}