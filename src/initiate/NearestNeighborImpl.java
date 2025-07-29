package initiate;

import dtos.City;
import dtos.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NearestNeighborImpl implements Initiatior{
    @Override
    public List<Path> generatePopulation(List<City> cities, int populationSize) {
        List<Path> population = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < populationSize; i++) {
            List<City> unvisited = new ArrayList<>(cities);
            List<City> route = new ArrayList<>();

            City current = unvisited.remove(rand.nextInt(unvisited.size()));
            route.add(current);

            while (!unvisited.isEmpty()) {
                City next = findNearestNeighbor(current, unvisited);
                route.add(next);
                unvisited.remove(next);
                current = next;
            }

            population.add(new Path(route));
        }

        return population;
    }

    private City findNearestNeighbor(City from, List<City> candidates) {
        City nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (City candidate : candidates) {
            double distance = from.distanceTo(candidate);
            if (distance < minDistance) {
                minDistance = distance;
                nearest = candidate;
            }
        }

        return nearest;
    }
}
