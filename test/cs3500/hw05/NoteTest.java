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
  public void constructNegativeDuration() {
    new Note(3, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructZeroDuration() {
    new Note(24, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructNegativePosition() {
    new Note(-1, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructNullNote() {
    new Note(null);
  }

  @Test
  public void constructNew() {
    Note n = new Note(5, 3);
    assertEquals("Duration: 3\nPosition: 5", n.toString());
  }

  @Test
  public void constructDuplicateSameData() {
    Note n = new Note(5, 3);
    Note n2 = new Note(n);
    assertEquals(n2.toString(), n.toString());
  }

  @Test
  public void constructDuplicateDifferentReferences() {
    Note n = new Note(5, 3);
    Note n2 = new Note(n);
    n.setPosition(4);
    assertNotEquals(n2.toString(), n.toString());
  }

  // Tests for the equals method
  @Test
  public void equalsSameObject() {
    Note n = new Note(2, 4);
    assertTrue(n.equals(n));
  }

  @Test
  public void equalsNull() {
    Note n = new Note(2, 4);
    assertFalse(n.equals(null));
  }

  @Test
  public void equalsDifferentObjectType() {
    Note n = new Note(2, 4);
    assertFalse(n.equals(3));
  }

  @Test
  public void equalsDifferentDuration() {
    Note n = new Note(2, 4);
    Note n2 = new Note(2, 3);
    assertFalse(n.equals(n2));
  }

  @Test
  public void equalsDifferentPosition() {
    Note n = new Note(2, 4);
    Note n2 = new Note(5, 4);
    assertFalse(n.equals(n2));
  }

  @Test
  public void equalsSameValues() {
    Note n = new Note(2, 4);
    Note n2 = new Note(2, 4);
    assertTrue(n.equals(n2));
  }

  @Test
  public void equalsDuplicate() {
    Note n = new Note(2, 4);
    Note n2 = new Note(n);
    assertTrue(n.equals(n2));
  }

  // Tests for the hashCode method
  @Test
  public void hashCodeNormal() {
    Note n = new Note(2, 4);
    assertEquals(40002, n.hashCode());
  }

  @Test
  public void hashCodeHighEnd() {
    Note n = new Note(9999, 9999);
    assertEquals(99999999, n.hashCode());
  }

  // Tests for the toString method
  @Test
  public void toStringNoChange() {
    Note n = new Note(23, 15);
    assertEquals("Duration: 15\nPosition: 23", n.toString());
  }

  @Test
  public void toStringChangeDuration() {
    Note n = new Note(23, 15);
    String toString = n.toString();
    n.setDuration(82);
    assertNotEquals(n.toString(), toString);
  }

  @Test
  public void toStringChangePosition() {
    Note n = new Note(23, 15);
    String toString = n.toString();
    n.setPosition(82);
    assertNotEquals(n.toString(), toString);
  }

  // Tests for the setDuration method
  @Test(expected = IllegalArgumentException.class)
  public void setDurationNegative() {
    Note n = new Note(24, 3);
    n.setPosition(-32);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setDurationZero() {
    Note n = new Note(24, 3);
    n.setDuration(0);
  }

  @Test
  public void setDurationValid() {
    Note n = new Note(24, 3);
    n.setDuration(52);
    assertEquals("Duration: 52\nPosition: 24", n.toString());
  }

  // Tests for the setDuration method
  @Test(expected = IllegalArgumentException.class)
  public void setPositionNegative() {
    Note n = new Note(24, 3);
    n.setPosition(-32);
  }

  @Test
  public void setPositionValid() {
    Note n = new Note(24, 3);
    n.setPosition(52);
    assertEquals("Duration: 3\nPosition: 52", n.toString());
  }
}
