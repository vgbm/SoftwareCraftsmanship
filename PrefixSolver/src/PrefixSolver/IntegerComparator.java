package PrefixSolver;

import java.util.Comparator;

/**
 * Created by james on 9/7/16.
 */
public class IntegerComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer first, Integer second) {
        return Integer.compare(first, second);
    }

}
