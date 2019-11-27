import processing.core.PImage;

import java.util.List;

public abstract class AnimatedEntity extends ActiveEntity{
    private int animationPeriod;
    public AnimatedEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
    }

    protected int getAnimationPeriod() {
        return animationPeriod;
    }
    protected void nextImage()
    {
        setImageIndex((getImageIndex() + 1) % getImages().size());
    }

    protected Animation createAnimationAction(int repeatCount)
    {
        return new Animation(this, repeatCount);
    }
    protected void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this, this.createAnimationAction(0), this.getAnimationPeriod());
    }
    protected void executeAnimationAction(EventScheduler scheduler, Animation animation)
    {
        nextImage();

        if (animation.getRepeatCount() != 1)
        {
            scheduler.scheduleEvent(this,
                    createAnimationAction(
                            Math.max(animation.getRepeatCount() - 1, 0)),
                    this.getAnimationPeriod());
        }
    }



}
