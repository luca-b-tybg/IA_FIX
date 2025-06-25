import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class COFDraw {

    public void drawCircleOfFifths(Graphics2D g2, int centerX, int centerY, int radius, COFKeyFile.Key key, COFMMFile.COFMM mm, JButton[] relTonButton, JButton[] relKeyButton) {
        // Key names
        String[] majors = {"C", "G", "D", "A", "E", "B", "F#", "C#", "G#", "D#", "A#", "F"};
        String[] minors = {"a", "e", "b", "f#", "c#", "g#", "d#", "a#", "f", "c", "g", "d"};
        String[] diminished = {"Bdim", "F#dim", "C#dim", "G#dim", "D#dim", "A#dim", "Fdim", "Cdim", "Gdim", "Ddim", "Adim", "Edim"};

        String highlightTonic = "";
        String highlightDiminished = "";
        ArrayList<String> highlightRelated = new ArrayList<>();

        for(int i=0;i<5;i++){
            relTonButton[i] = new JButton(highlightRelated.get(i));
        }

        if (mm == COFMMFile.COFMM.MAJOR){
            for(int i=0;i<majors.length;i++){
                if (majors[i] == key.name()){
                    highlightTonic = majors[i];
                    highlightDiminished = diminished[i];
                    if (i == 0) {
                        highlightRelated.add(majors[11]);
                        highlightRelated.add(majors[i+1]);
                        highlightRelated.add(minors[11]);
                        highlightRelated.add(minors[i+1]);
                    }
                    else if (i == 11){
                        highlightRelated.add(majors[0]);
                        highlightRelated.add(majors[i-1]);
                        highlightRelated.add(minors[0]);
                        highlightRelated.add(minors[i-1]);
                    }
                    else {
                        highlightRelated.add(majors[i - 1]);
                        highlightRelated.add(majors[i + 1]);
                        highlightRelated.add(minors[i - 1]);
                        highlightRelated.add(minors[i + 1]);
                    }
                    highlightRelated.add(minors[i]);
                }
            }
        }
        else if (mm == COFMMFile.COFMM.MINOR){
            for(int i=0;i<minors.length;i++){
                if (minors[i].toUpperCase().equals(key.name())){
                    highlightTonic = minors[i];
                    highlightDiminished = diminished[i];
                    if (i == 0) {
                        highlightRelated.add(minors[11]);
                        highlightRelated.add(minors[i+1]);
                        highlightRelated.add(majors[11]);
                        highlightRelated.add(majors[i+1]);
                    }
                    else if (i == 11){
                        highlightRelated.add(minors[0]);
                        highlightRelated.add(minors[i-1]);
                        highlightRelated.add(majors[0]);
                        highlightRelated.add(majors[i-1]);
                    }
                    else {
                        highlightRelated.add(majors[i - 1]);
                        highlightRelated.add(majors[i + 1]);
                        highlightRelated.add(minors[i - 1]);
                        highlightRelated.add(minors[i + 1]);
                    }
                    highlightRelated.add(majors[i]);

                }
            }
        }

        int minorRadius = radius - 40;
        int diminishedRadius = minorRadius - 40;

        // Draw circles
        g2.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius); // Major
        g2.drawOval(centerX - minorRadius, centerY - minorRadius, 2 * minorRadius, 2 * minorRadius); // Minor
        g2.drawOval(centerX - diminishedRadius, centerY - diminishedRadius, 2 * diminishedRadius, 2 * diminishedRadius); // Diminished

        // Draw labels
        for (int i = 0; i < 12; i++) {
            double angle = Math.toRadians((360 / 12) * i - 90);
            // Major (outer)
            int xMaj = (int) (centerX + Math.cos(angle) * (radius - 10));
            int yMaj = (int) (centerY + Math.sin(angle) * (radius - 10));
            if (majors[i].equals(highlightTonic)) {
                g2.setColor(Color.RED);
            } else if (highlightRelated.contains(majors[i])) {
                g2.setColor(Color.ORANGE);
            } else {
                g2.setColor(Color.BLACK);
            }
            g2.drawString(majors[i], xMaj - 10, yMaj + 5);

            // Minor (middle)
            int xMin = (int) (centerX + Math.cos(angle) * (minorRadius - 10));
            int yMin = (int) (centerY + Math.sin(angle) * (minorRadius - 10));
            if (highlightTonic.equals(minors[i])) {
                g2.setColor(Color.RED);
            } else if (highlightRelated.contains(minors[i])) {
                g2.setColor(Color.ORANGE);
            } else {
                g2.setColor(Color.BLACK);
            }
            g2.drawString(minors[i], xMin - 10, yMin + 5);

            // Diminished (inner)
            int xDim = (int) (centerX + Math.cos(angle) * (diminishedRadius - 10));
            int yDim = (int) (centerY + Math.sin(angle) * (diminishedRadius - 10));
            if (diminished[i].equals(highlightDiminished)) {
                g2.setColor(Color.ORANGE);
            } else {
                g2.setColor(Color.BLACK);
            }
            g2.drawString(diminished[i], xDim - 10, yDim + 5);
        }
    }
}