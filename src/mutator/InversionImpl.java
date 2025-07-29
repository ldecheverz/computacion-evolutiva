package mutator;

import dtos.Path;

import java.util.Collections;
import java.util.Random;

public class InversionImpl implements Mutator{

    @Override
    public void mutate(Path path) {
        Random rand = new Random();
        int size = path.getCities().size();

        int start = rand.nextInt(size);
        int end = rand.nextInt(size);
        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }

        while (start < end) {
            Collections.swap(path.getCities(), start, end);
            start++;
            end--;
        }

        path.setTotalDistance(path.calculateDistance());
    }
}
