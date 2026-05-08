package model;
import java.util.List;

public class RouteCalc {
    public final List<String> path; // ordered list of station names to traverse
    public final double total; // real travel time
    public final List<String> lineChanges; // list of transfers
    public final List <String> lines; // line each station was travelled on

    // constructor to assign the passed in values to associated fields
    public RouteCalc(List<String> path, double total, List<String> lineChanges, List<String> lines) {
        this.path = path;
        this.total = total;
        this.lineChanges = lineChanges;
        this.lines = lines;
    }
}
