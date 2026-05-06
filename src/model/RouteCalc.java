package model;
import java.util.List;

public class RouteCalc {
    public final List<String> path;
    public final int total;

    public RouteCalc(List<String> path, int total) {
        this.path = path;
        this.total = total;
    }
}
