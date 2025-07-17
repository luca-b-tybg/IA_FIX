package ui.components;

import scale.Note;

import javax.swing.*;

import static utils.FileUtils.getResourceUrl;

public class NoteImages {

    private static ImageIcon MINIM_ICON = new ImageIcon(getResourceUrl("notes/minim_ note_2.png"));
    private static ImageIcon SEMIBREVE_ICON = new ImageIcon(getResourceUrl("notes/semibreve_note_4.png"));
    private static ImageIcon QUAVER_ICON = new ImageIcon(getResourceUrl("notes/quaver_note_0_5.png"));
    private static ImageIcon CROTCHET_ICON = new ImageIcon(getResourceUrl("notes/crotchet_note_1.png"));
    private static ImageIcon DOTTED_CROTCHET_ICON = new ImageIcon(getResourceUrl("notes/dotted_crotchet_note_1_5.png"));
    private static ImageIcon DOTTED_MINIM_ICON = new ImageIcon(getResourceUrl("notes/dotted_minim_note_3.png"));

    public static ImageIcon getImageForNote(Note note) {
        return switch (note.getRhythmType()) {
            case MINIM -> MINIM_ICON;

            case QUAVER -> QUAVER_ICON;
            case CROTCHET -> CROTCHET_ICON;
            case DOTTED_CROTCHET -> DOTTED_CROTCHET_ICON;
            case DOTTED_MINIM -> DOTTED_MINIM_ICON;
            default -> SEMIBREVE_ICON;
        };
    }


}
