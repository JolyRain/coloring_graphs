import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileManager {
    private PaintGraphPanel graphicPanel;
    private FileFilter graphFilter = new FileNameExtensionFilter("Graph files (*.graph)", "graph");
    private FileFilter filterImage = new FileNameExtensionFilter("PNG files (*.png)", "png");
    private JFileChooser saveImageChooser = new JFileChooser();
    private JFileChooser graphSaveFileChooser = new JFileChooser();
    private JFileChooser saveFileChooser = new JFileChooser();


    public FileManager(PaintGraphPanel graphicPanel) {
        this.graphicPanel = graphicPanel;
    }

    public void saveImage() {
        saveImageChooser.setCurrentDirectory(new File("./src/images"));
        saveImageChooser.addChoosableFileFilter(filterImage);
        saveImageChooser.setAcceptAllFileFilterUsed(false);
        if (saveImageChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            saveImageGraph(saveImageChooser.getSelectedFile().getAbsolutePath());
        }
    }

    public void saveImageGraph(String fileName) {
        BufferedImage imageGraph = (BufferedImage)
                graphicPanel.createImage(graphicPanel.getWidth(), graphicPanel.getHeight());
        Graphics2D graphics2D = imageGraph.createGraphics();
        graphicPanel.paint(graphics2D);
        graphics2D.dispose();
        try {
            ImageIO.write(imageGraph, "png", new File(checkImageName(fileName)));
        } catch (IOException io) {
            JOptionPane.showMessageDialog(null, "Error!!!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String checkImageName(String imageName) {
        if (!imageName.endsWith(".png")) {
            imageName += ".png";
        }
        return imageName;
    }

    private String checkFileName(String fileName) {
        if (!fileName.endsWith(".graph")) {
            fileName += ".graph";
        }
        return fileName;
    }

    public void saveGraphToFile() {
        graphSaveFileChooser.setCurrentDirectory(new File("./src/input"));
        graphSaveFileChooser.addChoosableFileFilter(graphFilter);
        graphSaveFileChooser.setAcceptAllFileFilterUsed(false);
        graphSaveFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        if (graphSaveFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try (FileWriter writer = new FileWriter(
                    new File(checkFileName(graphSaveFileChooser.getSelectedFile().getAbsolutePath())))) {
                writer.write(graphicPanel.toString());
            } catch (Exception exp) {
                JOptionPane.showMessageDialog(null, "File not found",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void readGraphFromFile() {
        saveFileChooser.setCurrentDirectory(new File("./src/input"));
        saveFileChooser.addChoosableFileFilter(graphFilter);
        saveFileChooser.setAcceptAllFileFilterUsed(false);
        saveFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);

        if (saveFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try (Scanner scanner = new Scanner(saveFileChooser.getSelectedFile())) {
                graphicPanel.readGraphFromFile(scanner);
                graphicPanel.repaint();
            } catch (Exception exc) {
                graphicPanel.clear();
                JOptionPane.showMessageDialog(null, "Wrong file!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
