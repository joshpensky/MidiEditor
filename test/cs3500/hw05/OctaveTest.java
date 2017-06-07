package cs3500.hw05;

import org.junit.Test;

/**
 * Created by josh_jpeg on 6/7/17.
 */
public class OctaveTest {
  @Test
  public void toStringTest() {
    Octave o = new Octave();
    o.addNote(Pitch.C, 2, 1);
    o.addNote(Pitch.CSHARP, 0, 3);
    o.addNote(Pitch.CSHARP, 2, 1);
    o.addNote(Pitch.DSHARP, 1, 3);
    o.addNote(Pitch.E, 0, 3);
    //System.out.print(o.stringBuilder(1, 5));
  }
}
