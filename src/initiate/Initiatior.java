package initiate;

import dtos.City;
import dtos.Path;

import java.util.List;

public interface Initiatior {
    List<Path> generatePopulation(List<City> cities, int populationSize);
}
