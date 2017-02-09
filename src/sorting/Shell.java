package sorting;

import libs.StdIn;
import libs.StdOut;

public class Shell {
  /**
   * Rearranges the array in ascending order, using the natural order.
   *
   * @param a the array to be sorted
   */
  public static void sort(Comparable[] a) {
    //sort a[] ascending
    int n = a.length;

    // 3x+1 increment sequence:  1, 4, 13, 40, 121, 364, 1093, ...
    int h = 1;
    while (h < n/3) h = 3*h + 1;

    while (h >= 1) {
      // h-sort the array
      for (int i = h; i < n; i ++) {
        //insertion a[i] between a[i-2*h], a[i-3*h]...
        for (int j = i; j >= h && less(a[j], a[j-h]); j -=h) {
          exch(a, j, j-h);
        }
      }
      h = h/3;
    }
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
   * Reads in a sequence of strings from standard input; selection sorts them;
   * and prints them to standard output in ascending order.
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
