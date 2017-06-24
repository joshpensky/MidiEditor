package cs3500.music.view;

import cs3500.music.model.MusicEditorBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@code GuiView} class.
 */
public class GuiViewTest {
  private GuiView view = new GuiView(new MusicEditorBuilder().addNote(0, 30, 1, 24, 64).build());

  // Tests for the getCursorPosition method
  @Test
  public void getCursorPositionAtBeginning() {
    assertEquals(0, view.getCursorPosition());
  }

  @Test
  public void getCursorPositionAtEnd() {
    for (int i = 0; i < 30; i++) {
      view.updateCursor(true);
    }
    assertEquals(30, view.getCursorPosition());
  }

  @Test
  public void getCursorPositionAfterChange() {
    for (int i = 0; i < 5; i++) {
      view.updateCursor(true);
    }
    assertEquals(5, view.getCursorPosition());
  }

  // Tests for the updateCursor method
  @Test
  public void updateCursorBackwardAtBeginning() {
    assertEquals(0, view.getCursorPosition());
    view.updateCursor(false);
    assertEquals(0, view.getCursorPosition());
  }

  @Test
  public void updateCursorForwardAtEnd() {
    for (int i = 0; i < 30; i++) {
      view.updateCursor(true);
    }
    assertEquals(30, view.getCursorPosition());
    view.updateCursor(true);
    assertEquals(30, view.getCursorPosition());
  }

  @Test
  public void updateCursorForward() {
    assertEquals(0, view.getCursorPosition());
    for (int i = 0; i < 5; i++) {
      view.updateCursor(true);
    }
    assertEquals(5, view.getCursorPosition());
  }

  @Test
  public void updateCursorBackward() {
    for (int i = 0; i < 30; i++) {
      view.updateCursor(true);
    }
    assertEquals(30, view.getCursorPosition());
    for (int i = 0; i < 5; i++) {
      view.updateCursor(false);
    }
    assertEquals(25, view.getCursorPosition());
  }
}
