package ui.components;

import javax.swing.*;
import java.awt.*;

public class ScoreBarComponent extends JComponent {
    public ScoreBarComponent(int width, int height) {
        setSize(new Dimension(width,height));
    }

    @Override
    public void paint(Graphics g) {
        ((Graphics2D) g).setStroke(new BasicStroke(getWidth()));
        g.drawLine(0, 0, 0, getHeight());
    }
}
