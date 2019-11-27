import processing.core.PImage;

import java.util.List;
//TODO
//1. Find a way to instantiate one main character
//2. keyPressed in VirtualWorld to move main character. link them

// Singleton
public class Main extends Entity{
    private static Main single_instance = null;

    private Main(String id, Point position, List<PImage> images) {
        super(id, position, images);
    }

    public static Main getInstance(){
        if (single_instance==null) {
            single_instance = new Main("collector", new Point(200, 200), imageStore.getImageList(MAIN_KEY));
        }
        return single_instance;

    }

}
