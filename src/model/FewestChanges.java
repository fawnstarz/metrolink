package model;

import data.Edge;
import data.Graph;
import java.util.*;

// finds the route with the fewest line changes regardless of time
public class FewestChanges {
    private final Graph graph;

    public FewestChanges(Graph graph) {
        this.graph = graph;
    }

    public RouteCalc getRoute(String departure, String arrival) {
        Map<String, Integer> changes  = new HashMap<>();
        Map<String, Double>  times    = new HashMap<>();
        Map<String, String>  previous = new HashMap<>();

        PriorityQueue<String> pq = new PriorityQueue<>(
            Comparator.comparingInt(i -> changes.getOrDefault(i, Integer.MAX_VALUE))
        );

        String startState = departure + "|";
        changes.put(startState, 0);
        times.put(startState, 0.0);
        pq.offer(startState);

        while (!pq.isEmpty()) {
            String current        = pq.poll();
            int    currentChanges = changes.get(current);
            double currentTime    = times.get(current);
            String currentStation = current.split("\\|")[0];
            String currentLine    = current.split("\\|", -1)[1];

            for (Edge adjacent : graph.getAdjacent(currentStation)) {
                boolean isChange = !currentLine.isEmpty() && !adjacent.line.equals(currentLine);
                int    changeCost = isChange ? 1   : 0;
                double transfer   = isChange ? 2.0 : 0.0; 
                int    newChanges = currentChanges + changeCost;
                double newTime    = currentTime + adjacent.time + transfer;
                String nextState  = adjacent.arrival + "|" + adjacent.line;

                if (newChanges < changes.getOrDefault(nextState, Integer.MAX_VALUE)) {
                    changes.put(nextState, newChanges);
                    times.put(nextState, newTime);
                    previous.put(nextState, current);
                    pq.offer(nextState);
                }
            }
        }

        String bestArrival = null;
        int    bestChanges = Integer.MAX_VALUE;
        for (String state : changes.keySet()) {
            if (state.startsWith(arrival + "|") && changes.get(state) < bestChanges) {
                bestChanges = changes.get(state);
                bestArrival = state;
            }
        }

        if (bestArrival == null) {
            return new RouteCalc(Collections.emptyList(), -1.0, Collections.emptyList(), Collections.emptyList());
        }

        List<String> path  = new ArrayList<>();
        List<String> lines = new ArrayList<>();
        String step = bestArrival;
        while (step != null) {
            String[] parts = step.split("\\|", -1);
            path.add(0, parts[0]);
            lines.add(0, parts.length > 1 ? parts[1] : "");
            step = previous.get(step);
        }

        if (lines.size() > 1 && lines.get(0).isEmpty()) {
            lines.set(0, lines.get(1));
        }

        List<String> lineChanges = new ArrayList<>();
        for (int i = 0; i < lines.size() - 1; i++) {
            String lineA = lines.get(i);
            String lineB = lines.get(i + 1);
            if (!lineA.isEmpty() && !lineB.isEmpty() && !lineA.equals(lineB)) {
                if (i + 1 < path.size() - 1) {
                    lineChanges.add("change at " + path.get(i + 1)
                        + " from " + lineA + " to " + lineB);
                }
            }
        }

        return new RouteCalc(path, times.get(bestArrival), lineChanges, lines);
    }
}
