import data.Graph;
import data.Stations;
import gui.Input;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import model.Parsing;

public class App {
    public static void main(String[] args) {
        // new instance of the stations class so necessary logic for list of stations runs
        Stations s = new Stations();
        s.run();

        // creates parser and builds graph from the csv file to pass onto the djikstra algorithm
        Parsing parser = new Parsing();
        Graph graph = parser.parse("/Users/aimee/Coding/Uni/metrolink/bin/data/metrolink_times.csv");
        // prints entire adjacency list for error purposes
        graph.print();

        SwingUtilities.invokeLater(() -> {
            // basic JFrame set up so users can interact with GUI & program functionality
            JFrame frame = new JFrame("Metrolink Planner");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // adds interactable elements from input class to the JFrame
            frame.add(new Input(s, graph));
            frame.pack();
            frame.setVisible(true);
        });
    }
}