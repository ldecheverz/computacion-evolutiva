package selector;

import dtos.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SelectorByTournamentImpl implements Selector {

    @Override
    public Path selectCandidate(List<Path> population, int tournamentSize) {
        Random rand = new Random();
        List<Path> tournament = new ArrayList<>();
        for (int i = 0; i < tournamentSize; i++) {
            tournament.add(population.get(rand.nextInt(population.size())));
        }
        return Collections.min(tournament);
    }
}
