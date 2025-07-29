package mutator;

import dtos.Path;

import java.util.Collections;
import java.util.Random;

public class SwapMutatorImpl implements Mutator {

    @Override
    public void mutate(Path path) {
        Random rand = new Random();
        int size = path.getCities().size();

        int pos1 = rand.nextInt(size);
        int pos2 = rand.nextInt(size);
        while (pos2 == pos1) {
            pos2 = rand.nextInt(size);
        }

        Collections.swap(path.getCities(), pos1, pos2);

        path.setTotalDistance(path.calculateDistance());
    }

}
