package data;

// class for a single connection within the adjacency matrix
public class Edge {
    // variables to assign departure, arrival, and time taken from CSV list for each station
    public final String departure;
    public final String arrival;
    public final int time;

    // function assigning passed-in values from CSV file to related variables
    public Edge(String departure, String arrival, int time) {
        this.departure = departure;
        this.arrival = arrival;
        this.time = time;
    }

}
