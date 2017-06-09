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
    Piece p = new Piece("My Piece");
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

  // Tests for the addNote method

  // Tests for the removeNote method

  // Tests for the editPitch method

  // Tests for the editPosition method

  // Tests for the editDuration method

  // Tests for the merge method

  // Tests for the getTitle method
  @Test
  public void getTitleValid() {
    assertEquals("heyya", new Piece("heyya").getTitle());
  }
}