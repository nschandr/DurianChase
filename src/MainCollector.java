import processing.core.PImage;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class MainCollector extends AnimatedEntity{
    private int fruitCount = 0;
    private static MainCollector single_instance = null;
    public static final String FISH_KEY = "fish";
    public static final String FISH_ID_PREFIX = "fish -- ";
    public static final int FISH_CORRUPT_MIN = 20000;
    public static final int FISH_CORRUPT_MAX = 30000;

    private MainCollector(String id, Point position,List<PImage> images, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public static MainCollector createInstance(String id, Point position,List<PImage> images){
        if (single_instance==null) {
            single_instance = new MainCollector(id, position, images, 1, 1);
        }
        return single_instance;
    }

    public static MainCollector getInstance(){
        return single_instance;
    }

    public void executeActivity(Point pos, ImageStore imageStore, WorldModel world, EventScheduler scheduler){
        Point nextPos = new Point(getPosition().x + pos.x, getPosition().y + pos.y);
//        if (!this.getPosition().equals(nextPos))
//        {
//            Optional<Entity> collectorTarget = world.findNearest(getPosition(),
//                    Fruit.class);
        Entity neighbour = world.getOccupancyCell(nextPos);
        if (neighbour instanceof Fruit){
            world.removeEntity(neighbour);
            world.moveEntity(this, nextPos);
            scheduler.unscheduleAllEvents(neighbour);
            super.fruitCount++;
            System.out.println(super.fruitCount);
        } else {
            Predicate<Point> canPassThrough = (point) -> world.withinBounds(point) && !world.isOccupied(point);
            if (canPassThrough.test(nextPos)) {
                world.moveEntity(this, nextPos);
            }
        }
    }
//        }
//        int x = Functions.rand.nextInt(23);
//        int y = Functions.rand.nextInt(14);
//        Point point = new Point(x, y);
//        Fruit fruit = point.createFish(FISH_ID_PREFIX + getId(),
//                point, FISH_CORRUPT_MIN +
//                        Functions.rand.nextInt(FISH_CORRUPT_MAX - FISH_CORRUPT_MIN),
//                imageStore.getImageList(FISH_KEY));
//        world.addEntity(fruit);
//        fruit.scheduleActions(scheduler, world, imageStore);


    @Override
    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

    }
}
