import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy {


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {
        List<Point> path = new LinkedList<>();

        //hashmap for constant-time performance for the basic operations (get and put, contains)
        //value held is Gvalue
        Map<Point, Integer> closedMap = new HashMap<>();
        Map<Point, Integer> openMap = new HashMap<>();

        //priority queue for fast sorting
        //head of this queue is the least element, lowest F-val
        //constant time for the retrieval methods (peek, element, and size)
        Queue<Point> openList = new PriorityQueue<>(Comparator.comparing(Point::getF));

        //1. Choose/know starting and ending points of the path
        //Intialize start point
        start.setG(0);
        start.setH(start.manhattanDist(end));
        start.setF(start.getG() + start.getH());

        //2. Add start node to the open list and mark it as the current node
        openList.add(start);
        Point current = null;

        while (!openList.isEmpty()) {
            //5. Choose a node from the open list with the smallest f value and make it the current
            //node
            //try poll for O(1)?
//            current = openList.remove();
            current = openList.poll();

            // reached goal!
            if (withinReach.test(current, end)) {
                while (current != start) {
                    path.add(0, current);
                    current = current.getPrior();
                }
                return path;
            }

            // get valid neighbours that are not in the closed list
            List<Point> validNeighbours = potentialNeighbors.apply(current)
                    .filter(canPassThrough)
                    .filter(n -> !closedMap.containsKey(n))
                    .collect(Collectors.toList());

            //3. Analyze all valid adjacent nodes that are not on the closed list
            for (Point adjacent : validNeighbours) {
                int adjG = current.getG() + adjacent.manhattanDist(current);
                int adjH = adjacent.manhattanDist(end);
                int adjF = adjG + adjH;

                //3.a. Add to Open List if not already in it
                if (!openMap.containsKey(adjacent)) {
                    adjacent.setPrior(current);
                    adjacent.setG(adjG);
                    adjacent.setH(adjH);
                    adjacent.setF(adjF);
                    openList.add(adjacent);
                    openMap.put(adjacent, adjG);

                }
                //3.c. If the calculated g value is better than a previously calculated g value, replace
                //the old g value with the new one
                else if (openMap.containsKey(adjacent) && adjG < openMap.get(adjacent)) {
                    openList.remove(adjacent);
                    adjacent.setG(adjG);
                    adjacent.setH(adjH);
                    adjacent.setF(adjF);
                    adjacent.setPrior(current); //3.c.iii.Mark the adjacent node’s prior vertex as the current node
                    openList.add(adjacent);
                    openMap.replace(adjacent, adjG);

                }

            }
            //4. Move the current node to the closed list
            closedMap.put(current, current.getG());

        }
        return path;


    }
}