package initiate;

import dtos.City;
import dtos.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InitiatorRandomImpl implements Initiatior{

    @Override
    public List<Path> generatePopulation(List<City> cities, int populationSize) {
        List<Path> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            List<City> shuffled = new ArrayList<>(cities);
            Collections.shuffle(shuffled);
            population.add(new Path(shuffled));
        }
        return population;
    }
}
