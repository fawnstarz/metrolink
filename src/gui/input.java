package gui;

// imports from swing for necessary components
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

// utilising jpanel for input field for destination and arrival locations, alongside check button & result
public class input extends JPanel{
    public Input(Stations stations) {
        JTextField departure = new JTextField(20);
        JTextField arrival = new JTextField(20);
        JButton check = new JButton("Check");
        JLabel result = new JLabel(" ");
    }
}
