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

  @Test
  public void toStringSpanMultipleOctavesNoneInMiddle() {
    Piece piece = new Piece("title");
    piece.addNote(4, Pitch.D, 2, 4);
    piece.addNote(2, Pitch.B, 4, 5);
    piece.addNote(2, Pitch.B, 2, 4);
    String toString = ""
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
    assertEquals(toString, piece.toString());
  }

  @Test
  public void toStringOverlaidPieces() {
    Piece top = new Piece("top");
    top.addNote(3, Pitch.D, 1, 3);
    top.addNote(3, Pitch.DSHARP, 4, 6);
    top.addNote(3, Pitch.DSHARP, 8, 4);
    String topStr = "      D3  D#3 \n"
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
    assertEquals(topStr, top.toString());
    Piece bot = new Piece("bot");
    bot.addNote(3, Pitch.DSHARP, 6, 4);
    bot.addNote(3, Pitch.C, 2, 4);
    String botStr = "     C3  C#3   D3  D#3 \n"
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
    assertEquals(botStr, bot.toString());
    bot.overlay(top);
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
    assertEquals(overlay, bot.toString());
    assertEquals(topStr, top.toString());
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

  // Tests for the editNotePitch method
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

  // Tests for the editNotePosition method
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

  // Tests for the editNoteDuration method
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
  @Test(expected = IllegalArgumentException.class)
  public void overlayNullPiece() {
    new Piece("hey").overlay(null);
  }

  @Test
  public void overlayOntoEmptyPiece() {
    Piece p = new Piece("hey");
    p.addNote(1, Pitch.A, 3, 4);
    p.addNote(1, Pitch.DSHARP, 6, 8);
    p.addNote(1, Pitch.E, 15, 12);
    p.addNote(1, Pitch.E, 20, 3);
    p.addNote(1, Pitch.B, 8, 4);
    Piece empty = new Piece("hey");
    empty.overlay(p);
    assertTrue(p.equals(empty));
  }

  @Test
  public void overlayEmptyPieceOnto() {
    Piece p = new Piece("hey");
    p.addNote(1, Pitch.A, 3, 4);
    p.addNote(1, Pitch.DSHARP, 6, 8);
    p.addNote(1, Pitch.E, 15, 12);
    p.addNote(1, Pitch.E, 20, 3);
    p.addNote(1, Pitch.B, 8, 4);
    Piece empty = new Piece("hey");
    Piece overlaid = new Piece(p);
    overlaid.overlay(empty);
    assertFalse(overlaid.equals(empty));
    assertTrue(overlaid.equals(p));
  }

  @Test
  public void overlayNoConflictingAdds() {
    Piece top = new Piece("title");
    top.addNote(3, Pitch.A, 3, 4);
    top.addNote(3, Pitch.E, 0, 3);
    top.addNote(3, Pitch.GSHARP, 2, 2);
    top.addNote(3, Pitch.D, 1, 1);
    top.addNote(4, Pitch.CSHARP, 10, 6);
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
    assertEquals(tops, top.toString());

    Piece bot = new Piece("title");
    bot.addNote(3, Pitch.A, 0, 2);
    bot.addNote(4, Pitch.CSHARP, 2, 3);
    bot.addNote(4, Pitch.E, 5, 6);
    bot.addNote(3, Pitch.FSHARP, 3, 2);
    bot.addNote(3, Pitch.E, 4, 1);
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
    assertEquals(bots, bot.toString());

    top.overlay(bot);
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
    assertEquals(overlay, top.toString());
  }

  @Test
  public void overlayNoConflictsEqualsBothWays() {
    Piece top = new Piece("title");
    top.addNote(3, Pitch.A, 3, 4);
    top.addNote(3, Pitch.E, 0, 3);
    top.addNote(3, Pitch.GSHARP, 2, 2);
    top.addNote(3, Pitch.D, 1, 1);
    top.addNote(4, Pitch.CSHARP, 10, 6);

    Piece bot = new Piece("title");
    bot.addNote(3, Pitch.A, 0, 2);
    bot.addNote(4, Pitch.CSHARP, 2, 3);
    bot.addNote(4, Pitch.E, 5, 6);
    bot.addNote(3, Pitch.FSHARP, 3, 2);
    bot.addNote(3, Pitch.E, 4, 1);

    Piece botOverlay = new Piece(top);
    botOverlay.overlay(bot);
    Piece topOverlay = new Piece(bot);
    topOverlay.overlay(top);
    assertTrue(topOverlay.equals(botOverlay));
  }

  @Test
  public void overlayConflictingAdds() {
    Piece top = new Piece("title");
    top.addNote(3, Pitch.A, 3, 4);
    top.addNote(3, Pitch.E, 0, 3);
    top.addNote(3, Pitch.GSHARP, 2, 2);
    top.addNote(3, Pitch.D, 1, 1);
    top.addNote(4, Pitch.CSHARP, 10, 6);
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
    assertEquals(tops, top.toString());

    Piece bot = new Piece("title");
    bot.addNote(3, Pitch.A, 5, 3);
    bot.addNote(4, Pitch.CSHARP, 2, 3);
    bot.addNote(4, Pitch.CSHARP, 10, 12);
    bot.addNote(4, Pitch.CSHARP, 8, 4);
    bot.addNote(4, Pitch.E, 5, 6);
    bot.addNote(3, Pitch.FSHARP, 3, 2);
    bot.addNote(3, Pitch.E, 4, 1);
    bot.addNote(3, Pitch.GSHARP, 2, 8);
    bot.addNote(3, Pitch.GSHARP, 4, 2);
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
    assertEquals(bots, bot.toString());

    top.overlay(bot);
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
    assertEquals(overlay, top.toString());
  }

  @Test
  public void overlayConflictsNotEqualsBothWays() {
    Piece top = new Piece("title");
    top.addNote(3, Pitch.A, 3, 4);
    top.addNote(3, Pitch.E, 0, 3);
    top.addNote(3, Pitch.GSHARP, 2, 2);
    top.addNote(3, Pitch.D, 1, 1);
    top.addNote(4, Pitch.CSHARP, 10, 6);

    Piece bot = new Piece("title");
    bot.addNote(3, Pitch.A, 5, 3);
    bot.addNote(4, Pitch.CSHARP, 2, 3);
    bot.addNote(4, Pitch.CSHARP, 10, 12);
    bot.addNote(4, Pitch.CSHARP, 8, 4);
    bot.addNote(4, Pitch.E, 5, 6);
    bot.addNote(3, Pitch.FSHARP, 3, 2);
    bot.addNote(3, Pitch.E, 4, 1);
    bot.addNote(3, Pitch.GSHARP, 2, 8);
    bot.addNote(3, Pitch.GSHARP, 4, 2);

    Piece botOverlay = new Piece(top);
    botOverlay.overlay(bot);
    Piece topOverlay = new Piece(bot);
    topOverlay.overlay(top);
    assertFalse(topOverlay.equals(botOverlay));
  }

  // Tests for the move method
  @Test(expected = IllegalArgumentException.class)
  public void moveToNegativePosition() {
    Piece p = new Piece("hey");
    p.addNote(3, Pitch.DSHARP, 0, 3);
    p.move(-1);
  }

  @Test
  public void moveEmpty() {
    Piece p = new Piece("hey");
    p.move(23);
    assertTrue(p.equals(new Piece("hey")));
    p.move(-50);
    assertTrue(p.equals(new Piece("hey")));
  }

  @Test
  public void moveZeroDistance() {
    Piece p = new Piece("hey");
    p.addNote(3, Pitch.DSHARP, 0, 3);
    p.addNote(6, Pitch.F, 4, 8);
    Piece moved = new Piece(p);
    moved.move(0);
    assertTrue(p.equals(moved));
  }

  @Test
  public void movePositiveDistance() {
    Piece p = new Piece("hey");
    p.addNote(3, Pitch.DSHARP, 0, 3);
    p.addNote(2, Pitch.G, 2, 1);
    p.addNote(2, Pitch.FSHARP, 10, 4);
    p.addNote(2, Pitch.G, 1, 2);
    String toString = "     F#2   G2  G#2   A2  A#2   B2   C3  C#3   D3  D#3 \n"
                    + " 0                                                 X  \n"
                    + " 1         X                                       |  \n"
                    + " 2         X                                       |  \n"
                    + " 3                                                    \n"
                    + " 4                                                    \n"
                    + " 5                                                    \n"
                    + " 6                                                    \n"
                    + " 7                                                    \n"
                    + " 8                                                    \n"
                    + " 9                                                    \n"
                    + "10    X                                               \n"
                    + "11    |                                               \n"
                    + "12    |                                               \n"
                    + "13    |                                               \n";
    assertEquals(toString, p.toString());
    p.move(2);
    String moveToString = "     F#2   G2  G#2   A2  A#2   B2   C3  C#3   D3  D#3 \n"
                        + " 0                                                    \n"
                        + " 1                                                    \n"
                        + " 2                                                 X  \n"
                        + " 3         X                                       |  \n"
                        + " 4         X                                       |  \n"
                        + " 5                                                    \n"
                        + " 6                                                    \n"
                        + " 7                                                    \n"
                        + " 8                                                    \n"
                        + " 9                                                    \n"
                        + "10                                                    \n"
                        + "11                                                    \n"
                        + "12    X                                               \n"
                        + "13    |                                               \n"
                        + "14    |                                               \n"
                        + "15    |                                               \n";
    assertEquals(moveToString, p.toString());
  }

  @Test
  public void moveNegativeDistance() {
    Piece p = new Piece("hey");
    p.addNote(3, Pitch.DSHARP, 6, 3);
    p.addNote(2, Pitch.G, 4, 1);
    p.addNote(2, Pitch.FSHARP, 10, 4);
    p.addNote(2, Pitch.G, 3, 2);
    String toString = "     F#2   G2  G#2   A2  A#2   B2   C3  C#3   D3  D#3 \n"
                    + " 0                                                    \n"
                    + " 1                                                    \n"
                    + " 2                                                    \n"
                    + " 3         X                                          \n"
                    + " 4         X                                          \n"
                    + " 5                                                    \n"
                    + " 6                                                 X  \n"
                    + " 7                                                 |  \n"
                    + " 8                                                 |  \n"
                    + " 9                                                    \n"
                    + "10    X                                               \n"
                    + "11    |                                               \n"
                    + "12    |                                               \n"
                    + "13    |                                               \n";
    assertEquals(toString, p.toString());
    p.move(-2);
    String moveToString = "     F#2   G2  G#2   A2  A#2   B2   C3  C#3   D3  D#3 \n"
                        + " 0                                                    \n"
                        + " 1         X                                          \n"
                        + " 2         X                                          \n"
                        + " 3                                                    \n"
                        + " 4                                                 X  \n"
                        + " 5                                                 |  \n"
                        + " 6                                                 |  \n"
                        + " 7                                                    \n"
                        + " 8    X                                               \n"
                        + " 9    |                                               \n"
                        + "10    |                                               \n"
                        + "11    |                                               \n";
    assertEquals(moveToString, p.toString());
  }

  // Tests for the getTitle method
  @Test
  public void getTitleValid() {
    assertEquals("heyya", new Piece("heyya").getTitle());
  }

  @Test
  public void getTitleOfCopy() {
    Piece p = new Piece("heyya");
    assertEquals("heyya", new Piece(p).getTitle());
  }

  // Tests for the length method
  @Test
  public void lengthEmpty() {
    assertEquals(0, new Piece("hey").length());
  }

  @Test
  public void lengthEqualsCopy() {
    Piece p = new Piece("hey");
    p.addNote(3, Pitch.ASHARP, 5, 10);
    assertEquals(p.length(), new Piece(p).length());
  }

  @Test
  public void lengthOverlayPiece() {
    Piece p = new Piece("hey");
    p.addNote(3, Pitch.ASHARP, 5, 10);
    Piece p2 = new Piece("hey");
    p2.addNote(5, Pitch.C, 8, 5);

    Piece p2Overlay = new Piece(p);
    p2Overlay.overlay(p2);
    Piece pOverlay = new Piece(p2);
    pOverlay.overlay(p);

    assertTrue(p.length() > p2.length());
    assertEquals(pOverlay.length(), p2Overlay.length());
    assertEquals(pOverlay.length(), p.length());
    assertNotEquals(p2Overlay.length(), p2.length());
  }

  @Test
  public void lengthMovedPieced() {
    Piece p = new Piece("hey");
    p.addNote(3, Pitch.ASHARP, 5, 10);
    int length = p.length();

    p.move(-5);
    assertEquals(length - 5, p.length());
    p.move(10);
    assertEquals(length + 5, p.length());
  }
}