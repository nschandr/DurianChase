import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
//TODO
//remove fruits that are adjacent to collector. currently removes anything.



public class MainCollector extends Entity{
    private int fruitCount = 0;
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

    public void executeActivity(Point pos, WorldModel world, EventScheduler scheduler){
        Point nextPos = new Point(getPosition().x + pos.x, getPosition().y + pos.y);
        if (!this.getPosition().equals(nextPos))
        {
            Optional<Entity> collectorTarget = world.findNearest(getPosition(),
                    Fruit.class);
            if (collectorTarget.isPresent() && getPosition().adjacent(collectorTarget.get().getPosition()))
            {
                world.removeEntity(collectorTarget.get());
                scheduler.unscheduleAllEvents(collectorTarget.get());
                world.moveEntity(this, nextPos);
            }
            else{
                Predicate<Point> canPassThrough = (point) -> world.withinBounds(point) && !world.isOccupied(point);
                if (canPassThrough.test(nextPos)){
                    world.moveEntity(this, nextPos);
                }
            }

        }
    }



}
