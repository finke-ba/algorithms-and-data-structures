package graphs;

import libs.StdIn;
import libs.StdOut;

/**
 *  The {@code DepthFirstSearch} class represents a data type for
 *  determining the vertices connected to a given source vertex <em>s</em>
 *  in an undirected graph.
 *  <p>
 *  This implementation uses depth-first search.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>
 *  (in the worst case),
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  It uses extra space (not including the graph) proportional to <em>V</em>.
 */
public class DepthFirstSearch {
  private boolean[] marked;    // marked[v] = is there an s-v path?
  private int count;           // number of vertices connected to s

  /**
   * Computes the vertices in graph {@code G} that are
   * connected to the source vertex {@code s}.
   * @param G the graph
   * @param s the source vertex
   * @throws IllegalArgumentException unless {@code 0 <= s < V}
   */
  public DepthFirstSearch(Graph G, int s) {
    marked = new boolean[G.V()];
    validateVertex(s);
    dfs(G, s);
  }

  // depth first search from v
  private void dfs(Graph G, int v) {
    count++;
    marked[v] = true;
    for (int w : G.adj(v)) {
      if (!marked[w]) {
        dfs(G, w);
      }
    }
  }

  /**
   * Is there a path between the source vertex {@code s} and vertex {@code v}?
   * @param v the vertex
   * @return {@code true} if there is a path, {@code false} otherwise
   * @throws IllegalArgumentException unless {@code 0 <= v < V}
   */
  public boolean marked(int v) {
    validateVertex(v);
    return marked[v];
  }

  /**
   * Returns the number of vertices connected to the source vertex {@code s}.
   * @return the number of vertices connected to the source vertex {@code s}
   */
  public int count() {
    return count;
  }

  // throw an IllegalArgumentException unless {@code 0 <= v < V}
  private void validateVertex(int v) {
    int V = marked.length;
    if (v < 0 || v >= V)
      throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
  }

  /**
   * Unit tests the {@code DepthFirstSearch} data type.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    int s = StdIn.readInt();
    int verticesNbr = StdIn.readInt();
    int edgesNbr = StdIn.readInt();
    int[] vertices = StdIn.readAllInts();

    Graph G = new Graph(verticesNbr, edgesNbr, vertices);

    DepthFirstSearch search = new DepthFirstSearch(G, s);
    for (int v = 0; v < G.V(); v++) {
      if (search.marked(v))
        StdOut.print(v + " ");
    }

    StdOut.println();
    if (search.count() != G.V()) StdOut.println("NOT connected");
    else                         StdOut.println("connected");
  }

}