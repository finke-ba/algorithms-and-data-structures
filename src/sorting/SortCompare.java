package sorting;

import libs.StdIn;
import libs.StdOut;
import libs.StdRandom;
import libs.Stopwatch;

import java.util.Arrays;

public class SortCompare {

  public static double time(String alg, Double[] a) {
    Stopwatch sw = new Stopwatch();
    if      (alg.equals("Selection"))       Selection.sort(a); //N2
    else if (alg.equals("Insertion"))       Insertion.sort(a); //N2
    else if (alg.equals("Shell"))           Shell.sort(a); // N7/6, N4/3, depends on selected steps
    else if (alg.equals("Merge"))           Merge.sort(a); // NLgN
    else if (alg.equals("MergeBU"))         MergeBU.sort(a); // NLgN
    else if (alg.equals("Quick"))           Quick.sort(a); //NLgN
    else if (alg.equals("Quick3way"))       Quick3way.sort(a); //N when keys are repeated
      //else if (alg.equals("InsertionX"))      InsertionX.sort(a);
    //else if (alg.equals("BinaryInsertion")) BinaryInsertion.sort(a);
    //else if (alg.equals("Bubble"))          Bubble.sort(a);
    //else if (alg.equals("MergeX"))          MergeX.sort(a);
    //else if (alg.equals("QuickX"))          QuickX.sort(a);
    //else if (alg.equals("Heap"))            Heap.sort(a);
    else if (alg.equals("System"))          Arrays.sort(a);
    else throw new IllegalArgumentException("Invalid algorithm: " + alg);
    return sw.elapsedTime();
  }

  // Use alg to sort trials random arrays of length n.
  public static double timeRandomInput(String alg, int n, int trials)  {
    double total = 0.0;
    Double[] a = new Double[n];
    // Perform one experiment (generate and sort an array).
    for (int t = 0; t < trials; t++) {
      for (int i = 0; i < n; i++)
        a[i] = StdRandom.uniform(0.0, 1.0);
      total += time(alg, a);
    }
    return total;
  }

  // Use alg to sort trials random arrays of length n.
  public static double timeSortedInput(String alg, int n, int trials) {
    double total = 0.0;
    Double[] a = new Double[n];
    // Perform one experiment (generate and sort an array).
    for (int t = 0; t < trials; t++) {
      for (int i = 0; i < n; i++)
        a[i] = 1.0 * i;
      total += time(alg, a);
    }
    return total;
  }

  public static void main(String[] args) {
    String alg1 = StdIn.readString();
    String alg2 = StdIn.readString();
    int n = StdIn.readInt();
    int trials = StdIn.readInt();

    double time1 = timeRandomInput(alg1, n, trials);
    double time2 = timeRandomInput(alg2, n, trials);

    StdOut.printf("For %d random Doubles\n    %s is", n, alg1);
    StdOut.printf(" %.1f times faster than %s\n", time2/time1, alg2);
  }


}
