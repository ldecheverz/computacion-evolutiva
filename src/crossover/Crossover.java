package crossover;

import dtos.Path;

public interface Crossover {
    Path cross(Path parent1, Path parent2);
}
