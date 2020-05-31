import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class VertexColor {
    private ArrayList<Color> usedColors = null;
    private Color color;
    private static final int COLOR_BOUND = 256;

    VertexColor(ArrayList<Color> usedColors) {
        this.usedColors = usedColors;
    }

    public Color getColor() {
        return color;
    }

    public void setColor() {
        this.color = createColor();
        if (isUsed(color)){
            setColor();
        }
    }

    private Color createColor() {
        Random random = new Random();
        int red = random.nextInt(COLOR_BOUND);
        int blue = random.nextInt(COLOR_BOUND);
        int green = random.nextInt(COLOR_BOUND);
        return new Color(red, blue, green);
    }

    private boolean isUsed(Color color) {
        for (Color usedColor : usedColors) {
            if (usedColor == color) return true;
        }
        return false;
    }
}