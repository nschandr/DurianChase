import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
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
                HelperNotFull helper = tile.createHelperNotFull("helper", 1, tile, 5, 6, imageStore.getImageList("octo"));
                world.addEntity(helper);
                helper_count++;
                helper.scheduleActions(scheduler, world, imageStore);
            }
        }
        if (world.getOccupancyCell(tile) instanceof Obstacle) {
            world.removeEntityAt(tile);
        }
    }

    private static void createObstacles(String id, Point tile,List<PImage> images, WorldModel world){
        List<Obstacle> leaves = new ArrayList<>();
//        leaves.add(new Obstacle(id, new Point(tile.x, tile.y), images));
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

