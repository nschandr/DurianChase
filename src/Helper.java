import processing.core.PImage;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.List;
import java.util.stream.Stream;

public abstract class Helper extends Moves{
    protected PathingStrategy strategy = new AStarPathingStrategy();

    private int resourceLimit;
    public Helper(String id, Point position, List<PImage> images, int resourceLimit, int actionPeriod, int animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
    }
    protected abstract boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);
    protected abstract boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore);

    protected int getResourceLimit() {
        return resourceLimit;
    }
    protected Point nextPosition(WorldModel world,
                                 Point destPos){
        Predicate<Point> canPassThrough = (point) -> world.withinBounds(point) && !world.isOccupied(point);
        BiPredicate<Point, Point> withinReach = (p1, p2) -> p1.adjacent(p2);
        List<Point> path =  strategy.computePath(this.getPosition(),
                destPos, canPassThrough, withinReach,DIAGONAL_CARDINAL_NEIGHBORS);
        if (path.size()==0) return getPosition();
        return  path.get(0);
    }

    private static final Function<Point, Stream<Point>> DIAGONAL_CARDINAL_NEIGHBORS =
            point ->
                    Stream.<Point>builder()
                            .add(new Point(point.x - 1, point.y - 1))
                            .add(new Point(point.x + 1, point.y + 1))
                            .add(new Point(point.x - 1, point.y + 1))
                            .add(new Point(point.x + 1, point.y - 1))
                            .add(new Point(point.x, point.y - 1))
                            .add(new Point(point.x, point.y + 1))
                            .add(new Point(point.x - 1, point.y))
                            .add(new Point(point.x + 1, point.y))
                            .build();
}
