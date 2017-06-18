package cs3500.music.view;

import cs3500.music.model.MusicEditorBuilder;
import cs3500.music.model.MusicEditorOperations;
import org.junit.Test;

import java.awt.Dimension;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link GuiContainer} class.
 */
public class GuiContainerTest {
  private GuiContainer gc;
  private final MusicEditorBuilder m1 = new MusicEditorBuilder();
  private MusicEditorOperations model;

  void init() {
    model = m1.build();
    model.create();
    gc = new GuiContainer(model, 20);
  }

  // Tests for the constructor
  @Test(expected = IllegalArgumentException.class)
  public void constructorNullModel() {
    this.gc = new GuiContainer(null, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNegativeWidth() {
    init();
    this.gc = new GuiContainer(this.model, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorZeroWidth() {
    init();
    this.gc = new GuiContainer(this.model, 0);
  }

  @Test
  public void constructorValidInputs() {
    init();
    this.gc = new GuiContainer(this.model, 200);
    assertEquals(new Dimension(200, 730), this.gc.getPreferredSize());
  }

  // Tests for the getLog method
  @Test
  public void getLogTest() {
    init();
    assertEquals(gc.getLog(), "");
  }

}
