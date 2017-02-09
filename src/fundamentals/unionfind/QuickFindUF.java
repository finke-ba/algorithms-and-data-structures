package fundamentals.unionfind;

import libs.StdIn;
import libs.StdOut;

// N^2
public class QuickFindUF {
  private int[] id;    // id[i] = component identifier of i
  private int count;   // number of components

  /**
   * Initializes an empty union–find data structure with {@code n} sites
   * {@code 0} through {@code n-1}. Each site is initially in its own
   * component.
   *
   * @param n the number of sites
   */
  public QuickFindUF(int n) {
    count = n;
    id = new int[n];
    for (int i = 0; i < n; i++) {
      id[i] = i;
    }
  }

  /**
   * Returns the number of components.
   *
   * @return the number of components (between {@code 1} and {@code n})
   */
  public int count() {
    return count;
  }

  /**
   * Returns true if the the two sites are in the same component.
   *
   * @param p the integer representing one site
   * @param q the integer representing the other site
   * @return {@code true} if the two sites {@code p} and {@code q} are in the same component;
   *         {@code false} otherwise
   */
  public boolean connected(int p, int q) {
    return find(p) == find(q);
  }

  /**
   * Returns the component identifier for the component containing site {@code p}.
   *
   * @param p the integer representing one site
   * @return the component identifier for the component containing site {@code p}
   */
  public int find(int p) {
    return id[p];
  }

  /**
   * Merges the component containing site {@code p} with the
   * the component containing site {@code q}.
   *
   * @param p the integer representing one site
   * @param q the integer representing the other site
   */
  public void union(int p, int q) {
    int pID = find(p);
    int qID = find(q);

    if (pID == qID) return;

    for (int i = 0; i < id.length; i++) {
      if (id[i] == pID) {
        id[i] = qID;
      }
    }
    count--;

  }


  /**
   * Reads in a sequence of pairs of integers (between 0 and n-1) from standard input,
   * where each integer represents some site;
   * if the sites are in different components, merge the two components
   * and print the pair to standard output.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    int n = StdIn.readInt();
    QuickFindUF uf = new QuickFindUF(n);
    while (!StdIn.isEmpty()) {
      int p = StdIn.readInt();
      int q = StdIn.readInt();
      if (uf.connected(p, q)) continue;

      uf.union(p, q);
      StdOut.println(p + " " + q);
    }
    StdOut.println(uf.count() + " components");
  }

}
