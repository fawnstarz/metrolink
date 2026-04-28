package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class stations {
    // main entrypoint for java file, creates instance of stations class & stores as obj to run
    public static void main(String[] args) {
        stations obj = new stations();
        obj.run();
    }

    // main run class where the csv file gets parsed
    public void run() {
        // location of csv file (needs future updating due to runtime issues)
        String csv = "/Users/aimee/Coding/Uni/metrolink/bin/data/metrolink_times.csv";
        // 
        BufferedReader br = null;
        //
        String line = "";
        // string array to hold all unique names of train stations
        String[] names = new String[0];

        // try-catch to help reduce & flag runtime errors
        try {
            // bufferedreader opens & reads the metrolink times csv file
            br = new BufferedReader(new FileReader(csv));
            br.readLine();

            // while loop which checks if the line inside the csv file is not empty
            while ((line = br.readLine()) != null) { 
                // only checks first item of each line of csv file + setting evaluation variable to false
                String name = line.split(",")[0].trim();
                boolean found = false;

                // checking if first letter is false to avoid including train lines in output
                if (!Character.isUpperCase(name.charAt(0))) {
                    found = true;
                }

                // checking if station name already exists to avoid duplicates
                for (String n : names) {
                    if (n.equalsIgnoreCase(name)) {
                        found = true;
                        break;
                    }
                }

                // adding unique station name to names array
                if (!found) {
                    names = Arrays.copyOf(names, names.length + 1);
                    names[names.length - 1] = name;
                }
            }

            // temporary check, print results to confirm it's working
            System.out.println("Total unique stations: " + names.length);
            for (String n : names) System.out.println(n);

        } catch (IOException io) {
            // catch errors via ioexception
            System.out.println(io);
        }
    }
}