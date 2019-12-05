import processing.core.PImage;

import java.util.List;

public class Basket extends Stationary{
    public static final int ATLANTIS_ANIMATION_REPEAT_COUNT = 7;

    public Basket(String id, Point position,
                  List<PImage> images, int actionPeriod, int animationPeriod)
    {
        super(id, position,images,actionPeriod,animationPeriod);
    }
    protected void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, this.createAnimationAction(ATLANTIS_ANIMATION_REPEAT_COUNT), this.getAnimationPeriod());
    }
}
