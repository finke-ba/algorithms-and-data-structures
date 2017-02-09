package fundamentals.unionfind;

import libs.StdIn;
import libs.StdOut;

// N^2
public class QuickUnionUF {
  private int[] parent;  // parent[i] = parent of i
  private int count;     // number of components

  /**
   * Initializes an empty union–find data structure with {@code n} sites
   * {@code 0} through {@code n-1}. Each site is initially in its own
   * component.
   *
   * @param  n the number of sites
   */
  public QuickUnionUF(int n) {
    parent = new int[n];
    count = n;
    for (int i = 0; i < n; i++) {
      parent[i] = i;
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
   * @param  p the integer representing one site
   * @param  q the integer representing the other site
   * @return {@code true} if the two sites {@code p} and {@code q} are in the same component;
   *         {@code false} otherwise
   */
  public boolean connected(int p, int q) {
    return find(p) == find(q);
  }

  /**
   * Returns the component identifier for the component containing site {@code p}.
   *
   * @param  p the integer representing one object
   * @return the component identifier for the component containing site {@code p}
   */
  public int find(int p) {
    while (p != parent[p]) {
      p = parent[p];
    }
    return p;
  }

  /**
   * Merges the component containing site {@code p} with the
   * the component containing site {@code q}.
   *
   * @param  p the integer representing one site
   * @param  q the integer representing the other site
   */
  public void union(int p, int q) {
    int rootP = find(p);
    int rootQ = find(q);
    if (rootP == rootQ) return;
    parent[rootP] = rootQ;
    count--;
  }
  /**
   * Reads in a sequence of pairs of integers (between 0 and n-1) from standard input,
   * where each integer represents some object;
   * if the sites are in different components, merge the two components
   * and print the pair to standard output.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    int n = StdIn.readInt();
    QuickUnionUF uf = new QuickUnionUF(n);
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
