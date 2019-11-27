import processing.core.PImage;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.List;

public abstract class Octo extends Moves{
    private int resourceLimit;
    public Octo(String id, Point position, List<PImage> images, int resourceLimit, int actionPeriod, int animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
    }
    protected abstract boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);
    protected abstract boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore);

    protected int getResourceLimit() {
        return resourceLimit;
    }
}
