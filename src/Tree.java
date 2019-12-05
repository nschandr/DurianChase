import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Tree extends ActiveEntity{
    public static final String FISH_KEY = "fish";
    public static final String FISH_ID_PREFIX = "fish -- ";
    public static final int FISH_CORRUPT_MIN = 2000;
    public static final int FISH_CORRUPT_MAX = 3000;
    private int fruitSpawned = 0;

    public Tree(String id, Point position,
                List<PImage> images, int actionPeriod)
    {
        super(id, position, images, actionPeriod);
        super.setActionPeriod(10000);
    }

    protected void executeActivity(WorldModel world,
                                      ImageStore imageStore, EventScheduler scheduler)
    {

        Optional<Entity> mainChar = world.findNearest(getPosition(), MainCollector.class);
        if (mainChar.isPresent()) {
            int x = Functions.rand.nextInt(23);
            int y = Functions.rand.nextInt(14);
            Point point = new Point(x, y);
            if (!world.isOccupied(point) && world.getFruitsOnScreen() < 10) {
                Fruit fruit = point.createFish(FISH_ID_PREFIX + getId(),
                        point, 5000,
                        imageStore.getImageList(FISH_KEY), 1);
                world.addEntity(fruit);
                world.setFruitsOnScreen(world.getFruitsOnScreen() + 1);
                fruit.scheduleActions(scheduler, world, imageStore);
            }
//        Optional<Point> openPt = world.findOpenAround(getPosition());
//        if (openPt.isPresent())
//        {
//            Fruit fruit = getPosition().createFish(FISH_ID_PREFIX + getId(),
//                    openPt.get(), FISH_CORRUPT_MIN +
//                            Functions.rand.nextInt(FISH_CORRUPT_MAX - FISH_CORRUPT_MIN),
//                    imageStore.getImageList(FISH_KEY));
//            world.addEntity(fruit);
//            fruit.scheduleActions(scheduler, world, imageStore);
//        }
            scheduler.scheduleEvent(this,
                    this.createActivityAction(world, imageStore),
                    getActionPeriod());
        }
    }
}
