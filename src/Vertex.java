import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class Vertex {
    private int number;
    private Color color;

    public Vertex(int number) {
        this.number = number;
    }

    public Vertex(){
    }

    public void setColor(DefaultColors defaultColor) {
        switch (defaultColor) {
            case PINK:
                setColor(Color.PINK);
                break;
            case GREEN:
                setColor(Color.GREEN);
                break;
            case GRAY:
                setColor(Color.GRAY);
                break;
            case CYAN:
                setColor(Color.CYAN);
                break;
            case RED:
                setColor(Color.RED);
                break;
            case BLUE:
                setColor(Color.BLUE);
                break;
            case ORANGE:
                setColor(Color.ORANGE);
                break;
            case BROWN:
                setColor(new Color(139, 69, 19));
                break;
            case PURPLE:
                setColor(new Color(128, 0, 128));
                break;
            case MAGENTA:
                setColor(Color.MAGENTA);
                break;
        }
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Color getColor() {
        return color;
    }

    public void setRandomColor() {
        Random random = new Random();
        this.color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public void setColor(Color color) {
        if (color == null) this.color = null;
        this.color = color;
    }

    public void setNullColor() {
         this.color = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return number == vertex.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
