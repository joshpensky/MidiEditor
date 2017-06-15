package cs3500.music.model.josh;

import cs3500.music.model.EditorOperations;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by josh_jpeg on 6/14/17.
 */
public class AddTest {
  EditorOperations op = new EditorModel();

  @Test
  public void addTest() {
    op.create();
    op.addNote(5, 8, 23, 42, 32);
    op.addNote(0, 3, 1, 80, 3);
    List<Integer[]> notes = op.getNotes();
    Integer[] test = {5, 8, 23, 42, 32};
    Integer[] test2 = {0, 3, 1, 80, 3};
    assertEquals(test, notes.get(0));
    assertEquals(test2, notes.get(1));
  }

  @Test
  public void test77() {
    assertEquals(Pitch.F, Utils.getPitch(77));
    assertEquals(5, Utils.getOctave(77));
    assertEquals(77, Utils.getTone(5, Pitch.F));
  }
}
