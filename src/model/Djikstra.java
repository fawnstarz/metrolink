package model;
import data.Edge;
import data.Graph;
import java.util.*;

// class for actual djikstra algorithm to calculate the shortest time between two stations
public class Djikstra {
    private final Graph graph;

    public Djikstra(Graph graph) {
        this.graph = graph;
    }

    public RouteCalc getRoute(String departure, String arrival) {
        Map<String, Double> times = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(
            Comparator.comparingDouble(i -> times.getOrDefault(i, Double.MAX_VALUE))
        );

        times.put(departure, 0.0);
        pq.offer(departure);

        while (!pq.isEmpty()) {
            String current = pq.poll();
            double currentTime = times.get(current);

            for (Edge adjacent : graph.getAdjacent(current)) {
                double newTime = currentTime + adjacent.time;
                if (newTime < times.getOrDefault(adjacent.arrival, Double.MAX_VALUE)) {
                    times.put(adjacent.arrival, newTime);
                    previous.put(adjacent.arrival, current);
                    pq.offer(adjacent.arrival);
                }
            }
        }

        List<String> path = new ArrayList<>();
        String step = arrival;
        while (step != null) {
            path.add(0, step);
            step = previous.get(step);
        }

        if (path.isEmpty() || !path.get(0).equals(departure)) {
            return new RouteCalc(Collections.emptyList(), -1.0, Collections.emptyList());
        }

        List<String> lineChanges = new ArrayList<>();
        for (int i = 0; i < path.size() - 1; i++) {
            String lineA = graph.getLine(path.get(i));
            String lineB = graph.getLine(path.get(i + 1));
            if (!lineA.equals(lineB)) {
                lineChanges.add("change at " + path.get(i + 1) + " from " + lineA + " to " + lineB);
            }
        }

        return new RouteCalc(path, times.get(arrival), lineChanges);
    }
}
