package selector;

import dtos.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectorByRankingImpl implements Selector {

    @Override
    public Path selectCandidate(List<Path> sortedPopulation, int tournamentSize) {

        int size = sortedPopulation.size();

        double[] selectionProbabilities = new double[size];
        double totalRank = (size * (size + 1)) / 2.0;

        for (int i = 0; i < size; i++) {
            selectionProbabilities[i] = (size - i) / totalRank;
        }

        double randValue = Math.random();
        double cumulative = 0.0;

        for (int i = 0; i < size; i++) {
            cumulative += selectionProbabilities[i];
            if (randValue <= cumulative) {
                return sortedPopulation.get(i);
            }
        }

        return sortedPopulation.get(size - 1);
    }

}
