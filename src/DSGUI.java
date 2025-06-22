import javax.swing.*;
import java.awt.event.*;

public class DSGUI extends JFrame {

    private JTextField SOuserInput; // Starting octave
    private JTextField EOuserInput; // Ending octave
    private JTextField KuserInput;  // Key
    private JTextField TuserInput;  // Tonality/Mode
    private JButton buttonOK;

    // Variables to store user input
    private int startingOctave;
    private int endingOctave;
    private String key;
    private String tonality;

    // Variables for result
    private int[] octRange;
    private KeyFile.Key keyEnum;
    private ModeFile.Mode modeEnum;
    private DSUserInputResult userInputResult;

    public DSGUI() {
        setTitle("Diatonic scale");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(null);

        SOuserInput = addLabelAndField("Starting octave (2-5):", 30);
        EOuserInput = addLabelAndField("Ending octave (3-6):", 70);
        KuserInput  = addLabelAndField("Key (A-G):", 110);
        TuserInput  = addLabelAndField("Tonality (Mode):", 150);

        buttonOK = new JButton("OK");
        buttonOK.setBounds(150, 200, 100, 30);
        add(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInputs()) {
                    // If valid, you can now use userInputResult elsewhere
                    // For demonstration, show a success dialog
                    JOptionPane.showMessageDialog(null,
                            "Inputs accepted:\n" +
                                    "Starting Octave: " + startingOctave +
                                    "\nEnding Octave: " + endingOctave +
                                    "\nKey: " + keyEnum +
                                    "\nTonality: " + modeEnum
                    );
                    // Optionally, you can close the GUI or signal to main that input is ready
                    // dispose();
                }
                // If not valid, error dialogs are already shown in validateInputs()
            }
        });

        setVisible(true);
    }

    private JTextField addLabelAndField(String labelText, int y) {
        JLabel label = new JLabel(labelText);
        label.setBounds(50, y, 150, 30);
        add(label);

        JTextField field = new JTextField();
        field.setBounds(200, y, 100, 30);
        add(field);

        return field;
    }

    // Validate all inputs and show error messages in the GUI
    private boolean validateInputs() {
        // Validate starting octave
        try {
            startingOctave = Integer.parseInt(SOuserInput.getText().trim());
            if (startingOctave < 2 || startingOctave > 5) {
                JOptionPane.showMessageDialog(this, "Starting octave must be between 2 and 5.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Starting octave must be a number between 2 and 5.");
            return false;
        }

        // Validate ending octave
        try {
            endingOctave = Integer.parseInt(EOuserInput.getText().trim());
            if (endingOctave < 3 || endingOctave > 6) {
                JOptionPane.showMessageDialog(this, "Ending octave must be between 3 and 6.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ending octave must be a number between 3 and 6.");
            return false;
        }

        // Validate key (A-G)
        key = KuserInput.getText().trim().toUpperCase();
        try {
            keyEnum = KeyFile.Key.valueOf(key);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Key must be one of: A, B, C, D, E, F, G.");
            return false;
        }

        // Validate mode
        tonality = TuserInput.getText().trim().toUpperCase();
        try {
            modeEnum = ModeFile.Mode.valueOf(tonality);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Mode must be one of: Ionian, Dorian, Phrygian, Lydian, Mixolydian, Aeolian, Locrian.");
            return false;
        }

        // All inputs valid, set up result object
        octRange = new int[] { startingOctave, endingOctave };
        userInputResult = new DSUserInputResult(octRange, keyEnum, modeEnum);
        return true;
    }

    // Getter for result, so main can access it
    public DSUserInputResult getUserInputResult() {
        return userInputResult;
    }

    public static void main(String[] args) {
        new DSGUI();
    }
}