package data;

// necessary imports
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Stations {
    // string array to hold all unique names of train stations
    String[] names = new String[0];

    // main entrypoint for java file, creates instance of stations class & stores as obj to run
    public static void main(String[] args) {
        Stations obj = new Stations();
        obj.run();
    }

    // simple get method to access names array
    public String[] getNames() {
        return names;
    }

    // simple checking method to see if a certain value exists within the names array
    public boolean contains(String input) {
    for (String n : names) {
        if (n.equalsIgnoreCase(input)) return true;
    }
    return false;
}

    // main run class where the csv file gets parsed
    public void run() {
        // location of csv file (needs future updating due to runtime issues)
        String csv = "/Users/aimee/Coding/Uni/metrolink/bin/data/metrolink_times.csv";
        // reads & buffers text from character input stream
        BufferedReader br = null;
        String line = "";

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
        } catch (IOException io) {
            // catch errors via ioexception
            System.out.println(io);
        }
    }
}