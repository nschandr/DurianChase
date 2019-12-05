import processing.core.PImage;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class MainCollector extends Entity{
    private static MainCollector single_instance = null;

    private MainCollector(String id, Point position,List<PImage> images)
    {
        super(id, position, images);
    }

    public static MainCollector createInstance(String id, Point position,List<PImage> images){
        if (single_instance==null) {
            single_instance = new MainCollector(id, position, images);
        }
        return single_instance;
    }

    public static MainCollector getInstance(){
        return single_instance;
    }

    public void moveCollector(Point pos, ImageStore imageStore, WorldModel world, EventScheduler scheduler) {
        Point nextPos = new Point(getPosition().x + pos.x, getPosition().y + pos.y);

        if (world.withinBounds(nextPos)) {
            Entity neighbour = world.getOccupancyCell(nextPos);
            if (neighbour instanceof Fruit) {
                world.removeEntity(neighbour);
                world.moveEntity(this, nextPos);
                scheduler.unscheduleAllEvents(neighbour);
                world.setFruitsCollected(world.getFruitsCollected()+1);
                world.setFruitsOnScreen(world.getFruitsOnScreen()-1);
            } else {
                Predicate<Point> canPassThrough = (point) -> world.withinBounds(point) && !world.isOccupied(point);
                if (canPassThrough.test(nextPos)) {
                    world.moveEntity(this, nextPos);
                }
            }
        }
    }
}