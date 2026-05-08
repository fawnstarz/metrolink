package gui;

// imports yay
import data.Graph;
import data.Stations;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.Djikstra;
import model.RouteCalc;

// utilising jpanel for input field for destination and arrival locations, alongside check button & result
public class Input extends JPanel{
    public Input(Stations stations, Graph graph) {
        // text fields for user to input a departure and arrival station, alongside button to submit and a field for program response
        JTextField departure = new JTextField(20);
        JTextField arrival = new JTextField(20);
        JButton check = new JButton("Check");
        JTextArea result = new JTextArea(5, 30);
        result.setEditable(false);

        // sets action listener so whenever button is clicked, inputted departure and arrival fields are read and assigned to variables
        check.addActionListener(e -> {
        String dep = departure.getText().trim();
        String arr = arrival.getText().trim();

        // validate first, return early if invalid
        if (!stations.contains(dep)) {
            result.setText("Station " + dep + " was not found.");
            return;
        }
        if (!stations.contains(arr)) {
            result.setText("Station " + arr + " was not found.");
            return;
        }

        // creates new djikstra instance once stations are valid with graph, runs algorithm
        Djikstra djikstra = new Djikstra(graph);
        RouteCalc route   = djikstra.getRoute(dep, arr);

        // case if djikstra returns an empty path, aka no connections exist between the two stations
        if (route.path.isEmpty()) {
            result.setText("No route foiund between" + dep + " and " + arr);
            return;
        }

        // building output string to show time taken and route taken to user
        StringBuilder sb = new StringBuilder();
        sb.append("Route:\n");

        for (int i = 0; i < route.path.size(); i++) {
            String station  = route.path.get(i);
            String line     = route.lines.get(i);

            if (i > 0) {
                String prevLine = route.lines.get(i - 1);
                if (!prevLine.isEmpty() && !line.isEmpty() && !prevLine.equals(line)) {
                    sb.append("  ").append(station).append(" (").append(prevLine).append(")\n");
                    sb.append("*Change to ").append(line).append(" line*\n");
                }
            }

            // prints station on current line (new line if change occurred)
            sb.append("  ").append(station).append(" (").append(line).append(")\n");
        }

        sb.append("Time Taken: ").append(String.format("%.1f", route.total)).append(" minutes\n");

        if (route.lineChanges.isEmpty()) {
            sb.append("No changes required");
        } else {
            sb.append("Lie changes (").append(route.lineChanges.size()).append("):\n");
            for (int i = 0; i < route.lineChanges.size(); i++) {
                sb.append(" ").append(i + 1).append(". ")
                .append(route.lineChanges.get(i)).append("\n");
            }
        }

        result.setText(sb.toString());
    });
        // assigns input/submit/response fields into the JPanel for interactivity
        add(new JLabel("Departure Station:"));
        add(departure);
        add(new JLabel("Arrival Station:"));
        add(arrival);
        add(check);
        add(new JScrollPane(result));
    }

}
