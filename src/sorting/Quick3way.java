package sorting;

import libs.StdIn;
import libs.StdOut;
import libs.StdRandom;

public class Quick3way {

  /**
   * Rearranges the array in ascending order, using the natural order.
   * @param a the array to be sorted
   */
  public static void sort(Comparable[] a) {
    StdRandom.shuffle(a);
    sort(a, 0, a.length - 1);
  }

  // quicksort the subarray a[lo .. hi] using 3-way partitioning
  private static void sort(Comparable[] a, int lo, int hi) {
    if (hi <= lo) return;
    int lt = lo;
    int i = lo + 1;
    int gt = hi;
    Comparable v = a[lo];
    while (i <= gt) {
      int cmp = a[i].compareTo(v);
      if      (cmp < 0) exch(a, lt++, i++);
      else if (cmp > 0) exch(a, i, gt--);
      else              i++;
    }
    // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi].
    sort(a, lo, lt-1);
    sort(a, gt+1, hi);
  }


  /***************************************************************************
   *  Helper functions.
   ***************************************************************************/

  // is v < w ?
  private static boolean less(Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }

  // exchange a[i] and a[j]
  private static void exch(Comparable[] a, int i, int j) {
    Comparable swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }

  // is the array a[] sorted?
  private static boolean isSorted(Comparable[] a) {
    for (int i = 1; i < a.length; i++)
      if (less(a[i], a[i-1])) return false;
    return true;
  }


  // print array to standard output
  private static void show(Comparable[] a) {
    for (int i = 0; i < a.length; i++) {
      StdOut.print(a[i] + " ");
    }
    StdOut.println();
  }

  /***************************************************************************/

  /**
   * Reads in a sequence of strings from standard input; quicksorts them;
   * and prints them to standard output in ascending order.
   * Shuffles the array and then prints the strings again to
   * standard output, but this time, using the select method.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    String[] a = StdIn.readAllStrings();
    sort(a);
    assert isSorted(a);
    show(a);
  }

}
