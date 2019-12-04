import processing.core.PImage;

import java.util.List;

public class Fruit extends AnimatedEntity{
    public static final String CRAB_KEY = "bear";
    private static final int MAX_BEAR = 1;
    private static int bear_count = 0;
    public static final String BEAR_ID_SUFFIX = " -- bear";
    public static final int CRAB_PERIOD_SCALE = 10;
    public static final int CRAB_ANIMATION_MIN = 50;
    public static final int CRAB_ANIMATION_MAX = 150;
    
    public Fruit(String id, Point position,
                 List<PImage> images, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    protected void executeActivity(WorldModel world,
                                    ImageStore imageStore, EventScheduler scheduler)
    {

        if (bear_count<MAX_BEAR) {
            int x = Functions.rand.nextInt(23);
            int y = Functions.rand.nextInt(14);
            Point pos = new Point(x, y);
            EntityFactory entityFactory = new EntityFactory();
            if(!world.isOccupied(pos)) {
                Entity bear = entityFactory.createEntity("BEAR", getId() + BEAR_ID_SUFFIX,
                        pos, getActionPeriod() / CRAB_PERIOD_SCALE,
                        CRAB_ANIMATION_MIN +
                                Functions.rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN),
                        imageStore.getImageList(CRAB_KEY));

                world.addEntity(bear);
                ((Bear)bear).scheduleActions(scheduler, world, imageStore);
                bear_count++;
            }
        }
    }
}