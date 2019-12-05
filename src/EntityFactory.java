import processing.core.PImage;

import java.util.List;

public class EntityFactory {
    public Entity createEntity(String entityType, String id, Point position, List<PImage> images){

        if(entityType == null){
            return null;
        }
        else if(entityType.equalsIgnoreCase("BEAR")){
            return new Bear(id, position, images, 500, Fruit.BEAR_ANIMATION_MIN +
                    Functions.rand.nextInt(Fruit.BEAR_ANIMATION_MAX - Fruit.BEAR_ANIMATION_MIN));
        }
        else if(entityType.equalsIgnoreCase("FRUIT")){
            return new Fruit(id, position, images, 5000, 1);
        }
        else if(entityType.equalsIgnoreCase("HelperNOTFULL")){
            return new HelperNotFull(id, position, images, 1, 0, 5, 6);
        }
        else if(entityType.equalsIgnoreCase("HelperFULL")){
            return new HelperFull(id, position, images, 1,  5, 6);
        }
        else if(entityType.equalsIgnoreCase("OBSTACLE")){
            return new Obstacle(id, position, images);
        }

//        if(entityType.equalsIgnoreCase("ATLANTIS")){
//            return new Atlantis(id, position, images, 0, 0);
//
//        } else if(entityType.equalsIgnoreCase("BASKET")){
//            return new Basket(id, position, images, 0, 0);



        return null;
    }
}
