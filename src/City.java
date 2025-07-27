import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;

public class City {

    private String name; // Nombre único de la ciudad
    private HashMap<String, Double> distances; // Distancias hacia otras ciudades

    // Constructor para inicializar la ciudad con un nombre
    public City(String name) {
        this.name = name;
        this.distances = new HashMap<>();
    }

    // Agrega o actualiza la distancia hacia otra ciudad
    public void addDistance(String destinationName, double distance) {
        distances.put(destinationName, distance);
    }

    // Obtiene la distancia a otra ciudad
    public double distanceTo(City destination) {
        if (!distances.containsKey(destination.name)) {
            throw new IllegalArgumentException("No se encontró una distancia entre " + this.name + " y " + destination.name);
        }
        return distances.get(destination.name);
    }

    // Getter para el nombre de la ciudad
    public String getName() {
        return name;
    }

    // Sobrescribe toString para facilitar la impresión
    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                '}';
    }

    // Método para leer ciudades desde un archivo .atsp
    public static List<City> citiesReader(String filePath) {
        List<City> cities = new ArrayList<>();
        boolean readingMatrix = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int cityIndex = 0;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("DIMENSION:")) {
                    int dimension = Integer.parseInt(line.split(":")[1].trim());
                    for (int i = 0; i < dimension; i++) {
                        cities.add(new City("City" + (i + 1)));
                    }
                }

                if (line.startsWith("EDGE_WEIGHT_SECTION")) {
                    readingMatrix = true;
                    continue;
                }

                if (readingMatrix) {
                    if (line.equals("EOF")) {
                        break;
                    }

                    String[] distances = line.split("\\s+");
                    City currentCity = cities.get(cityIndex);

                    for (int i = 0; i < distances.length; i++) {
                        double distance = Double.parseDouble(distances[i]);
                        if (distance != 9999) { // Ignorar valores de "distancia infinita"
                            currentCity.addDistance(cities.get(i).getName(), distance);
                        }
                    }
                    cityIndex++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return cities;
    }

    // Método Steady-State para selección de sobrevivientes
    public static List<Individual> steadyStateSelection(List<Individual> population, List<Individual> offspring, int populationSize) {
        // Combinar la población actual con los descendientes
        List<Individual> combined = new ArrayList<>(population);
        combined.addAll(offspring);

        // Ordenar por aptitud (suponiendo que menor valor es mejor)
        combined.sort(Comparator.comparingDouble(Individual::getFitness));

        // Retener los mejores individuos hasta el tamaño original de la población
        return new ArrayList<>(combined.subList(0, populationSize));
    }
}

// Clase para representar un individuo en el algoritmo genético
class Individual {
    private double fitness;

    public Individual(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}
