package cs3500.hw05;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@code Octave} enumeration.
 */
public class OctaveTest {
  // Tests for the getValue method
  @Test
  public void getValueOne() {
    assertEquals(1, Octave.ONE.getValue());
  }

  @Test
  public void getValueTwo() {
    assertEquals(2, Octave.TWO.getValue());
  }

  @Test
  public void getValueThree() {
    assertEquals(3, Octave.THREE.getValue());
  }

  @Test
  public void getValueFour() {
    assertEquals(4, Octave.FOUR.getValue());
  }

  @Test
  public void getValueFive() {
    assertEquals(5, Octave.FIVE.getValue());
  }

  @Test
  public void getValueSix() {
    assertEquals(6, Octave.SIX.getValue());
  }

  @Test
  public void getValueSeven() {
    assertEquals(7, Octave.SEVEN.getValue());
  }

  @Test
  public void getValueEight() {
    assertEquals(8, Octave.EIGHT.getValue());
  }

  @Test
  public void getValueNine() {
    assertEquals(9, Octave.NINE.getValue());
  }

  @Test
  public void getValueTen() {
    assertEquals(10, Octave.TEN.getValue());
  }
}
