package scale;

@Deprecated
public class findNoteIndex{
    //searches for a note in a list of notes

    public static int fni(String note, String[] noteList){
        int noteIndex = 0;
        int listLength = noteList.length;
        for(int i=0;i<listLength;i++){
            if(noteList[i] == note){
                noteIndex = i;
            }
        }
        return noteIndex;
    }
}