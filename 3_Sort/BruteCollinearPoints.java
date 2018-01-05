import java.util.Arrays;
public class BruteCollinearPoints {
   private LineSegment[] segments;
   private int lineSegmentNum = 0;
   public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
   {
       if(points == null)
       {
           throw new java.lang.IllegalArgumentException();
       }
       Arrays.sort(points);
       int len  = points.length;
       segments = new LineSegment[len - 1];
       for(int i = 0;i < len;i++){
           if(points[i] == null){
               throw new java.lang.IllegalArgumentException();
           }
           if(i < len - 1){
               if(points[i].compareTo(points[i+1]) == 0){
                   throw new java.lang.IllegalArgumentException();
               }
           }
       }
      
       for(int i = 0;i<len;i++){
           Point p = points[i];
           Comparator<Point> com = p.slopeOrder();
           Arrays.sort(p,i,len,com);
           if(i == len -1)
           {
               break;
           }
           int o = i;
           int j = i+1;
           double preSlope =  p.slopeTo(points[j++]);
           while(j<len){
               double curSlope = p.slopeTo(points[j]);
               if(preSlope != curSlope){
                   segments[lineSegmentNum++] = new LineSegment(points[o],points[j]);
                   o = j;
                   j++;
               }
           }
       }
       
       
   }
   //public           int numberOfSegments()        // the number of line segments
  // public LineSegment[] segments()                // the line segments
     public static void main(String[] args) {
         
    }
       
}