package ui;

import circle.CircleOfFifthsGenerator;
import circle.CircleOfFifthsKeyFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CircleOfFifthsComponent extends JPanel {
    private static final int RADIUS_ADJUSTMENT = 60;
    public List<CircleOfFifthsKeyFile> selectedKeys = new ArrayList<>();
    public CircleOfFifthsKeyFile topSelectedKey = null;
    public CircleOfFifthsKeyFile secondSelectedKey = null;


    public CircleOfFifthsComponent() {
        this.setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.removeAll();

        renderCircle(g, 1);
        renderCircle(g, 2);
        renderCircle(g, 3);

        renderLabels(CircleOfFifthsGenerator.MAJOR_KEYS, 1);
        renderLabels(CircleOfFifthsGenerator.MINOR_KEYS, 2);
        renderLabels(CircleOfFifthsGenerator.DIMINISHED_KEYS, 3);
    }

    private Set<CircleOfFifthsKeyFile> getAllSelected() {
        Set<CircleOfFifthsKeyFile> all = new HashSet<>();
        for (CircleOfFifthsKeyFile keyFile : selectedKeys) {
            all.addAll(CircleOfFifthsGenerator.select(keyFile));
        }
        return all;
    }

    private boolean isSelectedRoot(CircleOfFifthsKeyFile keyFile) {

        return (topSelectedKey != null && topSelectedKey.equals(keyFile))
                ||
                (secondSelectedKey != null && secondSelectedKey.equals(keyFile));

    }

    private void selectKey(CircleOfFifthsKeyFile keyFile) {
        if(topSelectedKey == null) {
            topSelectedKey = keyFile;
        } else {
            secondSelectedKey = keyFile;
        }
        selectedKeys.add(keyFile);
        repaint();
    }

    public void renderLabels(CircleOfFifthsKeyFile[] keys, int circleLevel) {

        Set<CircleOfFifthsKeyFile> selectedKeys = getAllSelected();
        for (int i = 0; i < keys.length; i++) {
            var currentKey = keys[i];
            double angle = Math.toRadians((360 / 12.) * i - 90);
            var keyLabel = new JLabel(currentKey.toString());
            keyLabel.setFont(new Font("Default", Font.BOLD, 14));
            keyLabel.setSize(50, 16);
            if (isSelectedRoot(currentKey)) {
                keyLabel.setForeground(Color.RED);
                keyLabel.setFont(new Font("Default", Font.BOLD, 16));
            } else if (selectedKeys.contains(currentKey)) {
                keyLabel.setForeground(new Color(33, 36, 209));
            }

            int xMaj = (int) (getWidth() / 2. + Math.cos(angle) * (getRadius(circleLevel) - 10));
            int yMaj = (int) (getHeight() / 2. + Math.sin(angle) * (getRadius(circleLevel) - 10));
            keyLabel.setLocation(xMaj, yMaj);
            if (!currentKey.isDiminished()) {
                keyLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                keyLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        CircleOfFifthsComponent.this.selectKey(currentKey);
                    }


                });
            }
            this.add(keyLabel);
        }
    }

    private void renderCircle(Graphics g, int circleLevel) {
        int radius = getRadius(circleLevel);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        ((Graphics2D) g).setStroke(new BasicStroke(2));
        if (circleLevel == 1) {
            g.setColor(Color.DARK_GRAY);
        } else if (circleLevel == 2) {
            g.setColor(Color.GRAY);
        } else {
            g.setColor(Color.LIGHT_GRAY);
        }

        g.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
    }

    private int getRadius(int circleLevel) {
        return Math.min(getWidth(), getHeight()) / 2 - RADIUS_ADJUSTMENT * circleLevel;
    }


    public void reset() {
        selectedKeys.clear();
        topSelectedKey = null;
        secondSelectedKey = null;
        repaint();
    }
}