import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Bear extends Moves{

    public static final String QUAKE_KEY = "quake";

    public Bear(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    protected void executeActivity(WorldModel world,
                                    ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> bearTarget = world.findNearest(
                getPosition(), MainCollector.class);
        long nextPeriod = getActionPeriod();

        if (bearTarget.isPresent()) {
            Point tgtPos = bearTarget.get().getPosition();

            if (moveTo(world, bearTarget.get(), scheduler)) {
                Quake quake = (Quake)entityFactory.createEntity("QUAKE", "quake", tgtPos, imageStore.getImageList(QUAKE_KEY));
//                Quake quake = tgtPos.createQuake(tgtPos,
//                        imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += getActionPeriod();
                quake.scheduleActions(scheduler, world, imageStore);
                world.removeEntity(bearTarget.get());
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
    protected Point nextPosition(WorldModel world,
                                 Point destPos){
        Predicate<Point> canPassThrough = (point) -> world.withinBounds(point) && !world.isOccupied(point);
        BiPredicate<Point, Point> withinReach = (p1, p2) -> p1.adjacent(p2);
        PathingStrategy newStrategy = new NewPathingStrategy();
        List<Point> path =  newStrategy.computePath(this.getPosition(),
                destPos, canPassThrough, withinReach,PathingStrategy.CARDINAL_NEIGHBORS);
        if (path.size()==0) return getPosition();
        return  path.get(0);
    }

    @Override
    protected int getActionPeriod() {
        return super.getActionPeriod();
    }
}
