package sorting;

import libs.StdIn;
import libs.StdOut;
import libs.Transaction;

import java.util.Stack;

/**
 * The {@code BottomM} class provides a client that reads a sequence of
 * transactions from standard input and prints the <em>m</em> smallest ones
 * to standard output.
 *
 * Text for testing:
 * Turing      6/17/1990   644.08
 * vonNeumann  3/26/2002  4121.85
 * Dijkstra    8/22/2007  2678.40
 * vonNeumann  1/11/1999  4409.74
 * Dijkstra   11/18/1995   837.42
 * Hoare       5/10/1993  3229.27
 * vonNeumann  2/12/1994  4732.35
 * Hoare       8/18/1992  4381.21
 * Turing      1/11/2002    66.10
 * Thompson    2/27/2000  4747.08
 * Turing      2/11/1991  2156.86
 * Hoare       8/12/2003  1025.70
 * vonNeumann 10/13/1993  2520.97
 * Dijkstra    9/10/2000   708.95
 * Turing     10/12/1993  3532.36
 * Hoare       2/10/2005  4050.20
 *
 */
public class BottomM {

  /**
   *  Reads a sequence of transactions from standard input; takes a
   *  command-line integer m; prints to standard output the m smallest
   *  transactions in descending order.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    int m  = Integer.parseInt(args[0]);
    MaxPQ<Transaction> pq = new MaxPQ<>(m + 1);

    while (StdIn.hasNextLine()) {
      // Create an entry from the next line and put on the PQ.
      String line = StdIn.readLine();
      if (line.isEmpty()) break;
      Transaction transaction = new Transaction(line);
      pq.insert(transaction);

      // remove maximum if m+1 entries on the PQ
      if (pq.size() > m)
        pq.delMax();
    }   // bottom m entries are on the PQ

    // print entries on PQ in reverse order
    Stack<Transaction> stack = new Stack<>();
    while (!pq.isEmpty()) stack.push(pq.delMax());
    for (Transaction transaction : stack)
      StdOut.println(transaction);
  }
}
