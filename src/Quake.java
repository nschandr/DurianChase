import processing.core.PImage;

import java.util.List;

public class Quake extends Stationary{
    public static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    public Quake(String id, Point position,
                List<PImage> images, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }

//    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
//    {
//        scheduler.unscheduleAllEvents(this);
//        world.removeEntity(this);
//    }
    protected void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this, this.createAnimationAction(QUAKE_ANIMATION_REPEAT_COUNT), this.getAnimationPeriod());

    }
}
