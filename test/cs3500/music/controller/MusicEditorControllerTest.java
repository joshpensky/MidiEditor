package cs3500.music.controller;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Will on 6/23/2017.
 */
public class MusicEditorControllerTest {
  MusicEditorController controller;

  private void init() {
    controller = MusicEditorController.initialize();
  }

  private int cursorPos(String s) {
    int n1 = 0;
    int n2 = 0;
    String place = "";
    n2 = s.lastIndexOf("</EP>");
    n1 = s.lastIndexOf(":- ");
    s = s.substring(n1+3, n2 - 1);
    return Integer.parseInt(s);
  }

  @Test
  public void testSingletonPatternForController() {
    init();
    assertEquals(MusicEditorController.initialize(), controller);
  }

  @Test
  public void testCursorPosHelper() {
    String s = "erdctfyuhnijmo<EP>stuff:- 42 </EP>\n";

    assertEquals(this.cursorPos(s), 42);
  }


}
