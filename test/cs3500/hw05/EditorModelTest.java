package cs3500.hw05;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotEquals;

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

  // Tests for the copy method
  @Test(expected = IllegalArgumentException.class)
  public void copyNullCopyTitle() {
    model.copy(null, "hello");
  }

  @Test(expected = IllegalArgumentException.class)
  public void copyNullNewTitle() {
    model.copy("hello", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void copyNoPieceExistsWithCopyTitle() {
    model.copy("hello", "jack");
  }

  @Test(expected = IllegalArgumentException.class)
  public void copyIncorrectCasing() {
    model.create("hello");
    model.copy("hElLo", "jack");
  }

  @Test(expected = IllegalArgumentException.class)
  public void copyPieceAlreadyExistsWithNewTitle() {
    model.create("hello");
    model.create("jack");
    model.copy("hello", "jack");
  }

  @Test
  public void copyValidList() {
    model.create("hello");
    model.copy("hello", "jack");
    assertEquals(">  jack\n"
               + "   hello\n", model.list());
  }

  @Test
  public void copyValidAllNotesCopied() {
    model.create("hello");
    model.addNote(3, Pitch.C, 10, 23);
    model.addNote(2, Pitch.DSHARP, 4, 5);
    String hello = model.view();
    model.copy("hello", "jack");
    assertEquals(hello, model.view());
  }

  @Test
  public void copyDifferentReferences() {
    model.create("hello");
    model.addNote(3, Pitch.C, 10, 23);
    model.addNote(2, Pitch.DSHARP, 4, 5);
    model.copy("hello", "jack");
    model.removeNote(2, Pitch.DSHARP, 4);
    String copy = model.view();
    model.open("hello");
    assertNotEquals(copy, model.view());
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

  @Test
  public void toStringSpanMultipleOctavesNoneInMiddle() {
    model.create("hello");
    model.addNote(4, Pitch.D, 2, 4);
    model.addNote(2, Pitch.B, 4, 5);
    model.addNote(2, Pitch.B, 2, 4);
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

  @Test
  public void viewOverlaidPieces() {
    model.create("top");
    model.addNote(3, Pitch.D, 1, 3);
    model.addNote(3, Pitch.DSHARP, 4, 6);
    model.addNote(3, Pitch.DSHARP, 8, 4);
    String top = "      D3  D#3 \n"
               + " 0            \n"
               + " 1    X       \n"
               + " 2    |       \n"
               + " 3    |       \n"
               + " 4         X  \n"
               + " 5         |  \n"
               + " 6         |  \n"
               + " 7         |  \n"
               + " 8         X  \n"
               + " 9         |  \n"
               + "10         |  \n"
               + "11         |  \n";
    assertEquals(top, model.view());
    model.create("bot");
    model.addNote(3, Pitch.DSHARP, 6, 4);
    model.addNote(3, Pitch.C, 2, 4);
    String bot = "     C3  C#3   D3  D#3 \n"
               + "0                      \n"
               + "1                      \n"
               + "2    X                 \n"
               + "3    |                 \n"
               + "4    |                 \n"
               + "5    |                 \n"
               + "6                   X  \n"
               + "7                   |  \n"
               + "8                   |  \n"
               + "9                   |  \n";
    assertEquals(bot, model.view());
    model.overlay("top");
    String overlay = "      C3  C#3   D3  D#3 \n"
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
    assertEquals(overlay, model.view());
    model.open("top");
    assertEquals(top, model.view());
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

  // Tests for the editNotePitch method
  @Test(expected = IllegalStateException.class)
  public void editNotePitchNothingOpened() {
    model.editNotePitch(5, Pitch.D, 3, Pitch.E);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNotePitchInvalidOctave() {
    model.create("hey");
    model.editNotePitch(11, Pitch.D, 3, Pitch.E);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNotePitchNullPitch() {
    model.create("hey");
    model.editNotePitch(10, null, 3, Pitch.E);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNotePitchNegativePosition() {
    model.create("hey");
    model.editNotePitch(10, Pitch.D, -3, Pitch.E);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNotePitchNullNewPitch() {
    model.create("hey");
    model.editNotePitch(10, Pitch.D, 3, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNotePitchNoNoteAtPosition() {
    model.create("hey");
    model.editNotePitch(10, Pitch.D, 3, Pitch.E);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNotePitchNoteAtNewPosition() {
    model.create("hey");
    model.addNote(10, Pitch.D, 3, 4);
    model.addNote(10, Pitch.E, 3, 2);
    model.editNotePitch(10, Pitch.D, 3, Pitch.E);
  }

  @Test
  public void editNotePitchValid() {
    model.create("hey");
    model.addNote(10, Pitch.D, 3, 4);
    model.editNotePitch(10, Pitch.D, 3, Pitch.B);
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

  // Tests for the editNotePosition method
  @Test(expected = IllegalStateException.class)
  public void editNotePositionNothingOpened() {
    model.editNotePosition(5, Pitch.D, 3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNotePositionInvalidOctave() {
    model.create("hey");
    model.editNotePosition(-4, null, 3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNotePositionNullPitch() {
    model.create("hey");
    model.editNotePosition(1, null, 3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNotePositionNegativePosition() {
    model.create("hey");
    model.editNotePosition(1, Pitch.D, -3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNotePositionNullNegativeNewPosition() {
    model.create("hey");
    model.editNotePosition(1, Pitch.D, 3, -6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNotePositionNoNoteAtPosition() {
    model.create("hey");
    model.editNotePosition(1, Pitch.D, 3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPositionNoteAtNewPosition() {
    model.create("hey");
    model.addNote(1, Pitch.D, 3, 4);
    model.addNote(1, Pitch.D, 6, 2);
    model.editNotePosition(1, Pitch.D, 3, 6);
  }

  @Test
  public void editNotePositionValid() {
    model.create("hey");
    model.addNote(1, Pitch.D, 3, 4);
    model.editNotePosition(1, Pitch.D, 3, 6);
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

  // Tests for the editNoteDuration method
  @Test(expected = IllegalStateException.class)
  public void editNoteDurationNothingOpened() {
    model.editNoteDuration(4, Pitch.D, 3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNoteDurationInvalidOctave() {
    model.create("hey");
    model.editNoteDuration(23, Pitch.D, 3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNoteDurationNullPitch() {
    model.create("hey");
    model.editNoteDuration(4, null, 3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNoteDurationNegativePosition() {
    model.create("hey");
    model.editNoteDuration(4, Pitch.D, -3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNoteDurationNegativeNewDuration() {
    model.create("hey");
    model.editNoteDuration(4, Pitch.D, 3, -6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNoteDurationZeroNewDuration() {
    model.create("hey");
    model.editNoteDuration(4, Pitch.D, 3, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNoteDurationNoNoteAtPosition() {
    model.create("hey");
    model.editNoteDuration(4, Pitch.D, 3, 6);
  }

  @Test
  public void editNoteDurationValid() {
    model.create("hey");
    model.addNote(4, Pitch.D, 3, 4);
    model.editNoteDuration(4, Pitch.D, 3, 6);
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
  @Test(expected = IllegalStateException.class)
  public void overlayNothingOpened() {
    model.overlay("top");
  }

  @Test(expected = IllegalArgumentException.class)
  public void overlayNullTitle() {
    model.create("top");
    model.overlay(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void overlayPieceDoesNotExist() {
    model.create("top");
    model.overlay("bot");
  }

  @Test
  public void overlayOntoEmptyPiece() {
    model.create("top");
    model.addNote(1, Pitch.A, 3, 4);
    model.addNote(1, Pitch.DSHARP, 6, 8);
    model.addNote(1, Pitch.E, 15, 12);
    model.addNote(1, Pitch.E, 20, 3);
    model.addNote(1, Pitch.B, 8, 4);
    String top = model.view();
    model.create("empty");
    model.overlay("top");
    assertEquals(top, model.view());
  }

  @Test
  public void overlayEmptyPieceOnto() {
    model.create("empty");
    model.create("top");
    model.addNote(1, Pitch.A, 3, 4);
    model.addNote(1, Pitch.DSHARP, 6, 8);
    model.addNote(1, Pitch.E, 15, 12);
    model.addNote(1, Pitch.E, 20, 3);
    model.addNote(1, Pitch.B, 8, 4);
    String top = model.view();
    model.overlay("empty");
    assertEquals(top, model.view());
    model.open("empty");
    assertNotEquals(top, model.view());
  }

  @Test
  public void overlayNoConflictingAdds() {
    model.create("top");
    model.addNote(3, Pitch.A, 3, 4);
    model.addNote(3, Pitch.E, 0, 3);
    model.addNote(3, Pitch.GSHARP, 2, 2);
    model.addNote(3, Pitch.D, 1, 1);
    model.addNote(4, Pitch.CSHARP, 10, 6);
    String tops = "      D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4 \n"
                + " 0              X                                               \n"
                + " 1    X         |                                               \n"
                + " 2              |                   X                           \n"
                + " 3                                  |    X                      \n"
                + " 4                                       |                      \n"
                + " 5                                       |                      \n"
                + " 6                                       |                      \n"
                + " 7                                                              \n"
                + " 8                                                              \n"
                + " 9                                                              \n"
                + "10                                                           X  \n"
                + "11                                                           |  \n"
                + "12                                                           |  \n"
                + "13                                                           |  \n"
                + "14                                                           |  \n"
                + "15                                                           |  \n";
    assertEquals(tops, model.view());

    model.create("bot");
    model.addNote(3, Pitch.A, 0, 2);
    model.addNote(4, Pitch.CSHARP, 2, 3);
    model.addNote(4, Pitch.E, 5, 6);
    model.addNote(3, Pitch.FSHARP, 3, 2);
    model.addNote(3, Pitch.E, 4, 1);
    String bots = "      E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4 \n"
                + " 0                             X                                     \n"
                + " 1                             |                                     \n"
                + " 2                                                 X                 \n"
                + " 3              X                                  |                 \n"
                + " 4    X         |                                  |                 \n"
                + " 5                                                                X  \n"
                + " 6                                                                |  \n"
                + " 7                                                                |  \n"
                + " 8                                                                |  \n"
                + " 9                                                                |  \n"
                + "10                                                                |  \n";
    assertEquals(bots, model.view());

    model.open("top");
    model.overlay("bot");
    String overlay = ""
        + "      D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4 \n"
        + " 0              X                        X                                     \n"
        + " 1    X         |                        |                                     \n"
        + " 2              |                   X                        X                 \n"
        + " 3                        X         |    X                   |                 \n"
        + " 4              X         |              |                   |                 \n"
        + " 5                                       |                                  X  \n"
        + " 6                                       |                                  |  \n"
        + " 7                                                                          |  \n"
        + " 8                                                                          |  \n"
        + " 9                                                                          |  \n"
        + "10                                                           X              |  \n"
        + "11                                                           |                 \n"
        + "12                                                           |                 \n"
        + "13                                                           |                 \n"
        + "14                                                           |                 \n"
        + "15                                                           |                 \n";
    assertEquals(overlay, model.view());
  }

  @Test
  public void overlayNoConflictsEqualsBothWays() {
    model.create("top");
    model.addNote(3, Pitch.A, 3, 4);
    model.addNote(3, Pitch.E, 0, 3);
    model.addNote(3, Pitch.GSHARP, 2, 2);
    model.addNote(3, Pitch.D, 1, 1);
    model.addNote(4, Pitch.CSHARP, 10, 6);

    model.create("bot");
    model.addNote(3, Pitch.A, 0, 2);
    model.addNote(4, Pitch.CSHARP, 2, 3);
    model.addNote(4, Pitch.E, 5, 6);
    model.addNote(3, Pitch.FSHARP, 3, 2);
    model.addNote(3, Pitch.E, 4, 1);

    model.copy("top", "botOverlay");
    model.overlay("bot");
    String botOverlay = model.view();
    model.copy("bot", "topOverlay");
    model.overlay("top");
    String topOverlay = model.view();
    assertEquals(topOverlay, botOverlay);
  }

  @Test
  public void overlayConflictingAdds() {
    model.create("top");
    model.addNote(3, Pitch.A, 3, 4);
    model.addNote(3, Pitch.E, 0, 3);
    model.addNote(3, Pitch.GSHARP, 2, 2);
    model.addNote(3, Pitch.D, 1, 1);
    model.addNote(4, Pitch.CSHARP, 10, 6);
    String tops = "      D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4 \n"
                + " 0              X                                               \n"
                + " 1    X         |                                               \n"
                + " 2              |                   X                           \n"
                + " 3                                  |    X                      \n"
                + " 4                                       |                      \n"
                + " 5                                       |                      \n"
                + " 6                                       |                      \n"
                + " 7                                                              \n"
                + " 8                                                              \n"
                + " 9                                                              \n"
                + "10                                                           X  \n"
                + "11                                                           |  \n"
                + "12                                                           |  \n"
                + "13                                                           |  \n"
                + "14                                                           |  \n"
                + "15                                                           |  \n";
    assertEquals(tops, model.view());

    model.create("bot");
    model.addNote(3, Pitch.A, 5, 3);
    model.addNote(4, Pitch.CSHARP, 2, 3);
    model.addNote(4, Pitch.CSHARP, 10, 12);
    model.addNote(4, Pitch.CSHARP, 8, 4);
    model.addNote(4, Pitch.E, 5, 6);
    model.addNote(3, Pitch.FSHARP, 3, 2);
    model.addNote(3, Pitch.E, 4, 1);
    model.addNote(3, Pitch.GSHARP, 2, 8);
    model.addNote(3, Pitch.GSHARP, 4, 2);
    String bots = "      E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4 \n"
                + " 0                                                                   \n"
                + " 1                                                                   \n"
                + " 2                        X                        X                 \n"
                + " 3              X         |                        |                 \n"
                + " 4    X         |         X                        |                 \n"
                + " 5                        |    X                                  X  \n"
                + " 6                        |    |                                  |  \n"
                + " 7                        |    |                                  |  \n"
                + " 8                        |                        X              |  \n"
                + " 9                        |                        |              |  \n"
                + "10                                                 X              |  \n"
                + "11                                                 |                 \n"
                + "12                                                 |                 \n"
                + "13                                                 |                 \n"
                + "14                                                 |                 \n"
                + "15                                                 |                 \n"
                + "16                                                 |                 \n"
                + "17                                                 |                 \n"
                + "18                                                 |                 \n"
                + "19                                                 |                 \n"
                + "20                                                 |                 \n"
                + "21                                                 |                 \n";
    assertEquals(bots, model.view());

    model.open("top");
    model.overlay("bot");
    String overlay = ""
        + "      D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4 \n"
        + " 0              X                                                              \n"
        + " 1    X         |                                                              \n"
        + " 2              |                   X                        X                 \n"
        + " 3                        X         |    X                   |                 \n"
        + " 4              X         |         X    |                   |                 \n"
        + " 5                                  |    X                                  X  \n"
        + " 6                                       |                                  |  \n"
        + " 7                                       |                                  |  \n"
        + " 8                                                           X              |  \n"
        + " 9                                                           |              |  \n"
        + "10                                                           X              |  \n"
        + "11                                                           |                 \n"
        + "12                                                           |                 \n"
        + "13                                                           |                 \n"
        + "14                                                           |                 \n"
        + "15                                                           |                 \n";
    assertEquals(overlay, model.view());
  }

  @Test
  public void overlayConflictsNotEqualsBothWays() {
    model.create("top");
    model.addNote(3, Pitch.A, 3, 4);
    model.addNote(3, Pitch.E, 0, 3);
    model.addNote(3, Pitch.GSHARP, 2, 2);
    model.addNote(3, Pitch.D, 1, 1);
    model.addNote(4, Pitch.CSHARP, 10, 6);

    model.create("bot");
    model.addNote(3, Pitch.A, 5, 3);
    model.addNote(4, Pitch.CSHARP, 2, 3);
    model.addNote(4, Pitch.CSHARP, 10, 12);
    model.addNote(4, Pitch.CSHARP, 8, 4);
    model.addNote(4, Pitch.E, 5, 6);
    model.addNote(3, Pitch.FSHARP, 3, 2);
    model.addNote(3, Pitch.E, 4, 1);
    model.addNote(3, Pitch.GSHARP, 2, 8);
    model.addNote(3, Pitch.GSHARP, 4, 2);

    model.copy("top", "botOverlay");
    model.overlay("bot");
    String botOverlay = model.view();
    model.copy("bot", "topOverlay");
    model.overlay("top");
    String topOverlay = model.view();
    assertNotEquals(topOverlay, botOverlay);
  }
}
