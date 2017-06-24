package cs3500.music.controller;

import org.junit.Test;

import java.awt.Button;
import java.awt.event.KeyEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the {@code MusicEditorController} class.
 */
public class MusicEditorControllerTest {
  private MusicEditorController controller;
  private Appendable app;
  private KeyEvent left;
  private KeyEvent right;
  private KeyEvent home;
  private KeyEvent end;
  private KeyEvent space;
  private KeyEvent doesNothing;
  private static final int MARY_LAMB_PIECE_SIZE = 64;

  // Initializes the visual view
  private void initVisualView() {
    app = new StringBuilder();
    controller = MusicEditorController.initialize();
    controller.setModelAndView("resources/mary-little-lamb.txt", "visual");
    Button a = new Button("click");

    left = new KeyEvent(a, 2, 20, 1, KeyEvent.VK_LEFT, 'l');
    right = new KeyEvent(a, 2, 20, 1, KeyEvent.VK_RIGHT, 'r');
    home = new KeyEvent(a, 2, 20, 1, KeyEvent.VK_HOME, 'h');
    end = new KeyEvent(a, 2, 20, 1, KeyEvent.VK_END, 'e');
    space = new KeyEvent(a, 2, 20, 1, KeyEvent.VK_SPACE, ' ');
    doesNothing = new KeyEvent(a, 2, 20, 1, KeyEvent.VK_3, '3');
  }

  // Initializes the composite view
  private void initCompositeView() {
    Button a = new Button("click");

    left = new KeyEvent(a, 2, 20, 1, KeyEvent.VK_LEFT, 'l');
    right = new KeyEvent(a, 2, 20, 1, KeyEvent.VK_RIGHT, 'r');
    home = new KeyEvent(a, 2, 20, 1, KeyEvent.VK_HOME, 'h');
    end = new KeyEvent(a, 2, 20, 1, KeyEvent.VK_END, 'e');
    space = new KeyEvent(a, 2, 20, 1, KeyEvent.VK_SPACE, ' ');
    doesNothing = new KeyEvent(a, 2, 20, 1, KeyEvent.VK_3, '3');

    app = new StringBuilder();
    controller = MusicEditorController.initialize();
    controller.setModelAndView("resources/mary-little-lamb.txt", "composite");
    try {
      Thread.sleep(4000);
    }
    catch (InterruptedException e) {
      e.printStackTrace();
    }
    controller.keyPressed(space);
  }

  private int cursorPos(String s) {
    int n2 = s.lastIndexOf("</EP>");
    int n1 = s.lastIndexOf(":- ");
    s = s.substring(n1 + 3, n2 - 1);
    return Integer.parseInt(s);
  }

  @Test
  public void testCursorPosHelper() {
    String s = "erdctfyuhnijmo<EP>stuff:- 42 </EP>\n";
    assertEquals(this.cursorPos(s), 42);
  }

  @Test
  public void testSingletonPatternForController() {
    initVisualView();
    assertEquals(MusicEditorController.initialize(), controller);
  }

  @Test
  public void testKeyPressNothingShouldHappen() {
    initVisualView();

    String before = controller.getLog();
    controller.keyPressed(doesNothing);
    assertEquals(before, controller.getLog());
  }

  @Test
  public void testEndWorks() {
    initVisualView();
    controller.keyPressed(end);
    assertEquals(MARY_LAMB_PIECE_SIZE, cursorPos(controller.getLog()));
  }

  @Test
  public void testHomeWorks() {
    initVisualView();
    controller.keyPressed(right);
    controller.keyPressed(right);
    assertEquals(2, cursorPos(controller.getLog()));
    controller.keyPressed(home);
    assertEquals(0, cursorPos(controller.getLog()));
  }

  @Test
  public void testRightWorks() {
    initVisualView();
    controller.keyPressed(right);
    assertEquals(1, cursorPos(controller.getLog()));
    controller.keyPressed(right);
    assertEquals(2, cursorPos(controller.getLog()));
    controller.keyPressed(right);
    assertEquals(3, cursorPos(controller.getLog()));
    controller.keyPressed(right);
    assertEquals(4, cursorPos(controller.getLog()));
  }

  @Test
  public void testLeftWorks() {
    initVisualView();
    controller.keyPressed(end);
    assertEquals(MARY_LAMB_PIECE_SIZE, cursorPos(controller.getLog()));
    controller.keyPressed(left);
    assertEquals(MARY_LAMB_PIECE_SIZE - 1, cursorPos(controller.getLog()));
    controller.keyPressed(left);
    assertEquals(MARY_LAMB_PIECE_SIZE - 2, cursorPos(controller.getLog()));
    controller.keyPressed(left);
    assertEquals(MARY_LAMB_PIECE_SIZE - 3, cursorPos(controller.getLog()));
    controller.keyPressed(left);
    assertEquals(MARY_LAMB_PIECE_SIZE - 4, cursorPos(controller.getLog()));
  }


