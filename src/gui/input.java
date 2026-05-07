package gui;

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

        // only reaches here if both stations are valid
        Djikstra dijkstra = new Djikstra(graph);
        RouteCalc route   = dijkstra.getRoute(dep, arr);

        if (route.path.isEmpty()) {
            result.setText("no route found between " + dep + " and " + arr);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("route: ").append(String.join(" → ", route.path)).append("\n");
        sb.append("total time: ").append(route.total).append(" minutes\n");

        if (route.lineChanges.isEmpty()) {
            sb.append("no changes required");
        } else {
            sb.append("line changes (" + route.lineChanges.size() + "):\n");
            for (int i = 0; i < route.lineChanges.size(); i++) {
                sb.append(" ").append(i + 1).append(". ").append(route.lineChanges.get(i)).append("\n");
            }
        }

        result.setText(sb.toString());
    });
        // assigns input/submit/response fields into the JPanel for interactivity
        add(new JLabel("departure:"));
        add(departure);
        add(new JLabel("arrival:"));
        add(arrival);
        add(check);
        add(new JScrollPane(result));
    }

}
