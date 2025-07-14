import ui.CircleOfFifthsComponent;
import ui.CircleOfFifthsPanel;
import ui.DiatonicScalePanel;

import javax.swing.*;
import java.util.Scanner;

/**
 * Main class that coordinates the music theory application
 * It provides a menu for users to select different music theory tools
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        showWindow();
    }

    private static void showWindow() {
        JFrame frame = new JFrame("Music Theory Toolkit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 768);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Circle of Fifths Generator", new CircleOfFifthsPanel(frame));
        tabbedPane.addTab("Diatonic Scale Generator", new DiatonicScalePanel());
        frame.add(tabbedPane);
        frame.setVisible(true);
        //some change 2
    }
}