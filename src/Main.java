import java.util.ArrayList;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        ArrayList<Vertex> vertices = new ArrayList<>();
        Vertex vertex = new Vertex(0);
        vertex.setColor();
        vertices.add(vertex);
        Vertex vertex1 = new Vertex(1);
        vertex1.setColor();
        System.out.println(isUsedColor(vertices, vertex1));
    }
    private static boolean isUsedColor(ArrayList<Vertex> coloredVertex, Vertex otherColoredVertex){
        for (Vertex currentVertex : coloredVertex){
            if (currentVertex.getColor().equals(otherColoredVertex.getColor())){
                return true;
            }
        }
        return false;
    }
}
