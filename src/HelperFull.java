import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class HelperFull extends Helper {

    public HelperFull(String id, Point position,
                      List<PImage> images, int resourceLimit,
                      int actionPeriod, int animationPeriod)
    {
        super(id, position, images,resourceLimit, actionPeriod, animationPeriod);
    }

    protected void executeActivity(WorldModel world,
                                        ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest(getPosition(),
                 Basket.class);

        if (fullTarget.isPresent() &&
                moveTo(world, fullTarget.get(), scheduler))
        {
            //at atlantis trigger animation
            ((ActiveEntity)fullTarget.get()).scheduleActions(scheduler, world, imageStore);

            //transform to unfull
            transform(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this,
                    this.createActivityAction(world, imageStore),
                    getActionPeriod());
        }
    }

    protected boolean transform(WorldModel world,
                              EventScheduler scheduler, ImageStore imageStore)
    {
        HelperNotFull octo = getPosition().createOctoNotFull(getId(), this.getResourceLimit(),
                getPosition(), getActionPeriod(), getAnimationPeriod(),
                getImages());

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(octo);
        octo.scheduleActions(scheduler, world, imageStore);
        return true;
    }

    protected boolean moveTo(WorldModel world,
                              Entity target, EventScheduler scheduler)
    {
        if (this.getPosition().adjacent(target.getPosition()))
        {
            return true;
        }
        else
        {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

}
