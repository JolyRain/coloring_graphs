//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Iterator;
//
///**
// * Реализация графа на основе матрицы смежности
// */
//public class MatrixGraph implements Graph {
//
//    private boolean[][] adjacencyMatrix;
//    private int vertexCount = 0;
//    private int edgeCount = 0;
//    private ArrayList<Vertex> vertices = new ArrayList<>();
//
//    /**
//     * Конструктор
//     *
//     * @param vertexCount Кол-во вершин графа (может увеличиваться при добавлении ребер)
//     */
//    public MatrixGraph(int vertexCount) {
//        adjacencyMatrix = new boolean[vertexCount][vertexCount];
//        this.vertexCount = vertexCount;
//    }
//
//    /**
//     * Конструктор без парметров
//     * (лучше не использовать, т.к. при добавлении вершин каждый раз пересоздается матрица)
//     */
//    public MatrixGraph() {
//        this(0);
//    }
//
//    @Override
//    public int vertexCount() {
//        return vertexCount;
//    }
//
//    @Override
//    public int edgeCount() {
//        return edgeCount;
//    }
//
//    @Override
//    public void addEdge(Vertex startVertex, Vertex endVertex) {
//        int maxVertex = Math.max(startVertex.getNumber(), endVertex.getNumber());
//        if (maxVertex >= vertexCount()) {
//            adjacencyMatrix = Arrays.copyOf(adjacencyMatrix, maxVertex + 1);
//            for (int i = 0; i <= maxVertex; i++) {
//                adjacencyMatrix[i] = i < vertexCount ? Arrays.copyOf(adjacencyMatrix[i], maxVertex + 1) : new boolean[maxVertex + 1];
//            }
//            vertexCount = maxVertex + 1;
//        }
//        if (!adjacencyMatrix[startVertex.getNumber()][endVertex.getNumber()]) {
//            adjacencyMatrix[startVertex.getNumber()][endVertex.getNumber()] = true;
//            edgeCount++;
//            // для наследников
//        }
//    }
//
//    @Override
//    public void removeEdge(Vertex startVertex, Vertex endVertex) {
//        if (adjacencyMatrix[startVertex.getNumber()][endVertex.getNumber()]) {
//            adjacencyMatrix[startVertex.getNumber()][endVertex.getNumber()] = false;
//            edgeCount--;
//            // для наследников
//        }
//    }
//
//    @Override
//    public Iterable<Vertex> adjacent(Vertex vertex) {
//        return new Iterable<>() {
//            Vertex nextAdjacentVertex = null;
//
//            @Override
//            public Iterator<Vertex> iterator() {
//                for (int i = 0; i < vertexCount; i++) {
//                    if (adjacencyMatrix[vertex.getNumber()][i]) {
//                        nextAdjacentVertex = i;
//                        break;
//                    }
//                }
//
//                return new Iterator<Vertex>() {
//                    @Override
//                    public boolean hasNext() {
//                        return nextAdjacentVertex != null;
//                    }
//
//                    @Override
//                    public Vertex next() {
//                        Integer resultVertex = nextAdjacentVertex;
//                        nextAdjacentVertex = null;
//                        for (int i = resultVertex + 1; i < vertexCount; i++) {
//                            if (adjacencyMatrix[vertex.getNumber()][i]) {
//                                nextAdjacentVertex = i;
//                                break;
//                            }
//                        }
//                        return resultVertex;
//                    }
//                };
//            }
//        };
//    }
//}
