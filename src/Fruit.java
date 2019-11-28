import processing.core.PImage;

import java.util.List;

public class Fruit extends ActiveEntity{
    public static final String CRAB_KEY = "crab";
    public static final String CRAB_ID_SUFFIX = " -- crab";
    public static final int CRAB_PERIOD_SCALE = 4;
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

        Bear bear = pos.createCrab(getId() + CRAB_ID_SUFFIX,
                pos, getActionPeriod() / CRAB_PERIOD_SCALE,
                CRAB_ANIMATION_MIN +
                        Functions.rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN),
                imageStore.getImageList(CRAB_KEY));

        world.addEntity(bear);
        bear.scheduleActions(scheduler, world, imageStore);
    }
}
