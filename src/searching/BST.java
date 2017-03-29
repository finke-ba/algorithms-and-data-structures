package searching;

import fundamentals.Queue;
import libs.StdIn;
import libs.StdOut;

import java.util.NoSuchElementException;

/**
 *  The {@code BST} class represents an ordered symbol table of generic
 *  key-value pairs.
 *  <p>
 *  A symbol table implements the <em>associative array</em> abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be {@code null}—setting the
 *  value associated with a key to {@code null} is equivalent to deleting the key
 *  from the symbol table.
 *  <p>
 *  This implementation uses an (unbalanced) binary search tree. It requires that
 *  the key type implements the {@code Comparable} interface and calls the
 *  {@code compareTo()} and method to compare two keys. It does not call either
 *  {@code equals()} or {@code hashCode()}.
 *  The <em>put</em>, <em>contains</em>, <em>remove</em>, <em>minimum</em>,
 *  <em>maximum</em>, <em>ceiling</em>, <em>floor</em>, <em>select</em>, and
 *  <em>rank</em>  operations each take
 *  linear time in the worst case, if the tree becomes unbalanced.
 *  The <em>size</em>, and <em>is-empty</em> operations take constant time.
 *  Construction takes constant time.
 */
public class BST<Key extends Comparable<Key>, Value> {
  private Node root;           // root of BST

  private class Node {
    private Key key;           // sorted by key
    private Value val;         // associated data
    private Node left, right;  // left and right subtrees
    private int size;          // number of nodes in subtree

    public Node(Key key, Value val, int size) {
      this.key = key;
      this.val = val;
      this.size = size;
    }
  }

  /**
   * Returns true if this symbol table is empty.
   * @return {@code true} if this symbol table is empty; {@code false} otherwise
   */
  public boolean isEmpty() {
    return size() == 0;
  }

  /**
   * Does this symbol table contain the given key?
   *
   * @param  key the key
   * @return {@code true} if this symbol table contains {@code key} and
   *         {@code false} otherwise
   * @throws IllegalArgumentException if {@code key} is {@code null}
   */
  public boolean contains(Key key) {
    if (key == null) throw new IllegalArgumentException("argument to contains() is null");
    return get(key) != null;
  }

  /**
   * Returns the number of key-value pairs in this symbol table.
   * @return the number of key-value pairs in this symbol table
   */
  public int size() {
    return size(root);
  }

  // return number of key-value pairs in BST rooted at x
  private int size(Node x) {
    if (x == null) return 0;
    else return x.size;
  }


  /**
   * Returns the value associated with the given key.
   *
   * @param  key the key
   * @return the value associated with the given key if the key is in the symbol table
   *         and {@code null} if the key is not in the symbol table
   * @throws IllegalArgumentException if {@code key} is {@code null}
   */
  public Value get(Key key) {
    return get(root, key);
  }

  private Value get(Node x, Key key) {
    if (key == null) throw new IllegalArgumentException("called get() with a null key");
    if (x == null) return null;
    int cmp = key.compareTo(x.key);
    if      (cmp < 0) return get(x.left, key);
    else if (cmp > 0) return get(x.right, key);
    else              return x.val;
  }


  /**
   * Inserts the specified key-value pair into the symbol table, overwriting the old
   * value with the new value if the symbol table already contains the specified key.
   *
   * @param  key the key
   * @param  val the value
   * @throws IllegalArgumentException if {@code key} is {@code null}
   */
  public void put(Key key, Value val) {
    root = put(root, key, val);
  }

  //if key contains in subtree with root x then its value changes to val
  //else add new node with key - 'key' and value - 'val' to subtree
  private Node put(Node x, Key key, Value val) {
    if (key == null) throw new IllegalArgumentException("called put() with a null key");
    if (x == null) return new Node(key, val, 1);
    int cmp = key.compareTo(x.key);
    if      (cmp < 0) x.left  = put(x.left,  key, val);
    else if (cmp > 0) x.right = put(x.right, key, val);
    else              x.val   = val;
    x.size = 1 + size(x.left) + size(x.right);
    return x;
  }

  /**
   * Returns the smallest key in the symbol table.
   *
   * @return the smallest key in the symbol table
   * @throws NoSuchElementException if the symbol table is empty
   */
  public Key min() {
    if (isEmpty()) throw new NoSuchElementException("called min() with empty symbol table");
    return min(root).key;
  }

