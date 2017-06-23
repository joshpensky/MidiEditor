package cs3500.music.tests;

import cs3500.music.model.MusicEditorBuilder;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.MusicEditorOperations;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MidiConversion;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link MusicEditorModel} class.
 */
public class MusicEditorModelTest {
  private final CompositionBuilder<MusicEditorOperations> builder = new MusicEditorBuilder();
  private final MusicEditorOperations model = builder.build();


  // Tests for the default constructor
  @Test
  public void defaultConstructor() {
    model.create();
    assertEquals(0, model.getNotes().size());
    assertEquals(0, model.getTempo());
  }

  // Tests for the addNote method
  @Test(expected = IllegalArgumentException.class)
  public void addNoteInvalidOctane() {
    model.create();
    model.addNote(15, 20, 5, 22, 100);
  }


  @Test(expected = IllegalArgumentException.class)
  public void addNoteNegativePosition() {
    model.create();
    model.addNote(-1, 3, 1, 60, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteNegativeDuration() {
    model.create();
    model.addNote(3, 1, 1, 50, 100);
  }


  @Test
  public void addNoteAlreadyExistsAtLocation() {
    model.create();
    model.addNote(0, 1, 1, 50, 100);
    model.addNote(0, 1, 1, 50, 100);
    assertEquals(2, model.getNotes().size());
  }

  // Tests for the removeNote method
  @Test(expected = IllegalArgumentException.class)
  public void removeNoteInvalidOctave() {
    model.create();
    model.removeNote(1, 2, 20);
  }


  @Test(expected = IllegalArgumentException.class)
  public void removeNoteNegativePosition() {
    model.create();
    model.removeNote(-3, 2, 60);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeNoteNoNoteAtPosition() {
    model.create();
    model.removeNote(1, 2, 60);
  }

  // Tests for the editNotePitch method
  @Test(expected = IllegalArgumentException.class)
  public void editNotePitchInvalidOctave() {
    model.create();
    model.editNotePitch(1, 2, 20, 30);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNotePitchNegativePosition() {
    model.create();
    model.editNotePitch(-2, 2, 50, 70);
  }


  @Test(expected = IllegalArgumentException.class)
  public void editNotePitchNoNoteAtPosition() {
    model.create();
    model.editNotePitch(0, 1, 60, 62);
  }

  @Test
  public void editNotePitchNoteAtNewPosition() {
    model.create();
    model.addNote(0, 2, 1, 60, 100);
    model.addNote(0, 2, 1, 70, 100);
    model.editNotePitch(0, 1, 60, 70);
    assertEquals(2, model.getNotes().size());
  }

  // Tests for the editNotePosition method
  @Test(expected = IllegalArgumentException.class)
  public void editNotePositionInvalidOctave() {
    model.create();
    model.editNotePosition(0, 2, 129, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNotePositionNullPitch() {
    model.create();
    model.editNotePosition(0, 2,0, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNotePositionNegativePosition() {
    model.create();
    model.editNotePosition(-8, 3, 60, 42);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNotePositionNullNegativeNewPosition() {
    model.create();
    model.editNotePosition(1, 3, 50, -9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNotePositionNoNoteAtPosition() {
    model.create();
    model.editNotePosition(1, 3, 50, 20);
  }

  @Test
  public void editPositionNoteAtNewPosition() {
    model.create();
    model.addNote(0, 2, 1, 60, 100);
    model.addNote(1, 2, 1, 60, 100);
    model.editNotePosition(0, 1, 60, 1);
    assertEquals(2, model.getNotes().size());
  }

  // Tests for the editNoteDuration method
  @Test(expected = IllegalArgumentException.class)
  public void editNoteDurationInvalidOctave() {
    model.create();
    model.editNoteDuration(0, 1, 0, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNoteDurationNegativePosition() {
    model.create();
    model.editNoteDuration(-3, 1, 60, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNoteDurationNegativeNewDuration() {
    model.create();
    model.editNoteDuration(0, 1, 60, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNoteDurationZeroNewDuration() {
    model.create();
    model.editNoteDuration(4, 1, 60, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNoteDurationNoNoteAtPosition() {
    model.create();
    model.editNoteDuration(0, 1, 60, 2);
  }

  // Tests for the setTempo method
  @Test(expected = IllegalArgumentException.class)
  public void setTempoNegative() {
    model.create();
    model.setTempo(-5);
  }

  @Test
  public void setTempoValid() {
    model.create();
    model.setTempo(400);
    assertEquals(400, model.getTempo());
  }

  // Tests for the getTempo method
  @Test
  public void getTempoInit() {
    model.create();
    assertEquals(0, model.getTempo());
  }

  @Test
  public void getTempoAfterSetting() {
    model.create();
    model.setTempo(5342);
    assertEquals(5342, model.getTempo());
  }

  // Tests for the getNotes method
  @Test
  public void getNotesOnEmptyPiece() {
    model.create();
    assertEquals(model.getNotes().size(), 0);
  }

  @Test
  public void getNotesWithNotesThere1() {
    model.create();
    model.addNote(0, 1, 1, 60, 100);
    model.addNote(0, 2, 1, 70, 100);
    assertEquals(model.getNotes().size(), 2);
    assertEquals(model.getNotes().get(0)[MidiConversion.NOTE_START] == 0, true);
    assertEquals(model.getNotes().get(0)[MidiConversion.NOTE_END] == 1, true);
    assertEquals(model.getNotes().get(0)[MidiConversion.NOTE_INSTRUMENT] == 1, true);
    assertEquals(model.getNotes().get(0)[MidiConversion.NOTE_PITCH] == 60, true);
    assertEquals(model.getNotes().get(0)[MidiConversion.NOTE_VOLUME] == 100, true);
    assertEquals(model.getNotes().get(1)[MidiConversion.NOTE_START] == 0, true);
    assertEquals(model.getNotes().get(1)[MidiConversion.NOTE_END] == 2, true);
    assertEquals(model.getNotes().get(1)[MidiConversion.NOTE_INSTRUMENT] == 1, true);
    assertEquals(model.getNotes().get(1)[MidiConversion.NOTE_PITCH] == 70, true);
    assertEquals(model.getNotes().get(1)[MidiConversion.NOTE_VOLUME] == 100, true);
  }

  @Test
  public void getNotesWithNotesThere2() {
    model.create();
    model.addNote(0, 1, 1, 60, 100);
    model.addNote(0, 2, 1, 70, 100);
    model.removeNote(0, 1, 60);
    assertEquals(model.getNotes().size(), 1);
    assertEquals(model.getNotes().get(0)[MidiConversion.NOTE_START] == 0, true);
    assertEquals(model.getNotes().get(0)[MidiConversion.NOTE_END] == 2, true);
    assertEquals(model.getNotes().get(0)[MidiConversion.NOTE_INSTRUMENT] == 1, true);
    assertEquals(model.getNotes().get(0)[MidiConversion.NOTE_PITCH] == 70, true);
    assertEquals(model.getNotes().get(0)[MidiConversion.NOTE_VOLUME] == 100, true);
  }

  @Test
  public void getNotesWithNotesThere3() {
    model.create();
    model.addNote(0, 1, 1, 60, 100);
    model.addNote(0, 2, 1, 70, 100);
    model.editNoteDuration(0, 1, 60, 5);
    assertEquals(model.getNotes().get(0)[MidiConversion.NOTE_START] == 0, true);
    assertEquals(model.getNotes().get(0)[MidiConversion.NOTE_END] == 4, true);
    assertEquals(model.getNotes().get(0)[MidiConversion.NOTE_INSTRUMENT] == 1, true);
    assertEquals(model.getNotes().get(0)[MidiConversion.NOTE_PITCH] == 60, true);
    assertEquals(model.getNotes().get(0)[MidiConversion.NOTE_VOLUME] == 100, true);
    assertEquals(model.getNotes().get(1)[MidiConversion.NOTE_START] == 0, true);
    assertEquals(model.getNotes().get(1)[MidiConversion.NOTE_END] == 2, true);
    assertEquals(model.getNotes().get(1)[MidiConversion.NOTE_INSTRUMENT] == 1, true);
    assertEquals(model.getNotes().get(1)[MidiConversion.NOTE_PITCH] == 70, true);
    assertEquals(model.getNotes().get(1)[MidiConversion.NOTE_VOLUME] == 100, true);
  }

  // Tests for the getNotesAtBeat method
  @Test
  public void getNotesAtBeatIndexGreaterThanLength() {
    model.create();
    model.addNote(0, 1, 1, 60, 100);
    model.getNotesAtBeat(3);
    assertEquals(0, model.getNotesAtBeat(model.getLength() + 1).size());
  }

  @Test
  public void getNotesAtBeatNegativeIndex() {
    model.create();
    model.addNote(0, 1, 1, 60, 100);
    assertEquals(0, model.getNotesAtBeat(-1).size());
  }

  @Test
  public void getNotesAtBeatValid() {
    model.create();
    model.addNote(0, 1, 1, 60, 100);
    assertEquals(model.getNotesAtBeat(0).get(0)[MidiConversion.NOTE_START] == 0, true);
    assertEquals(model.getNotesAtBeat(0).get(0)[MidiConversion.NOTE_END] == 1, true);
    assertEquals(model.getNotesAtBeat(0).get(0)[MidiConversion.NOTE_INSTRUMENT] == 1, true);
    assertEquals(model.getNotesAtBeat(0).get(0)[MidiConversion.NOTE_PITCH] == 60, true);
    assertEquals(model.getNotesAtBeat(0).get(0)[MidiConversion.NOTE_VOLUME] == 100, true);
  }

  @Test
  public void getNotesAtBeatValid2() {
    model.create();
    model.addNote(0, 1, 1, 60, 100);
    model.addNote(0, 2, 1, 70, 100);
    assertEquals(model.getNotesAtBeat(0).get(0)[MidiConversion.NOTE_START] == 0, true);
    assertEquals(model.getNotesAtBeat(0).get(0)[MidiConversion.NOTE_END] == 1, true);
    assertEquals(model.getNotesAtBeat(0).get(0)[MidiConversion.NOTE_INSTRUMENT] == 1, true);
    assertEquals(model.getNotesAtBeat(0).get(0)[MidiConversion.NOTE_PITCH] == 60, true);
    assertEquals(model.getNotesAtBeat(0).get(0)[MidiConversion.NOTE_VOLUME] == 100, true);
    assertEquals(model.getNotesAtBeat(0).get(1)[MidiConversion.NOTE_START] == 0, true);
    assertEquals(model.getNotesAtBeat(0).get(1)[MidiConversion.NOTE_END] == 2, true);
    assertEquals(model.getNotesAtBeat(0).get(1)[MidiConversion.NOTE_INSTRUMENT] == 1, true);
    assertEquals(model.getNotesAtBeat(0).get(1)[MidiConversion.NOTE_PITCH] == 70, true);
    assertEquals(model.getNotesAtBeat(0).get(1)[MidiConversion.NOTE_VOLUME] == 100, true);
  }

  @Test
  public void getNotesAtBeatValid3() {
    model.create();
    model.addNote(2, 4, 1, 60, 100);
    List<Integer[]> temp = new ArrayList<>();
    assertEquals(temp, model.getNotesAtBeat(0));
  }

  // Tests for the getLength method
  @Test
  public void getLengthEmpty() {
    model.create();
    assertEquals(0, model.getLength());
  }

  @Test
  public void getLengthOneNote() {
    model.create();
    model.addNote(3, 45, 3, 60, 64);
    assertEquals(45, model.getLength());
  }

  @Test
  public void getLengthMultipleNotes() {
    model.create();
    model.addNote(3, 10, 3, 60, 64);
    model.addNote(20, 24, 67, 92, 63);
    model.addNote(4, 5, 16, 45, 120);
    assertEquals(24, model.getLength());
  }
}
