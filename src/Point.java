import processing.core.PImage;

import java.util.List;



final class Point
{
   public static final String QUAKE_ID = "quake";
   public static final int QUAKE_ACTION_PERIOD = 1100;
   public static final int QUAKE_ANIMATION_PERIOD = 100;
   public final int x;
   public final int y;
   private int g;  //dist from start node;
   private int h;  //manhattan dist to destpos
   private int f;  //g+h
   private Point prior = null;
   private final int D = 10;

   public Point(int x, int y)
   {
      this.x = x;
      this.y = y;
   }
   public int getG() {
      return g;
   }

   public void setG(int g) {
      this.g = g;
   }

   public int getH() {
      return h;
   }

   public void setH(int h) {
      this.h = h;
   }

   public int getF() {
      return f;
   }

   public void setF(int f) {
      this.f = f;
   }

   public Point getPrior() {
      return prior;
   }

   public void setPrior(Point prior) {
      this.prior = prior;
   }

   public int manhattanDist(Point end){
      return D * (Math.abs(this.x - end.x) +
              Math.abs(this.y - end.y));
   }


   public String toString()
   {
      return "(" + x + "," + y + ")";
   }

   public boolean equals(Object other)
   {
      return other instanceof Point &&
         ((Point)other).x == this.x &&
         ((Point)other).y == this.y;
   }

   public int hashCode()
   {
      int result = 17;
      result = result * 31 + x;
      result = result * 31 + y;
      return result;
   }

   public Basket createAtlantis(String id, Point position,
                                List<PImage> images)
   {
      return new Basket(id, position, images, 0, 0);
   }

   public HelperFull createOctoFull(String id, int resourceLimit,
                                    Point position, int actionPeriod, int animationPeriod,
                                    List<PImage> images)
   {
      return new HelperFull(id, position, images,
              resourceLimit, actionPeriod, animationPeriod);
   }

   public HelperNotFull createOctoNotFull(String id, int resourceLimit,
                                          Point position, int actionPeriod, int animationPeriod,
                                          List<PImage> images)
   {
      return new HelperNotFull(id, position, images,
              resourceLimit, 0, actionPeriod, animationPeriod);
   }

   public Obstacle createObstacle(String id, Point position,
                                       List<PImage> images)
   {
      return new Obstacle(id, position, images);
   }

   public Fish createFish(String id, Point position, int actionPeriod,
                                   List<PImage> images)
   {
      return new Fish(id, position, images,
              actionPeriod);
   }

   public Crab createCrab(String id, Point position,
                                   int actionPeriod, int animationPeriod, List<PImage> images)
   {
      return new Crab(id, position, images, actionPeriod, animationPeriod);
   }

   public Quake createQuake(Point position, List<PImage> images)
   {
      return new Quake(QUAKE_ID, position, images, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
   }
   public SGrass createSgrass(String id, Point position, int actionPeriod,
                                     List<PImage> images)
   {
      return new SGrass(id, position, images, actionPeriod);
   }
   public MainCollector createMainCollector(String id, Point position,
                                            List<PImage> images)
   {
      return new MainCollector(id, position, images);
   }

}
