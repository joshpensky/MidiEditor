package cs3500.hw05;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotEquals;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

/**
 * Tests for the {@link Piece} class.
 */
public class PieceTest {
  // Tests for the default constructor
  @Test(expected = IllegalArgumentException.class)
  public void defaultConstructorNullTitle() {
    String nullStr = null;
    new Piece(nullStr);
  }

  @Test
  public void defaultConstructorValid() {
    new Piece("My Piece");
  }

  // Tests for the copy constructor
  @Test(expected = IllegalArgumentException.class)
  public void copyConstructorNullName() {
    String nullStr = null;
    new Piece(new Piece(nullStr));
  }

  @Test(expected = IllegalArgumentException.class)
  public void copyConstructorNullPiece() {
    Piece nullPiece = null;
    new Piece(nullPiece);
  }

  @Test
  public void copyConstructorValid() {
    Piece copy = new Piece("boom");
    copy.addNote(9, Pitch.ASHARP, 1, 5);
    copy.addNote(2, Pitch.G, 4, 6);
    assertTrue(new Piece(copy).equals(copy));
  }

  // Tests for the equals method

  // Tests for the toString method
  @Test
  public void toStringEmpty() {
    assertEquals("", new Piece("title").toString());
  }

  @Test
  public void toStringSingleNote() {
    Piece piece = new Piece("title");
    piece.addNote(3, Pitch.D, 1, 3);
    String toString = "     D3 \n"
                    + "0       \n"
                    + "1    X  \n"
                    + "2    |  \n"
                    + "3    |  \n";
    assertEquals(toString, piece.toString());
  }

  @Test
  public void toStringOverlayingNotes() {
    Piece piece = new Piece("title");
    piece.addNote(3, Pitch.D, 1, 3);
    piece.addNote(3, Pitch.C, 2, 4);
    piece.addNote(3, Pitch.DSHARP, 4, 6);
    piece.addNote(3, Pitch.DSHARP, 6, 2);
    String toString = "     C3  C#3   D3  D#3 \n"
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
    assertEquals(toString, piece.toString());
  }
  @Test
  public void toStringLineNumbersRightAligned() {
    Piece piece = new Piece("title");
    piece.addNote(3, Pitch.D, 1, 3);
    piece.addNote(3, Pitch.C, 2, 4);
    piece.addNote(3, Pitch.DSHARP, 4, 6);
    piece.addNote(3, Pitch.DSHARP, 6, 2);
    piece.addNote(3, Pitch.DSHARP, 8, 4);
    String toString = "      C3  C#3   D3  D#3 \n"
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
    assertEquals(toString, piece.toString());
  }

  @Test
  public void toStringSpanOneOctave() {
    Piece piece = new Piece("title");
    piece.addNote(3, Pitch.D, 1, 3);
    piece.addNote(3, Pitch.C, 2, 8);
    piece.addNote(3, Pitch.B, 0, 1);
    String toString = "     C3  C#3   D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3 \n"
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
    assertEquals(toString, piece.toString());
  }

  @Test
  public void toStringSpanTwoOctaves() {
    Piece piece = new Piece("title");
    piece.addNote(3, Pitch.GSHARP, 4, 6);
    piece.addNote(4, Pitch.D, 2, 4);
    String toString = "    G#3   A3  A#3   B3   C4  C#4   D4 \n"
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
    assertEquals(toString, piece.toString());
  }

  @Test
  public void toStringSpanMultipleOctaves() {
    Piece piece = new Piece("title");
    piece.addNote(3, Pitch.GSHARP, 4, 6);
    piece.addNote(4, Pitch.D, 2, 4);
    piece.addNote(2, Pitch.B, 4, 5);
    piece.addNote(2, Pitch.B, 2, 4);
    String toString = ""
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
    assertEquals(toString, piece.toString());
  }

