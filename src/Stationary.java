import processing.core.PImage;

import java.util.List;

public abstract class Stationary extends AnimatedEntity{
    public Stationary(String id, Point position,
                 List<PImage> images, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }
    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }
}
