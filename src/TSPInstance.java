import java.io.*;
import java.util.*;

public class TSPInstance {
    private int dimension;
    private int[][] costMatrix;

    public TSPInstance(String filePath) throws IOException {
        parseFile(filePath);
    }

    private void parseFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        boolean readingMatrix = false;
        List<Integer> matrixData = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("DIMENSION")) {
                dimension = Integer.parseInt(line.split(":")[1].trim());
            } else if (line.equals("EDGE_WEIGHT_SECTION")) {
                readingMatrix = true;
            } else if (line.equals("EOF")) {
                break;
            } else if (readingMatrix) {
                String[] tokens = line.split("\\s+");
                for (String token : tokens) {
                    if (!token.isEmpty()) {
                        matrixData.add(Integer.parseInt(token));
                    }
                }
            }
        }

        reader.close();

        // Convertir lista a matriz
        costMatrix = new int[dimension][dimension];
        Iterator<Integer> it = matrixData.iterator();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (it.hasNext()) {
                    costMatrix[i][j] = it.next();
                }
            }
        }
    }

    public int getDimension() {
        return dimension;
    }

    public int[][] getCostMatrix() {
        return costMatrix;
    }

    public int getDistance(int from, int to) {
        return costMatrix[from][to];
    }
}

