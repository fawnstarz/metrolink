package model;

import data.Graph;
import java.io.*;

// class to parse data from the CSV file
public class Parsing {

    public Graph parse(String file) {
        Graph graph = new Graph();
        String line = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");

                if (cols.length < 3) continue;

                String departure = cols[0].trim();
                String arrival = cols[1].trim();
                int time = Integer.parseInt(cols[2].trim());

                graph.newEdge(departure, arrival, time);
            }
        } catch (IOException e) {
            System.out.println("erorr in parsing :3");
        } catch (NumberFormatException e) {
            System.out.println("invalid time value :3333 " + line);
        }
        return graph;
    }
}
