import processing.core.PImage;

import java.util.List;

public class EntityFactory {
    public Entity createEntity(String entityType, String id, Point position,
                               int actionPeriod, int animationPeriod, List<PImage> images){

        if(entityType == null){
            return null;
        }
//        if(entityType.equalsIgnoreCase("ATLANTIS")){
//            return new Atlantis(id, position, images, 0, 0);
//
//        } else if(entityType.equalsIgnoreCase("BASKET")){
//            return new Basket(id, position, images, 0, 0);

        if(entityType.equalsIgnoreCase("BEAR")){
            return new Bear(id, position, images, actionPeriod, animationPeriod);
        }

        return null;
    }
}
