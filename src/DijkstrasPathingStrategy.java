import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class DijkstrasPathingStrategy
        implements PathingStrategy
{
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        List<Point> path = new LinkedList<>();

        //open list
        Map<Point,Node> openMap = new HashMap<>();
        Comparator<Node> sorter = Comparator.comparing(Node::getH);
        PriorityQueue<Node> openQueue = new PriorityQueue<>(sorter);

        //closed list
        Map<Point, Node> closedMap = new HashMap<>();

        //create current node which is the starting node
        Node current = new  Node(null, start, getDistance(start, end));

        //add to open list
        openMap.put(current.getPosition(), current);
        openQueue.add(current);

        //while target is not within reach
        while (!withinReach.test(current.getPosition(), end)){

            //get list of neighbour points
            List<Point> neighbours = potentialNeighbors.apply(current.getPosition())
                    .filter(canPassThrough)
                    .filter(p -> !closedMap.containsKey(p))
                    .collect(Collectors.toList());


            for (Point neighbour : neighbours){
                // turn point into node
                Node node = new Node(current, neighbour, getDistance(neighbour, end));
                //add adjacent nodes to open list if not already in open list
                if (!openMap.containsKey(node.getPosition())){
                    openQueue.add(node);
                    openMap.put(node.getPosition(), node);
                }
            }

            openQueue.remove(current); //remove current node from open list
            openMap.remove(current.getPosition());//remove current node from open map
            closedMap.put(current.getPosition(), current); //add current node to closedMap

            //if open list is not empty, sort the open list by F value and get the first node
            if (openQueue.peek() != null){
                current = openQueue.poll();
            } else { // if empty
                return path;
            }
        }
        while (current.getParent() != null) {
            path.add(0, current.getPosition());
            current = current.getParent();
        }
        return path;
    }

    public double getDistance(Point p1, Point p2){
        return Math.abs((p1.y - p2.y) + (p1.x - p2.x));
    }

    private class Node{

        private double h;
        private Node parent;
        private Point position;


        public Node(Node parent, Point position, double h){
            this.parent = parent;
            this.position = position;
            this.h = h;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || obj.getClass() == this.getClass()){
                return false;
            }

            boolean parentEQ, posEQ, hEQ;

            Node other = (Node)obj;
            parentEQ = Objects.equals(this.parent, other.parent);
            posEQ = Objects.equals(this.position, other.position);
            hEQ = this.h == other.h;
            return parentEQ && posEQ && hEQ;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.parent, this.position, this.h);
        }

        @Override
        public String toString() {
            return String.valueOf(this.position);
        }

        public Node getParent() {
            return parent;
        }

        public Point getPosition() {
            return position;
        }

        public double getH() {
            return h;
        }
    }

}