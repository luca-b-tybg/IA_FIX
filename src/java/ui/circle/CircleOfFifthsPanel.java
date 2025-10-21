package ui.circle;

import javax.swing.*;
import java.awt.*;

/**
 * Main Container for Circle of fifths feature
 * contains the CircleOfFifthsComponent that renders the circle and control panel that adds
 * the control buttons
 */
public class CircleOfFifthsPanel extends JPanel {


    private final CircleOfFifthsComponent circleOfFifthsComponent = new CircleOfFifthsComponent();

    public CircleOfFifthsPanel(JFrame parentWindow) {
        setLayout(new BorderLayout());
        CircleOfFifthsControlPanel controlPanel = new CircleOfFifthsControlPanel(parentWindow, circleOfFifthsComponent);
        add(controlPanel, BorderLayout.PAGE_END);
        circleOfFifthsComponent.addProgressionChangeListener(controlPanel);
        add(circleOfFifthsComponent, BorderLayout.CENTER);
    }


}