  // Tests for the addNote method
  @Test(expected = IllegalArgumentException.class)
  public void addNoteInvalidOctane() {
    Piece p = new Piece("hey");
    p.addNote(0, Pitch.G, 2, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteNullPitch() {
    Piece p = new Piece("hey");
    p.addNote(3, null, 2, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteNegativePosition() {
    Piece p = new Piece("hey");
    p.addNote(3, Pitch.G, -2, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteNegativeDuration() {
    Piece p = new Piece("hey");
    p.addNote(3, Pitch.G, 2, -4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteZeroDuration() {
    Piece p = new Piece("hey");
    p.addNote(3, Pitch.G, 2, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteAlreadyExistsAtLocation() {
    Piece p = new Piece("hey");
    p.addNote(3, Pitch.G, 2, 4);
    p.addNote(3, Pitch.G, 2, 2);
  }

  @Test
  public void addNoteValid() {
    Piece p = new Piece("hey");
    p.addNote(3, Pitch.G, 2, 3);
    String toString = "     G3 \n"
                    + "0       \n"
                    + "1       \n"
                    + "2    X  \n"
                    + "3    |  \n"
                    + "4    |  \n";
    assertEquals(toString, p.toString());
  }

  // Tests for the removeNote method
  @Test(expected = IllegalArgumentException.class)
  public void removeNoteInvalidOctave() {
    Piece p = new Piece("hey");
    p.removeNote(-5, Pitch.D, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeNoteNullPitch() {
    Piece p = new Piece("hey");
    p.removeNote(5, null, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeNoteNegativePosition() {
    Piece p = new Piece("hey");
    p.removeNote(5, Pitch.D, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeNoteNoNoteAtPosition() {
    Piece p = new Piece("hey");
    p.removeNote(5, Pitch.D, 3);
  }

  @Test
  public void removeNoteValid() {
    Piece p = new Piece("hey");
    p.addNote(5, Pitch.D, 3, 4);
    p.removeNote(5, Pitch.D, 3);
    assertEquals(new Piece("hey").toString(), p.toString());
  }

  // Tests for the editPitch method
  @Test(expected = IllegalArgumentException.class)
  public void editPitchInvalidOctave() {
    Piece p = new Piece("hey");
    p.editPitch(11, Pitch.D, 3, Pitch.E);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPitchNullPitch() {
    Piece p = new Piece("hey");
    p.editPitch(10, null, 3, Pitch.E);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPitchNegativePosition() {
    Piece p = new Piece("hey");
    p.editPitch(10, Pitch.D, -3, Pitch.E);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPitchNullNewPitch() {
    Piece p = new Piece("hey");
    p.editPitch(10, Pitch.D, 3, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPitchNoNoteAtPosition() {
    Piece p = new Piece("hey");
    p.editPitch(10, Pitch.D, 3, Pitch.E);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPitchNoteAtNewPosition() {
    Piece p = new Piece("hey");
    p.addNote(10, Pitch.D, 3, 4);
    p.addNote(10, Pitch.E, 3, 2);
    p.editPitch(10, Pitch.D, 3, Pitch.E);
  }

  @Test
  public void editPitchValid() {
    Piece p = new Piece("hey");
    p.addNote(10, Pitch.D, 3, 4);
    p.editPitch(10, Pitch.D, 3, Pitch.B);
    Piece product = new Piece("hey");
    product.addNote(10, Pitch.B, 3, 4);
    assertTrue(p.equals(product));
  }

  // Tests for the editPosition method
  @Test(expected = IllegalArgumentException.class)
  public void editPositionInvalidOctave() {
    Piece p = new Piece("hey");
    p.editPosition(-4, null, 3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPositionNullPitch() {
    Piece p = new Piece("hey");
    p.editPosition(1, null, 3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPositionNegativePosition() {
    Piece p = new Piece("hey");
    p.editPosition(1, Pitch.D, -3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPositionNullNegativeNewPosition() {
    Piece p = new Piece("hey");
    p.editPosition(1, Pitch.D, 3, -6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPositionNoNoteAtPosition() {
    Piece p = new Piece("hey");
    p.editPosition(1, Pitch.D, 3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPositionNoteAtNewPosition() {
    Piece p = new Piece("hey");
    p.addNote(1, Pitch.D, 3, 4);
    p.addNote(1, Pitch.D, 6, 2);
    p.editPosition(1, Pitch.D, 3, 6);
  }

  @Test
  public void editPositionValid() {
    Piece p = new Piece("hey");
    p.addNote(1, Pitch.D, 3, 4);
    p.editPosition(1, Pitch.D, 3, 6);
    Piece product = new Piece("hey");
    product.addNote(1, Pitch.D, 6, 4);
    assertTrue(p.equals(product));
  }

  // Tests for the editDuration method
  @Test(expected = IllegalArgumentException.class)
  public void editDurationInvalidOctave() {
    Piece p = new Piece("hey");
    p.editDuration(23, Pitch.D, 3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editDurationNullPitch() {
    Piece p = new Piece("hey");
    p.editDuration(4, null, 3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editDurationNegativePosition() {
    Piece p = new Piece("hey");
    p.editDuration(4, Pitch.D, -3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editDurationNegativeNewDuration() {
    Piece p = new Piece("hey");
    p.editDuration(4, Pitch.D, 3, -6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editDurationZeroNewDuration() {
    Piece p = new Piece("hey");
    p.editDuration(4, Pitch.D, 3, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editDurationNoNoteAtPosition() {
    Piece p = new Piece("hey");
    p.editDuration(4, Pitch.D, 3, 6);
  }

  @Test
  public void editDurationValid() {
    Piece p = new Piece("hey");
    p.addNote(4, Pitch.D, 3, 4);
    p.editDuration(4, Pitch.D, 3, 6);
    Piece product = new Piece("hey");
    product.addNote(4, Pitch.D, 3, 6);
    assertTrue(p.equals(product));
  }

  // Tests for the overlay method

  // Tests for the getTitle method
  @Test
  public void getTitleValid() {
    assertEquals("heyya", new Piece("heyya").getTitle());
  }
}