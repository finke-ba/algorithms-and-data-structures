package sorting;

import libs.StdIn;
import libs.StdOut;
import libs.StdRandom;

/**
 * @author Finkelshteyn
 */
public class Quick {

  /**
   * Rearranges the array in ascending order, using the natural order.
   * @param a the array to be sorted
   */
  public static void sort(Comparable[] a) {
    StdRandom.shuffle(a);
    sort(a, 0, a.length - 1);
  }

  private static void sort(Comparable[] a, int lo, int hi) {
    if (hi <= lo) return;
    int j = partition(a, lo, hi);
    sort(a, lo, j-1);
    sort(a, j+1, hi);
  }


  // partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
  // and return the index j.
  private static int partition(Comparable[] a, int lo, int hi) {
    int i = lo;
    int j = hi + 1;
    Comparable v = a[lo];
    while (true) {
      // find item on lo to swap
      while (less(a[++i], v)) if (i == hi) break;
      // find item on hi to swap
      while (less(v, a[--j])) if (j == lo) break;

      // check if pointers cross
      if (i >= j) break;
      exch(a, i, j);
    }
    // put partitioning item v at a[j]
    exch(a, lo, j);
    // now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
    return j;
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
