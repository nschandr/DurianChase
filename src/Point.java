import processing.core.PImage;

import java.util.List;



final class Point
{
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

   public boolean adjacent(Point p2)
   {
       return (x == p2.x && Math.abs(y - p2.y) == 1) ||
               (y == p2.y && Math.abs(x - p2.x) == 1);
   }


}
