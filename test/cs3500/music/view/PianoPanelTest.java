package cs3500.music.view;

import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.util.MidiConversion;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

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

  @Test
  public void testGetPitchMinPitch() {
    init();
    Posn min_note_position = new Posn(15,9);
    assertEquals(MidiConversion.getPitch(p.getPitch(min_note_position.getX(), min_note_position.getY())), Pitch.C);
  }

  @Test
  public void testGetPitchSharpPitch() {
    init();
    Posn sharp_note_position = new Posn(490,34);
    assertEquals(MidiConversion.getPitch(p.getPitch(sharp_note_position.getX(), sharp_note_position.getY())), Pitch.FSHARP);
  }

  @Test
  public void testGetPitchMaxPitch() {
    init();
    Posn max_note_position = new Posn(1068,63);
    assertEquals(MidiConversion.getPitch(p.getPitch(max_note_position.getX(), max_note_position.getY())), Pitch.B);
  }
}
