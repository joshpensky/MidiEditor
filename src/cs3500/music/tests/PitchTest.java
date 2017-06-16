package cs3500.music.tests;

import cs3500.music.model.Pitch;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link Pitch} enumeration.
 */
public class PitchTest {
  // Tests for the toString method
  @Test
  public void toStringC() {
    assertEquals("C", Pitch.C.toString());
  }

  @Test
  public void toStringCSharp() {
    assertEquals("C#", Pitch.CSHARP.toString());
  }

  @Test
  public void toStringD() {
    assertEquals("D", Pitch.D.toString());
  }

  @Test
  public void toStringDSharp() {
    assertEquals("D#", Pitch.DSHARP.toString());
  }

  @Test
  public void toStringE() {
    assertEquals("E", Pitch.E.toString());
  }

  @Test
  public void toStringF() {
    assertEquals("F", Pitch.F.toString());
  }

  @Test
  public void toStringFSharp() {
    assertEquals("F#", Pitch.FSHARP.toString());
  }

  @Test
  public void toStringG() {
    assertEquals("G", Pitch.G.toString());
  }

  @Test
  public void toStringGSharp() {
    assertEquals("G#", Pitch.GSHARP.toString());
  }

  @Test
  public void toStringA() {
    assertEquals("A", Pitch.A.toString());
  }

  @Test
  public void toStringASharp() {
    assertEquals("A#", Pitch.ASHARP.toString());
  }

  @Test
  public void toStringB() {
    assertEquals("B", Pitch.B.toString());
  }
}
