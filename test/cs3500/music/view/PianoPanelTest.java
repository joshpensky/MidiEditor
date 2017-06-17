package cs3500.music.view;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for the {@link PianoPanel} class.
 */
public class PianoPanelTest {
  PianoPanel p;
  List<Integer[]> loai;

  void init() {
    loai = new ArrayList<>();
    loai.add(new Integer[]{0, 1, 1, 60, 100});
    p = new PianoPanel(loai, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructNullList() {
    p = new PianoPanel(null, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructBadWidth() {
    p = new PianoPanel(loai, 0);
  }
}
