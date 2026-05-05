package data;
import java.util.*;

// class for the adjacency matrix itself
public class Graph {
    // hash map used as adjacency matrix base due to key/pair functionality, departure->arrival and time
    private Map<String, List<Edge>> adjacency = new HashMap<>();
    
    // function to assign new edges onto the graph due to undirected nature
    public void newEdge(String departure, String arrival, int time) {
        // checks if station already has list of edges, if not then creates a new array list and adds the edge to it
        adjacency.computeIfAbsent(departure, i -> new ArrayList<>()).add(new Edge(departure, arrival, time));
        adjacency.computeIfAbsent(arrival, i -> new ArrayList<>()).add(new Edge(arrival, departure, time));
    }
    
    // function to return all edges leaving a station
    public List<Edge> getAdjacent(String node) {
        // if station doesn't exist, returns empty list 
        return adjacency.getOrDefault(node, new ArrayList<>());
    }

    // returns every station name in graph
    public Set<String> getNodes() {
        return adjacency.keySet();
    }

    // function to print out all valuesw within the adjacency list (for trial & error purposes)
    public void print() {
        for (String node : adjacency.keySet()) {
            System.out.print(node + " -> ");
            for (Edge i : adjacency.get(node)) {
                System.out.print(i.departure + "(" + i.time + ") ");
            }
            System.out.println();
        }
    }
}
