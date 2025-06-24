import java.util.Scanner;
import javax.swing.*;

/**
 * Main class that coordinates the music theory application
 * It provides a menu for users to select different music theory tools
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exitProgram = false;

        while (!exitProgram) {
            // Display the menu
            displayMenu();

            // Get user's choice
            int choice = getUserChoice();

            // Process the user's choice
            switch (choice) {
                case 1:
                    System.out.println("\nLaunching Diatonic Scale Generator...\n");
                    runScaleGenerator();
                    break;
                case 2:
                    System.out.println("\nLaunching Circle of Fifths Generator...\n");
                    runCircleOfFifths();
                    break;
                case 3:
                    System.out.println("\nLaunching Melody Generator...\n");
                    runMelodyGenerator();
                    break;
                case 0:
                    System.out.println("\nExiting the application. Goodbye!");
                    exitProgram = true;
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }

            // If not exiting, wait for user to press Enter before showing menu again
            if (!exitProgram) {
                System.out.println("\nPress Enter to return to the main menu...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    /**
     * Displays the main menu options
     */
    private static void displayMenu() {
        System.out.println("===============================================");
        System.out.println("       WELCOME TO THE MUSIC THEORY TOOLKIT     ");
        System.out.println("===============================================");
        System.out.println("1. Diatonic Scale Generator");
        System.out.println("2. Circle of Fifths Generator");
        System.out.println("3. Melody Generator");
        System.out.println("0. Exit");
        System.out.println("===============================================");
    }

    /**
     * Gets the user's menu choice
     * @return The user's selection (0-3)
     */
    private static int getUserChoice() {
        int choice = -1;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Enter your choice (0-3): ");
            try {
                String input = scanner.nextLine().trim();
                choice = Integer.parseInt(input);

                if (choice >= 0 && choice <= 3) {
                    validInput = true;
                } else {
                    System.out.println("Invalid input. Please enter a number between 0 and 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 0 and 3.");
            }
        }

        return choice;
    }

    /**
     * Runs the diatonic scale generator functionality
     * This preserves the original scale generation logic from the original Main class
     */
    private static void runScaleGenerator() {
        DSGUI gui = new DSGUI();

        // Wait for user to finish input in the GUI, then get the result
        // You may want to use a callback or observer pattern for production code
        // For a simple approach, you can check for null in a loop (not ideal for production)
        while (gui.getUserInputResult() == null) {
            try { Thread.sleep(100); } catch (InterruptedException e) {}
        }
        DSUserInputResult result = gui.getUserInputResult();

        int[] octRange = result.getOctRange();
        KeyFile.Key key = result.getKey();
        ModeFile.Mode mode = result.getMode();

        DS7Note sevenNoteTest = new DS7Note();
        sevenNoteTest.generateFinalScale(key, mode);

        GenerateFullScaleWithOctaves octaveTest = new GenerateFullScaleWithOctaves();
        octaveTest.fullFinalScale(octRange, sevenNoteTest );
    }

    /**
     * Runs the Circle of Fifths generator functionality
     */
    private static void runCircleOfFifths() {
        System.out.println("Circle of Fifths Generator");
        System.out.println("==========================");

        // Get input for the circle of fifths
        COFTakeUserInput userInput = new COFTakeUserInput();
        COFUserInputResult result = userInput.getUserInput();

        // Display basic information about the circle of fifths for selected key
        COFKeyFile.Key key = result.getKey();
        COFMMFile.COFMM COFMM = result.getCOFMM();

        System.out.println("\nSelected key: " + key + " " + COFMM);

        JFrame frame = new JFrame("Circle of Fifths");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(new CirclePanel(key, COFMM));
        frame.setVisible(true);
    }

    /**
     * Runs the Melody Generator functionality
     */
    private static void runMelodyGenerator() {
        System.out.println("Melody Generator");
        System.out.println("================");
/*
        // Get input for melody generation
        DSTakeUserInput userInput = new DSTakeUserInput();
        DSUserInputResult result = userInput.getUserInput();

        KeyFile.Key key = result.getKey();
        ModeFile.Mode mode = result.getMode();

        // Generate the scale as a basis for the melody
        Generate7NoteScale scaleGen = new Generate7NoteScale();
        scaleGen.setKey(key);
        scaleGen.setMode(mode);
        scaleGen.reorganizeScale();
        scaleGen.findSharpsAndFlats();

        // Display the scale
        System.out.println("\nScale for melody generation:");
        String[] scale = scaleGen.getOrganisedScale();
        for (String note : scale) {
            System.out.print(note + " ");
        }

        System.out.println("\n\nMelody generation will be implemented in future updates.");

 */
    }
}