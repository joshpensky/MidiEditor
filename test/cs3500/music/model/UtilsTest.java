package cs3500.music.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link Utils} class.
 */
public class UtilsTest {
  // Tests for the padString method
  @Test(expected = IllegalArgumentException.class)
  public void padStringNullString() {
    Utils.padString(null, 12, Utils.Alignment.CENTER);
  }

  @Test(expected = IllegalArgumentException.class)
  public void padStringNullAlignment() {
    Utils.padString("hey", 12, null);
  }

  @Test
  public void padStringLengthLessThanStringLength() {
    assertEquals("hey", Utils.padString("hey", 2, Utils.Alignment.LEFT));
  }

  @Test
  public void padStringLengthEqualsStringLength() {
    assertEquals("hey", Utils.padString("hey", 3, Utils.Alignment.LEFT));
  }

  @Test
  public void padStringLeftAlignment() {
    assertEquals("hey  ", Utils.padString("hey", 5, Utils.Alignment.LEFT));
  }

  @Test
  public void padStringCenterAlignmentEvenSiding() {
    assertEquals(" hey ", Utils.padString("hey", 5, Utils.Alignment.CENTER));
  }

  @Test
  public void padStringCenterAlignmentOddSiding() {
    assertEquals(" hey", Utils.padString("hey", 4, Utils.Alignment.CENTER));
  }

  @Test
  public void padStringRightAlignment() {
    assertEquals("  hey", Utils.padString("hey", 5, Utils.Alignment.RIGHT));
  }

  // Tests for the reverse method
  @Test(expected = IllegalArgumentException.class)
  public void reverseNull() {
    Utils.reverse(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void reverseContainsNull() {
    Utils.reverse(new ArrayList<>(Arrays.asList("a", null, "b")));
  }

  @Test
  public void reverseEmpty() {
    List<Integer> list = new ArrayList<>();
    assertEquals(list, Utils.reverse(list));
  }

  @Test
  public void reverseOneElem() {
    List<Integer> list = new ArrayList<>(Arrays.asList(1));
    assertEquals(list, Utils.reverse(list));
  }

  @Test
  public void reverseMultElem() {
    List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
    List<Integer> rev = new ArrayList<>(Arrays.asList(3, 2, 1));
    assertEquals(rev, Utils.reverse(list));
  }
}
