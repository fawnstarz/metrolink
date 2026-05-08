package data;

// class for single edge in adjacency matrix that follows a walking route
public class WalkEdge {
    // variables to assign departure, arrival, time taken, and if the user is walking
    public final String departure;
    public final String arrival;
    public final double time;
    public final boolean walking;

    // function assigning passed-in values from CSV file to related variables
    public WalkEdge(String departure, String arrival, double time, boolean walking) {
        this.departure = departure;
        this.arrival = arrival;
        this.time = time;
        this.walking = true;
    }

}
