package cs3500.hw05;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@code OctaveType} enumeration.
 */
public class OctaveTypeTest {
  // Tests for the getValue method
  @Test
  public void getValueOne() {
    assertEquals(1, OctaveType.ONE.getValue());
  }

  @Test
  public void getValueTwo() {
    assertEquals(2, OctaveType.TWO.getValue());
  }

  @Test
  public void getValueThree() {
    assertEquals(3, OctaveType.THREE.getValue());
  }

  @Test
  public void getValueFour() {
    assertEquals(4, OctaveType.FOUR.getValue());
  }

  @Test
  public void getValueFive() {
    assertEquals(5, OctaveType.FIVE.getValue());
  }

  @Test
  public void getValueSix() {
    assertEquals(6, OctaveType.SIX.getValue());
  }

  @Test
  public void getValueSeven() {
    assertEquals(7, OctaveType.SEVEN.getValue());
  }

  @Test
  public void getValueEight() {
    assertEquals(8, OctaveType.EIGHT.getValue());
  }

  @Test
  public void getValueNine() {
    assertEquals(9, OctaveType.NINE.getValue());
  }

  @Test
  public void getValueTen() {
    assertEquals(10, OctaveType.TEN.getValue());
  }
}