  private Node min(Node x) {
    if (x.left == null) return x;
    else                return min(x.left);
  }

  /**
   * Returns the largest key in the symbol table.
   *
   * @return the largest key in the symbol table
   * @throws NoSuchElementException if the symbol table is empty
   */
  public Key max() {
    if (isEmpty()) throw new NoSuchElementException("called max() with empty symbol table");
    return max(root).key;
  }

  private Node max(Node x) {
    if (x.right == null) return x;
    else                 return max(x.right);
  }

  /**
   * Returns the largest key in the symbol table less than or equal to {@code key}.
   *
   * @param  key the key
   * @return the largest key in the symbol table less than or equal to {@code key}
   * @throws NoSuchElementException if there is no such key
   * @throws IllegalArgumentException if {@code key} is {@code null}
   */
  public Key floor(Key key) {
    if (key == null) throw new IllegalArgumentException("argument to floor() is null");
    if (isEmpty()) throw new NoSuchElementException("called floor() with empty symbol table");
    Node x = floor(root, key);
    if (x == null) return null;
    else return x.key;
  }

  private Node floor(Node x, Key key) {
    if (x == null) return null;
    int cmp = key.compareTo(x.key);
    if (cmp == 0) return x;
    if (cmp <  0) return floor(x.left, key);
    Node t = floor(x.right, key);
    if (t != null) return t;
    else return x;
  }

  /**
   * Returns the smallest key in the symbol table greater than or equal to {@code key}.
   *
   * @param  key the key
   * @return the smallest key in the symbol table greater than or equal to {@code key}
   * @throws NoSuchElementException if there is no such key
   * @throws IllegalArgumentException if {@code key} is {@code null}
   */
  public Key ceiling(Key key) {
    if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
    if (isEmpty()) throw new NoSuchElementException("called ceiling() with empty symbol table");
    Node x = ceiling(root, key);
    if (x == null) return null;
    else return x.key;
  }

  private Node ceiling(Node x, Key key) {
    if (x == null) return null;
    int cmp = key.compareTo(x.key);
    if (cmp == 0) return x;
    if (cmp < 0) {
      Node t = ceiling(x.left, key);
      if (t != null) return t;
      else return x;
    }
    return ceiling(x.right, key);
  }

  /**
   * Return the kth smallest key in the symbol table.
   *
   * @param  k the order statistic
   * @return the {@code k}th smallest key in the symbol table
   * @throws IllegalArgumentException unless {@code k} is between 0 and
   *        <em>n</em>–1
   */
  public Key select(int k) {
    if (k < 0 || k >= size()) {
      throw new IllegalArgumentException("called select() with invalid argument: " + k);
    }
    Node x = select(root, k);
    return x.key;
  }

  // Return key of rank k.
  private Node select(Node x, int k) {
    if (x == null) return null;
    int t = size(x.left);
    if      (t > k) return select(x.left,  k);
    else if (t < k) return select(x.right, k-t-1);
    else            return x;
  }

  /**
   * Return the number of keys in the symbol table strictly less than {@code key}.
   *
   * @param  key the key
   * @return the number of keys in the symbol table strictly less than {@code key}
   * @throws IllegalArgumentException if {@code key} is {@code null}
   */
  public int rank(Key key) {
    if (key == null) throw new IllegalArgumentException("argument to rank() is null");
    return rank(key, root);
  }

  // Number of keys in the subtree less than key.
  private int rank(Key key, Node x) {
    if (x == null) return 0;
    int cmp = key.compareTo(x.key);
    if      (cmp < 0) return rank(key, x.left);
    else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
    else              return size(x.left);
  }

  /**
   * Removes the smallest key and associated value from the symbol table.
   *
   * @throws NoSuchElementException if the symbol table is empty
   */
  public void deleteMin() {
    if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
    root = deleteMin(root);
  }

  private Node deleteMin(Node x) {
    if (x.left == null) return x.right;
    x.left = deleteMin(x.left);
    x.size = size(x.left) + size(x.right) + 1;
    return x;
  }

  /**
   * Removes the largest key and associated value from the symbol table.
   *
   * @throws NoSuchElementException if the symbol table is empty
   */
  public void deleteMax() {
    if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
    root = deleteMax(root);
  }

