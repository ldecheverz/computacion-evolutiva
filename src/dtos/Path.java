package dtos;

import java.util.List;

public class Path implements Comparable<Path> {
    private List<City> cities;
    private double totalDistance;

    public Path(List<City> cities) {
        this.cities = cities;
        this.totalDistance = calculateDistance();
    }

    public List<City> getCities() {
        return cities;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public double calculateDistance() {
        double total = 0;
        for (int i = 0; i < cities.size() - 1; i++) {
            total += cities.get(i).distanceTo(cities.get(i + 1));
        }
        total += cities.get(cities.size() - 1).distanceTo(cities.get(0));
        totalDistance = total;
        return total;
    }

    @Override
    public String toString() {
        return cities.toString() + " (Distancia: " + totalDistance + ")";
    }

    @Override
    public int compareTo(Path other) {
        return Double.compare(this.totalDistance, other.totalDistance);
    }
}
