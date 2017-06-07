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
  // Tests for the default constructor
  @Test(expected = IllegalArgumentException.class)
  public void defaultConstructorNegativeDuration() {
    new Note(3, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void defaultConstructorZeroDuration() {
    new Note(24, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void defaultConstructorNegativePosition() {
    new Note(-1, 3);
  }

  @Test
  public void defaultConstructorValid() {
    Note n = new Note(5, 3);
  }

  // Tests for the copy constructor
  @Test(expected = IllegalArgumentException.class)
  public void copyConstructorNullNote() {
    new Note(null);
  }

  @Test
  public void copyConstructorSameData() {
    Note n = new Note(5, 3);
    Note n2 = new Note(n);
    assertEquals(n2.toString(), n.toString());
  }

  @Test
  public void copyConstructorDifferentPositionReference() {
    Note n = new Note(5, 3);
    Note n2 = new Note(n);
    n.setPosition(4);
    assertNotEquals(n.getPosition(), n2.getPosition());
  }

  @Test
  public void copyConstructorDifferentDurationReference() {
    Note n = new Note(5, 3);
    Note n2 = new Note(n);
    n.setDuration(4);
    assertNotEquals(n.getEndPoint(), n2.getEndPoint());
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
  public void equalsCopy() {
    Note n = new Note(2, 4);
    Note n2 = new Note(n);
    assertTrue(n.equals(n2));
  }

  // Tests for the hashCode method
  @Test
  public void hashCodeNormal() {
    Note n = new Note(2, 4);
    assertEquals(200004, n.hashCode());
  }

  @Test
  public void hashCodeHighEnd() {
    Note n = new Note(9999, 99999);
    assertEquals(999999999, n.hashCode());
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
    assertEquals(75, n.getEndPoint());
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
    assertEquals(52, n.getPosition());
  }
}
