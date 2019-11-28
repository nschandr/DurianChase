import processing.core.PImage;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class Moves extends AnimatedEntity{
//    protected PathingStrategy strategy = new SingleStepPathingStrategy();
    protected PathingStrategy strategy = new AStarPathingStrategy();
    public Moves(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);
    }

    protected Point nextPosition(WorldModel world,
                                 Point destPos){
        Predicate<Point> canPassThrough = (point) -> world.withinBounds(point) && !world.isOccupied(point);
        BiPredicate<Point, Point> withinReach = (p1, p2) -> p1.adjacent(p2);
        List<Point> path =  strategy.computePath(this.getPosition(),
                destPos, canPassThrough, withinReach,PathingStrategy.CARDINAL_NEIGHBORS);
        if (path.size()==0) return getPosition();
        return  path.get(0);
    }
    protected abstract boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);

}
