package cs3500.hw05;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotEquals;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

/**
 * Tests for the {@link Note} class.
 */
public class NoteTest {
  // Tests for the constructor
  @Test(expected = IllegalArgumentException.class)
  public void constructNullPitch() {
    new Note(null, Octave.EIGHT, 3, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructNullOctave() {
    new Note(Pitch.A, null, 3, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructNegativeDuration() {
    new Note(Pitch.A, Octave.EIGHT, -1, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructNegativePosition() {
    new Note(Pitch.A, Octave.EIGHT, 3, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructNullNote() {
    new Note(null);
  }

  @Test
  public void constructNew() {
    Note n = new Note(Pitch.ASHARP, Octave.TWO, 3, 5);
    assertEquals("Pitch: A#2\nDuration: 3\nPosition: 5", n.toString());
  }

  @Test
  public void constructDuplicateSameData() {
    Note n = new Note(Pitch.ASHARP, Octave.TWO, 3, 5);
    Note n2 = new Note(n);
    assertEquals(n2.toString(), n.toString());
  }

  @Test
  public void constructDuplicateDifferentReferences() {
    Note n = new Note(Pitch.ASHARP, Octave.TWO, 3, 5);
    Note n2 = new Note(n);
    n.setPosition(4);
    assertNotEquals(n2.toString(), n.toString());
  }

  // Tests for the equals method
  @Test
  public void equalsSameObject() {
    Note n = new Note(Pitch.FSHARP, Octave.FIVE, 4, 2);
    assertTrue(n.equals(n));
  }

  @Test
  public void equalsNull() {
    Note n = new Note(Pitch.FSHARP, Octave.FIVE, 4, 2);
    assertFalse(n.equals(null));
  }

  @Test
  public void equalsDifferentObjectType() {
    Note n = new Note(Pitch.FSHARP, Octave.FIVE, 4, 2);
    assertFalse(n.equals(3));
  }

  @Test
  public void equalsDifferentPitch() {
    Note n = new Note(Pitch.FSHARP, Octave.FIVE, 4, 2);
    Note n2 = new Note(Pitch.F, Octave.FIVE, 4, 2);
    assertFalse(n.equals(n2));
  }

  @Test
  public void equalsDifferentOctave() {
    Note n = new Note(Pitch.FSHARP, Octave.FIVE, 4, 2);
    Note n2 = new Note(Pitch.F, Octave.FOUR, 4, 2);
    assertFalse(n.equals(n2));
  }

  @Test
  public void equalsDifferentDuration() {
    Note n = new Note(Pitch.FSHARP, Octave.FIVE, 4, 2);
    Note n2 = new Note(Pitch.FSHARP, Octave.FIVE, 3, 2);
    assertFalse(n.equals(n2));
  }

  @Test
  public void equalsDifferentPosition() {
    Note n = new Note(Pitch.FSHARP, Octave.FIVE, 4, 2);
    Note n2 = new Note(Pitch.FSHARP, Octave.FIVE, 4, 5);
    assertFalse(n.equals(n2));
  }

  @Test
  public void equalsSameValues() {
    Note n = new Note(Pitch.FSHARP, Octave.FIVE, 4, 2);
    Note n2 = new Note(Pitch.FSHARP, Octave.FIVE, 4, 2);
    assertTrue(n.equals(n2));
  }

  @Test
  public void equalsDuplicate() {
    Note n = new Note(Pitch.FSHARP, Octave.FIVE, 4, 2);
    Note n2 = new Note(n);
    assertTrue(n.equals(n2));
  }

  // Tests for the hashCode method
  @Test
  public void hashCodeNormal() {
    Note n = new Note(Pitch.FSHARP, Octave.FIVE, 4, 2);
    assertEquals(60040002, n.hashCode());
  }

  @Test
  public void hashCodeHighEnd() {
    Note n = new Note(Pitch.B, Octave.SEVEN, 999, 9999);
    assertEquals(119999999, n.hashCode());
  }

  // Tests for the toString method
  @Test
  public void toStringNoChange() {
    Note n = new Note(Pitch.C, Octave.SIX, 15, 23);
    assertEquals("Pitch: C6\nDuration: 15\nPosition: 23", n.toString());
  }

  @Test
  public void toStringChangePitch() {
    Note n = new Note(Pitch.C, Octave.SIX, 15, 23);
    String toString = n.toString();
    n.setPitch(Pitch.ASHARP, Octave.FOUR);
    assertNotEquals(n.toString(), toString);
  }

  @Test
  public void toStringChangeDuration() {
    Note n = new Note(Pitch.C, Octave.SIX, 15, 23);
    String toString = n.toString();
    n.setDuration(82);
    assertNotEquals(n.toString(), toString);
  }

  @Test
  public void toStringChangePosition() {
    Note n = new Note(Pitch.C, Octave.SIX, 15, 23);
    String toString = n.toString();
    n.setPosition(82);
    assertNotEquals(n.toString(), toString);
  }

  // Tests for the setPitch method
  @Test(expected = IllegalArgumentException.class)
  public void setPitchNullPitch() {
    Note n = new Note(Pitch.D, Octave.SIX, 3, 24);
    n.setPitch(null, Octave.EIGHT);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setPitchNullOctave() {
    Note n = new Note(Pitch.D, Octave.SIX, 3, 24);
    n.setPitch(Pitch.ASHARP, null);
  }

  @Test
  public void setPitchValid() {
    Note n = new Note(Pitch.D, Octave.SIX, 3, 24);
    n.setPitch(Pitch.ASHARP, Octave.EIGHT);
    assertEquals("Pitch: A#8\nDuration: 3\nPosition: 24", n.toString());
  }

  // Tests for the setDuration method
  @Test(expected = IllegalArgumentException.class)
  public void setDurationNegative() {
    Note n = new Note(Pitch.D, Octave.SIX, 3, 24);
    n.setPosition(-32);
  }

  @Test
  public void setDurationValid() {
    Note n = new Note(Pitch.D, Octave.SIX, 3, 24);
    n.setDuration(52);
    assertEquals("Pitch: D6\nDuration: 52\nPosition: 24", n.toString());
  }

  // Tests for the setDuration method
  @Test(expected = IllegalArgumentException.class)
  public void setPositionNegative() {
    Note n = new Note(Pitch.D, Octave.SIX, 3, 24);
    n.setPosition(-32);
  }

  @Test
  public void setPositionValid() {
    Note n = new Note(Pitch.D, Octave.SIX, 3, 24);
    n.setPosition(52);
    assertEquals("Pitch: D6\nDuration: 3\nPosition: 52", n.toString());
  }
}
