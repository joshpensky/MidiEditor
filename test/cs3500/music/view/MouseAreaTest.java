package cs3500.music.view;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

import static org.junit.Assert.assertTrue;

/**
 * Tests for the {@link MouseArea} class.
 */
public class MouseAreaTest {
  // Tests for the constructor
  @Test(expected = IllegalArgumentException.class)
  public void constructorNegativeWidth() {
    new MouseArea(new Posn(0, 0), new Posn(-1, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNegativeHeight() {
    new MouseArea(new Posn(0, 0), new Posn(0, -1));
  }

  @Test
  public void constructorZeroWidth() {
    MouseArea a = new MouseArea(new Posn(0, 0), new Posn(0, 10));
    assertTrue(a.mouseWithinArea(0, 5));
  }

  @Test
  public void constructorZeroHeight() {
    MouseArea a = new MouseArea(new Posn(0, 0), new Posn(10, 0));
    assertTrue(a.mouseWithinArea(5, 0));
  }

  @Test
  public void constructorRegular() {
    MouseArea a = new MouseArea(new Posn(0, 0), new Posn(6, 6));
    assertTrue(a.mouseWithinArea(3, 3));
  }

  // Tests for the mouseWithinArea method
  @Test
  public void mouseWithinAreaMouseTooLow() {
    MouseArea a = new MouseArea(new Posn(0, 0), new Posn(10, 10));
    assertFalse(a.mouseWithinArea(5, -1));
  }

  @Test
  public void mouseWithinAreaMouseTooHigh() {
    MouseArea a = new MouseArea(new Posn(0, 0), new Posn(10, 10));
    assertFalse(a.mouseWithinArea(5, 11));
  }

  @Test
  public void mouseWithinAreaMouseTooLeft() {
    MouseArea a = new MouseArea(new Posn(0, 0), new Posn(10, 10));
    assertFalse(a.mouseWithinArea(-1, 5));
  }

  @Test
  public void mouseWithinAreaMouseTooRight() {
    MouseArea a = new MouseArea(new Posn(0, 0), new Posn(10, 10));
    assertFalse(a.mouseWithinArea(11, 5));
  }

  @Test
  public void mouseWithinAreaZeroWidthHeight() {
    MouseArea a = new MouseArea(new Posn(0, 0), new Posn(0, 0));
    assertTrue(a.mouseWithinArea(0, 0));
    assertFalse(a.mouseWithinArea(1, -1));
    assertFalse(a.mouseWithinArea(-1, -1));
    assertFalse(a.mouseWithinArea(-1, 0));
    assertFalse(a.mouseWithinArea(1, 0));
    assertFalse(a.mouseWithinArea(1, 1));
    assertFalse(a.mouseWithinArea(-1, 1));
  }

  @Test
  public void mouseWithinAreaOnCorner() {
    MouseArea a = new MouseArea(new Posn(0, 0), new Posn(10, 10));
    assertTrue(a.mouseWithinArea(0, 0));
    assertTrue(a.mouseWithinArea(10, 0));
    assertTrue(a.mouseWithinArea(0, 10));
    assertTrue(a.mouseWithinArea(10, 10));
  }

  @Test
  public void mouseWithinAreaOnEdge() {
    MouseArea a = new MouseArea(new Posn(0, 0), new Posn(10, 10));
    assertTrue(a.mouseWithinArea(5, 0));
    assertTrue(a.mouseWithinArea(5, 10));
    assertTrue(a.mouseWithinArea(0, 5));
    assertTrue(a.mouseWithinArea(10, 5));
  }

  @Test
  public void mouseWithinAreaInsideArea() {
    MouseArea a = new MouseArea(new Posn(0, 0), new Posn(10, 10));
    assertTrue(a.mouseWithinArea(1, 1));
    assertTrue(a.mouseWithinArea(9, 1));
    assertTrue(a.mouseWithinArea(1, 9));
    assertTrue(a.mouseWithinArea(9, 9));
    assertTrue(a.mouseWithinArea(5, 5));
  }
}
