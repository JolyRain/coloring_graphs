package graph;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class Vertex {
    private int number;
    private Color color;

    public Vertex() {
    }

    public Vertex(int number) {
        this.number = number;
    }

    void setColor(DefaultColors defaultColor) {
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

    void setNumber(int number) {
        this.number = number;
    }

    public Color getColor() {
        return color;
    }

    void setRandomColor() {
        Random random = new Random();
        this.color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    void setColor(Color color) {
        if (color == null) this.color = null;
        this.color = color;
    }

    void setNullColor() {
        this.color = null;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Vertex vertex = (Vertex) object;
        return number == vertex.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "{" + number + "}";
    }
}
