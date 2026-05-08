package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Stations {
    // array of station names accessible by all methods
    private String[] names = new String[0];

    // initialises new instance of the stations object
    public static void main(String[] args) {
        Stations obj = new Stations();
        obj.run();
    }

    // getter that returns all names for other functions if necessary
    public String[] getNames() {
        return names;
    }

    // checks if a given station exists in the array already to avoid duplicates
    public boolean contains(String input) {
        for (String n : names) {
            if (n.equalsIgnoreCase(input)) return true;
        }
        return false;
    }

    // helper method to add a station if it doesn't already exist
    private String[] addIfAbsent(String[] names, String name) {
        // skip if first character isn't uppercase
        if (name.isEmpty() || !Character.isUpperCase(name.charAt(0))) return names;
        // skip if already exists
        for (String n : names) {
            if (n.equalsIgnoreCase(name)) return names;
        }
        // add to array
        names = Arrays.copyOf(names, names.length + 1);
        names[names.length - 1] = name;
        return names;
    }

    // general run method for applying logic to a csv file
    public void run() {
        // setting file path, reader, and current line var
        String csv = "/Users/aimee/Coding/Uni/metrolink/bin/data/metrolink_times.csv";
        BufferedReader br = null;
        String line = "";

        try {
            // opens file, skips first row as not data
            br = new BufferedReader(new FileReader(csv));
            br.readLine(); 

            // computes while the line being read in the csv file isn't empty
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");

                // skip line name rows and malformed rows
                if (cols.length < 3 || cols[1].trim().isEmpty()) continue;

                // register both departure and arrival
                names = addIfAbsent(names, cols[0].trim());
                names = addIfAbsent(names, cols[1].trim());
            }

        } catch (IOException io) {
            System.out.println(io);
        }
    }
}