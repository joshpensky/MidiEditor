package cs3500.music.model;

import cs3500.music.util.MidiConversion;
import org.junit.Test;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by Will on 6/16/2017.
 */
public class MusicEditorBuilderTest {
    MusicEditorBuilder meb;

    void init() {
        meb = new MusicEditorBuilder();
    }

    @Test
    public void testBuildReturnsCorrect() {
        init();
        assertEquals(meb.build().getNotes().size(), 0 );
        assertEquals(meb.build().getTempo(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalTempo() {
        init();
        meb.setTempo(-5);
    }

    @Test
    public void testSetTempo() {
        init();
        meb.setTempo(5000);
        assertEquals(meb.build().getTempo(), 5000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNoteIllArgs1() {
        init();
        meb.addNote(-1, 0, 1, 60, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNoteIllArgs2() {
        init();
        meb.addNote(2, 1, 1, 60, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNoteIllArgs3() {
        init();
        meb.addNote(1, 2, -4, 60, 100);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddNoteIllArgs5() {
        init();
        meb.addNote(1, 2, 1, -4, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNoteIllArgs6() {
        init();
        meb.addNote(1, 2, 1, 128, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNoteIllArgs7() {
        init();
        meb.addNote(1, 2, 1, 60, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNoteIllArgs8() {
        init();
        meb.addNote(1, 2, 1, 60, 128);
    }

    @Test
    public void testAddNote1() {
        init();
        assertEquals(meb.build().totalPieceLength(), 0);
        meb.addNote(0, 50, 1, 60, 100);
        assertEquals(meb.build().totalPieceLength(), 50);
    }

    @Test
    public void testAddNote2() {
        init();
        meb.addNote(0, 1, 1, 60, 100);
        assertEquals(meb.build().getNotesAtBeat(0).get(0)[MidiConversion.NOTE_START] == 0, true);
        assertEquals(meb.build().getNotesAtBeat(0).get(0)[MidiConversion.NOTE_END] == 1, true);
        assertEquals(meb.build().getNotesAtBeat(0).get(0)[MidiConversion.NOTE_INSTRUMENT] == 1, true);
        assertEquals(meb.build().getNotesAtBeat(0).get(0)[MidiConversion.NOTE_PITCH] == 60, true);
        assertEquals(meb.build().getNotesAtBeat(0).get(0)[MidiConversion.NOTE_VOLUME] == 100, true);
    }





}
