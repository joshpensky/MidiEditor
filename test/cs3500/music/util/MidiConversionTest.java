package cs3500.music.util;

import cs3500.music.model.Pitch;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link MidiConversion} class.
 */
public class MidiConversionTest {
  private final MidiConversion mc = new MidiConversion();

  // Tests for the getPitch method
  @Test(expected = IllegalArgumentException.class)
  public void getPitchTooLow() {
    mc.getPitch(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getPitchTooHigh() {
    mc.getPitch(128);
  }

  @Test
  public void getPitchMiddleC() {
    assertEquals(Pitch.C, mc.getPitch(60));
  }

  @Test
  public void getPitchLowEnd() {
    assertEquals(Pitch.GSHARP, mc.getPitch(32));
  }

  @Test
  public void getPitchHighEnd() {
    assertEquals(Pitch.F, mc.getPitch(125));
  }

  // Tests for the getOctave method
  @Test(expected = IllegalArgumentException.class)
  public void getOctaveTooLow() {
    mc.getOctave(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getOctaveTooHigh() {
    mc.getOctave(128);
  }

  @Test
  public void getOctaveMiddleC() {
    assertEquals(4, mc.getOctave(60));
  }

  @Test
  public void getOctaveLowEnd() {
    assertEquals(2, mc.getOctave(42));
  }

  @Test
  public void getOctaveHighEnd() {
    assertEquals(5, mc.getOctave(72));
  }

  // Tests for the getDuration method
  @Test(expected = IllegalArgumentException.class)
  public void getDurationEndBeforeStart() {
    mc.getDuration(31, 15);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getDurationNegativeStart() {
    mc.getDuration(-2, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getDurationNegativeEnd() {
    mc.getDuration(-4, -2);
  }

  @Test
  public void getDurationSameStartEnd() {
    assertEquals(1, mc.getDuration(3, 3));
  }

  @Test
  public void getDurationValid() {
    assertEquals(mc.getDuration(12, 16), 5);
  }

  // Tests for the getMidiPitch method d
  @Test(expected = IllegalArgumentException.class)
  public void getMidiPitchNullPitch() {
    mc.getMidiPitch(3, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getMidiPitchLowOctave() {
    mc.getMidiPitch(0, Pitch.B);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getMidiPitchHighOctave() {
    mc.getMidiPitch(11, Pitch.C);
  }

  @Test
  public void getMidiPitchMiddleC() {
    assertEquals(60, mc.getMidiPitch(4, Pitch.C));
  }

  @Test
  public void getMidiPitchLowEnd() {
    assertEquals(35, mc.getMidiPitch(1, Pitch.B));
  }

  @Test
  public void getMidiPitchHighEnd() {
    assertEquals(116, mc.getMidiPitch(8, Pitch.GSHARP));
  }

  @Test
  public void getMidiPitchRoundaboutMiddleC() {
    assertEquals(Pitch.C, mc.getPitch(60));
    assertEquals(4, mc.getOctave(60));
    assertEquals(60, mc.getMidiPitch(mc.getOctave(60), mc.getPitch(60)));
  }

  @Test
  public void getMidiPitchRoundabout77() {
    assertEquals(Pitch.F, mc.getPitch(77));
    assertEquals(5, mc.getOctave(77));
    assertEquals(77, mc.getMidiPitch(mc.getOctave(77), mc.getPitch(77)));
  }

  // Tests for the getPitchName method
  @Test(expected = IllegalArgumentException.class)
  public void getPitchNameTooLow() {
    mc.getPitchName(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getPitchNameTooHigh() {
    mc.getPitchName(128);
  }

  @Test
  public void getPitchNameMiddleC() {
    assertEquals("C4", mc.getPitchName(60));
  }

  @Test
  public void getPitchNameLowEnd() {
    assertEquals("A1", mc.getPitchName(33));
  }

  @Test
  public void getPitchNameHighEnd() {
    assertEquals("E9", mc.getPitchName(124));
  }
}
