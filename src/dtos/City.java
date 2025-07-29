package dtos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class City {

    private String name;
    private HashMap<String, Double> distances;

    public City(String name) {
        this.name = name;
        this.distances = new HashMap<>();
    }

    public void addDistance(String destinationName, double distance) {
        distances.put(destinationName, distance);
    }

    public double distanceTo(City destination) {
        if (!distances.containsKey(destination.name)) {
            throw new IllegalArgumentException("No se encontró una distancia entre " + this.name + " y " + destination.name);
        }
        return distances.get(destination.name);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

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
                        cities.add(new City(String.valueOf(i + 1)));
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
                        if (distance != 9999) {
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
}
