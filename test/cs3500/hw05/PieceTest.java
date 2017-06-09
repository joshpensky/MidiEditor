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

  // Tests for the hashCode method

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
    String toString = "      C3  C#3   D3  D#3 \n"
                    + " 0                      \n"
                    + " 1              X       \n"
                    + " 2    X         |       \n"
                    + " 3    |         |       \n"
                    + " 4    |              X  \n"
                    + " 5    |              |  \n"
                    + " 6                   X  \n"
                    + " 7                   |  \n"
                    + " 8                   |  \n"
                    + " 9                   |  \n";
    assertEquals(toString, piece.toString());
  }

  @Test
  public void toStringSpanOneOctave() {
    Piece piece = new Piece("title");
    piece.addNote(3, Pitch.D, 1, 3);
    piece.addNote(3, Pitch.C, 2, 8);
    piece.addNote(3, Pitch.B, 0, 1);
    String toString = "      C3  C#3   D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3 \n"
                    + " 0                                                           X  \n"
                    + " 1              X                                               \n"
                    + " 2    X         |                                               \n"
                    + " 3    |         |                                               \n"
                    + " 4    |                                                         \n"
                    + " 5    |                                                         \n"
                    + " 6    |                                                         \n"
                    + " 7    |                                                         \n"
                    + " 8    |                                                         \n"
                    + " 9    |                                                         \n";
    assertEquals(toString, piece.toString());
  }

  @Test
  public void toStringSpanTwoOctaves() {
    Piece piece = new Piece("title");
    piece.addNote(3, Pitch.GSHARP, 4, 6);
    piece.addNote(4, Pitch.D, 2, 4);
    String toString = "     G#3   A3  A#3   B3   C4  C#4   D4 \n"
                    + " 0                                     \n"
                    + " 1                                     \n"
                    + " 2                                  X  \n"
                    + " 3                                  |  \n"
                    + " 4    X                             |  \n"
                    + " 5    |                             |  \n"
                    + " 6    |                                \n"
                    + " 7    |                                \n"
                    + " 8    |                                \n"
                    + " 9    |                                \n";
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
        + "      B2   C3  C#3   D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4 \n"
        + " 0                                                                                  \n"
        + " 1                                                                                  \n"
        + " 2    X                                                                          X  \n"
        + " 3    |                                                                          |  \n"
        + " 4    X                                            X                             |  \n"
        + " 5    |                                            |                             |  \n"
        + " 6    |                                            |                                \n"
        + " 7    |                                            |                                \n"
        + " 8    |                                            |                                \n"
        + " 9                                                 |                                \n";
    assertEquals(toString, piece.toString());
  }

  // Tests for the addNote method

  // Tests for the removeNote method

  // Tests for the editPitch method

  // Tests for the editPosition method

  // Tests for the editDuration method

  // Tests for the overlay method

  // Tests for the getTitle method
  @Test
  public void getTitleValid() {
    assertEquals("heyya", new Piece("heyya").getTitle());
  }
}