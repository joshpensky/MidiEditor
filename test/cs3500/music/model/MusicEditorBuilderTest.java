package cs3500.music.model;

import cs3500.music.util.MidiConversion;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link MusicEditorBuilder} class.
 */
public class MusicEditorBuilderTest {
  private final MusicEditorBuilder builder = new MusicEditorBuilder();

  // Tests for the build method
  @Test
  public void buildImmediately() {
    assertEquals(builder.build().getNotes().size(), 0);
    assertEquals(builder.build().getTempo(), 0);
  }

  @Test
  public void buildAfterTempo() {
    builder.setTempo(400);
    assertEquals(builder.build().getNotes().size(), 0);
    assertEquals(builder.build().getTempo(), 400);
  }

  @Test
  public void buildAfterAddingNotes() {
    builder.addNote(5, 20, 30, 78, 59);
    builder.addNote(0, 13, 21, 45, 120);
    builder.addNote(9, 12, 3, 60, 67);
    assertEquals(builder.build().getNotes().size(), 3);
    assertEquals(builder.build().getTempo(), 0);
  }

  @Test
  public void buildAfterAddingNotesAndTempo() {
    builder.addNote(5, 20, 30, 78, 59);
    builder.addNote(0, 13, 21, 45, 120);
    builder.addNote(9, 12, 3, 60, 67);
    builder.setTempo(400);
    assertEquals(builder.build().getNotes().size(), 3);
    assertEquals(builder.build().getTempo(), 400);
  }

  // Tests for the setTempo method
  @Test(expected = IllegalArgumentException.class)
  public void setTempoNegative() {
    builder.setTempo(-5);
  }

  @Test
  public void setTempoValid() {
    builder.setTempo(5000);
    assertEquals(builder.build().getTempo(), 5000);
  }

  // Tests for the addNote method
  @Test(expected = IllegalArgumentException.class)
  public void addNoteNegativeStart() {
    builder.addNote(-1, 5, 1, 60, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteNegativeEnd() {
    builder.addNote(3, -5, 1, 60, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteEndBeforeStart() {
    builder.addNote(3, 2, 1, 60, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteInstrumentTooLow() {
    builder.addNote(3, 5, -1, 60, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteInstrumentTooHigh() {
    builder.addNote(3, 5, 128, 60, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNotePitchTooLow() {
    builder.addNote(3, 5, 1, -1, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNotePitchTooHigh() {
    builder.addNote(3, 5, 1, 128, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteVolumeTooLow() {
    builder.addNote(3, 5, 1, 60, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteVolumeTooHigh() {
    builder.addNote(3, 5, 1, 60, 128);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteAlreadyExistsAtStartAndPitchSameInstrument() {
    builder.addNote(0, 50, 1, 60, 100);
    builder.addNote(0, 20, 1, 60, 70);
  }

  @Test
  public void addNoteAlreadyExistsAtStartAndPitchDiffInstrument() {
    builder.addNote(0, 50, 1, 60, 100);
    builder.addNote(0, 20, 3, 40, 70);
    assertEquals(2, builder.build().getNotes().size());
  }

  @Test
  public void addNoteAlreadyExistsAtStartNotPitchSameInstrument() {
    builder.addNote(0, 50, 1, 60, 100);
    builder.addNote(0, 20, 1, 40, 70);
    assertEquals(2, builder.build().getNotes().size());
  }

  @Test
  public void addNotePieceLengthChanges() {
    assertEquals(builder.build().getLength(), 0);
    builder.addNote(0, 50, 1, 60, 100);
    assertEquals(builder.build().getLength(), 50);
  }

  @Test
  public void addNoteAppearsInGetNotes() {
    builder.addNote(4, 16, 21, 92, 45);
    assertEquals(1, builder.build().getNotes().size());
    assertEquals(0, builder.build().getNotesAtBeat(0).size());
    assertEquals(1, builder.build().getNotesAtBeat(4).size());

    assertEquals(4, (int) builder.build().getNotes().get(0)[MidiConversion.NOTE_START]);
    assertEquals(16, (int) builder.build().getNotes().get(0)[MidiConversion.NOTE_END]);
    assertEquals(21, (int) builder.build().getNotes().get(0)[MidiConversion.NOTE_INSTRUMENT]);
    assertEquals(92, (int) builder.build().getNotes().get(0)[MidiConversion.NOTE_PITCH]);
    assertEquals(45, (int) builder.build().getNotes().get(0)[MidiConversion.NOTE_VOLUME]);
  }
}
