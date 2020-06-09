import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class Vertex {
    private int number;
    private Color color;

    public Vertex(int number) {
        this.number = number;
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

    public void setColor() {
        Random random = new Random();
        this.color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public void setColor(Color color) {
        this.color = color;
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
