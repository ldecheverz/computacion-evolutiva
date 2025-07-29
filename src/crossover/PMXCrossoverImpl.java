package crossover;

import dtos.City;
import dtos.Path;
import java.util.*;

public class PMXCrossoverImpl implements Crossover {

    @Override
    public Path cross(Path parent1, Path parent2) {
        Random rand = new Random();
        int size = parent1.getCities().size();
        List<City> p1 = parent1.getCities();
        List<City> p2 = parent2.getCities();

        int point1 = rand.nextInt(size);
        int point2 = rand.nextInt(size);
        while (point1 == point2) {
            point2 = rand.nextInt(size);
        }
        int start = Math.min(point1, point2);
        int end = Math.max(point1, point2);

        List<City> child = new ArrayList<>(Collections.nCopies(size, null));

        // Copiar segmento fijo
        for (int i = start; i <= end; i++) {
            child.set(i, p2.get(i));
        }

        // Completar el resto con mapeo
        fillPMX(child, p1, p2, start, end);

        Path path = new Path(child);
        path.calculateDistance();

        return path;
    }

    private void fillPMX(List<City> child, List<City> source, List<City> target, int start, int end) {
        int size = child.size();

        for (int i = 0; i < size; i++) {
            if (i >= start && i <= end) continue; // Ya asignado

            City candidate = source.get(i);
            while (child.contains(candidate)) {
                int index = target.indexOf(candidate);
                candidate = source.get(index);
            }
            child.set(i, candidate);
        }
    }

}
