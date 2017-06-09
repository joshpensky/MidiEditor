package cs3500.hw05;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

  // Tests for the overlay method

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

  // Tests for the length method
  @Test
  public void lengthEmpty() {
    assertEquals(0, new Octave().length());
  }

  @Test
  public void lengthOneNote() {
    Octave o = new Octave();
    Note n = new Note(3, 5);
    o.addNote(Pitch.E, 3, 5);
    assertEquals(n.getEndPoint(), o.length());
    assertEquals(7, o.length());
  }

  @Test
  public void lengthMultipleNotes() {
    Octave o = new Octave();
    Note n = new Note(0, 10);
    o.addNote(Pitch.DSHARP, 0, 10);
    o.addNote(Pitch.E, 3, 5);
    assertEquals(n.getEndPoint(), o.length());
    assertEquals(9, o.length());
  }

  @Test
  public void lengthRemoveNote() {
    Octave o = new Octave();
    o.addNote(Pitch.DSHARP, 0, 10);
    o.addNote(Pitch.G, 8, 4);
    o.addNote(Pitch.E, 3, 5);
    assertEquals(11, o.length());
    o.removeNote(Pitch.G, 8);
    assertEquals(9, o.length());
  }

  // Tests for the addNote method
  @Test(expected = IllegalArgumentException.class)
  public void addNoteNullPitch() {
    Octave o = new Octave();
    o.addNote(null, 2, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteNegativePosition() {
    Octave o = new Octave();
    o.addNote(Pitch.G, -2, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteNegativeDuration() {
    Octave o = new Octave();
    o.addNote(Pitch.G, 2, -4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteZeroDuration() {
    Octave o = new Octave();
    o.addNote(Pitch.G, 2, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteAlreadyExistsAtLocation() {
    Octave o = new Octave();
    o.addNote(Pitch.G, 2, 4);
    o.addNote(Pitch.G, 2, 2);
  }

  @Test
  public void addNoteValid() {
    Octave o = new Octave();
    o.addNote(Pitch.G, 2, 3);
    List<List<String>> table = new ArrayList<>();
    table.add(new ArrayList<>(Arrays.asList("  C3 ", "     ", "     ", "     ", "     ", "     ")));
    table.add(new ArrayList<>(Arrays.asList(" C#3 ", "     ", "     ", "     ", "     ", "     ")));
    table.add(new ArrayList<>(Arrays.asList("  D3 ", "     ", "     ", "     ", "     ", "     ")));
    table.add(new ArrayList<>(Arrays.asList(" D#3 ", "     ", "     ", "     ", "     ", "     ")));
    table.add(new ArrayList<>(Arrays.asList("  E3 ", "     ", "     ", "     ", "     ", "     ")));
    table.add(new ArrayList<>(Arrays.asList("  F3 ", "     ", "     ", "     ", "     ", "     ")));
    table.add(new ArrayList<>(Arrays.asList(" F#3 ", "     ", "     ", "     ", "     ", "     ")));
    table.add(new ArrayList<>(Arrays.asList("  G3 ", "     ", "     ", "  X  ", "  |  ", "  |  ")));
    table.add(new ArrayList<>(Arrays.asList(" G#3 ", "     ", "     ", "     ", "     ", "     ")));
    table.add(new ArrayList<>(Arrays.asList("  A3 ", "     ", "     ", "     ", "     ", "     ")));
    table.add(new ArrayList<>(Arrays.asList(" A#3 ", "     ", "     ", "     ", "     ", "     ")));
    table.add(new ArrayList<>(Arrays.asList("  B3 ", "     ", "     ", "     ", "     ", "     ")));
    assertEquals(table, o.getOctaveTable(3, 5));
  }

  // Tests for the removeNote method
  @Test(expected = IllegalArgumentException.class)
  public void removeNoteNullPitch() {
    Octave o = new Octave();
    o.removeNote(null, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeNoteNegativePosition() {
    Octave o = new Octave();
    o.removeNote(Pitch.D, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeNoteNoNoteAtPosition() {
    Octave o = new Octave();
    o.removeNote(Pitch.D, 3);
  }

  @Test
  public void removeNoteValid() {
    Octave o = new Octave();
    o.addNote(Pitch.D, 3, 4);
    o.removeNote(Pitch.D, 3);
    assertEquals(new Octave().getOctaveTable(3, 5), o.getOctaveTable(3, 5));
  }

  // Tests for the editPitch method
  @Test(expected = IllegalArgumentException.class)
  public void editPitchNullPitch() {
    Octave o = new Octave();
    o.editPitch(null, 3, Pitch.E);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPitchNegativePosition() {
    Octave o = new Octave();
    o.editPitch(Pitch.D, -3, Pitch.E);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPitchNullNewPitch() {
    Octave o = new Octave();
    o.editPitch(Pitch.D, 3, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPitchNoNoteAtPosition() {
    Octave o = new Octave();
    o.editPitch(Pitch.D, 3, Pitch.E);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPitchNoteAtNewPosition() {
    Octave o = new Octave();
    o.addNote(Pitch.D, 3, 4);
    o.addNote(Pitch.E, 3, 2);
    o.editPitch(Pitch.D, 3, Pitch.E);
  }

  @Test
  public void editPitchValid() {
    Octave o = new Octave();
    o.addNote(Pitch.D, 3, 4);
    o.editPitch(Pitch.D, 3, Pitch.B);
    Octave product = new Octave();
    product.addNote(Pitch.B, 3, 4);
    assertTrue(o.equals(product));
  }

  // Tests for the editPosition method
  @Test(expected = IllegalArgumentException.class)
  public void editPositionNullPitch() {
    Octave o = new Octave();
    o.editPosition(null, 3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPositionNegativePosition() {
    Octave o = new Octave();
    o.editPosition(Pitch.D, -3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPositionNullNegativeNewPosition() {
    Octave o = new Octave();
    o.editPosition(Pitch.D, 3, -6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPositionNoNoteAtPosition() {
    Octave o = new Octave();
    o.editPosition(Pitch.D, 3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editPositionNoteAtNewPosition() {
    Octave o = new Octave();
    o.addNote(Pitch.D, 3, 4);
    o.addNote(Pitch.D, 6, 2);
    o.editPosition(Pitch.D, 3, 6);
  }

  @Test
  public void editPositionValid() {
    Octave o = new Octave();
    o.addNote(Pitch.D, 3, 4);
    o.editPosition(Pitch.D, 3, 6);
    Octave product = new Octave();
    product.addNote(Pitch.D, 6, 4);
    assertTrue(o.equals(product));
  }

  // Tests for the editDuration method
  @Test(expected = IllegalArgumentException.class)
  public void editDurationNullPitch() {
    Octave o = new Octave();
    o.editDuration(null, 3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editDurationNegativePosition() {
    Octave o = new Octave();
    o.editDuration(Pitch.D, -3, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editDurationNullNegativeNewDuration() {
    Octave o = new Octave();
    o.editDuration(Pitch.D, 3, -6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editDurationNoNoteAtPosition() {
    Octave o = new Octave();
    o.editDuration(Pitch.D, 3, 6);
  }

  @Test
  public void editDurationValid() {
    Octave o = new Octave();
    o.addNote(Pitch.D, 3, 4);
    o.editDuration(Pitch.D, 3, 6);
    Octave product = new Octave();
    product.addNote(Pitch.D, 3, 6);
    assertTrue(o.equals(product));
  }
}
