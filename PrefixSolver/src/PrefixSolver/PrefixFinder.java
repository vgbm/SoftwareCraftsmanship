package PrefixSolver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by james on 9/7/16.
 */
public class PrefixFinder {

    public static <T> List<T> longestPrefix(List<T> list1, List<T> list2, Comparator<? super T> cmp) {
        Iterator<T> iter1 = list1.iterator();
        Iterator<T> iter2 = list2.iterator();

        return findCommonPrefix(iter1, iter2, cmp);
    }

    private static <T> List<T> findCommonPrefix(Iterator<T> iter1, Iterator<T> iter2, Comparator<? super T> cmp) {

        //ArrayList is preferred to LinkedList since appending to lists should be O(1)
        List<T> prefix = new ArrayList<>();

        while (bothIteratorsHaveNextElement(iter1, iter2)) {
            T elem1 = iter1.next();
            T elem2 = iter2.next();

            if (elementsAreEqual(elem1, elem2, cmp)) {
                prefix.add(elem1);
            } else {
                break;
            }
        }

        return prefix;
    }

    private static <T> boolean bothIteratorsHaveNextElement(Iterator<T> iter1, Iterator<T> iter2) {
        return iter1.hasNext() && iter2.hasNext();
    }

    private static <T> boolean elementsAreEqual(T elem1, T elem2, Comparator<? super T> cmp) {
        return cmp.compare(elem1, elem2) == 0;
    }
}