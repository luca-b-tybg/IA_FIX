package ui.components;

import scale.Note;

import javax.swing.*;
import java.awt.*;

public class NoteComponent extends JComponent {
    public enum CrossLinePosition {
        MIDDLE, BOTTOM, TOP, NONE
    }

    private final CrossLinePosition crossLinePosition;

    public NoteComponent(Note note, CrossLinePosition crossLinePosition) {
        this.crossLinePosition = crossLinePosition;
        JLabel label = new JLabel();
        ImageIcon imageIcon = NoteImages.getImageForNote(note);
        label.setIcon(imageIcon);
        label.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
        setSize(label.getSize());
        add(label);
    }

    public NoteComponent(Note note) {
        this(note, CrossLinePosition.NONE);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        ((Graphics2D) g).setStroke(new BasicStroke(2));
        if (crossLinePosition == CrossLinePosition.MIDDLE) {
            g.drawLine(0, getHeight() - 10, getWidth(), getHeight() - 10);
        }

        if (crossLinePosition == CrossLinePosition.BOTTOM) {
            g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
        }
        if (crossLinePosition == CrossLinePosition.TOP) {
            g.drawLine(0, getHeight() - 20, getWidth(), getHeight() - 20);
        }
    }
}
