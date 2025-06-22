import java.util.Scanner;

/*
    take key and mode the user is working in
    take chord type the user is starting with
    generates a default circle of fifths. the same image will always be used to present the circle of fifths
    highlight section of circle of fifths being worked with. ie draw an outline around the tonic key and the notes next to it that are used
    label which surrounding notes/chords are what chord type in relation to the tonic ie c major will have:
    f - subdominant
    g - dominant
    a - superdominant/submediant
    etc...

    each chord change in music theory has a certain sound
    changing from a dominant chord to a tonic chord is a perfect cadence, which has this resolved, finished sound
    this function aims to take a chord within a key (let say g within c major), and determine either:
    how it sounds when it changes from g to c (perfect cadence)
    or
    what chord is needed to achieve a sound input by the user (ie user asks for a resolved sound, the program will output c major and that it is a
    perfect cadence)

    this will help the client with generating chord progressions

    the function should also have a feature to restablish the key their in, in case they want to make a key change in the piece their composing
    ie a progression goes from c to g, and then the client decides to continue composing in g major, then the relevant chords to the g major scale shows up


   the chord progressions and key changes will be stored in a file that represents their chord progression they created.
   also output the notes in each chord needed ie c major will output c, e and g, d major will output d, f#, and a, etc (just to avoid confusion)

*/

public class COFTakeUserInput {

    public COFUserInputResult getUserInput() {
        Scanner scanner = new Scanner(System.in);
        KeyFile.Key key = null;
        COFMMFile.COFMM COFMM = null;

        // Accept valid key input
        String keyEntered;
        while (true) {
            System.out.println("Enter Key (valid keys: A, B, C, D, E, F, G): ");
            keyEntered = scanner.nextLine().trim().toUpperCase();
            try {
                key = KeyFile.Key.valueOf(keyEntered); // Reference Key enum from KeyFile
                break; // Exit loop if valid key is entered
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Key must be one of: A, B, C, D, E, F, G.");
            }
        }
        System.out.println("Key is: " + key);

        // Accept valid mode input
        String mmEntered;
        while (true) {
            System.out.println("Enter Major or Minor: ");
            mmEntered = scanner.nextLine().trim().toUpperCase();
            try {
                COFMM = COFMMFile.COFMM.valueOf(mmEntered); // Reference Mode enum from ModeFile
                break; // Exit loop if valid mode is entered
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Mode must be one of: Ionian, Dorian, Phrygian, Lydian, Mixolydian, Aeolian, Locrian.");
            }
        }
        System.out.println("Chose: " + COFMM);

        return new COFUserInputResult(key, COFMM);
    }
}