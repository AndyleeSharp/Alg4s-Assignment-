import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation_backwash {
   private WeightedQuickUnionUF _uf;
   private final int _virtualTopIndex = 0;
   private final int _virtualBottemIndex = 1;
   private int _lineSize;
   private int _total;
   private boolean _openSites[];
   private int _openCount = 0;
   private int calIndex(int row,int cal){
       return (row - 1)*_lineSize + (cal - 1) + 2;
   }
   
   private int getUpperIndex(int index){
       if(index < _lineSize + 2){
           return _virtualTopIndex;
       }
       index -= _lineSize;
       if(index < 2){
           return -1;
       }
       return index;
   }
   
   private int getUnderIndex(int index){
       if(index >= _total - _lineSize){
           return _virtualBottemIndex;
       }
       index += _lineSize;
       if(index >= _total){
           return -1;
       }
       return index;
   }
   
   private int getLeftIndex(int index){
       if(index == 2){
           return -1;
       }
       int row = (index - 2)/_lineSize;
       index -= 1;
       int newRow = (index - 2)/_lineSize;
       if(row!=newRow){
           return -1;
       }
       return index;
   }
   
    private int getRightIndex(int index){
       if(index == _total - 1){
           return -1;
       }
       int row = (index - 2)/_lineSize;
       index += 1;
       int newRow = (index - 2)/_lineSize;
       if(row!=newRow){
           return -1;
       }
       return index;
   }
    
    private void checkRange(int row,int col){
        if(row < 1 || row > _lineSize || col < 1|| col > _lineSize){
            throw new java.lang.IllegalArgumentException();
        }
    }
   
   public Percolation_backwash(int n)                // create n-by-n grid, with all sites blocked
   {
       if(n<=0){
           throw new java.lang.IllegalArgumentException();
       }
       _lineSize = n;
       _total = n*n+2;
       _uf = new WeightedQuickUnionUF(_total);
       _openSites = new boolean[_total];
       for(int i = 0;i < _total;i++){
           _openSites[i] = false;
       }       
       _openSites[_virtualTopIndex] = true;  
       _openSites[_virtualBottemIndex] = true;    
      
       
   }
   public void open(int row, int col)    // open site (row, col) if it is not open already
   {
       checkRange(row,col);
       int index = calIndex(row,col);
       if(_openSites[index]){
           return;
       }
       _openCount++;
       _openSites[index] = true;
       
       int nearIndex = getUpperIndex(index);
       if(nearIndex!=-1 && _openSites[nearIndex]){           
           _uf.union(index,nearIndex);
       }
       nearIndex = getUnderIndex(index);
       if(nearIndex!=-1 && _openSites[nearIndex]){          
           _uf.union(index,nearIndex);
       }
       nearIndex = getLeftIndex(index);
       if(nearIndex!=-1 && _openSites[nearIndex]){          
           _uf.union(index,nearIndex);
       }
       nearIndex = getRightIndex(index);
       if(nearIndex!=-1 && _openSites[nearIndex]){          
           _uf.union(index,nearIndex);
       }
   }
   public boolean isOpen(int row, int col)  // is site (row, col) open?
   {
       checkRange(row,col);
       int index = calIndex(row,col);
       return  _openSites[index];     
   }
   public boolean isFull(int row, int col)  // is site (row, col) full?
   {
       checkRange(row,col);
       int index = calIndex(row,col);
       return (_openSites[index] && _uf.connected(index,_virtualTopIndex));
   }
   public int numberOfOpenSites()       // number of open sites
   {
       return _openCount;
   }
   public boolean percolates()              // does the system percolate?
   {
       return _uf.connected(_virtualTopIndex,_virtualBottemIndex);
   }
   public static void main(String[] args)   // test client (optional)
   {
   }
}