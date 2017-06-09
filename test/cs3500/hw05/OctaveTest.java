package cs3500.hw05;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotEquals;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

/**
 * Tests for the {@link Octave} class.
 */
public class OctaveTest {
  // Tests for the default constructor
  @Test
  public void defaultConstructor() {
    Octave o = new Octave();
    assertTrue(o.isEmpty());
  }

  // Tests for the copy constructor
  @Test(expected = IllegalArgumentException.class)
  public void copyConstructorNullOctave() {
    new Octave(null);
  }

  @Test
  public void copyConstructorNewOctave() {
    Octave o = new Octave(new Octave());
    assertTrue(o.isEmpty());
    assertEquals(o, new Octave());
  }

  @Test
  public void copyConstructorSameData() {
    Octave o = new Octave();
    o.addNote(Pitch.ASHARP, 3, 4);
    o.addNote(Pitch.B, 2, 6);
    Octave o2 = new Octave(o);
    assertEquals(o, o2);
  }

  @Test
  public void copyConstructorDifferentReferences() {
    Octave o = new Octave();
    o.addNote(Pitch.ASHARP, 3, 4);
    o.addNote(Pitch.B, 2, 6);
    Octave o2 = new Octave(o);
    o.addNote(Pitch.C, 10, 4);
    assertNotEquals(o, o2);
  }

  // Tests for the equals method
  @Test
  public void equalsSameObjectEmpty() {
    Octave o = new Octave();
    assertTrue(o.equals(o));
  }

  @Test
  public void equalsSameObjectWithData() {
    Octave o = new Octave();
    o.addNote(Pitch.ASHARP, 3, 4);
    o.addNote(Pitch.B, 2, 6);
    assertTrue(o.equals(o));
  }

  @Test
  public void equalsNull() {
    Octave o = new Octave();
    o.addNote(Pitch.ASHARP, 3, 4);
    o.addNote(Pitch.B, 2, 6);
    assertFalse(o.equals(null));
  }

  @Test
  public void equalsDifferentObjectType() {
    Octave o = new Octave();
    o.addNote(Pitch.ASHARP, 3, 4);
    o.addNote(Pitch.B, 2, 6);
    assertFalse(o.equals(3));
  }

  @Test
  public void equalsDifferentNotes() {
    Octave o = new Octave();
    o.addNote(Pitch.ASHARP, 3, 4);
    Octave o2 = new Octave();
    o2.addNote(Pitch.B, 4, 5);
    assertFalse(o.equals(o2));
  }

  @Test
  public void equalsSameNotes() {
    Octave o = new Octave();
    o.addNote(Pitch.ASHARP, 3, 4);
    o.addNote(Pitch.B, 2, 6);
    Octave o2 = new Octave();
    o2.addNote(Pitch.B, 2, 6);
    o2.addNote(Pitch.ASHARP, 3, 4);
    assertTrue(o.equals(o2));
  }

  @Test
  public void equalsCopy() {
    Octave o = new Octave();
    o.addNote(Pitch.ASHARP, 3, 4);
    o.addNote(Pitch.B, 4, 5);
    Octave o2 = new Octave(o);
    assertTrue(o.equals(o2));
  }

  @Test
  public void equalsNewObject() {
    assertTrue(new Octave().equals(new Octave()));
  }

  // Tests for the merge method

  // Tests for the getOctaveTable method

  // Tests for the isEmpty method
  @Test
  public void isEmptyNewInstance() {
    assertTrue(new Octave().isEmpty());
  }

  @Test
  public void isEmptyAddedNotes() {
    Octave o = new Octave();
    o.addNote(Pitch.GSHARP, 10, 4);
    assertFalse(o.isEmpty());
  }

  @Test
  public void isEmptyRemoveNotes() {
    Octave o = new Octave();
    o.addNote(Pitch.GSHARP, 10, 4);
    assertFalse(o.isEmpty());
    o.removeNote(Pitch.GSHARP, 10);
    assertTrue(o.isEmpty());
  }

  // Tests for the size method

  // Tests for the addNote method

  // Tests for the removeNote method

  // Tests for the editPitch method

  // Tests for the editPosition method

  // Tests for the editDuration method

}
