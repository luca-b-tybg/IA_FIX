package ui;

import diatonicscale.DS7Scales;
import diatonicscale.DiatonicScaleInputs;
import scale.Mode;
import scale.Note;
import scale.OctaveRange;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * The Panel that contains the input parameters for
 */
public class DiatonicScaleInputsComponent extends JPanel {

    private JPanel startingOctaveInput; // Starting octave
    private JPanel endingOctaveInput; // Ending octave
    private JComboBox<Note> keyInputCb = new JComboBox<>(DS7Scales.getKnownScales());  // Key
    private JComboBox<Mode> tonalityInputCb = new JComboBox<>(Mode.values());  // Tonality/Mode
    private java.util.List<DiatonicScaleParameterListener> parameterListeners = new ArrayList<>();


    // Variables for result
    private int[] octRange = {2, 3};

    private void onParamChanged() {
        try {
            //for each radiobutton placed on the startOctaveInput panel we enable or disable the valid octave ranges
            for (Component c : startingOctaveInput.getComponents()) {
                if (c instanceof JRadioButton b) {

                    b.setEnabled(Integer.parseInt(b.getText()) < getEndOctave());
                }
            }
            //for each radiobutton placed on the startOctaveInput panel we enable or disable the valid octave ranges
            for (Component c : endingOctaveInput.getComponents()) {
                if (c instanceof JRadioButton b) {
                    //enable onl the buttons with the text greater than start octave
                    b.setEnabled(Integer.parseInt(b.getText()) > getStartOctave());
                }
            }

            DiatonicScaleInputs userInputResult = new DiatonicScaleInputs(new OctaveRange(octRange[0], octRange[1]),
                    (Note) keyInputCb.getSelectedItem(),
                    (Mode) tonalityInputCb.getSelectedItem());

            //we notify each listener that the Sale Input parameter has been changed
            for (DiatonicScaleParameterListener listener : parameterListeners) {
                listener.onDiatonicScaleParametersChanged(userInputResult);
            }
        } catch (Exception e) {
            System.out.println("Error when creating parameters" + e);
            e.printStackTrace();
        }


    }

    private int getEndOctave() {
        return octRange[1];
    }

    private int getStartOctave() {
        return octRange[0];
    }

    //creates a panel that contains radio buttons for selecting the octave range
    private JPanel getOctaveRangePanel(int from, int to, int octaveIndex) {
        JPanel container = new JPanel(new FlowLayout());
        container.setPreferredSize(new Dimension(300, 60));
        ButtonGroup group = new ButtonGroup();
        for (int i = from; i <= to; i++) {
            JRadioButton b = new JRadioButton("" + i);
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            final int justI = i;
            if (i == octRange[octaveIndex]) {
                b.setSelected(true);
            }
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    octRange[octaveIndex] = justI;
                    onParamChanged();
                }
            });
            group.add(b);
            container.add(b);
        }
        return container;
    }

    public DiatonicScaleInputsComponent() {
        startingOctaveInput = getOctaveRangePanel(2, 5, 0); // Starting octave
        endingOctaveInput = getOctaveRangePanel(3, 6, 1); // Ending octave
        keyInputCb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onParamChanged();
            }
        });
        tonalityInputCb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onParamChanged();
            }
        });
        setPreferredSize(new Dimension(300, 300));
        setLayout(null);

        addLabelAndField("Starting octave:", 30, startingOctaveInput);
        addLabelAndField("Ending octave:", 70, endingOctaveInput);
        addLabelAndField("Key (A-G):", 110, keyInputCb);
        addLabelAndField("Tonality (Mode):", 150, tonalityInputCb);

    }

    private void addLabelAndField(String labelText, int y, JComponent component) {
        JLabel label = new JLabel(labelText);
        label.setBounds(10, y, 120, 30);
        add(label);
        component.setBounds(110, y, 180, 30);
        add(component);
    }

    public void addParameterChangeListener(DiatonicScaleParameterListener listener) {
        parameterListeners.add(listener);
        onParamChanged();
    }


}