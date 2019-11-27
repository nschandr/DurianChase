import java.util.List;
import java.util.Optional;

import processing.core.PImage;

final class Background
{
   private String id;
   private List<PImage> images;
   private int imageIndex;

   public Background(String id, List<PImage> images)
   {
      this.id = id;
      this.images = images;
   }
   protected List<PImage> getImages()
   {
      return images;
   }
   protected int getImageIndex()
   {
      return this.imageIndex;
   }


}
