package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link MusicEditorModel} class.
 */
public class MusicEditorModelTest {
  private final MusicEditorOperations model = new MusicEditorModel();

  // Tests for the default constructor
  @Test
  public void defaultConstructor() {
    model.create();
    assertEquals("", model.view());
  }


  // Tests for the view method
  @Test(expected = IllegalStateException.class)
  public void viewNothingOpen() {
    model.view();
  }

  @Test
  public void viewEmptyPiece() {
    model.create();
    assertEquals("", model.view());
  }

  @Test
  public void viewSingleNote() {
    model.create();
    model.addNote(1, 3, 1, 50, 100);
    String view = "     D3 \n"
                + "0       \n"
                + "1    X  \n"
                + "2    |  \n"
                + "3    |  \n";
    assertEquals(view, model.view());
  }

  @Test
  public void viewOverlayingNotes() {
    model.create();
    model.addNote(2, 5, 1, 48, 100);
    model.addNote(1, 3, 1, 50, 100);
    model.addNote(4, 6, 1, 51, 100);
    model.addNote(6, 9, 1, 51, 100);
    String view = "     C3  C#3   D3  D#3 \n"
                + "0                      \n"
                + "1              X       \n"
                + "2    X         |       \n"
                + "3    |         |       \n"
                + "4    |              X  \n"
                + "5    |              |  \n"
                + "6                   X  \n"
                + "7                   |  \n"
                + "8                   |  \n"
                + "9                   |  \n";
    assertEquals(view, model.view());
  }

  @Test
  public void toStringLineNumbersRightAligned() {
    model.create();
    model.addNote(2, 5, 1, 48, 100);
    model.addNote(1, 3, 1, 50, 100);
    model.addNote(4, 6, 1, 51, 100);
    model.addNote(6, 8, 1, 51, 100);
    model.addNote(8, 11, 1, 51, 100);
    String view = "      C3  C#3   D3  D#3 \n"
                + " 0                      \n"
                + " 1              X       \n"
                + " 2    X         |       \n"
                + " 3    |         |       \n"
                + " 4    |              X  \n"
                + " 5    |              |  \n"
                + " 6                   X  \n"
                + " 7                   |  \n"
                + " 8                   X  \n"
                + " 9                   |  \n"
                + "10                   |  \n"
                + "11                   |  \n";
    assertEquals(view, model.view());
  }

  @Test
  public void viewSpanOneOctave() {
    model.create();
    model.addNote(2, 9, 1, 48, 100);
    model.addNote(1, 3, 1, 50, 100);
    model.addNote(0, 0, 1, 59, 100);
    String view = "     C3  C#3   D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3 \n"
                + "0                                                           X  \n"
                + "1              X                                               \n"
                + "2    X         |                                               \n"
                + "3    |         |                                               \n"
                + "4    |                                                         \n"
                + "5    |                                                         \n"
                + "6    |                                                         \n"
                + "7    |                                                         \n"
                + "8    |                                                         \n"
                + "9    |                                                         \n";
    assertEquals(view, model.view());
  }

  @Test
  public void viewSpanTwoOctaves() {
    model.create();
    model.addNote(4, 9, 1, 56, 100);
    model.addNote(2, 5, 1, 62, 100);
    String view = "    G#3   A3  A#3   B3   C4  C#4   D4 \n"
                + "0                                     \n"
                + "1                                     \n"
                + "2                                  X  \n"
                + "3                                  |  \n"
                + "4    X                             |  \n"
                + "5    |                             |  \n"
                + "6    |                                \n"
                + "7    |                                \n"
                + "8    |                                \n"
                + "9    |                                \n";
    assertEquals(view, model.view());
  }

  @Test
  public void viewSpanMultipleOctaves() {
    model.create();
    model.addNote(2, 4, 1, 47, 100);
    model.addNote(4, 8, 1, 47, 100);
    model.addNote(4, 9, 1, 56, 100);
    model.addNote(2, 5, 1, 62, 100);
    String view = ""
        + "     B2   C3  C#3   D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4 \n"
        + "0                                                                                  \n"
        + "1                                                                                  \n"
        + "2    X                                                                          X  \n"
        + "3    |                                                                          |  \n"
        + "4    X                                            X                             |  \n"
        + "5    |                                            |                             |  \n"
        + "6    |                                            |                                \n"
        + "7    |                                            |                                \n"
        + "8    |                                            |                                \n"
        + "9                                                 |                                \n";
    assertEquals(view, model.view());
  }

