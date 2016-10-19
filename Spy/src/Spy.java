import java.util.*;

/**
 * Created by james on 10/18/16.
 */
public class Spy <T> {


    private HashMap<T, HashSet<T>> knownObjectsToOpponents;

    public Spy(){
        knownObjectsToOpponents = new HashMap<>();
    }

    public boolean create(T x) {
        if( !knownObjectsToOpponents.containsKey(x) ) {
            knownObjectsToOpponents.put(x, new HashSet<>());
            return true;
        }
        return false;
    }

    public void oppose(T x, T y) throws RuntimeException {
        //needs to be a bitwise OR to prevent short-circuiting
        boolean hadToCreateXOrY = create(x) | create(y);


        if( !hadToCreateXOrY && areNotOpponents(x, y)) {
            throw new RuntimeException("X and Y cannot be made opponents.");
        }

        HashSet<T> xHashSet = knownObjectsToOpponents.get(x);
        HashSet<T> yHashSet = knownObjectsToOpponents.get(y);

        xHashSet.add(y);
        yHashSet.add(x);
    }

    private boolean areNotOpponents(T x, T y) {
        //Not using orElse to reduce the optional checking as the result of doing so looks really confusing
        //It looks like this: areNotOpponents = !opponents(x, y).orElse(true);
        //I find it hard to keep the flag for orElse straight in my head, so I would rather be explicit here
        //at the cost of additional checking and complexity
        Optional<Boolean> areOpponents = opponents(x, y);
        return areOpponents.isPresent() && !areOpponents.get();
    }

    public Optional<Boolean> opponents(T x, T y){
        int depth = depthToY(x, y);

        if (depth == -1) {
            return Optional.empty();
        }
        //this looks silly since it should just be Optional.of(depth%2==1), but I will stick to the pseudocode
        else if (depth % 2 == 1) {
            return Optional.of(true);
        }
        return Optional.of(false);
    }

    private int depthToY(T x, T y) {
        boolean wasFound = false;
        int depth = 0;

        ArrayDeque<T> searchQueue = new ArrayDeque<>();
        Set<T> inspectedObjects = new HashSet<>();

        searchQueue.add(x);

        breadthFirstSearchLoop:
        while ( !searchQueue.isEmpty() ) {
            depth++;

            T current = searchQueue.poll();
            if(knownObjectsToOpponents.get(current).contains(y)) {
                wasFound = true;
                break breadthFirstSearchLoop;
            }

            addNeighbors(current, searchQueue, inspectedObjects);
        }

        return wasFound? depth : -1;
    }

    private void addNeighbors(T current, ArrayDeque<T> searchQueue, Set<T> inspectedObjects) {
        for (T neighbor : knownObjectsToOpponents.get(current)) {
            boolean needsToBeChecked = inspectedObjects.add(neighbor);
            if( needsToBeChecked) {
                searchQueue.add(neighbor);
            }
        }
    }

    //delegate for testing purposes
    public boolean containsKey(Object o) {
        return knownObjectsToOpponents.containsKey(o);
    }
}
