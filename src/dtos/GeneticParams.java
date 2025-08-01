package dtos;

public class GeneticParams {
    private final int populationSize;
    private final int generationSize;
    private final double mutationRate;
    private final double crossoverRate;

    public GeneticParams(int populationSize, int generationSize, double mutationRate, double crossoverRate) {
        this.populationSize = populationSize;
        this.generationSize = generationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getGenerationSize() {
        return generationSize;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public double getCrossoverRate() {
        return crossoverRate;
    }
}
