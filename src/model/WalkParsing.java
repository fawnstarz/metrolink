package model;

import data.Graph;
import java.io.*;
import java.util.*;

// class to parse walking tiems file
public class WalkParsing {

    // main logic to parse walking file
    public void parse(String file, Graph graph) {
        try {
            // reads first row of csv file, ensures not parsed due to it being a header file
            BufferedReader br = new BufferedReader(new FileReader(file));
            String head = br.readLine();
            if (head == null) return;

            // splits header line by comma values, adds list of stations to array list
            String[] headers = head.split(",");
            List<String> stationsCol = new ArrayList<>();
            for (int i = 1; i < headers.length; i++) {
                stationsCol.add(headers[i].trim());
            }

            // main body of code to read each line of times on the 
            String line;
            // if csv line isn't empty, line is split by commas so list of times can be utilised
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",", -1);
                String stationsRow = columns[0].trim();

                // fuck my stupid baka life
                for (int i = 1; i < columns.length; i++) {
                    String cell = columns[i].trim();

                    // if
                    if (cell.isEmpty() || cell.equals("0")) continue;

                    try {
                        double walking = Double.parseDouble(cell);
                        String stationCol = stationsCol.get(i - 1);

                        if (stationsRow.equals(stationCol)) continue;

                        graph.addWalkingEdge(stationsRow. stationCol, walking);
                    } 
                        // error catching in caawse issues with reafing walking time values in the file
                        catch (NumberFormatException e) {
                            System.out.println("invalid walking time");
                    }
                }
            }
        } 
        // error catching in case issues with input/output & file reading
            catch (IOException e) {
                System.out.println("error reading csv:" + e.getMessage());
        }
    }

}