  @Test
  public void testEndIsTheEnd() {
    initVisualView();
    controller.keyPressed(end);
    assertEquals(MARY_LAMB_PIECE_SIZE, cursorPos(controller.getLog()));
    controller.keyPressed(right);
    assertEquals(MARY_LAMB_PIECE_SIZE, cursorPos(controller.getLog()));
    controller.keyPressed(left);
    assertEquals(MARY_LAMB_PIECE_SIZE - 1, cursorPos(controller.getLog()));
  }

  @Test
  public void testBeginingIsTheBegining() {
    initVisualView();
    controller.keyPressed(right);
    assertEquals(1, cursorPos(controller.getLog()));
    controller.keyPressed(left);
    assertEquals(0, cursorPos(controller.getLog()));
    controller.keyPressed(left);
    assertEquals(0, cursorPos(controller.getLog()));
  }

  @Test
  public void testCannotActDuringCompositeViewPlaying() {
    initVisualView();
    controller = MusicEditorController.initialize();
    controller.setModelAndView("build/mary-little-lamb.txt", "composite");
    controller.keyPressed(right);
    assertEquals(controller.getLog().length(), 0);
    controller.keyPressed(end);
    assertEquals(controller.getLog().length(), 0);
    controller.keyPressed(left);
    assertEquals(controller.getLog().length(), 0);
  }

  @Test
  public void testCompPausedLeft() {
    initCompositeView();
    controller.keyPressed(end);
    assertEquals(MARY_LAMB_PIECE_SIZE, cursorPos(controller.getLog()));
    controller.keyPressed(left);
    assertEquals(MARY_LAMB_PIECE_SIZE - 1, cursorPos(controller.getLog()));
    controller.keyPressed(left);
    assertEquals(MARY_LAMB_PIECE_SIZE - 2, cursorPos(controller.getLog()));
    controller.keyPressed(left);
    assertEquals(MARY_LAMB_PIECE_SIZE - 3, cursorPos(controller.getLog()));
    controller.keyPressed(left);
    assertEquals(MARY_LAMB_PIECE_SIZE - 4, cursorPos(controller.getLog()));
  }


  @Test
  public void testCompPausedRight() {
    initCompositeView();
    controller.keyPressed(home);
    controller.keyPressed(right);
    controller.keyPressed(home);
    controller.keyPressed(right);
    assertEquals(1, cursorPos(controller.getLog()));
    controller.keyPressed(right);
    assertEquals(2, cursorPos(controller.getLog()));
    controller.keyPressed(right);
    assertEquals(3, cursorPos(controller.getLog()));
    controller.keyPressed(right);
    assertEquals(4, cursorPos(controller.getLog()));
  }

  @Test
  public void testKeyPressNothingShouldHappenInPausedComp() {
    initCompositeView();
    int before = cursorPos(controller.getLog());
    controller.keyPressed(doesNothing);
    assertEquals(before, cursorPos(controller.getLog()));
  }

  @Test
  public void testCompPausedEndWorks() {
    initCompositeView();
    controller.keyPressed(end);
    assertEquals(MARY_LAMB_PIECE_SIZE, cursorPos(controller.getLog()));
  }

  @Test
  public void testCompPausedHomeWorks() {
    initCompositeView();
    controller.keyPressed(home);
    controller.keyPressed(right);
    controller.keyPressed(right);
    assertEquals(2, cursorPos(controller.getLog()));
    controller.keyPressed(home);
    assertEquals(0, cursorPos(controller.getLog()));
  }


  @Test
  public void testCompPausedEndIsTheEnd() {
    initCompositeView();
    controller.keyPressed(end);
    assertEquals(MARY_LAMB_PIECE_SIZE, cursorPos(controller.getLog()));
    controller.keyPressed(right);
    assertEquals(MARY_LAMB_PIECE_SIZE, cursorPos(controller.getLog()));
    controller.keyPressed(left);
    assertEquals(MARY_LAMB_PIECE_SIZE - 1, cursorPos(controller.getLog()));
  }

  @Test
  public void testCompPausedBeginingIsTheBegining() {
    initCompositeView();
    controller.keyPressed(home);
    controller.keyPressed(right);
    controller.keyPressed(home);
    assertEquals(0, cursorPos(controller.getLog()));
    controller.keyPressed(left);
    assertEquals(0, cursorPos(controller.getLog()));
    controller.keyPressed(left);
    assertEquals(0, cursorPos(controller.getLog()));
  }

  @Test
  public void testCompPausedThenPlayedAtDiffLocations() {
    initCompositeView();
    int place1 = cursorPos(controller.getLog());
    controller.keyPressed(space);

    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    //pauses the view again
    controller.keyPressed(space);
    // moves the cursor forwards 1 and back 1 to log the current position correctly.
    // does not modify end result 1-1=0
    controller.keyPressed(right);
    controller.keyPressed(left);
    assertTrue(place1 < cursorPos(controller.getLog()));
  }
}
