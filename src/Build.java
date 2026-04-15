import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Build {

  /**
   * Prints words that are reachable from the given vertex and are strictly shorter than k characters.
   * If the vertex is null or no reachable words meet the criteria, prints nothing.
   *
   * @param vertex the starting vertex
   * @param k the maximum word length (exclusive)
   */
  public static void printShortWords(Vertex<String> vertex, int k) {
    printShortWords(vertex, k, new HashSet<>());
  }

  private static void printShortWords(Vertex<String> vertex, int k, Set<Vertex<String>> seen) {
    // Base case
    if (vertex == null) return;
    if (seen.contains(vertex)) return;
    
    // Action
    seen.add(vertex);
    if (vertex.data.length() < k) {System.out.println(vertex.data);}

    // Recursive call
    for (Vertex<String> word : vertex.neighbors) {
      printShortWords(word, k, seen);
    }
  }

  /**
   * Returns the longest word reachable from the given vertex, including its own value.
   *
   * @param vertex the starting vertex
   * @return the longest reachable word, or an empty string if the vertex is null
   */
  public static String longestWord(Vertex<String> vertex) {
    Set<Vertex<String>> seen = new HashSet<>();
    Vertex<String> longestWord = new Vertex<String>("");
    // System.out.println(longestWord);
    longestWordHelper(vertex, seen, longestWord);
    return longestWord.data;
  }

  private static void longestWordHelper(Vertex<String> vertex, Set<Vertex<String>> seen, Vertex<String> longestWord) {
    if (vertex == null) return;
    if (vertex.data.length() > longestWord.data.length()) longestWord.data = vertex.data;
    seen.add(vertex);
    for (Vertex<String> v : vertex.neighbors) {
      if (!seen.contains(v)) longestWordHelper(v, seen, longestWord);
    }
  }

  /**
   * Prints the values of all vertices that are reachable from the given vertex and 
   * have themself as a neighbor.
   * 
   * 7 -> 32, 45, 60 // false
   * 60 -> 40, 70, 60 // true
   *
   * @param vertex the starting vertex
   * @param <T> the type of values stored in the vertices
   */
  public static <T> void printSelfLoopers(Vertex<T> vertex) {
    printSelfLoopers(vertex, new HashSet<>());
  }

  private static <T> void printSelfLoopers(Vertex<T> vertex, Set<Vertex<T>> seen) {
    if (vertex == null) return;
    if (vertex.neighbors.contains(vertex)) System.out.println(vertex.data);
    // if (vertex == null || seen.contains(vertex)) return;
    seen.add(vertex);
    for (Vertex<T> neighbor : vertex.neighbors) {
      if (!seen.contains(neighbor)) printSelfLoopers(neighbor, seen);
    }
  }

  /**
   * Determines whether it is possible to reach the destination airport through a series of flights
   * starting from the given airport. If the start and destination airports are the same, returns true.
   *
   * @param start the starting airport
   * @param destination the destination airport
   * @return true if the destination is reachable from the start, false otherwise
   */
  public static boolean canReach(Airport start, Airport destination) {
    return canReach(start, destination, new HashSet<>());
  }

  public static boolean canReach(Airport start, Airport destination, Set<Airport> seen) {
    if (start == destination) return true;
    // if (seen.contains(start)) return false;
    seen.add(start);
    for (Airport flight : start.getOutboundFlights()) {
      if (!seen.contains(flight)) {
        return canReach(flight, destination, seen);
      }
    }
    return false;
  }

  /**
   * Returns the set of all values in the graph that cannot be reached from the given starting value.
   * The graph is represented as a map where each vertex is associated with a list of its neighboring values.
   *
   * @param graph the graph represented as a map of vertices to neighbors
   * @param starting the starting value
   * @param <T> the type of values stored in the graph
   * @return a set of values that cannot be reached from the starting value
   */
  public static <T> Set<T> unreachable(Map<T, List<T>> graph, T starting) {
    if (!graph.containsKey(starting)) {
      return new HashSet<>(graph.keySet());
    }

    Set<T> seen = new HashSet<>();
    unreachableHelper(graph, starting, seen);

    Set<T> notSeen = new HashSet<>(graph.keySet());
    notSeen.removeAll(seen);

    return notSeen;
  }

  private static <T> void unreachableHelper(Map<T, List<T>> graph, T current, Set<T> seen) {
    if (seen.contains(current)) return;

    seen.add(current);

    List<T> neighbors = graph.get(current);
    if (neighbors == null) return;

    for (T next : neighbors) {
      unreachableHelper(graph, next, seen);
    }
  }
}
