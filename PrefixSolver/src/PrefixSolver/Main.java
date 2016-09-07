package PrefixSolver;

import java.util.Arrays;
import java.util.List;

/**
 * Created by james on 9/7/16.
 */
public class Main {

    public static void main(String[] main){
        List<Integer> list1 = Arrays.asList(1,2,3,4);
        List<Integer> list2 = Arrays.asList(1,2,4,4);

        IntegerComparator comparator = new IntegerComparator();
        List<Integer> prefix = PrefixFinder.longestPrefix(list1, list2, comparator);

        System.out.print(prefix);
    }
}
