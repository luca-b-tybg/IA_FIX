package melodygenerator;

import circle.CircleOfFifthsKeyFile;
import scale.KeyFile;
import scale.Note;
import scale.Octave;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MelodyGenerator {

    public static void main(String[] args) {
        {
            String[] egChordProg = {"A", "D", "c#", "G#dim", "f#", "A", "g", "F", "A"};
            int numberOfBars = egChordProg.length;
            double[] noteLengths = {4, 3, 2, 1.5, 1, 0.5};
            for (int i = 0; i <= numberOfBars; i++) {
                System.out.println(BarRhythm(noteLengths));
            }
        }
    }
    public List<Octave> generate(List<CircleOfFifthsKeyFile> keys) {
        Octave fourthOctave = new Octave(4);
        fourthOctave.add(Note.forKey(KeyFile.C));
        return List.of(fourthOctave);
    }


        public static ArrayList<Double> BarRhythm(double[] noteLengths){
            double beatCount = 4;
            Random rand = new Random();
            ArrayList<Double> barNoteLengths = new ArrayList<Double>();

            while(beatCount > 0){
                int randomLength = rand.nextInt(6);
                if (beatCount >= noteLengths[randomLength]){
                    barNoteLengths.add(noteLengths[randomLength]);
                    beatCount -= noteLengths[randomLength];
                  //  System.out.println(Double.toString(noteLengths[randomLength]));
                }

                //else if(beatCount < noteLengths[randomLength]){
                 //   randomLength = rand.nextInt(6);
                //}
            }
            return barNoteLengths;
        }

        //then randomly select a note for each double value in the list.
        //then randomly select an octave between octaves 4 and 5 for each note


}
