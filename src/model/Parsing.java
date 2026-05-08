package model;

import data.Graph;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// class to parse data from the CSV file
public class Parsing {

    public Graph parse(String file) {
        // setting up empty graph data structure
        Graph graph = new Graph();
        // maps a station name to a set of all the lines it appears on
        Map<String, Set<String>> stationLines = new HashMap<>();
        String line = "";
        String current = "";

        // main logic to attempt to parse data
        try {
            // opens csv file, getting rid of first row since it is not data
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine();
            
            // main logic, runs loop while each line being read isn't empty
            while ((line = br.readLine()) != null) {
                // splits row into columns at commas
                String[] cols = line.split(",");

                // checks if row being read is a train line and assigns to current + ensures it doesn't get further parsed
                if (cols.length < 3 || cols[1].trim().isEmpty()) {
                    current = cols[0].trim();
                    continue;
                }

                // extracts row into three values: departure, arrival, and the time taken
                String departure = cols[0].trim();
                String arrival = cols[1].trim();
                double time = Double.parseDouble(cols[2].trim());

                // assigns current train line to both stations; creates empty hash set if station doesn't have one already + ensures no duplicate lines
                stationLines.computeIfAbsent(departure, k -> new HashSet<>()).add(current);
                stationLines.computeIfAbsent(arrival, k -> new HashSet<>()).add(current);

                // adds connection to graph in both directions + with current line for djikstra algorithm to utilise
                graph.newEdge(departure, arrival, time, current);
            }
        } catch (IOException e) {
            // outputs general input/output error if problem with parsing the data
            System.out.println("erorr in parsing");
        } catch (NumberFormatException e) {
            // outputs number-based errors if the time value used within csv data is invalid
            System.out.println("invalid time value " + line);
        }

        // passes statiion & lines map to graph so it can be used when parse is called
        graph.setStationLines(stationLines);
        return graph;
    }
}
