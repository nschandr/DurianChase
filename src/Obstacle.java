import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// leaves now an obstacle.
public class Obstacle extends Entity {
    private static int numObstacles= 0;
    private static int maxObstacles = 120;
    private static int helper_count = 0;
    private static final int HELPER_LIMIT = 6;
    public Obstacle(String id, Point position,List<PImage> images)
    {
        super(id, position, images);
    }

    public static void clicked(String id, Point tile,List<PImage> images, WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        if (!world.isOccupied(tile) && numObstacles < maxObstacles) {
            createObstacles(id, tile, images, world);
            if (helper_count < HELPER_LIMIT){
                EntityFactory entityFactory = new EntityFactory();
                HelperNotFull notFull = (HelperNotFull)entityFactory.createEntity("HelperNOTFULL", "helper", tile, imageStore.getImageList("octo"));
                world.addEntity(notFull);
                helper_count++;
                notFull.scheduleActions(scheduler, world, imageStore);
                Optional<Entity> bearTarget = world.findNearest(new Point(0, 0),
                        Bear.class);
                if (bearTarget.isPresent() && (((Bear) bearTarget.get()).getActionPeriod() > 10)) {
                    ((Bear) bearTarget.get()).setActionPeriod(((Bear) bearTarget.get()).getActionPeriod() - 50);
                }
            }
        }
        if (world.getOccupancyCell(tile) instanceof Obstacle) {
            world.removeEntityAt(tile);
        }
    }

    private static void createObstacles(String id, Point tile,List<PImage> images, WorldModel world){
        List<Obstacle> leaves = new ArrayList<>();
        leaves.add(new Obstacle(id, new Point(tile.x + 1, tile.y), images));
        leaves.add(new Obstacle(id, new Point(tile.x - 1, tile.y), images));
        leaves.add(new Obstacle(id, new Point(tile.x, tile.y + 1), images));
        leaves.add(new Obstacle(id, new Point(tile.x, tile.y - 1), images));
        leaves.add(new Obstacle(id, new Point(tile.x - 1, tile.y + 1), images));
        leaves.add(new Obstacle(id, new Point(tile.x + 1, tile.y - 1), images));
        for (Obstacle leaf: leaves){
            if (!world.isOccupied(leaf.getPosition())) {
                world.tryAddEntity(leaf);
                numObstacles++;
            }
        }
    }
}

