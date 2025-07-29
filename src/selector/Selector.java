package selector;

import dtos.Path;

import java.util.List;

public interface Selector {
   Path selectCandidate(List<Path> population, int tournamentSize);
}
