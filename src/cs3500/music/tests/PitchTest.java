package cs3500.music.tests;

import cs3500.music.model.Pitch;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

  // Tests for the isSharp method
  @Test
  public void isSharpC() {
    assertFalse(Pitch.C.isSharp());
  }

  @Test
  public void isSharpCSharp() {
    assertTrue(Pitch.CSHARP.isSharp());
  }

  @Test
  public void isSharpD() {
    assertFalse(Pitch.D.isSharp());
  }

  @Test
  public void isSharpDSharp() {
    assertTrue(Pitch.DSHARP.isSharp());
  }

  @Test
  public void isSharpE() {
    assertFalse(Pitch.E.isSharp());
  }

  @Test
  public void isSharpF() {
    assertFalse(Pitch.F.isSharp());
  }

  @Test
  public void isSharpFSharp() {
    assertTrue(Pitch.FSHARP.isSharp());
  }

  @Test
  public void isSharpG() {
    assertFalse(Pitch.G.isSharp());
  }

  @Test
  public void isSharpGSharp() {
    assertTrue(Pitch.GSHARP.isSharp());
  }

  @Test
  public void isSharpA() {
    assertFalse(Pitch.A.isSharp());
  }

  @Test
  public void isSharpASharp() {
    assertTrue(Pitch.ASHARP.isSharp());
  }

  @Test
  public void isSharpB() {
    assertFalse(Pitch.B.isSharp());
  }
}
