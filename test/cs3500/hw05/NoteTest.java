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
  public void constructNullPitchType() {
    new Note(null, 3, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructNegativeDuration() {
    new Note(PitchType.A, -1, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructNegativePosition() {
    new Note(PitchType.A, 3, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructNullNote() {
    new Note(null);
  }

  @Test
  public void constructNew() {
    Note n = new Note(PitchType.ASHARP, 3, 5);
    assertEquals("Pitch: ASHARP\nDuration: 3\nPosition: 5", n.toString());
  }

  @Test
  public void constructDuplicateSameData() {
    Note n = new Note(PitchType.ASHARP, 3, 5);
    Note n2 = new Note(n);
    assertEquals(n2.toString(), n.toString());
  }

  @Test
  public void constructDuplicateDifferentReferences() {
    Note n = new Note(PitchType.ASHARP, 3, 5);
    Note n2 = new Note(n);
    n.setPosition(4);
    assertNotEquals(n2.toString(), n.toString());
  }

  // Tests for the equals method
  @Test
  public void equalsSameObject() {
    Note n = new Note(PitchType.FSHARP, 4, 2);
    assertTrue(n.equals(n));
  }

  @Test
  public void equalsNull() {
    Note n = new Note(PitchType.FSHARP, 4, 2);
    assertFalse(n.equals(null));
  }

  @Test
  public void equalsDifferentObjectType() {
    Note n = new Note(PitchType.FSHARP, 4, 2);
    assertFalse(n.equals(3));
  }

  @Test
  public void equalsDifferentPitchType() {
    Note n = new Note(PitchType.FSHARP, 4, 2);
    Note n2 = new Note(PitchType.F, 4, 2);
    assertFalse(n.equals(n2));
  }

  @Test
  public void equalsDifferentDuration() {
    Note n = new Note(PitchType.FSHARP, 4, 2);
    Note n2 = new Note(PitchType.FSHARP, 3, 2);
    assertFalse(n.equals(n2));
  }

  @Test
  public void equalsDifferentPosition() {
    Note n = new Note(PitchType.FSHARP, 4, 2);
    Note n2 = new Note(PitchType.FSHARP, 4, 5);
    assertFalse(n.equals(n2));
  }

  @Test
  public void equalsSameValues() {
    Note n = new Note(PitchType.FSHARP, 4, 2);
    Note n2 = new Note(PitchType.FSHARP, 4, 2);
    assertTrue(n.equals(n2));
  }

  @Test
  public void equalsDuplicate() {
    Note n = new Note(PitchType.FSHARP, 4, 2);
    Note n2 = new Note(n);
    assertTrue(n.equals(n2));
  }

  // Tests for the hashCode method
  @Test
  public void hashCodeNormal() {
    Note n = new Note(PitchType.FSHARP, 4, 2);
    assertEquals(60040002, n.hashCode());
  }

  @Test
  public void hashCodeHighEnd() {
    Note n = new Note(PitchType.B, 999, 9999);
    assertEquals(119999999, n.hashCode());
  }

  // Tests for the toString method
  @Test
  public void toStringNoChange() {
    Note n = new Note(PitchType.C, 15, 23);
    assertEquals("Pitch: C\nDuration: 15\nPosition: 23", n.toString());
  }

  @Test
  public void toStringChangePitch() {
    Note n = new Note(PitchType.C, 15, 23);
    String toString = n.toString();
    n.setPitch(PitchType.ASHARP);
    assertNotEquals(n.toString(), toString);
  }

  @Test
  public void toStringChangeDuration() {
    Note n = new Note(PitchType.C, 15, 23);
    String toString = n.toString();
    n.setDuration(82);
    assertNotEquals(n.toString(), toString);
  }

  @Test
  public void toStringChangePosition() {
    Note n = new Note(PitchType.C, 15, 23);
    String toString = n.toString();
    n.setPosition(82);
    assertNotEquals(n.toString(), toString);
  }

  // Tests for the setPitch method
  @Test(expected = IllegalArgumentException.class)
  public void setPitchNull() {
    Note n = new Note(PitchType.D, 3, 24);
    n.setPitch(null);
  }

  @Test
  public void setPitchDifferentPitch() {
    Note n = new Note(PitchType.D, 3, 24);
    n.setPitch(PitchType.ASHARP);
    assertEquals("Pitch: ASHARP\nDuration: 3\nPosition: 24", n.toString());
  }

  @Test
  public void setPitchSamePitch() {
    Note n = new Note(PitchType.D, 3, 24);
    n.setPitch(PitchType.D);
    assertEquals("Pitch: D\nDuration: 3\nPosition: 24", n.toString());
  }

  // Tests for the setDuration method
  @Test(expected = IllegalArgumentException.class)
  public void setDurationNegative() {
    Note n = new Note(PitchType.D, 3, 24);
    n.setPosition(-32);
  }

  @Test
  public void setDurationDifferentValue() {
    Note n = new Note(PitchType.D, 3, 24);
    n.setDuration(52);
    assertEquals("Pitch: D\nDuration: 52\nPosition: 24", n.toString());
  }

  @Test
  public void setDurationSameValue() {
    Note n = new Note(PitchType.D, 3, 24);
    n.setDuration(3);
    assertEquals("Pitch: D\nDuration: 3\nPosition: 24", n.toString());
  }

  // Tests for the setDuration method
  @Test(expected = IllegalArgumentException.class)
  public void setPositionNegative() {
    Note n = new Note(PitchType.D, 3, 24);
    n.setPosition(-32);
  }

  @Test
  public void setPositionDifferentValue() {
    Note n = new Note(PitchType.D, 3, 24);
    n.setPosition(52);
    assertEquals("Pitch: D\nDuration: 3\nPosition: 52", n.toString());
  }

  @Test
  public void setPositionSameValue() {
    Note n = new Note(PitchType.D, 3, 24);
    n.setPosition(24);
    assertEquals("Pitch: D\nDuration: 3\nPosition: 24", n.toString());
  }
}
