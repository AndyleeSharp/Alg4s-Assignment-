import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
public class Deque<Item> implements Iterable<Item> {
    private class Node<Item>{
        Item item;
        Node<Item> pre;
        Node<Item> next;
    } 
    
    private Node<Item> first;
    private Node<Item> last;
    private int count = 0;
   public Deque()                           // construct an empty deque
   {
       first = new Node<Item>();
       last = new Node<Item>();
       first.pre = null;
       first.next = last;
       last.pre = first;
       last.next = null;
   }
   public boolean isEmpty()                 // is the deque empty?
   {
       return first.next == last;
   }
   public int size()                        // return the number of items on the deque
   {
       return count;
   }
   public void addFirst(Item item)          // add the item to the front
   {
       if(item == null){
           throw new java.lang.IllegalArgumentException();
       }
       
       Node<Item> n = new Node<Item>();
       n.item = item;
       Node<Item> oldNext = first.next;
       first.next = n;
       n.pre = first;
       oldNext.pre = n;
       n.next = oldNext;
       count++;
   }
   public void addLast(Item item)           // add the item to the end
   {
        if(item == null){
           throw new java.lang.IllegalArgumentException();
       }
       
       Node<Item> n = new Node<Item>();
       n.item = item;
       Node<Item> oldPre = last.pre;
       last.pre = n;
       n.next = last;
       oldPre.next = n;
       n.pre = oldPre;
       count++;
   }
   public Item removeFirst()                // remove and return the item from the front
   {
       if(isEmpty()){
           throw new java.util.NoSuchElementException();
       }
       Node<Item> oldNext = first.next;
       first.next = oldNext.next;
       oldNext.next.pre = first;
       count--;
       return oldNext.item;
       
   }
   public Item removeLast()                 // remove and return the item from the end
   {
       if(isEmpty()){
           throw new java.util.NoSuchElementException();
       }
       Node<Item> oldPre = last.pre;
       last.pre = oldPre.pre;
       oldPre.pre.next = last;
       count--;
       return oldPre.item;
   }
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   {
       return new DequeIterator();
   }
   
   private class DequeIterator implements Iterator<Item>{
       private Node<Item> current = first;
       public boolean hasNext(){
           return current.next!=last;
       }
       public void remove(){
           throw new java.lang.UnsupportedOperationException();
       }
       public Item next(){
           if(current.next == last){
               throw new java.util.NoSuchElementException();
           }
           current = current.next;
           return current.item;
       }
   }
   
   public static void main(String[] args)   // unit testing (optional)
   {
       Deque<Integer> de = new Deque<Integer>();
       for(int i = 0;i<3;i++){
           de.addFirst(i);
       }
       
       for(int i:de)
       {
           StdOut.printf("%s\n",i);
       }
       //StdOut.print(de.removeLast());
   }
}