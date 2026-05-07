package model;

import data.Graph;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

// class to parse data from the CSV file
public class Parsing {

    public Graph parse(String file) {
        Graph graph = new Graph();
        Map<String, String> stationLines = new HashMap<>();
        String line = "";
        String current = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");

                if (cols.length < 3 || cols[1].trim().isEmpty()) {
                    current = cols[0].trim();
                    continue;
                }

                String departure = cols[0].trim();
                String arrival = cols[1].trim();
                double time = Double.parseDouble(cols[2].trim());

                stationLines.put(departure, current);
                stationLines.put(arrival, current);

                graph.newEdge(departure, arrival, time);
            }
        } catch (IOException e) {
            System.out.println("erorr in parsing :3");
        } catch (NumberFormatException e) {
            System.out.println("invalid time value :3333 " + line);
        }

        graph.setStationLines(stationLines);
        return graph;
    }
}
