import crossover.Crossover;
import crossover.OrderCrossoverImpl;
import crossover.PMXCrossoverImpl;
import dtos.City;
import dtos.Path;
import initiate.Initiatior;
import initiate.InitiatorRandomImpl;
import initiate.NearestNeighborImpl;
import mutator.InversionImpl;
import mutator.Mutator;
import mutator.SwapMutatorImpl;
import selector.Selector;
import selector.SelectorByRankingImpl;
import selector.SelectorByTournamentImpl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TSP {

    private static int POPULATION_SIZE = 300;
    private static final int TOURNAMENT_SIZE = 2;
    private static final int GENERATION_SIZE = 1000;
    private static final double MUTATION_RATE = 0.1;
    private static final double CROSSOVER_RATE = 0.8;

    public static void main(String[] args) {
        if (args.length < 5) {
            System.err.println("Uso: <rutaArchivo> <selector> <mutator> <crossover> <initiator>");
            System.exit(1);
        }

        String filePath = args[0];
        String selectorType = args[1];
        String mutatorType = args[2];
        String crossoverType = args[3];
        String initiatorType = args[4];


        Selector selector = getSelector(selectorType);
        Mutator mutator = getMutator(mutatorType);
        Crossover crossover = getCrossover(crossoverType);
        Initiatior initiatior = getInitiator(initiatorType);

        List<City> cities = getCitiesFromFile(filePath);
        List<Path> population = initiatior.generatePopulation(cities, POPULATION_SIZE);
        Path bestRoute = Collections.min(population);
        List<Double> fitnessEvolution = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        for (int gen = 0; gen < GENERATION_SIZE; gen++) {
            List<Path> sortedPopulation = population;
            if (selector instanceof SelectorByRankingImpl) {
                sortedPopulation = new ArrayList<>(population);
                Collections.sort(sortedPopulation);
            }
                List<Path> newPopulation = new ArrayList<>();

            for (int i = 0; i < POPULATION_SIZE; i++) {
                if (Math.random() < CROSSOVER_RATE) {
                    Path parent1 = selector.selectCandidate(sortedPopulation, TOURNAMENT_SIZE);
                    Path parent2 = selector.selectCandidate(sortedPopulation, TOURNAMENT_SIZE);
                    Path child = crossover.cross(parent1, parent2);

                    if (Math.random() < MUTATION_RATE) {
                        mutator.mutate(child);
                    }

                    newPopulation.add(child);
                }
                else {
                    newPopulation.add(selector.selectCandidate(sortedPopulation, TOURNAMENT_SIZE));
                }

            }

            population = newPopulation;
            Path currentBest = Collections.min(population);
            fitnessEvolution.add(currentBest.getTotalDistance());

            if (currentBest.getTotalDistance() < bestRoute.getTotalDistance()) {
                bestRoute = currentBest;
            }

        }
        System.out.printf("Mejor distancia: %.2f%n", bestRoute.getTotalDistance());
        long endTime = System.currentTimeMillis();
        double executionTimeSeconds = (endTime - startTime) / 1000.0;
        saveResultsToFile(bestRoute, fitnessEvolution, executionTimeSeconds, selectorType, mutatorType, crossoverType, initiatorType);
    }

    private static void saveResultsToFile(Path bestRoute, List<Double> fitnessEvolution, double timeSeconds,
                                          String selectorName, String mutatorName, String crossoverName, String initiatorName) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        String fileName = "resultado_TSP_" + timestamp + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("=== Configuración del Algoritmo ===\n");
            writer.write("Población: " + POPULATION_SIZE + "\n");
            writer.write("Generaciones: " + GENERATION_SIZE + "\n");
            writer.write("Tamaño del torneo: " + TOURNAMENT_SIZE + "\n");
            writer.write("Tasa de cruce: " + CROSSOVER_RATE + "\n");
            writer.write("Tasa de mutación: " + MUTATION_RATE + "\n");
            writer.write("Inicialización: " + initiatorName + "\n");
            writer.write("Operador de cruce: " + crossoverName + "\n");
            writer.write("Mutación: " + mutatorName + "\n");
            writer.write("Selector: " + selectorName + "\n\n");

            writer.write("=== Evolución del Fitness ===\n");
            for (int i = 0; i < fitnessEvolution.size(); i++) {
                writer.write("Generación " + (i + 1) + ": " + fitnessEvolution.get(i) + "\n");
            }

            writer.write("\n=== Mejor Solución ===\n");
            writer.write("Ruta: " + bestRoute + "\n");
            writer.write("Distancia total: " + bestRoute.getTotalDistance() + "\n");

            writer.write("\nTiempo de ejecución: " + timeSeconds + " segundos\n");

            System.out.println("\nResultados guardados en: " + fileName);

        } catch (IOException e) {
            System.err.println("Error al escribir el archivo de resultados: " + e.getMessage());
        }
    }

    private static List<City> getCitiesFromFile(String filePath) {
        try {
            TSPInstance instance = new TSPInstance(filePath);
            return City.citiesReader(filePath);


        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return null;
    }

    private static Selector getSelector(String type) {
        switch (type.toLowerCase()) {
            case "ranking":
                return new SelectorByRankingImpl();
            case "tournament":
                return new SelectorByTournamentImpl();
            default:
                throw new IllegalArgumentException("Selector desconocido: " + type);
        }
    }

    private static Mutator getMutator(String type) {
        switch (type.toLowerCase()) {
            case "swap":
                return new SwapMutatorImpl();
            case "invert":
                return new InversionImpl();
            default:
                throw new IllegalArgumentException("Mutator desconocido: " + type);
        }
    }

    private static Crossover getCrossover(String type) {
        switch (type.toLowerCase()) {
            case "order":
                return new OrderCrossoverImpl();
            case "pmx":
                return new PMXCrossoverImpl();
            default:
                throw new IllegalArgumentException("Crossover desconocido: " + type);
        }
    }

    private static Initiatior getInitiator(String type) {
        switch (type.toLowerCase()) {
            case "random":
                return new InitiatorRandomImpl();
            case "heuristic":
                return new NearestNeighborImpl();
            default:
                throw new IllegalArgumentException("Initiator desconocido: " + type);
        }
    }
}