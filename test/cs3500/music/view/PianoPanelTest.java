package cs3500.music.view;

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
    Posn minNotePosition = new Posn(15,9);
    assertEquals(MidiConversion.getPitch(p.getPitch(minNotePosition.getX(),
        minNotePosition.getY())), Pitch.C);
  }

  @Test
  public void testGetPitchSharpPitch() {
    init();
    Posn sharpNotePosition = new Posn(490,34);
    assertEquals(MidiConversion.getPitch(p.getPitch(sharpNotePosition.getX(),
        sharpNotePosition.getY())), Pitch.FSHARP);
  }

  @Test
  public void testGetPitchMaxPitch() {
    init();
    Posn maxNotePosition = new Posn(1068,63);
    assertEquals(MidiConversion.getPitch(p.getPitch(maxNotePosition.getX(),
        maxNotePosition.getY())), Pitch.B);
  }
}
