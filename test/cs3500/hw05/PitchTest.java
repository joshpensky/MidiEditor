package cs3500.hw05;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link Pitch} enumeration.
 */
public class PitchTest {
  // Tests for the getId method
  @Test
  public void getIdC() {
    assertEquals(0, Pitch.C.getId());
  }

  @Test
  public void getIdCSharp() {
    assertEquals(1, Pitch.CSHARP.getId());
  }

  @Test
  public void getIdD() {
    assertEquals(2, Pitch.D.getId());
  }

  @Test
  public void getIdDSharp() {
    assertEquals(3, Pitch.DSHARP.getId());
  }

  @Test
  public void getIdE() {
    assertEquals(4, Pitch.E.getId());
  }

  @Test
  public void getIdF() {
    assertEquals(5, Pitch.F.getId());
  }

  @Test
  public void getIdFSharp() {
    assertEquals(6, Pitch.FSHARP.getId());
  }

  @Test
  public void getIdG() {
    assertEquals(7, Pitch.G.getId());
  }

  @Test
  public void getIdGSharp() {
    assertEquals(8, Pitch.GSHARP.getId());
  }

  @Test
  public void getIdA() {
    assertEquals(9, Pitch.A.getId());
  }

  @Test
  public void getIdASharp() {
    assertEquals(10, Pitch.ASHARP.getId());
  }

  @Test
  public void getIdB() {
    assertEquals(11, Pitch.B.getId());
  }

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
