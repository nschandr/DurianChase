import processing.core.PImage;

import java.util.List;

public class Fruit extends ActiveEntity{
    public static final String CRAB_KEY = "bear";
    private static final int MAX_BEAR = 2;
    private static int bear_count = 0;
    public static final String BEAR_ID_SUFFIX = " -- bear";
    public static final int CRAB_PERIOD_SCALE = 50;
    public static final int CRAB_ANIMATION_MIN = 50;
    public static final int CRAB_ANIMATION_MAX = 150;


    public Fruit(String id, Point position,
                 List<PImage> images, int actionPeriod)
    {
        super(id, position, images, actionPeriod);
    }

    protected void executeActivity(WorldModel world,
                                    ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = getPosition();  // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);
        if (bear_count<MAX_BEAR) {
            EntityFactory entityFactory = new EntityFactory();
            Entity bear = entityFactory.createEntity("BEAR", getId() + BEAR_ID_SUFFIX,
                    pos, getActionPeriod() / CRAB_PERIOD_SCALE,
                    CRAB_ANIMATION_MIN +
                            Functions.rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN),
                    imageStore.getImageList(CRAB_KEY));
            System.out.print("getid"+ getId());

            world.addEntity(bear);
            ((Bear)bear).scheduleActions(scheduler, world, imageStore);
            bear_count++;
        }
    }
}