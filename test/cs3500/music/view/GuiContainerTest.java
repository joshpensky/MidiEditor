package cs3500.music.view;

import cs3500.music.model.MusicEditorBuilder;
import cs3500.music.model.MusicEditorOperations;
import cs3500.music.model.Pitch;
import cs3500.music.util.MidiConversion;
import org.junit.Test;

import java.awt.*;
import java.awt.event.MouseEvent;

import static org.junit.Assert.assertArrayEquals;
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

  // Tests for the getNote method
  @Test(expected = IllegalArgumentException.class)
  public void getNoteNullMouseEvent() {
    init();
    gc.getNote(null);
  }

  @Test
  public void getNoteOffsetCursorPosition() {
    init();
    model.addNote(0, 30, 1, 24, 64);
    gc = new GuiContainer(model, 1100);
    gc.setPreferredSize(new Dimension(1100, 750));
    gc.updatePosition(true);
    gc.updatePosition(true);
    gc.updatePosition(true);
    MouseEvent me = new MouseEvent(gc, MouseEvent.MOUSE_PRESSED, 1, 1, 29, 109, 1, false);
    Integer[] note = {3, 4, 1, MidiConversion.getMidiPitch(1, Pitch.C), 64};
    assertArrayEquals(note, gc.getNote(me));
  }

  @Test
  public void getNoteEndOfPiece() {
    init();
    model.addNote(0, 30, 1, 24, 64);
    gc = new GuiContainer(model, 1100);
    gc.setPreferredSize(new Dimension(1100, 750));
    gc.jumpToEnd();
    MouseEvent me = new MouseEvent(gc, MouseEvent.MOUSE_PRESSED, 1, 1, 29, 109, 1, false);
    Integer[] note = {30, 31, 1, MidiConversion.getMidiPitch(1, Pitch.C), 64};
    assertArrayEquals(note, gc.getNote(me));
  }

  @Test
  public void getNoteC1() {
    init();
    gc = new GuiContainer(model, 1100);
    gc.setPreferredSize(new Dimension(1100, 750));
    MouseEvent me = new MouseEvent(gc, MouseEvent.MOUSE_PRESSED, 1, 1, 29, 109, 1, false);
    Integer[] note = {0, 1, 1, MidiConversion.getMidiPitch(1, Pitch.C), 64};
    assertArrayEquals(note, gc.getNote(me));
  }

  @Test
  public void getNoteFSharp5() {
    init();
    gc = new GuiContainer(model, 1100);
    gc.setPreferredSize(new Dimension(1100, 750));
    MouseEvent me = new MouseEvent(gc, MouseEvent.MOUSE_PRESSED, 1, 1, 504, 34, 1, false);
    Integer[] note = {0, 1, 1, MidiConversion.getMidiPitch(5, Pitch.FSHARP), 64};
    assertArrayEquals(note, gc.getNote(me));
  }

  @Test
  public void getNoteB10() {
    init();
    gc = new GuiContainer(model, 1100);
    gc.setPreferredSize(new Dimension(1100, 750));
    MouseEvent me = new MouseEvent(gc, MouseEvent.MOUSE_PRESSED, 1, 1, 1068, 63, 1, false);
    Integer[] note = {0, 1, 1, MidiConversion.getMidiPitch(10, Pitch.B), 64};
    assertArrayEquals(note, gc.getNote(me));
  }

  // Tests for the jumpToBeginning method
  @Test
  public void jumpToBeginningValid() {
    init();
    model.addNote(0, 30, 1, 24, 64);
    gc = new GuiContainer(model, 1100);
    assertEquals(0, gc.getCursorPosition());
    for (int i = 0; i < 5; i++) {
      gc.updatePosition(true);
    }
    assertEquals(5, gc.getCursorPosition());
    gc.jumpToBeginning();
    assertEquals(0, gc.getCursorPosition());
  }

  @Test
  public void jumpToBeginningFromBeginningNoChange() {
    init();
    model.addNote(0, 30, 1, 24, 64);
    gc = new GuiContainer(model, 1100);
    assertEquals(0, gc.getCursorPosition());
    gc.jumpToBeginning();
    assertEquals(0, gc.getCursorPosition());
  }

  // Tests for the jumpToEnd method
  @Test
  public void jumpToEndValid() {
    init();
    model.addNote(0, 30, 1, 24, 64);
    gc = new GuiContainer(model, 1100);
    assertEquals(0, gc.getCursorPosition());
    gc.jumpToEnd();
    assertEquals(30, gc.getCursorPosition());
  }

  @Test
  public void jumpToEndFromEndNoChange() {
    init();
    model.addNote(0, 30, 1, 24, 64);
    gc = new GuiContainer(model, 1100);
    assertEquals(0, gc.getCursorPosition());
    gc.jumpToEnd();
    assertEquals(30, gc.getCursorPosition());
    gc.jumpToEnd();
    assertEquals(30, gc.getCursorPosition());
  }

  // Tests for the updatePosition method
  @Test
  public void updatePositionNoNotes() {
    init();
    gc.updatePosition(true);
    assertEquals(0, gc.getCursorPosition());
    gc.updatePosition(false);
    assertEquals(0, gc.getCursorPosition());
  }

  @Test
  public void updatePositionBackwardsAtBeginning() {
    init();
    model.addNote(0, 30, 1, 24, 64);
    gc = new GuiContainer(model, 1100);
    assertEquals(0, gc.getCursorPosition());
    gc.updatePosition(false);
    assertEquals(0, gc.getCursorPosition());
  }

  @Test
  public void updatePositionForwardsAtEnd() {
    init();
    model.addNote(0, 30, 1, 24, 64);
    gc = new GuiContainer(model, 1100);
    assertEquals(0, gc.getCursorPosition());
    gc.jumpToEnd();
    assertEquals(30, gc.getCursorPosition());
    gc.updatePosition(true);
    assertEquals(30, gc.getCursorPosition());
  }

  @Test
  public void updatePositionForwards() {
    init();
    model.addNote(0, 30, 1, 24, 64);
    gc = new GuiContainer(model, 1100);
    assertEquals(0, gc.getCursorPosition());
    for (int i = 0; i < 5; i++) {
      gc.updatePosition(true);
    }
    assertEquals(5, gc.getCursorPosition());
  }

  @Test
  public void updatePositionBackwards() {
    init();
    model.addNote(0, 30, 1, 24, 64);
    gc = new GuiContainer(model, 1100);
    gc.jumpToEnd();
    assertEquals(30, gc.getCursorPosition());
    for (int i = 0; i < 5; i++) {
      gc.updatePosition(false);
    }
    assertEquals(25, gc.getCursorPosition());
  }

  // Tests for the getCursorPosition method
  @Test
  public void getCursorPositionAtBeginning() {
    init();
    model.addNote(0, 30, 1, 24, 64);
    gc = new GuiContainer(model, 1100);
    assertEquals(0, gc.getCursorPosition());
  }

  @Test
  public void getCursorPositionAtEnd() {
    init();
    model.addNote(0, 30, 1, 24, 64);
    gc = new GuiContainer(model, 1100);
    gc.jumpToEnd();
    assertEquals(30, gc.getCursorPosition());
  }

  @Test
  public void getCursorPositionAfterMove() {
    init();
    model.addNote(0, 30, 1, 24, 64);
    gc = new GuiContainer(model, 1100);
    for (int i = 0; i < 5; i++) {
      gc.updatePosition(true);
    }
    assertEquals(5, gc.getCursorPosition());
  }
}
