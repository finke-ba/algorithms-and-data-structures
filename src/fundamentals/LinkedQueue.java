package fundamentals;

import libs.StdIn;
import libs.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedQueue<Item> implements Iterable<Item>{
  private int  n;     // number of elements on queue
  private Node first; // beginning of queue
  private Node last;  // end of queue

  // helper linked list class
  private class Node {
    private Item item;
    private Node next;
  }

  /**
   * Is this queue empty?
   * @return true if this queue is empty; false otherwise
   */
  public boolean isEmpty() {
    return first == null;
  }

  /**
   * Returns the number of items in this queue.
   * @return the number of items in this queue
   */
  public int size() {
    return n;
  }

  /**
   * Adds the item to this queue.
   * @param item the item to add
   */
  public void enqueue (Item item) {
    Node oldLast = last;
    last = new Node();
    last.item = item;
    last.next = null;
    if (isEmpty()) {
      first = last;
    } else {
      oldLast.next = last;
    }
    n++;
  }

  public Item dequeue() {
    if (isEmpty()) throw new NoSuchElementException("Queue underflow");
    Item item = first.item;
    first = first.next;
    n--;
    if (isEmpty()) {
      last = null;
    }
    return item;
  }

  /**
   * Returns an iterator that iterates over the items in this queue in FIFO order.
   * @return an iterator that iterates over the items in this queue in FIFO order
   */
  @Override
  public Iterator<Item> iterator() {
    return new ListIterator();
  }

  private class ListIterator implements Iterator<Item> {
    private Node current = first;

    @Override
    public boolean hasNext() {
      return current != null;
    }

    @Override
    public Item next() {
      if (!hasNext()) throw new NoSuchElementException();
      Item item = current.item;
      current = current.next;
      return item;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  /**
   * Unit tests the {@code LinkedQueue} data type.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    LinkedQueue<String> queue = new LinkedQueue<String>();
    while (!StdIn.isEmpty()) {
      String item = StdIn.readString();
      if (!item.equals("-"))
        queue.enqueue(item);
      else if (!queue.isEmpty())
        StdOut.print(queue.dequeue() + " ");
    }
    StdOut.println("(" + queue.size() + " left on queue)");
  }

}
