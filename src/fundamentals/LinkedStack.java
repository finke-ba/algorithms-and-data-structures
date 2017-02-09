package fundamentals;

import libs.StdIn;
import libs.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedStack<Item> implements Iterable<Item>{
  private int  n;      //size of the stack
  private Node first; //top of stack(node added last)

  // helper linked list class
  private class Node {
    private Item item;
    private Node next;
  }

  /**
   * Is this stack empty?
   * @return true if this stack is empty; false otherwise
   */
  public boolean isEmty() {
    return first == null;
  }

  /**
   * Returns the number of items in the stack.
   * @return the number of items in the stack
   */
  public int size() {
    return n;
  }

  /**
   * Adds the item to this stack.
   * @param item the item to add
   */
  public void push(Item item) {
    Node oldFirst = first;
    first = new Node();
    first.item = item;
    first.next = oldFirst;
    n++;
  }

  /**
   * Removes and returns the item most recently added to this stack.
   * @return the item most recently added
   * @throws java.util.NoSuchElementException if this stack is empty
   */
  public Item pop() {
    if (isEmty()) throw new NoSuchElementException("Stack underflow");
    Item item = first.item; //save item to return
    first = first.next;     //declare first node
    n--;
    return item;            //returned the saved item
  }

  /**
   * Returns an iterator to this stack that iterates through the items in LIFO order.
   * @return an iterator to this stack that iterates through the items in LIFO order.
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
   * Unit tests the {@code LinkedStack} data type.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    LinkedStack<String> stack = new LinkedStack<>();
    while (!StdIn.isEmpty()){
      String item = StdIn.readString();
      if (!item.equals("-")) {
        stack.push(item);
      } else if (!stack.isEmty()) {
        StdOut.print(stack.pop() + " ");
      }
    }
    StdOut.println("(" + stack.size() + " left on stack)");
  }

}
