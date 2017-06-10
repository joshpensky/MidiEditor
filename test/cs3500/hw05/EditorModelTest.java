package cs3500.hw05;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotEquals;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

/**
 * Tests for the {@link EditorModel} class.
 */
public class EditorModelTest {
  EditorOperations model = new EditorModel();

  // Tests for the default constructor
  @Test
  public void defaultConstructor() {
    assertEquals("", model.list());
  }

  // Tests for the create method
  @Test(expected = IllegalArgumentException.class)
  public void createNullTitle() {
    model.create(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createPieceAlreadyExists() {
    model.create("hello");
    model.create("hello");
  }

  @Test
  public void createValid() {
    model.create("hello");
    assertEquals(">  hello\n", model.list());
  }

  // Tests for the open method
  @Test(expected = IllegalArgumentException.class)
  public void openNullTitle() {
    model.open(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void openNoPieceExistsWithTitle() {
    model.open("hello");
  }

  @Test(expected = IllegalArgumentException.class)
  public void openIncorrectCasing() {
    model.create("hello");
    model.open("hElLo");
  }

  @Test
  public void openValidList() {
    model.create("hello");
    model.create("jack");
    model.open("hello");
    assertEquals("   jack\n"
               + ">  hello\n", model.list());
  }

  @Test
  public void openValidChanges() {
    model.create("jill");
    model.create("jack");
    model.addNote(5, Pitch.E, 1, 1);
    model.addNote(5, Pitch.DSHARP, 0, 2);
    String jack = "    D#5   E5 \n"
                + "0    X       \n"
                + "1    |    X  \n";
    assertEquals(jack, model.view());
    model.open("jill");
    model.addNote(3, Pitch.D, 1, 3);
    String jill = "     D3 \n"
                + "0       \n"
                + "1    X  \n"
                + "2    |  \n"
                + "3    |  \n";
    assertEquals(jill, model.view());
    model.open("jack");
    assertEquals(jack, model.view());
  }

  // Tests for the view method
  @Test(expected = IllegalStateException.class)
  public void viewNothingOpen() {
    model.view();
  }

  @Test
  public void viewEmptyPiece() {
    model.create("hello");
    assertEquals("", model.view());
  }

  @Test
  public void viewSingleNote() {
    model.create("hello");
    model.addNote(3, Pitch.D, 1, 3);
    String view = "     D3 \n"
                + "0       \n"
                + "1    X  \n"
                + "2    |  \n"
                + "3    |  \n";
    assertEquals(view, model.view());
  }

  @Test
  public void viewOverlayingNotes() {
    model.create("hello");
    model.addNote(3, Pitch.D, 1, 3);
    model.addNote(3, Pitch.C, 2, 4);
    model.addNote(3, Pitch.DSHARP, 4, 6);
    model.addNote(3, Pitch.DSHARP, 6, 2);
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
    model.create("hello");
    model.addNote(3, Pitch.D, 1, 3);
    model.addNote(3, Pitch.C, 2, 4);
    model.addNote(3, Pitch.DSHARP, 4, 6);
    model.addNote(3, Pitch.DSHARP, 6, 2);
    model.addNote(3, Pitch.DSHARP, 8, 4);
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
    model.create("hello");
    model.addNote(3, Pitch.D, 1, 3);
    model.addNote(3, Pitch.C, 2, 8);
    model.addNote(3, Pitch.B, 0, 1);
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
    model.create("hello");
    model.addNote(3, Pitch.GSHARP, 4, 6);
    model.addNote(4, Pitch.D, 2, 4);
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
    model.create("hello");
    model.addNote(3, Pitch.GSHARP, 4, 6);
    model.addNote(4, Pitch.D, 2, 4);
    model.addNote(2, Pitch.B, 4, 5);
    model.addNote(2, Pitch.B, 2, 4);
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

  // Tests for the close method
  @Test(expected = IllegalStateException.class)
  public void closeNothingOpen() {
    model.close();
  }

  @Test
  public void closeValid() {
    model.create("hey");
    model.addNote(3, Pitch.D, 1, 3);
    String view = "     D3 \n"
                + "0       \n"
                + "1    X  \n"
                + "2    |  \n"
                + "3    |  \n";
    assertEquals(view, model.view());
    assertEquals(">  hey\n", model.list());
    model.close();
    String test = "";
    try {
      model.view();
    } catch (IllegalStateException e) {
      test = "   hey\n"; // should catch here and make the assertEquals below true
    }
    assertEquals(test, model.list());
  }

  // Tests for the list method
  @Test
  public void listNoPieces() {
    assertEquals("", model.list());
  }

  @Test
  public void listMultiplePieces() {
    model.create("how");
    model.create("are");
    model.create("you");
    assertEquals(">  you\n"
               + "   are\n"
               + "   how\n", model.list());
    model.open("are");
    assertEquals("   you\n"
               + ">  are\n"
               + "   how\n", model.list());

    model.open("how");
    assertEquals("   you\n"
               + "   are\n"
               + ">  how\n", model.list());
    model.close();
    assertEquals("   you\n"
               + "   are\n"
               + "   how\n", model.list());
  }

  // Tests for the addNote method
  @Test(expected = IllegalStateException.class)
  public void addNoteNothingOpened() {
    model.addNote(0, Pitch.G, 2, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteInvalidOctane() {
    model.create("hey");
    model.addNote(0, Pitch.G, 2, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteNullPitch() {
    model.create("hey");
    model.addNote(3, null, 2, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteNegativePosition() {
    model.create("hey");
    model.addNote(3, Pitch.G, -2, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteNegativeDuration() {
    model.create("hey");
    model.addNote(3, Pitch.G, 2, -4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteZeroDuration() {
    model.create("hey");
    model.addNote(3, Pitch.G, 2, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteAlreadyExistsAtLocation() {
    model.create("hey");
    model.addNote(3, Pitch.G, 2, 4);
    model.addNote(3, Pitch.G, 2, 2);
  }

  @Test
  public void addNoteValid() {
    model.create("hey");
    model.addNote(3, Pitch.G, 2, 3);
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
    model.removeNote(5, Pitch.D, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeNoteInvalidOctave() {
    model.create("hey");
    model.removeNote(-5, Pitch.D, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeNoteNullPitch() {
    model.create("hey");
    model.removeNote(5, null, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeNoteNegativePosition() {
    model.create("hey");
    model.removeNote(5, Pitch.D, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeNoteNoNoteAtPosition() {
    model.create("hey");
    model.removeNote(5, Pitch.D, 3);
  }

  @Test
  public void removeNoteValid() {
    model.create("hey");
    model.addNote(5, Pitch.D, 3, 4);
    model.removeNote(5, Pitch.D, 3);
    assertEquals("", model.view());
  }

  // Tests for the editPitch method
  @Test(expected = IllegalStateException.class)
  public void editPitchNothingOpened() {
    model.editPitch(5, Pitch.D, 3, Pitch.E);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPitchInvalidOctave() {
    model.create("hey");
    model.editPitch(11, Pitch.D, 3, Pitch.E);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPitchNullPitch() {
    model.create("hey");
    model.editPitch(10, null, 3, Pitch.E);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPitchNegativePosition() {
    model.create("hey");
    model.editPitch(10, Pitch.D, -3, Pitch.E);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPitchNullNewPitch() {
    model.create("hey");
    model.editPitch(10, Pitch.D, 3, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPitchNoNoteAtPosition() {
    model.create("hey");
    model.editPitch(10, Pitch.D, 3, Pitch.E);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPitchNoteAtNewPosition() {
    model.create("hey");
    model.addNote(10, Pitch.D, 3, 4);
    model.addNote(10, Pitch.E, 3, 2);
    model.editPitch(10, Pitch.D, 3, Pitch.E);
  }

  @Test
  public void editPitchValid() {
    model.create("hey");
    model.addNote(10, Pitch.D, 3, 4);
    model.editPitch(10, Pitch.D, 3, Pitch.B);
    String view = "    B10 \n"
                + "0       \n"
                + "1       \n"
                + "2       \n"
                + "3    X  \n"
                + "4    |  \n"
                + "5    |  \n"
                + "6    |  \n";
    assertEquals(view, model.view());
    model.create("what");
    model.addNote(10, Pitch.B, 3, 4);
    assertEquals(view, model.view());
  }

  // Tests for the editPosition method
  @Test(expected = IllegalStateException.class)
  public void editPositionNothingOpened() {
    model.editPosition(5, Pitch.D, 3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPositionInvalidOctave() {
    model.create("hey");
    model.editPosition(-4, null, 3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPositionNullPitch() {
    model.create("hey");
    model.editPosition(1, null, 3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPositionNegativePosition() {
    model.create("hey");
    model.editPosition(1, Pitch.D, -3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPositionNullNegativeNewPosition() {
    model.create("hey");
    model.editPosition(1, Pitch.D, 3, -6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPositionNoNoteAtPosition() {
    model.create("hey");
    model.editPosition(1, Pitch.D, 3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPositionNoteAtNewPosition() {
    model.create("hey");
    model.addNote(1, Pitch.D, 3, 4);
    model.addNote(1, Pitch.D, 6, 2);
    model.editPosition(1, Pitch.D, 3, 6);
  }

  @Test
  public void editPositionValid() {
    model.create("hey");
    model.addNote(1, Pitch.D, 3, 4);
    model.editPosition(1, Pitch.D, 3, 6);
    String view = "     D1 \n"
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
    model.create("what");
    model.addNote(1, Pitch.D, 6, 4);
    assertEquals(view, model.view());
  }

  // Tests for the editDuration method
  @Test(expected = IllegalStateException.class)
  public void editDurationNothingOpened() {
    model.editDuration(4, Pitch.D, 3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editDurationInvalidOctave() {
    model.create("hey");
    model.editDuration(23, Pitch.D, 3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editDurationNullPitch() {
    model.create("hey");
    model.editDuration(4, null, 3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editDurationNegativePosition() {
    model.create("hey");
    model.editDuration(4, Pitch.D, -3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editDurationNegativeNewDuration() {
    model.create("hey");
    model.editDuration(4, Pitch.D, 3, -6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editDurationZeroNewDuration() {
    model.create("hey");
    model.editDuration(4, Pitch.D, 3, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editDurationNoNoteAtPosition() {
    model.create("hey");
    model.editDuration(4, Pitch.D, 3, 6);
  }

  @Test
  public void editDurationValid() {
    model.create("hey");
    model.addNote(4, Pitch.D, 3, 4);
    model.editDuration(4, Pitch.D, 3, 6);
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
    model.create("what");
    model.addNote(4, Pitch.D, 3, 6);
    assertEquals(view, model.view());
  }

  // Tests for the overlay method
}
