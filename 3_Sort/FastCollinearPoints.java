import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdDraw;
public class FastCollinearPoints {
    private class MyLineSegment{
        public double slope;
        public LineSegment segment;
        public Point endPt;
        public MyLineSegment(Point p1,Point p2,double s){
            segment = new LineSegment(p1,p2);
            slope = s;
            endPt = p2;
        }
    }
   private MyLineSegment[] segments;
   private int lineSegmentNum = 0;
   private void addSegment(Point p1,Point p2,double slope){
       
       for(int i = 0; i < lineSegmentNum;i++)
       {
           MyLineSegment ls = segments[i];
           if(ls.endPt.compareTo(p2) == 0 && slope == ls.slope){
               
               return;

           }
           
       }
       segments[lineSegmentNum++] = new MyLineSegment(p1,p2,slope);
   }
   public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
   {
       if(points == null)
       {
           throw new java.lang.IllegalArgumentException();
       }
       Arrays.sort(points);
       int len  = points.length;
       segments = new MyLineSegment[len];
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
       Point[] helpArray = new Point[len];
       for(int i = 0;i<len - 3;i++){
          
           for(int k = i; k <len ;k++){
               helpArray[k] = points[k];
           }
           Point p = points[i];
           Comparator<Point> com = p.slopeOrder();
           int j = i+1;
           Arrays.sort(helpArray,j,len,com);
          
          
           int k = 0;
           
           double preSlope =  p.slopeTo(helpArray[j]);
           while(j<len){
               double curSlope = p.slopeTo(helpArray[j]);
               if(preSlope != curSlope){
                   if(k >=3){
                       
                       addSegment(p,helpArray[j-1],preSlope);
                         
                   }
                   k = 1;             
                   preSlope = curSlope;
               }else{
                   
                   k++;
               }
               if(j == len -1){
                   if(k >= 3){
                       
                       addSegment(p,helpArray[j],curSlope);
                   }
                   
               }
               j++;
           }
       }
       
       
       
       
   }
   public int numberOfSegments()        // the number of line segments
   {
       return lineSegmentNum;
   }
   public LineSegment[] segments()                // the line segments
   {
       LineSegment[] ret = new LineSegment[lineSegmentNum];
       for(int i = 0;i<lineSegmentNum;i++){
           ret[i] = segments[i].segment;
       }
       return ret;
   }
   
   public static void main(String[] args) {
         int n = Integer.parseInt(StdIn.readString());
         //StdOut.printf("%d\n",n);
         Point pts[] = new Point[n];
         for(int i = 0;i<n;i++){
              if(StdIn.isEmpty()){
                 throw new java.lang.IllegalArgumentException();
             }
             int x = Integer.parseInt(StdIn.readString());
             int y = Integer.parseInt(StdIn.readString());
             //StdOut.printf("%d:%d,%d\n",i,x,y);
             pts[i] = new Point(x,y);
         }
         
         FastCollinearPoints bcp = new FastCollinearPoints(pts);
         StdOut.printf("numberOfSegments：%d",bcp.numberOfSegments());
         
         // draw the points
         StdDraw.enableDoubleBuffering();
         StdDraw.setXscale(0, 32768);
         StdDraw.setYscale(0, 32768);
         for (Point p : pts) {
             p.draw();
         }
         StdDraw.show();

         // print and draw the line segments
         //FastCollinearPoints collinear = new FastCollinearPoints(points);
         for (LineSegment segment : bcp.segments()) {
             StdOut.println(segment);
             segment.draw();
         }
         StdDraw.show();
     }
}