  private Node deleteMax(Node x) {
    if (x.right == null) return x.left;
    x.right = deleteMax(x.right);
    x.size = size(x.left) + size(x.right) + 1;
    return x;
  }

  /**
   * Removes the specified key and its associated value from this symbol table
   * (if the key is in this symbol table).
   *
   * @param  key the key
   * @throws IllegalArgumentException if {@code key} is {@code null}
   */
  public void delete(Key key) {
    if (key == null) throw new IllegalArgumentException("called delete() with a null key");
    root = delete(root, key);
  }

  private Node delete(Node x, Key key) {
    if (x == null) return null;

    int cmp = key.compareTo(x.key);
    if      (cmp < 0) x.left  = delete(x.left,  key);
    else if (cmp > 0) x.right = delete(x.right, key);
    else {
      if (x.right == null) return x.left;
      if (x.left  == null) return x.right;
      Node t = x;
      x = min(t.right);
      x.right = deleteMin(t.right);
      x.left = t.left;
    }
    x.size = size(x.left) + size(x.right) + 1;
    return x;
  }

  /**
   * Returns all keys in the symbol table as an {@code Iterable}.
   * To iterate over all of the keys in the symbol table named {@code st},
   * use the foreach notation: {@code for (Key key : st.keys())}.
   *
   * @return all keys in the symbol table
   */
  public Iterable<Key> keys() {
    return keys(min(), max());
  }

  /**
   * Returns all keys in the symbol table in the given range,
   * as an {@code Iterable}.
   *
   * @param  lo minimum endpoint
   * @param  hi maximum endpoint
   * @return all keys in the symbol table between {@code lo}
   *         (inclusive) and {@code hi} (inclusive)
   * @throws IllegalArgumentException if either {@code lo} or {@code hi}
   *         is {@code null}
   */
  public Iterable<Key> keys(Key lo, Key hi) {
    if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
    if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

    Queue<Key> queue = new Queue<>();
    keys(root, queue, lo, hi);
    return queue;
  }

  private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
    if (x == null) return;
    int cmplo = lo.compareTo(x.key);
    int cmphi = hi.compareTo(x.key);
    if (cmplo < 0) keys(x.left, queue, lo, hi);
    if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
    if (cmphi > 0) keys(x.right, queue, lo, hi);
  }

  /**
   * Returns the number of keys in the symbol table in the given range.
   *
   * @param  lo minimum endpoint
   * @param  hi maximum endpoint
   * @return the number of keys in the symbol table between {@code lo}
   *         (inclusive) and {@code hi} (inclusive)
   * @throws IllegalArgumentException if either {@code lo} or {@code hi}
   *         is {@code null}
   */
  public int size(Key lo, Key hi) {
    if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
    if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

    if (lo.compareTo(hi) > 0) return 0;
    if (contains(hi)) return rank(hi) - rank(lo) + 1;
    else              return rank(hi) - rank(lo);
  }

  /**
   * Returns the height of the BST (for debugging).
   *
   * @return the height of the BST (a 1-node tree has height 0)
   */
  public int height() {
    return height(root);
  }
  private int height(Node x) {
    if (x == null) return -1;
    return 1 + Math.max(height(x.left), height(x.right));
  }

  /**
   * Returns the keys in the BST in level order (for debugging).
   *
   * @return the keys in the BST in level order traversal
   */
  public Iterable<Key> levelOrder() {
    Queue<Key> keys = new Queue<>();
    Queue<Node> queue = new Queue<>();
    queue.enqueue(root);
    while (!queue.isEmpty()) {
      Node x = queue.dequeue();
      if (x == null) continue;
      keys.enqueue(x.key);
      queue.enqueue(x.left);
      queue.enqueue(x.right);
    }
    return keys;
  }

  /**
   * Unit tests the {@code BST} data type.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    BST<String, Integer> st = new BST<>();
    for (int i = 0; !StdIn.isEmpty(); i++) {
      String key = StdIn.readString();
      st.put(key, i);
    }

    for (String s : st.levelOrder())
      StdOut.println(s + " " + st.get(s));

    StdOut.println();

    for (String s : st.keys())
      StdOut.println(s + " " + st.get(s));
  }

}