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

    // takes a departure station and returns a map of every adjacent station
    public Map<String, Integer> compute(String start) {
        Map<String, Integer> times = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(
            Comparator.comparingInt(i -> times.getOrDefault(i, Integer.MAX_VALUE))
        );

        times.put(start, 0);
        pq.offer(start);

        while (!pq.isEmpty()) {
            String current = pq.poll();
            int currentTime = times.get(current);
        }

        for (Edge adjacent : graph.getAdjacent(current)) {
            int newTime = currentTime + adjacent.time;

            if (newTime < times.getOrDefault(adjacent.arrival, Integer.MAX_VALUE)) {
                times.put(adjacent.arrival, newTime);
                pq.offer(adjacent.arrival);
            }
        }
    }
}
