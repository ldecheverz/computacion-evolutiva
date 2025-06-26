import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: java Main <ruta_archivo.atsp>");
            return;
        }

        String filePath = args[0];

        try {
            TSPInstance instance = new TSPInstance(filePath);

            int[][] matrix = instance.getCostMatrix();
            int dimension = instance.getDimension();

            System.out.println("Matriz de costos (" + dimension + "x" + dimension + "):\n");

            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    System.out.printf("%5d", matrix[i][j]);
                }
                System.out.println();
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}