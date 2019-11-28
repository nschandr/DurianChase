import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Bear extends Moves{

    public static final String QUAKE_KEY = "quake";

    public Bear(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    protected void executeActivity(WorldModel world,
                                    ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> bearTarget = world.findNearest(
                getPosition(), Fruit.class);
        long nextPeriod = getActionPeriod();

        if (bearTarget.isPresent()) {
            Point tgtPos = bearTarget.get().getPosition();

            if (moveTo(world, bearTarget.get(), scheduler)) {
                Quake quake = tgtPos.createQuake(tgtPos,
                        imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += getActionPeriod();
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }
        scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                nextPeriod);
    }
    protected boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (this.getPosition().adjacent(target.getPosition()))
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos))
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
