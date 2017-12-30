import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
public class RandomizedQueue<Item> implements Iterable<Item> {
   private Item[] items;
   private int count = 0;
   private int headIndex = 0;
  
   private void resiz(int capacity)
   {
       Item[] newItems = (Item[]) new Object[capacity];
       for(int i = 0 ;i<count;i++)
       {
           newItems[i] = items[i];
       }
       items = newItems;
   }
   public RandomizedQueue()                 // construct an empty randomized queue
   {
       items = (Item[]) new Object[1];
   }
   public boolean isEmpty()                 // is the randomized queue empty?
   {
       return count == 0;
   }
   public int size()                        // return the number of items on the randomized queue
   {
       return count;
   }
   public void enqueue(Item item)           // add the item
   {
       if(item == null){
           throw new java.lang.IllegalArgumentException();
       }
       
       if(count == items.length){
           resiz(count*2);
       }
       items[count++] = item;
      
   }
   public Item dequeue()                    // remove and return a random item
   {
       if(isEmpty()){
            throw new java.util.NoSuchElementException();
       }
       count--;
       int index = count;
       
       
       index = StdRandom.uniform(count+1);
           
       
       Item item = items[index];
       items[index] = items[count];
       
       if(count > 0 && count == items.length/4){
           resiz(items.length/2);
       }
       
       return item;
   }
   public Item sample()                     // return a random item (but do not remove it)
   {
       if(isEmpty()){
            throw new java.util.NoSuchElementException();
       }
       int index = StdRandom.uniform(count);
       return items[index];
   }
   public Iterator<Item> iterator()         // return an independent iterator over items in random order
   {
       return new RandomizedQueueIterator();
   }
   
    private class RandomizedQueueIterator implements Iterator<Item>{
       public RandomizedQueueIterator()
       {
           init();
       }
       
       private void init()
       {
           preCount = count;
           indexes = new int[count];
           for(int i = 0;i<count;i++){
               indexes[i] = i;
           }
           StdRandom.shuffle(indexes);
       }
       
       
       private int preCount = 0;
       private int current = 0;
       private int[] indexes;
       
       public boolean hasNext(){
           
           return current < indexes.length;
       }
       public void remove(){
           throw new java.lang.UnsupportedOperationException();
       }
       public Item next(){
            
           if(current >= indexes.length){
               throw new java.util.NoSuchElementException();
           }
           
           return items[indexes[current++]];
       }
   }
   public static void main(String[] args)   // unit testing (optional)
   {
       RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
       rq.enqueue(10);
       rq.enqueue(20);
       StdOut.printf("%s\n",rq.dequeue());
       StdOut.printf("%s\n",rq.dequeue());
                  
   }
}