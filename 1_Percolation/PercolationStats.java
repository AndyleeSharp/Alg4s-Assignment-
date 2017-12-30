import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
public class PercolationStats {
    
   private double _mean;
   private double _stddev;
   private double _confidenceLo;
   private double _confidenceHi;
   private int _trials;
   
   private double calThreshold(int n)
   {
       int blockIndex = 0;
       int total = n*n;
       int blockSites[] = new int[total];
       for(int i = 0;i<total;i++)
       {
           blockSites[i] = i;
       }
       StdRandom.shuffle(blockSites);
       Percolation p = new Percolation(n);
       int index,row,col;
       while(!p.percolates()){          
           index = blockSites[blockIndex++];           
           row = index/n + 1;
           col = index%n + 1;          
           p.open(row,col);
       }
       double x = p.numberOfOpenSites()*1.0/total;
       return x;
       
   }
   
   public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
   {
       if(n<=0 || trials <=0)
       {
           throw new java.lang.IllegalArgumentException();
       }
       _trials = trials;
       double xArray[] = new double[trials];
       for(int i = 0;i<trials;i++)
       {
           xArray[i] = calThreshold(n);
       }
       _mean = StdStats.mean(xArray);
       _stddev = StdStats.stddev(xArray);
       double t = Math.sqrt(trials);
       _confidenceLo = _mean - 1.96*_stddev/t;
       _confidenceHi = _mean + 1.96*_stddev/t;
       
   }
   public double mean()                          // sample mean of percolation threshold
   {
       return _mean;
   }
   public double stddev()                        // sample standard deviation of percolation threshold
   {
       return _stddev;
   }
   public double confidenceLo()                  // low  endpoint of 95% confidence interval
   {
       return _confidenceLo;
   }
   public double confidenceHi()                  // high endpoint of 95% confidence interval
   {
       return _confidenceHi;
   }

   public static void main(String[] args)        // test client (described below)
   {
       int n = Integer.parseInt(args[0]);
       int trials = Integer.parseInt(args[1]);
       PercolationStats ps = new PercolationStats(n, trials);
       StdOut.printf("mean                   =%f\n",ps.mean());
       StdOut.printf("stddev                 =%f\n",ps.stddev());
       StdOut.printf("95%% confidence interval= [%f, %f]\n",ps.confidenceLo(),ps.confidenceHi());
       
   }
}