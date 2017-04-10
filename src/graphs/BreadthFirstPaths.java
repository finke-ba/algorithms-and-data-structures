package graphs;

import fundamentals.Queue;
import libs.StdIn;
import libs.StdOut;

import java.util.Collections;
import java.util.Stack;

/**
 *  The {@code BreadthFirstPaths} class represents a data type for finding
 *  shortest paths (number of edges) from a source vertex <em>s</em>
 *  (or a set of source vertices)
 *  to every other vertex in an undirected graph.
 *  <p>
 *  This implementation uses breadth-first search.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  It uses extra space (not including the graph) proportional to <em>V</em>.
 */
public class BreadthFirstPaths {
  private static final int INFINITY = Integer.MAX_VALUE;
  private boolean[] marked;  // marked[v] = is there an s-v path
  private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
  private int[] distTo;      // distTo[v] = number of edges shortest s-v path

  /**
   * Computes the shortest path between the source vertex {@code s}
   * and every other vertex in the graph {@code G}.
   * @param G the graph
   * @param s the source vertex
   * @throws IllegalArgumentException unless {@code 0 <= s < V}
   */
  public BreadthFirstPaths(Graph G, int s) {
    marked = new boolean[G.V()];
    distTo = new int[G.V()];
    edgeTo = new int[G.V()];
    validateVertex(s);
    bfs(G, s);
  }

  // breadth-first search from a single source
  private void bfs(Graph G, int s) {
    Queue<Integer> q = new Queue<>();
    for (int v = 0; v < G.V(); v++)
      distTo[v] = INFINITY;
    distTo[s] = 0;
    marked[s] = true;
    q.enqueue(s);

    while (!q.isEmpty()) {
      int v = q.dequeue();
      for (int w : G.adj(v)) {
        if (!marked[w]) {
          edgeTo[w] = v;
          distTo[w] = distTo[v] + 1;
          marked[w] = true;
          q.enqueue(w);
        }
      }
    }
  }

  /**
   * Is there a path between the source vertex {@code s} (or sources) and vertex {@code v}?
   * @param v the vertex
   * @return {@code true} if there is a path, and {@code false} otherwise
   * @throws IllegalArgumentException unless {@code 0 <= v < V}
   */
  public boolean hasPathTo(int v) {
    validateVertex(v);
    return marked[v];
  }

  /**
   * Returns the number of edges in a shortest path between the source vertex {@code s}
   * (or sources) and vertex {@code v}?
   * @param v the vertex
   * @return the number of edges in a shortest path
   * @throws IllegalArgumentException unless {@code 0 <= v < V}
   */
  public int distTo(int v) {
    validateVertex(v);
    return distTo[v];
  }

  /**
   * Returns a shortest path between the source vertex {@code s} (or sources)
   * and {@code v}, or {@code null} if no such path.
   * @param  v the vertex
   * @return the sequence of vertices on a shortest path, as an Iterable
   * @throws IllegalArgumentException unless {@code 0 <= v < V}
   */
  public Iterable<Integer> pathTo(int v) {
    validateVertex(v);
    if (!hasPathTo(v)) return null;
    Stack<Integer> path = new Stack<>();
    int x;
    for (x = v; distTo[x] != 0; x = edgeTo[x])
      path.push(x);
    path.push(x);

    Collections.reverse(path);

    return path;
  }

  // throw an IllegalArgumentException unless {@code 0 <= v < V}
  private void validateVertex(int v) {
    int V = marked.length;
    if (v < 0 || v >= V)
      throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
  }

  /**
   * Unit tests the {@code BreadthFirstPaths} data type.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    int s = StdIn.readInt();
    int verticesNbr = StdIn.readInt();
    int edgesNbr = StdIn.readInt();
    int[] vertices = StdIn.readAllInts();
    Graph G = new Graph(verticesNbr, edgesNbr, vertices);

    BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);

    for (int v = 0; v < G.V(); v++) {
      if (bfs.hasPathTo(v)) {
        StdOut.printf("%d to %d (%d):  ", s, v, bfs.distTo(v));
        for (int x : bfs.pathTo(v)) {
          if (x == s) StdOut.print(x);
          else        StdOut.print("-" + x);
        }
        StdOut.println();
      }

      else {
        StdOut.printf("%d to %d (-):  not connected\n", s, v);
      }

    }
  }




}
