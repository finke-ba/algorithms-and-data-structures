package sorting;

import java.util.NoSuchElementException;

/**
 * The {@code MaxPQ} class represents a priority queue of generic keys.
 * This implementation uses a binary heap.
 * The <em>insert</em> and <em>delete-the-maximum</em> operations take logarithmic amortized time.
 * The <em>max</em>, <em>size</em>, and <em>is-empty</em> operations take constant time.
 */
public class MaxPQ<Key extends Comparable<Key>> {
  private Key[] pq;  // store items at indices 1 to n
  private int n = 0; // number of items on priority queue

  /**
   * Initializes an empty priority queue with the given initial capacity.
   *
   * @param  initCapacity the initial capacity of this priority queue
   */
  public MaxPQ(int initCapacity) {
    pq = (Key[]) new Comparable[initCapacity + 1];
  }

  /**
   * Initializes an empty priority queue.
   */
  public MaxPQ() {
    this(1);
  }

  /**
   * Returns true if this priority queue is empty.
   *
   * @return {@code true} if this priority queue is empty;
   *         {@code false} otherwise
   */
  public boolean isEmpty() {
    return n == 0;
  }

  /**
   * Returns the number of keys on this priority queue.
   *
   * @return the number of keys on this priority queue
   */
  public int size() {
    return n;
  }

  /**
   * Returns a largest key on this priority queue.
   *
   * @return a largest key on this priority queue
   * @throws NoSuchElementException if this priority queue is empty
   */
  public Key max() {
    if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
    return pq[1];
  }

  /**
   * Adds a new key to this priority queue.
   *
   * @param  x the new key to add to this priority queue
   */
  public void insert(Key x) {

    // double size of array if necessary
    if (n >= pq.length - 1) resize(2 * pq.length);

    // add x, and percolate it up to maintain heap invariant
    pq[++n] = x;
    swim(n);
  }

  /**
   * Removes and returns a largest key on this priority queue.
   *
   * @return a largest key on this priority queue
   * @throws NoSuchElementException if this priority queue is empty
   */
  public Key delMax() {
    if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");

    Key max = pq[1];
    exch(1, n--);
    pq[n+1] = null;     // to avoid loiterig and help with garbage collection
    sink(1);
    if ((n > 0) && (n == (pq.length - 1) / 4)) resize(pq.length / 2);
    return max;
  }


  /***************************************************************************
   * Helper functions to restore the heap invariant.
   ***************************************************************************/

  private void swim(int k) {
    while (k > 1 && less(k/2, k)) {
      exch(k, k/2);
      k = k/2;
    }
  }

  private void sink(int k) {
    while (2*k <= n) {
      int j = 2*k;
      if (j < n && less(j, j+1)) j++;
      if (!less(k, j)) break;
      exch(k, j);
      k = j;
    }
  }

  /***************************************************************************
   * Helper functions for compares and swaps.
   ***************************************************************************/
  private boolean less(int i, int j) {
    return pq[i].compareTo(pq[j]) < 0;
  }

  private void exch(int i, int j) {
    Key swap = pq[i];
    pq[i] = pq[j];
    pq[j] = swap;
  }

  /***************************************************************************
   * Helper function to double the size of the heap array
   ***************************************************************************/
  private void resize(int capacity) {
    Key[] temp = (Key[]) new Comparable[capacity];
    for (int i = 1; i <= n; i++) {
      temp[i] = pq[i];
    }
    pq = temp;
  }

}
