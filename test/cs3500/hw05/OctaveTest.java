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

  // Tests for the getOctaveTable method
  @Test
  public void getOctaveTableEmpty() {
    List<List<String>> table = new ArrayList<>();
    table.add(new ArrayList<>(Arrays.asList(" C10 ", "     ")));
    table.add(new ArrayList<>(Arrays.asList(" C#10", "     ")));
    table.add(new ArrayList<>(Arrays.asList(" D10 ", "     ")));
    table.add(new ArrayList<>(Arrays.asList(" D#10", "     ")));
    table.add(new ArrayList<>(Arrays.asList(" E10 ", "     ")));
    table.add(new ArrayList<>(Arrays.asList(" F10 ", "     ")));
    table.add(new ArrayList<>(Arrays.asList(" F#10", "     ")));
    table.add(new ArrayList<>(Arrays.asList(" G10 ", "     ")));
    table.add(new ArrayList<>(Arrays.asList(" G#10", "     ")));
    table.add(new ArrayList<>(Arrays.asList(" A10 ", "     ")));
    table.add(new ArrayList<>(Arrays.asList(" A#10", "     ")));
    table.add(new ArrayList<>(Arrays.asList(" B10 ", "     ")));
    assertEquals(table, new Octave().getOctaveTable(10, 5));
  }

  @Test
  public void getOctaveTableOneNote() {
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

  @Test
  public void getOctaveTableMultipleNotes() {
    Octave o = new Octave();
    o.addNote(Pitch.G, 2, 3);
    o.addNote(Pitch.G, 1, 2);
    o.addNote(Pitch.CSHARP, 1, 4);
    o.addNote(Pitch.A, 0, 1);
    o.addNote(Pitch.A, 2, 2);
    List<List<String>> table = new ArrayList<>();
    table.add(new ArrayList<>(Arrays.asList("  C6 ", "     ", "     ", "     ", "     ", "     ")));
    table.add(new ArrayList<>(Arrays.asList(" C#6 ", "     ", "  X  ", "  |  ", "  |  ", "  |  ")));
    table.add(new ArrayList<>(Arrays.asList("  D6 ", "     ", "     ", "     ", "     ", "     ")));
    table.add(new ArrayList<>(Arrays.asList(" D#6 ", "     ", "     ", "     ", "     ", "     ")));
    table.add(new ArrayList<>(Arrays.asList("  E6 ", "     ", "     ", "     ", "     ", "     ")));
    table.add(new ArrayList<>(Arrays.asList("  F6 ", "     ", "     ", "     ", "     ", "     ")));
    table.add(new ArrayList<>(Arrays.asList(" F#6 ", "     ", "     ", "     ", "     ", "     ")));
    table.add(new ArrayList<>(Arrays.asList("  G6 ", "     ", "  X  ", "  X  ", "  |  ", "  |  ")));
    table.add(new ArrayList<>(Arrays.asList(" G#6 ", "     ", "     ", "     ", "     ", "     ")));
    table.add(new ArrayList<>(Arrays.asList("  A6 ", "  X  ", "     ", "  X  ", "  |  ", "     ")));
    table.add(new ArrayList<>(Arrays.asList(" A#6 ", "     ", "     ", "     ", "     ", "     ")));
    table.add(new ArrayList<>(Arrays.asList("  B6 ", "     ", "     ", "     ", "     ", "     ")));
    assertEquals(table, o.getOctaveTable(6, 5));
  }

  @Test
  public void getOctaveTableDifferentSpacing() {
    Octave o = new Octave();
    o.addNote(Pitch.G, 2, 3);
    List<List<String>> table = new ArrayList<>();
    table.add(new ArrayList<>(Arrays.asList("C4", " ", " ", " ", " ", " ")));
    table.add(new ArrayList<>(Arrays.asList("C#4", " ", " ", " ", " ", " ")));
    table.add(new ArrayList<>(Arrays.asList("D4", " ", " ", " ", " ", " ")));
    table.add(new ArrayList<>(Arrays.asList("D#4", " ", " ", " ", " ", " ")));
    table.add(new ArrayList<>(Arrays.asList("E4", " ", " ", " ", " ", " ")));
    table.add(new ArrayList<>(Arrays.asList("F4", " ", " ", " ", " ", " ")));
    table.add(new ArrayList<>(Arrays.asList("F#4", " ", " ", " ", " ", " ")));
    table.add(new ArrayList<>(Arrays.asList("G4", " ", " ", "X", "|", "|")));
    table.add(new ArrayList<>(Arrays.asList("G#4", " ", " ", " ", " ", " ")));
    table.add(new ArrayList<>(Arrays.asList("A4", " ", " ", " ", " ", " ")));
    table.add(new ArrayList<>(Arrays.asList("A#4", " ", " ", " ", " ", " ")));
    table.add(new ArrayList<>(Arrays.asList("B4", " ", " ", " ", " ", " ")));
    assertEquals(table, o.getOctaveTable(4, 1));
  }

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

  // Tests for the editNotePitch method
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

  // Tests for the editNotePosition method
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

  // Tests for the editNoteDuration method
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
  public void editDurationNegativeNewDuration() {
    Octave o = new Octave();
    o.editDuration(Pitch.D, 3, -6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editDurationZeroNewDuration() {
    Octave o = new Octave();
    o.editDuration(Pitch.D, 3, 0);
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

  // Tests for the overlay method
  @Test(expected = IllegalArgumentException.class)
  public void overlayNullOctave() {
    new Octave().overlay(null);
  }

  @Test
  public void overlayOntoEmptyOctave() {
    Octave o = new Octave();
    o.addNote(Pitch.A, 3, 4);
    o.addNote(Pitch.DSHARP, 6, 8);
    o.addNote(Pitch.E, 15, 12);
    o.addNote(Pitch.E, 20, 3);
    o.addNote(Pitch.B, 8, 4);
    Octave empty = new Octave();
    empty.overlay(o);
    assertTrue(o.equals(empty));
  }

  @Test
  public void overlayEmptyOctaveOnto() {
    Octave o = new Octave();
    o.addNote(Pitch.A, 3, 4);
    o.addNote(Pitch.DSHARP, 6, 8);
    o.addNote(Pitch.E, 15, 12);
    o.addNote(Pitch.E, 20, 3);
    o.addNote(Pitch.B, 8, 4);
    Octave empty = new Octave();
    Octave overlaid = new Octave(o);
    overlaid.overlay(empty);
    assertFalse(overlaid.equals(empty));
    assertTrue(overlaid.equals(o));
  }

  @Test
  public void overlayNoConflictingAdds() {
    Octave o = new Octave();
    o.addNote(Pitch.A, 3, 4);
    o.addNote(Pitch.E, 0, 3);
    o.addNote(Pitch.GSHARP, 2, 2);
    o.addNote(Pitch.C, 1, 1);
    List<List<String>> oTab = new ArrayList<>();
    oTab.add(new ArrayList<>(Arrays.asList("  C3 ", "     ", "  X  ", "     ", "     ", "     ",
        "     ", "     ")));
    oTab.add(new ArrayList<>(Arrays.asList(" C#3 ", "     ", "     ", "     ", "     ", "     ",
        "     ", "     ")));
    oTab.add(new ArrayList<>(Arrays.asList("  D3 ", "     ", "     ", "     ", "     ", "     ",
        "     ", "     ")));
    oTab.add(new ArrayList<>(Arrays.asList(" D#3 ", "     ", "     ", "     ", "     ", "     ",
        "     ", "     ")));
    oTab.add(new ArrayList<>(Arrays.asList("  E3 ", "  X  ", "  |  ", "  |  ", "     ", "     ",
        "     ", "     ")));
    oTab.add(new ArrayList<>(Arrays.asList("  F3 ", "     ", "     ", "     ", "     ", "     ",
        "     ", "     ")));
    oTab.add(new ArrayList<>(Arrays.asList(" F#3 ", "     ", "     ", "     ", "     ", "     ",
        "     ", "     ")));
    oTab.add(new ArrayList<>(Arrays.asList("  G3 ", "     ", "     ", "     ", "     ", "     ",
        "     ", "     ")));
    oTab.add(new ArrayList<>(Arrays.asList(" G#3 ", "     ", "     ", "  X  ", "  |  ", "     ",
        "     ", "     ")));
    oTab.add(new ArrayList<>(Arrays.asList("  A3 ", "     ", "     ", "     ", "  X  ", "  |  ",
        "  |  ", "  |  ")));
    oTab.add(new ArrayList<>(Arrays.asList(" A#3 ", "     ", "     ", "     ", "     ", "     ",
        "     ", "     ")));
    oTab.add(new ArrayList<>(Arrays.asList("  B3 ", "     ", "     ", "     ", "     ", "     ",
        "     ", "     ")));
    assertEquals(oTab, o.getOctaveTable(3, 5));

    Octave o2 = new Octave();
    o2.addNote(Pitch.A, 0, 2);
    o2.addNote(Pitch.CSHARP, 2, 3);
    o2.addNote(Pitch.FSHARP, 3, 2);
    o2.addNote(Pitch.E, 4, 1);
    List<List<String>> o2Tab = new ArrayList<>();
    o2Tab.add(new ArrayList<>(Arrays.asList("  C3 ", "     ", "     ", "     ", "     ", "     ")));
    o2Tab.add(new ArrayList<>(Arrays.asList(" C#3 ", "     ", "     ", "  X  ", "  |  ", "  |  ")));
    o2Tab.add(new ArrayList<>(Arrays.asList("  D3 ", "     ", "     ", "     ", "     ", "     ")));
    o2Tab.add(new ArrayList<>(Arrays.asList(" D#3 ", "     ", "     ", "     ", "     ", "     ")));
    o2Tab.add(new ArrayList<>(Arrays.asList("  E3 ", "     ", "     ", "     ", "     ", "  X  ")));
    o2Tab.add(new ArrayList<>(Arrays.asList("  F3 ", "     ", "     ", "     ", "     ", "     ")));
    o2Tab.add(new ArrayList<>(Arrays.asList(" F#3 ", "     ", "     ", "     ", "  X  ", "  |  ")));
    o2Tab.add(new ArrayList<>(Arrays.asList("  G3 ", "     ", "     ", "     ", "     ", "     ")));
    o2Tab.add(new ArrayList<>(Arrays.asList(" G#3 ", "     ", "     ", "     ", "     ", "     ")));
    o2Tab.add(new ArrayList<>(Arrays.asList("  A3 ", "  X  ", "  |  ", "     ", "     ", "     ")));
    o2Tab.add(new ArrayList<>(Arrays.asList(" A#3 ", "     ", "     ", "     ", "     ", "     ")));
    o2Tab.add(new ArrayList<>(Arrays.asList("  B3 ", "     ", "     ", "     ", "     ", "     ")));
    assertEquals(o2Tab, o2.getOctaveTable(3, 5));

    o.overlay(o2);
    List<List<String>> fTab = new ArrayList<>();
    fTab.add(new ArrayList<>(Arrays.asList("  C3 ", "     ", "  X  ", "     ", "     ", "     ",
      "     ", "     ")));
    fTab.add(new ArrayList<>(Arrays.asList(" C#3 ", "     ", "     ", "  X  ", "  |  ", "  |  ",
      "     ", "     ")));
    fTab.add(new ArrayList<>(Arrays.asList("  D3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ")));
    fTab.add(new ArrayList<>(Arrays.asList(" D#3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ")));
    fTab.add(new ArrayList<>(Arrays.asList("  E3 ", "  X  ", "  |  ", "  |  ", "     ", "  X  ",
      "     ", "     ")));
    fTab.add(new ArrayList<>(Arrays.asList("  F3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ")));
    fTab.add(new ArrayList<>(Arrays.asList(" F#3 ", "     ", "     ", "     ", "  X  ", "  |  ",
      "     ", "     ")));
    fTab.add(new ArrayList<>(Arrays.asList("  G3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ")));
    fTab.add(new ArrayList<>(Arrays.asList(" G#3 ", "     ", "     ", "  X  ", "  |  ", "     ",
      "     ", "     ")));
    fTab.add(new ArrayList<>(Arrays.asList("  A3 ", "  X  ", "  |  ", "     ", "  X  ", "  |  ",
      "  |  ", "  |  ")));
    fTab.add(new ArrayList<>(Arrays.asList(" A#3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ")));
    fTab.add(new ArrayList<>(Arrays.asList("  B3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ")));
    assertEquals(fTab, o.getOctaveTable(3, 5));
  }

  @Test
  public void overlayNoConflictsEqualsBothWays() {
    Octave o = new Octave();
    o.addNote(Pitch.A, 3, 4);
    o.addNote(Pitch.E, 0, 3);
    o.addNote(Pitch.GSHARP, 2, 2);
    o.addNote(Pitch.C, 1, 1);

    Octave o2 = new Octave();
    o2.addNote(Pitch.A, 0, 2);
    o2.addNote(Pitch.CSHARP, 2, 3);
    o2.addNote(Pitch.FSHARP, 3, 2);
    o2.addNote(Pitch.E, 4, 1);

    Octave o2Overlay = new Octave(o);
    o2Overlay.overlay(o2);
    Octave oOverlay = new Octave(o2);
    oOverlay.overlay(o);
    assertTrue(oOverlay.equals(o2Overlay));
  }

  @Test
  public void overlayConflictingAdds() {
    Octave o = new Octave();
    o.addNote(Pitch.A, 3, 4);
    o.addNote(Pitch.E, 0, 3);
    o.addNote(Pitch.GSHARP, 2, 2);
    o.addNote(Pitch.C, 1, 1);
    List<List<String>> oTab = new ArrayList<>();
    oTab.add(new ArrayList<>(Arrays.asList("  C3 ", "     ", "  X  ", "     ", "     ", "     ",
      "     ", "     ")));
    oTab.add(new ArrayList<>(Arrays.asList(" C#3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ")));
    oTab.add(new ArrayList<>(Arrays.asList("  D3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ")));
    oTab.add(new ArrayList<>(Arrays.asList(" D#3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ")));
    oTab.add(new ArrayList<>(Arrays.asList("  E3 ", "  X  ", "  |  ", "  |  ", "     ", "     ",
      "     ", "     ")));
    oTab.add(new ArrayList<>(Arrays.asList("  F3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ")));
    oTab.add(new ArrayList<>(Arrays.asList(" F#3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ")));
    oTab.add(new ArrayList<>(Arrays.asList("  G3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ")));
    oTab.add(new ArrayList<>(Arrays.asList(" G#3 ", "     ", "     ", "  X  ", "  |  ", "     ",
      "     ", "     ")));
    oTab.add(new ArrayList<>(Arrays.asList("  A3 ", "     ", "     ", "     ", "  X  ", "  |  ",
      "  |  ", "  |  ")));
    oTab.add(new ArrayList<>(Arrays.asList(" A#3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ")));
    oTab.add(new ArrayList<>(Arrays.asList("  B3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ")));
    assertEquals(oTab, o.getOctaveTable(3, 5));

    Octave o2 = new Octave();
    o2.addNote(Pitch.A, 5, 3);
    o2.addNote(Pitch.CSHARP, 2, 3);
    o2.addNote(Pitch.FSHARP, 3, 2);
    o2.addNote(Pitch.GSHARP, 2, 8);
    List<List<String>> o2Tab = new ArrayList<>();
    o2Tab.add(new ArrayList<>(Arrays.asList("  C3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ", "     ", "     ", "     ")));
    o2Tab.add(new ArrayList<>(Arrays.asList(" C#3 ", "     ", "     ", "  X  ", "  |  ", "  |  ",
      "     ", "     ", "     ", "     ", "     ")));
    o2Tab.add(new ArrayList<>(Arrays.asList("  D3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ", "     ", "     ", "     ")));
    o2Tab.add(new ArrayList<>(Arrays.asList(" D#3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ", "     ", "     ", "     ")));
    o2Tab.add(new ArrayList<>(Arrays.asList("  E3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ", "     ", "     ", "     ")));
    o2Tab.add(new ArrayList<>(Arrays.asList("  F3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ", "     ", "     ", "     ")));
    o2Tab.add(new ArrayList<>(Arrays.asList(" F#3 ", "     ", "     ", "     ", "  X  ", "  |  ",
      "     ", "     ", "     ", "     ", "     ")));
    o2Tab.add(new ArrayList<>(Arrays.asList("  G3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ", "     ", "     ", "     ")));
    o2Tab.add(new ArrayList<>(Arrays.asList(" G#3 ", "     ", "     ", "  X  ", "  |  ", "  |  ",
      "  |  ", "  |  ", "  |  ", "  |  ", "  |  ")));
    o2Tab.add(new ArrayList<>(Arrays.asList("  A3 ", "     ", "     ", "     ", "     ", "     ",
      "  X  ", "  |  ", "  |  ", "     ", "     ")));
    o2Tab.add(new ArrayList<>(Arrays.asList(" A#3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ", "     ", "     ", "     ")));
    o2Tab.add(new ArrayList<>(Arrays.asList("  B3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ", "     ", "     ", "     ")));
    assertEquals(o2Tab, o2.getOctaveTable(3, 5));

    o.overlay(o2);
    List<List<String>> fTab = new ArrayList<>();
    fTab.add(new ArrayList<>(Arrays.asList("  C3 ", "     ", "  X  ", "     ", "     ", "     ",
      "     ", "     ", "     ")));
    fTab.add(new ArrayList<>(Arrays.asList(" C#3 ", "     ", "     ", "  X  ", "  |  ", "  |  ",
      "     ", "     ", "     ")));
    fTab.add(new ArrayList<>(Arrays.asList("  D3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ", "     ")));
    fTab.add(new ArrayList<>(Arrays.asList(" D#3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ", "     ")));
    fTab.add(new ArrayList<>(Arrays.asList("  E3 ", "  X  ", "  |  ", "  |  ", "     ", "     ",
      "     ", "     ", "     ")));
    fTab.add(new ArrayList<>(Arrays.asList("  F3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ", "     ")));
    fTab.add(new ArrayList<>(Arrays.asList(" F#3 ", "     ", "     ", "     ", "  X  ", "  |  ",
      "     ", "     ", "     ")));
    fTab.add(new ArrayList<>(Arrays.asList("  G3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ", "     ")));
    fTab.add(new ArrayList<>(Arrays.asList(" G#3 ", "     ", "     ", "  X  ", "  |  ", "     ",
      "     ", "     ", "     ")));
    fTab.add(new ArrayList<>(Arrays.asList("  A3 ", "     ", "     ", "     ", "  X  ", "  |  ",
      "  X  ", "  |  ", "  |  ")));
    fTab.add(new ArrayList<>(Arrays.asList(" A#3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ", "     ")));
    fTab.add(new ArrayList<>(Arrays.asList("  B3 ", "     ", "     ", "     ", "     ", "     ",
      "     ", "     ", "     ")));
    assertEquals(fTab, o.getOctaveTable(3, 5));
  }

  @Test
  public void overlayConflictsNotEqualsBothWays() {
    Octave o = new Octave();
    o.addNote(Pitch.A, 3, 4);
    o.addNote(Pitch.E, 0, 3);
    o.addNote(Pitch.GSHARP, 2, 2);
    o.addNote(Pitch.C, 1, 1);

    Octave o2 = new Octave();
    o2.addNote(Pitch.A, 5, 3);
    o2.addNote(Pitch.CSHARP, 2, 3);
    o2.addNote(Pitch.FSHARP, 3, 2);
    o2.addNote(Pitch.GSHARP, 2, 8);

    Octave o2Overlay = new Octave(o);
    o2Overlay.overlay(o2);
    Octave oOverlay = new Octave(o2);
    oOverlay.overlay(o);
    assertFalse(oOverlay.equals(o2Overlay));
  }
}
