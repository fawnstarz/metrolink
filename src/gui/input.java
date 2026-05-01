package gui;

// imports from swing for necessary components
import data.Stations;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

// utilising jpanel for input field for destination and arrival locations, alongside check button & result
public class Input extends JPanel{
    public Input(Stations stations) {
        // text fields for user to input a departure and arrival station, alongside button to submit and a field for program response
        JTextField departure = new JTextField(20);
        JTextField arrival = new JTextField(20);
        JButton check = new JButton("Check");
        JLabel result = new JLabel(" ");

        // listens for action of input from the user in the departure and arrival GUI inputs
        check.addActionListener(e -> {
            // assigning text from inputs into variables for comparisons
            String dep = departure.getText().trim();
            String arr = arrival.getText().trim();
            // checking if the inputted departure and arrival stations are valid; returning necessary response
            if (stations.contains(dep) && stations.contains(arr)) {
                result.setText("Stations " + dep + " and " + arr + "have been found.");
            } else if (!stations.contains(dep)) {
                result.setText("Station" + dep + " was not found.");
            } else if (!stations.contains(arr)) {
                result.setText("Station" + arr + " was not found.");
            } else {
                result.setText("Stations" + dep + " and " + arr + "were not found.");
            }
        });

        // assigns input/submit/response fields into the JPanel for interactivity
        add(new JLabel("departure:"));
        add(departure);
        add(new JLabel("arrival:"));
        add(arrival);
        add(check);
        add(result);
    }
    
}
