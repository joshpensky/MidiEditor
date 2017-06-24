package cs3500.music.view;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;

/**
 * Tests for the {@link Posn} class.
 */
public class PosnTest {
  @Test
  public void testConstructor() {
    Posn p = new Posn(12, 12);
    assertTrue(p != null);
    assertEquals(p.getX(), 12);
    assertEquals(p.getY(), 12);
  }

  @Test
  public void testGetX() {
    Posn a = new Posn(0, 0);
    assertEquals(a.getX(), 0);
    Posn b = new Posn(20, 10);
    assertEquals(b.getX(), 20);
    Posn c = new Posn(-20, -10);
    assertEquals(c.getX(), -20);
  }

  @Test
  public void testGetY() {
    Posn a = new Posn(0, 0);
    assertEquals(a.getY(), 0);
    Posn b = new Posn(20, 10);
    assertEquals(b.getY(), 10);
    Posn c = new Posn(-20, -10);
    assertEquals(c.getY(), -10);
  }
}
