import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdDraw;
public class BruteCollinearPoints {
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
               //StdOut.printf("return!!!=========\n");
               return;

           }
           
       }
       segments[lineSegmentNum++] = new MyLineSegment(p1,p2,slope);
   }
   public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
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
      
       for(int i = 0;i<len - 3;i++){
           Arrays.sort(points,i,len);
           Point p = points[i];
           Comparator<Point> com = p.slopeOrder();
           int j = i+1;
           Arrays.sort(points,j,len,com);
          
          
           int k = 0;
           
           double preSlope =  p.slopeTo(points[j]);
           while(j<len){
               double curSlope = p.slopeTo(points[j]);
               //StdOut.printf("i:%d j:%d,preSlope:%f,curSlope:%f,k:%d pt:%s\n",i,j,preSlope,curSlope,k,points[j].toString());
               if(preSlope != curSlope){
                   if(k >=3){
                       //StdOut.printf("add!!\n");
                       addSegment(p,points[j-1],preSlope);
                         
                   }
                   k = 1;             
                   preSlope = curSlope;
               }else{
                   
                   k++;
               }
               if(j == len -1){
                   if(k >= 3){
                      // StdOut.printf("add!!\n");
                       addSegment(p,points[j],curSlope);
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
         
         BruteCollinearPoints bcp = new BruteCollinearPoints(pts);
         //StdOut.printf("numberOfSegments：%d",bcp.numberOfSegments());
         
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