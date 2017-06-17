package cs3500.music.view;

import cs3500.music.model.MusicEditorBuilder;
import cs3500.music.model.MusicEditorOperations;
import org.junit.Test;

import java.awt.Dimension;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link GuiContainer} class.
 */
public class EditorPanelTest {
  private EditorPanel ep;
  private final MusicEditorBuilder m1 = new MusicEditorBuilder();
  private MusicEditorOperations model;

  void init() {
    model = m1.build();
    model.create();
    model.addNote(0, 30, 1, 60, 100);
    ep = new EditorPanel(model, 20, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNullModel() {
    this.ep = new EditorPanel(null, 20, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorZeroWidth() {
    init();
    this.ep = new EditorPanel(this.model, 0, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNegativeWidth() {
    init();
    this.ep = new EditorPanel(this.model, -1, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorZeroHeight() {
    init();
    this.ep = new EditorPanel(this.model, 20, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNegativeHeight() {
    init();
    this.ep = new EditorPanel(this.model, 20, -1);
  }

  @Test
  public void constructorValid() {
    init();
    this.ep = new EditorPanel(this.model, 20, 20);
    assertEquals(new Dimension(20, 55), this.ep.getPreferredSize());
  }

  @Test
  public void testCursorForwards() {
    init();
    assertEquals(ep.updateCursor(true), 1);
  }

  @Test
  public void testCursorBackwards() {
    init();
    ep.updateCursor(true);
    ep.updateCursor(true);
    ep.updateCursor(true);
    assertEquals(ep.updateCursor(false), 2);
  }

  @Test
  public void testCursorBackAndForwardsEquality() {
    init();
    MusicEditorBuilder temp = new MusicEditorBuilder();
    MusicEditorOperations m2 = temp.build();
    m2.create();
    m2.addNote(0, 30, 1, 60, 100);
    EditorPanel other = new EditorPanel(m2, 20, 20);
    ep.updateCursor(true);
    other.updateCursor(true);
    ep.updateCursor(false);
    other.updateCursor(true);
    assertEquals(ep.updateCursor(true), other.updateCursor(false));
  }
}
