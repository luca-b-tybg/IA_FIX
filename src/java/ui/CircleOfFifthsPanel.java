package ui;

import javax.swing.*;
import java.awt.*;

public class CircleOfFifthsPanel extends JPanel {

    static class ControlPanel extends JPanel {

        public ControlPanel(CircleOfFifthsComponent circleOfFifthsComponent) {
            //  setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            JButton resetBtn = new JButton("Reset");
            JButton renderScale = new JButton("Show Scale");
            renderScale.setEnabled(false);
            resetBtn.addActionListener(_e -> {
                circleOfFifthsComponent.reset();
                renderScale.setEnabled(false);
            });

            add(resetBtn);
            add(renderScale);
        }
    }


    public CircleOfFifthsPanel() {
        setLayout(new BorderLayout());
        CircleOfFifthsComponent circleOfFifthsComponent = new CircleOfFifthsComponent();
        add(new ControlPanel(circleOfFifthsComponent), BorderLayout.PAGE_END);
        add(circleOfFifthsComponent, BorderLayout.CENTER);
    }
}
