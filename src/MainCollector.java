import processing.core.PImage;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
//TODO
//2. keyPressed in VirtualWorld to move main character. link them


public class MainCollector extends Entity{
    private static MainCollector single_instance = null;

    private MainCollector(String id, Point position,List<PImage> images)
    {
        super(id, position, images);
    }

    public static MainCollector createInstance(String id, Point position,List<PImage> images){
        if (single_instance==null) {
            single_instance = new MainCollector(id, position, images);
        }
        return single_instance;

    }


}
