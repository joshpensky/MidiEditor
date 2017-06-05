package cs3500.hw05;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link PitchType} enumeration.
 */
public class PitchTypeTest {
  // Tests for the getId method
  @Test
  public void getIdC() {
    assertEquals(0, PitchType.C.getId());
  }

  @Test
  public void getIdCSharp() {
    assertEquals(1, PitchType.CSHARP.getId());
  }

  @Test
  public void getIdD() {
    assertEquals(2, PitchType.D.getId());
  }

  @Test
  public void getIdDSharp() {
    assertEquals(3, PitchType.DSHARP.getId());
  }

  @Test
  public void getIdE() {
    assertEquals(4, PitchType.E.getId());
  }

  @Test
  public void getIdF() {
    assertEquals(5, PitchType.F.getId());
  }

  @Test
  public void getIdFSharp() {
    assertEquals(6, PitchType.FSHARP.getId());
  }

  @Test
  public void getIdG() {
    assertEquals(7, PitchType.G.getId());
  }

  @Test
  public void getIdGSharp() {
    assertEquals(8, PitchType.GSHARP.getId());
  }

  @Test
  public void getIdA() {
    assertEquals(9, PitchType.A.getId());
  }

  @Test
  public void getIdASharp() {
    assertEquals(10, PitchType.ASHARP.getId());
  }

  @Test
  public void getIdB() {
    assertEquals(11, PitchType.B.getId());
  }
}
