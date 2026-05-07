package model;
import java.util.List;

public class RouteCalc {
    public final List<String> path;
    public final double total;
    public final List<String> lineChanges;

    public RouteCalc(List<String> path, double total, List<String> lineChanges) {
        this.path = path;
        this.total = total;
        this.lineChanges = lineChanges;
    }
}
