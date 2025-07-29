package crossover;

import dtos.City;
import dtos.Path;

import java.util.*;

public class OrderCrossoverImpl implements Crossover{

    @Override
    public Path cross(Path parent1, Path parent2) {
        Random rand = new Random();
        int size = parent1.getCities().size();
        List<City> child = new ArrayList<>(Collections.nCopies(size, null));

        int crossoverPoint = rand.nextInt(size);

        for (int i = 0; i <= crossoverPoint; i++) {
            child.set(i, parent1.getCities().get(i));
        }

        int index1 = crossoverPoint + 1;
        for (City city : parent2.getCities()) {
            if (!child.contains(city)) {
                child.set(index1, city);
                index1++;
            }
        }

        Path childPath = new Path(child);
        childPath.calculateDistance();

        return childPath;
    }
}
