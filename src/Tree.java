import processing.core.PImage;
import java.util.List;

public class Tree extends ActiveEntity{
    public static final String FISH_KEY = "fish";
    public static final String FISH_ID_PREFIX = "fish -- ";
    public static final int FISH_CORRUPT_MIN = 2000;
    public static final int FISH_CORRUPT_MAX = 3000;

    public Tree(String id, Point position,
                List<PImage> images, int actionPeriod)
    {
        super(id, position, images, actionPeriod);
        super.setActionPeriod(10000);
    }

    protected void executeActivity(WorldModel world,
                                      ImageStore imageStore, EventScheduler scheduler)
    {

        int x = Functions.rand.nextInt(23);
        int y = Functions.rand.nextInt(14);
        Point point = new Point(x, y);
        if(!world.isOccupied(point)) {
            Entity fruit = entityFactory.createEntity("FRUIT", FISH_ID_PREFIX + getId(),
                    point, imageStore.getImageList(FISH_KEY));
            world.addEntity(fruit);
            if (fruit instanceof Fruit) {
                ((Fruit)fruit).scheduleActions(scheduler, world, imageStore);
            }
        }
        scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                getActionPeriod());
    }
}
