package model;
import java.util.List;

public class RouteCalc {
    public final List<String> path;
    public final double total;

    public RouteCalc(List<String> path, double total) {
        this.path = path;
        this.total = total;
    }
}
