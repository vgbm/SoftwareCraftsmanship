package PrefixSolver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by james on 9/7/16.
 */
public class Solver {

    public static <T> List<T> longestPrefix(List<T> list1, List<T> list2, Comparator<? super T> cmp){
        Iterator iter1 = list1.iterator();
        Iterator iter2 = list2.iterator();

        //ArrayList is preferred to LinkedList since appending to lists should be O(1)
        List<T> prefix = new ArrayList<>();

        while( iter1.hasNext() && iter2.hasNext() ) {
            Object elem = iter1.next();
            if(cmp.compare(elem, iter2.next()) == 0) {
                prefix.add(elem);
            }
        }

        return prefix;
    }
}
