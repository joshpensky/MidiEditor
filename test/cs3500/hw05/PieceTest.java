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
  // Tests for the constructor
  @Test(expected = IllegalArgumentException.class)
  public void newConstructorNullName() {
    new Piece(null, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void newConstructorNegativeMeasure() {
    new Piece("My Piece", -3);
  }

  @Test
  public void newConstructorValid() {
    Piece p = new Piece("My Piece", 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void starterConstructorNullName() {
    new Piece(null, 0, new Note(Pitch.ASHARP, Octave.SIX, 3, 5));
  }

  @Test(expected = IllegalArgumentException.class)
  public void starterConstructorNegativeMeasure() {
    new Piece("piece", -3, new Note(Pitch.ASHARP, Octave.SIX, 3, 5));
  }

  @Test(expected = IllegalArgumentException.class)
  public void starterConstructorNullNotes() {
    new Piece("piece", 2, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void starterConstructorContainsNullNote() {
    new Piece("piece", 2, new Note(Pitch.ASHARP, Octave.SIX, 3, 5), null,
        new Note(Pitch.C, Octave.ONE, 2, 9));
  }

  @Test
  public void starterConstructorValid() {
    Piece p = new Piece("piece", 2, new Note(Pitch.ASHARP, Octave.SIX, 3, 5),
        new Note(Pitch.C, Octave.ONE, 2, 9));
  }

  @Test(expected = IllegalArgumentException.class)
  public void duplicateConstructorNullName() {
    new Piece(null, new Piece("hey", 3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void duplicateConstructorNullPiece() {
    new Piece("hey", null);
  }

  @Test
  public void duplicateConstructorValid() {
    Piece dup = new Piece("boom", 3, new Note(Pitch.ASHARP, Octave.SIX, 3, 5));
    Piece p = new Piece("hey", dup);
    assertTrue(p.equals(dup));
  }
}
