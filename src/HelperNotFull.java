import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class HelperNotFull extends Helper {
    private int resourceCount;

    public HelperNotFull(String id, Point position,
                         List<PImage> images, int resourceLimit, int resourceCount,
                         int actionPeriod, int animationPeriod)
    {
        super(id, position, images, resourceLimit, actionPeriod, animationPeriod);
        this.resourceCount = resourceCount;
    }

    protected void executeActivity(WorldModel world, ImageStore imageStore,
                                           EventScheduler scheduler)
    {
        Optional<Entity> mainChar = world.findNearest(getPosition(), MainCollector.class);
        if (mainChar.isPresent()) {
            Optional<Entity> notFullTarget = world.findNearest(getPosition(),
                    Fruit.class);
            if (!notFullTarget.isPresent() ||
                    !moveTo(world, notFullTarget.get(), scheduler) ||
                    !transform(world, scheduler, imageStore)) {
                scheduler.scheduleEvent(this,
                        this.createActivityAction(world, imageStore),
                        getActionPeriod());
            }
            Optional<Entity> bearTarget = world.findNearest(getPosition(),
                    Bear.class);
            if (bearTarget.isPresent()) {
                ((Bear) bearTarget.get()).setActionPeriod(((Bear) bearTarget.get()).getActionPeriod() / 2);
            }
        }
    }
    protected boolean transform(WorldModel world,
                                    EventScheduler scheduler, ImageStore imageStore)
    {
        if (this.resourceCount >= this.getResourceLimit())
        {
            HelperFull helperFull = (HelperFull)entityFactory.createEntity("HelperFULL", getId(), getPosition(), getImages());

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(helperFull);
            world.setFruitsOnScreen(world.getFruitsOnScreen()-1);
            world.setFruitsCollected(world.getFruitsCollected()+1);
            world.addEntity(helperFull);
            helperFull.scheduleActions(scheduler, world, imageStore);
            return true;
        }

        return false;
    }

    protected boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (getPosition().adjacent(target.getPosition()))
        {
            this.resourceCount += 1;
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

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
