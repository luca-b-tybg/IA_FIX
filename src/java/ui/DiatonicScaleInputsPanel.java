package ui;

import diatonicscale.DiatonicScaleInputs;
import scale.KeyFile;
import scale.Mode;
import scale.OctaveRange;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class DiatonicScaleInputsPanel extends JPanel {

    private JPanel startingOctaveInput; // Starting octave
    private JPanel endingOctaveInput; // Ending octave
    private JComboBox<KeyFile> keyInputCb = new JComboBox<>(KeyFile.values());  // Key
    private JComboBox<Mode> tonalityInputCb = new JComboBox<>(Mode.values());  // Tonality/Mode
    private java.util.List<DiatonicScaleParameterListener> parameterListeners = new ArrayList<>();


    // Variables for result
    private int[] octRange = {2, 3};

    private void onParamChanged() {
        try {
            for(Component c : startingOctaveInput.getComponents()) {
                if(c instanceof JRadioButton ) {
                    JRadioButton b = (JRadioButton) c;
                    if(Integer.parseInt(b.getText()) > getEndOctave()) {
                        b.setEnabled(false);
                    } else {
                        b.setEnabled(true);
                    }
                }
            }

            for(Component c : endingOctaveInput.getComponents()) {
                if(c instanceof JRadioButton ) {
                    JRadioButton b = (JRadioButton) c;
                    if(Integer.parseInt(b.getText()) < getStartOctave()) {
                        b.setEnabled(false);
                    } else {
                        b.setEnabled(true);
                    }
                }
            }
            DiatonicScaleInputs userInputResult = new DiatonicScaleInputs(new OctaveRange(octRange[0], octRange[1]),
                    (KeyFile) keyInputCb.getSelectedItem(),
                    (Mode) tonalityInputCb.getSelectedItem());
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

    public DiatonicScaleInputsPanel() {
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
        setBorder(new LineBorder(Color.BLUE));

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