package cs3500.hw05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotEquals;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

/**
 * Tests for the {@link Utils} class.
 */
public class UtilsTest {
  // Tests for the padString method

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
