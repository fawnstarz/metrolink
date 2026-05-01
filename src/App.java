import data.Stations;
import gui.Input;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        // new instance of the Stations class so necessary logic for list of stations runs
        Stations s = new Stations();
        s.run();

        //
        SwingUtilities.invokeLater(() -> {
            // basic JFrame set up so users can interact with GUI & program functionality
            JFrame frame = new JFrame("Metrolink Planner");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // adds interactable elements from input class to the JFrame
            frame.add(new Input(s));
            frame.pack();
            frame.setVisible(true);
        });
    }
}