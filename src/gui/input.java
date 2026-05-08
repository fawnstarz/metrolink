package gui;

import data.Graph;
import data.Stations;
import javax.swing.*;
import model.Djikstra;
import model.FewestChanges;
import model.RouteCalc;

// utilising jpanel for input field for destination and arrival locations, alongside check button & result
public class Input extends JPanel {
    public Input(Stations stations, Graph graph) {
        JTextField departure = new JTextField(20);
        JTextField arrival   = new JTextField(20);
        JButton    check     = new JButton("Find Route");
        JTextArea  result    = new JTextArea(10, 40);
        result.setEditable(false);

        // radio buttons for route selection
        JRadioButton fastestBtn  = new JRadioButton("Fastest route", true);
        JRadioButton fewestBtn   = new JRadioButton("Fewest changes");
        ButtonGroup  group       = new ButtonGroup();
        group.add(fastestBtn);
        group.add(fewestBtn);

        // sets action listener so whenever button is clicked, inputted departure and arrival fields are read and assigned to variables
        check.addActionListener(e -> {
            String dep = departure.getText().trim();
            String arr = arrival.getText().trim();
        
            // validates if stations exist to catch errors
            if (!stations.contains(dep)) {
                result.setText("Station " + dep + " was not found.");
                return;
            }
            if (!stations.contains(arr)) {
                result.setText("Station " + arr + " was not found.");
                return;
            }

            // computes selected algorithm
            RouteCalc route;
            if (fastestBtn.isSelected()) {
                route = new Djikstra(graph).getRoute(dep, arr);
            } else {
                route = new FewestChanges(graph).getRoute(dep, arr);
            }

            // outputs if a route is not found between two given stations
            if (route.path.isEmpty()) {
                result.setText("No route found between " + dep + " and " + arr);
                return;
            }

            result.setText(buildOutput(route));
        });

        // output panel buttons & output results
        add(new JLabel("Departure:"));
        add(departure);
        add(new JLabel("Arrival:"));
        add(arrival);
<<<<<<< HEAD
        add(fastestBtn);  // add radio buttons to panel
=======
        add(fastestBtn); 
>>>>>>> c18f7fe (adding final comments & missed points)
        add(fewestBtn);
        add(check);
        add(new JScrollPane(result));
    }

    // helper method since both algorithms use the same display format
    private String buildOutput(RouteCalc route) {

        // builds string instead of repeated variable iterations
        StringBuilder sb = new StringBuilder();
        sb.append("Route:\n");

        // loops through each station on route
        for (int i = 0; i < route.path.size(); i++) {
            String station = route.path.get(i);
            String line    = route.lines.get(i);

            // detects line changes & outputs them where necessary
            if (i > 0) {
                String prevLine = route.lines.get(i - 1);
                if (!prevLine.isEmpty() && !line.isEmpty() && !prevLine.equals(line)) {
                    sb.append("  ").append(station).append(" (").append(prevLine).append(")\n");
                    sb.append("*Change to ").append(line).append(" line*\n");
                }
            }

            sb.append("  ").append(station).append(" (").append(line).append(")\n");
        }

        // outputting total time taken on route
        sb.append("Time Taken: ").append(String.format("%.1f", route.total)).append(" minutes\n");

        // outputting if any changes were required on the route and where
        if (route.lineChanges.isEmpty()) {
            sb.append("No changes required");
        } else {
            sb.append("Line changes (").append(route.lineChanges.size()).append("):\n");
            for (int i = 0; i < route.lineChanges.size(); i++) {
                sb.append(" ").append(i + 1).append(". ")
                  .append(route.lineChanges.get(i)).append("\n");
            }
        }

        // outputting the newly built result string
        return sb.toString();
    }
}
