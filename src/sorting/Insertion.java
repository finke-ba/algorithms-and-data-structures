package sorting;

import libs.StdIn;
import libs.StdOut;

public class Insertion {

  /**
   * Rearranges the array in ascending order, using the natural order.
   *
   * @param a the array to be sorted
   */
  public static void sort(Comparable[] a) {
    //sort a[] ascending
    int n = a.length;
    for (int i = 0; i < n; i++) {
      for (int j = i; j > 0 && less(a[j], a[j-1]); j--) {
        exch(a, j, j-1);
      }
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
