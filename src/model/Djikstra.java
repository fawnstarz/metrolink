package model;
import data.Edge;
import data.Graph;
import java.util.*;

// class for actual djikstra algorithm to calculate the shortest time between two stations
public class Djikstra {
    // storing graph as a field for access across methods
    private final Graph graph;

    // graph passed in from input file for use in the algorithm
    public Djikstra(Graph graph) {
        this.graph = graph;
    }

    // main algorithm to calculate the fastest available route, including set up for actual code
    public RouteCalc getRoute(String departure, String arrival) {
        // main variables for decision making, as well as tracking train lines used
        Map<String, Double> times = new HashMap<>();
        Map<String, Double> realTimes = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        Map<String, String> lineUsed = new HashMap<>(); 

        // priority queue used, returns state with lowest penalised time 
        PriorityQueue<String> pq = new PriorityQueue<>(
            Comparator.comparingDouble(i -> times.getOrDefault(i, Double.MAX_VALUE))
        );

        // initialising the algorithm, start state set to | as no line has been travelled on yet
        String startState = departure + "|";
        // maps set to 0 and start state is added to queue to start algorithm
        times.put(startState, 0.0);
        realTimes.put(startState, 0.0);
        pq.offer(startState);

        // runs main body of logic 
        while (!pq.isEmpty()) {
            // returns lowest cost state from priority queue
            String current = pq.poll();
            double currentTime = times.get(current);
            double realTime = realTimes.get(current);
            // splits state string on | to extract station name
            String currentStation = current.split("\\|")[0];
            // assigns the current line to detect for line changes on the next edge
            String currentLine = lineUsed.getOrDefault(current, "");

            // retrieves neighboring stations to current station and calculates whether a change is necessary/the best option
            for (Edge adjacent : graph.getAdjacent(currentStation)) {
                boolean isChange = !currentLine.isEmpty() && !adjacent.line.equals(currentLine);
                double penalty = isChange ? 5.0 : 0.0; // penalises unnecessary transfers, only counting realistic & necessary ones
                double transfer = isChange ? 2.0 : 0.0;

                // formatted into penalised time (for calculation) and real time (for display)
                double newTime = currentTime + adjacent.time + penalty;
                double newReal = realTime + adjacent.time + transfer; 

                String nextState = adjacent.arrival + "|" + adjacent.line;

                // if new penalised time is better than previously known time, maps are updated and next state is added to queue for comparison
                if (newTime < times.getOrDefault(nextState, Double.MAX_VALUE)) {
                    times.put(nextState, newTime);
                    realTimes.put(nextState, newReal);
                    previous.put(nextState, current);
                    lineUsed.put(nextState, adjacent.line);
                    pq.offer(nextState);
                }
            }
        }

        // calculates the overall best arrival state as each train station can appear on multiple lines
        String bestArrival = null;
        double bestTime = Double.MAX_VALUE;
        for (String state : times.keySet()) {
            if (state.startsWith(arrival + "|") && times.get(state) < bestTime) {
                bestTime = times.get(state);
                bestArrival = state;
            }
        }

        // if no arrival state is found, the destination is unreachable so returns empty result for appropriate handling in input.java
        if (bestArrival == null) {
            return new RouteCalc(Collections.emptyList(), -1.0, Collections.emptyList(), Collections.emptyList());
        }

        List<String> path = new ArrayList<>();
        List<String> lines = new ArrayList<>();
        String step = bestArrival;
        while (step != null) {
            String[] parts = step.split("\\|", -1);
            path.add(0, parts[0]);
            lines.add(0, parts.length > 1 ? parts[1] : "");
            step = previous.get(step);
        }

        // if line changes are detected, then outputs them where necessary
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

        if (lines.size() > 1 && lines.get(0).isEmpty()) {
        lines.set(0, lines.get(1));
}
        Map<String, String> stationLineMap = new HashMap<>();
        for (int i = 0; i < path.size(); i++) {
            stationLineMap.put(path.get(i), lines.get(i));
        }

        // returns function with necessary variables
        return new RouteCalc(path, realTimes.get(bestArrival), lineChanges, lines);
    }
}
