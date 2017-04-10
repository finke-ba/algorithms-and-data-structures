package graphs;

import fundamentals.Bag;
import libs.StdIn;
import libs.StdOut;

import java.util.NoSuchElementException;

/**
 *  The {@code Graph} class represents an undirected graph of vertices
 *  named 0 through <em>V</em> – 1.
 *  It supports the following two primary operations: add an edge to the graph,
 *  iterate over all of the vertices adjacent to a vertex. It also provides
 *  methods for returning the number of vertices <em>V</em> and the number
 *  of edges <em>E</em>. Parallel edges and self-loops are permitted.
 *  By convention, a self-loop <em>v</em>-<em>v</em> appears in the
 *  adjacency list of <em>v</em> twice and contributes two to the degree
 *  of <em>v</em>.
 *  <p>
 *  This implementation uses an adjacency-lists representation, which
 *  is a vertex-indexed array of {@link Bag} objects.
 *  All operations take constant time (in the worst case) except
 *  iterating over the vertices adjacent to a given vertex, which takes
 *  time proportional to the number of such vertices.
 *
 */
public class Graph {
  private static final String NEWLINE = System.getProperty("line.separator");

  private final int      V;   //number of vertices
  private int            E;   //number of edges
  private Bag<Integer>[] adj; //adjacency-lists

  /**
   * Initializes an empty graph with {@code V} vertices and 0 edges.
   * param V the number of vertices
   *
   * @param  V number of vertices
   * @throws IllegalArgumentException if {@code V < 0}
   */
  public Graph(int V) {
    if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
    this.V = V;
    this.E = 0;
    adj = (Bag<Integer>[]) new Bag[V];
    for (int v = 0; v < V; v++) {
      adj[v] = new Bag<>();
    }
  }

  /**
   * Initializes a graph from the specified input stream.
   * The format is the number of vertices <em>V</em>,
   * followed by the number of edges <em>E</em>,
   * followed by <em>E</em> pairs of vertices, with each entry separated by whitespace.
   *
   * @param  verticesNbr number of vertices
   * @param  edgesNbr number of edges
   * @param  vertices vertices array
   * @throws IllegalArgumentException if the endpoints of any edge are not in prescribed range
   * @throws IllegalArgumentException if the number of vertices or edges is negative
   * @throws IllegalArgumentException if the input stream is in the wrong format
   */
  public Graph(int verticesNbr, int edgesNbr, int[] vertices) {
    try {
      this.V = verticesNbr;
      if (V < 0) throw new IllegalArgumentException("number of vertices in a Graph must be nonnegative");
      adj = (Bag<Integer>[]) new Bag[V];
      for (int v = 0; v < V; v++) {
        adj[v] = new Bag<>();
      }
      int E = edgesNbr;
      if (E < 0) throw new IllegalArgumentException("number of edges in a Graph must be nonnegative");
      for (int i = 0; i < E*2; i = i +2) {
        int v = vertices[i];
        int w = vertices[i+1];
        validateVertex(v);
        validateVertex(w);
        addEdge(v, w);
      }
    }
    catch (NoSuchElementException e) {
      throw new IllegalArgumentException("invalid input format in Graph constructor", e);
    }
  }

  /**
   * Returns the number of vertices in this graph.
   *
   * @return the number of vertices in this graph
   */
  public int V() {
    return V;
  }

  /**
   * Returns the number of edges in this graph.
   *
   * @return the number of edges in this graph
   */
  public int E() {
    return E;
  }

  // throw an IllegalArgumentException unless {@code 0 <= v < V}
  private void validateVertex(int v) {
    if (v < 0 || v >= V)
      throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
  }

  /**
   * Adds the undirected edge v-w to this graph.
   *
   * @param  v one vertex in the edge
   * @param  w the other vertex in the edge
   * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
   */
  public void addEdge(int v, int w) {
    validateVertex(v);
    validateVertex(w);
    E++;
    adj[v].add(w);
    adj[w].add(v);
  }

  /**
   * Returns the vertices adjacent to vertex {@code v}.
   *
   * @param  v the vertex
   * @return the vertices adjacent to vertex {@code v}, as an iterable
   * @throws IllegalArgumentException unless {@code 0 <= v < V}
   */
  public Iterable<Integer> adj(int v) {
    validateVertex(v);
    return adj[v];
  }

  /**
   * Returns the degree of vertex {@code v}.
   *
   * @param  v the vertex
   * @return the degree of vertex {@code v}
   * @throws IllegalArgumentException unless {@code 0 <= v < V}
   */
  public int degree(int v) {
    validateVertex(v);
    return adj[v].size();
  }

  /**
   * Returns a string representation of this graph.
   *
   * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
   *         followed by the <em>V</em> adjacency lists
   */
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(V).append(" vertices, ").append(E).append(" edges ").append(NEWLINE);
    for (int v = 0; v < V; v++) {
      s.append(v).append(": ");
      for (int w : adj[v]) {
        s.append(w).append(" ");
      }
      s.append(NEWLINE);
    }
    return s.toString();
  }

  /**
   * Unit tests the {@code Graph} data type.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    int verticesNbr = StdIn.readInt();
    int edgesNbr = StdIn.readInt();
    int[] vertices = StdIn.readAllInts();

    Graph G = new Graph(verticesNbr, edgesNbr, vertices);

    StdOut.println(G);
  }

}