  @Test
  public void toStringSpanMultipleOctavesNoneInMiddle() {
    model.create();
    model.addNote(2, 4, 1, 47, 100);
    model.addNote(4, 8, 1, 47, 0);
    model.addNote(2, 5, 1, 62, 0);
    String view = ""
        + "     B2   C3  C#3   D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4 \n"
        + "0                                                                                  \n"
        + "1                                                                                  \n"
        + "2    X                                                                          X  \n"
        + "3    |                                                                          |  \n"
        + "4    X                                                                          |  \n"
        + "5    |                                                                          |  \n"
        + "6    |                                                                             \n"
        + "7    |                                                                             \n"
        + "8    |                                                                             \n";
    assertEquals(view, model.view());
  }

  // Tests for the addNote method
  @Test(expected = IllegalStateException.class)
  public void addNoteNothingOpened() {
    model.addNote(0, 1, 1, 60, 100);
  }

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


  @Test(expected = IllegalArgumentException.class)
  public void addNoteAlreadyExistsAtLocation() {
    model.create();
    model.addNote(0, 1, 1, 50, 100);
    model.addNote(0, 1, 1, 50, 100);
  }

  @Test
  public void addNoteValid() {
    model.create();
    model.addNote(2, 4, 1, 55, 100);
    String view = "     G3 \n"
                + "0       \n"
                + "1       \n"
                + "2    X  \n"
                + "3    |  \n"
                + "4    |  \n";
    assertEquals(view, model.view());
  }

  // Tests for the removeNote method
  @Test(expected = IllegalStateException.class)
  public void removeNoteNothingOpened() {
    model.removeNote(1, 2, 60);
  }

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

  @Test
  public void removeNoteValid() {
    model.create();
    model.addNote(0, 1, 1, 60, 100);
    model.removeNote(0, 1, 60);
    assertEquals("", model.view());
  }

  // Tests for the editNotePitch method
  @Test(expected = IllegalStateException.class)
  public void editNotePitchNothingOpened() {
    model.editNotePitch(0, 1, 60, 59);
  }

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

  @Test(expected = IllegalArgumentException.class)
  public void editNotePitchNoteAtNewPosition() {
    model.create();
    model.addNote(0, 2, 1, 60, 100);
    model.addNote(0, 2, 1, 70, 100);
    model.editNotePitch(0, 1, 60, 70);
  }

  @Test
  public void editNotePitchValid() {
    model.create();
    model.addNote(3, 6, 1, 57, 100);
    model.editNotePitch(3, 1, 57, 59);
    String view = "     B3 \n"
                + "0       \n"
                + "1       \n"
                + "2       \n"
                + "3    X  \n"
                + "4    |  \n"
                + "5    |  \n"
                + "6    |  \n";
    assertEquals(view, model.view());

    model.create();
    model.addNote(3, 6, 1, 59, 100);
    assertEquals(view, model.view());
  }

  // Tests for the editNotePosition method
  @Test(expected = IllegalStateException.class)
  public void editNotePositionNothingOpened() {
    model.editNotePosition(2, 2, 40, 60);
  }

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

  @Test(expected = IllegalArgumentException.class)
  public void editPositionNoteAtNewPosition() {
    model.create();
    model.addNote(0, 2, 1, 60, 100);
    model.addNote(1, 2, 1, 60, 100);
    model.editNotePosition(0, 1, 60, 1);
  }

  @Test
  public void editNotePositionValid() {
    model.create();
    model.addNote(0,3, 1, 60, 100);
    model.editNotePosition(0, 1, 60, 6);
    String view = "     C4 \n"
                + "0       \n"
                + "1       \n"
                + "2       \n"
                + "3       \n"
                + "4       \n"
                + "5       \n"
                + "6    X  \n"
                + "7    |  \n"
                + "8    |  \n"
                + "9    |  \n";
    assertEquals(view, model.view());
    model.create();
    model.addNote(6, 9, 1, 60, 100  );
    assertEquals(view, model.view());
  }

  // Tests for the editNoteDuration method
  @Test(expected = IllegalStateException.class)
  public void editNoteDurationNothingOpened() {
    model.editNoteDuration(0, 1, 60, 2);
  }

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

  @Test
  public void editNoteDurationValid() {
    model.create();
    model.addNote(3, 4, 1, 62, 100);
    model.editNoteDuration(3, 1, 62, 6);
    String view = "     D4 \n"
                + "0       \n"
                + "1       \n"
                + "2       \n"
                + "3    X  \n"
                + "4    |  \n"
                + "5    |  \n"
                + "6    |  \n"
                + "7    |  \n"
                + "8    |  \n";
    assertEquals(view, model.view());
    model.create();
    model.addNote(3, 8, 1, 62, 100);
    assertEquals(view, model.view());
  }

  // Tests for the setTempo method
  @Test(expected = IllegalStateException.class)
  public void setTempoNothingOpened() {
    model.setTempo(30);
  }

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
  @Test(expected = IllegalStateException.class)
  public void getTempoNothingOpened() {
    model.getTempo();
  }

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

  // Tests for the getNotesAtBeat method

  // Tests for the getLength method
}
