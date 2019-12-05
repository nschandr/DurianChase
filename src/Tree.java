import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Tree extends ActiveEntity{
    public static final String FISH_KEY = "fish";
    public static final String FISH_ID_PREFIX = "fish -- ";

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
                Fruit fruit = (Fruit)entityFactory.createEntity("FRUIT", FISH_ID_PREFIX + getId(), point, imageStore.getImageList(FISH_KEY));
                world.addEntity(fruit);
                world.setFruitsOnScreen(world.getFruitsOnScreen() + 1);
                fruit.scheduleActions(scheduler, world, imageStore);
            }
            scheduler.scheduleEvent(this,
                    this.createActivityAction(world, imageStore),
                    getActionPeriod());
        }
    }
}
