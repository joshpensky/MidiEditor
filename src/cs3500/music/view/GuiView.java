package cs3500.music.view;

import cs3500.music.controller.MusicEditorController;
import cs3500.music.model.MusicEditorOperations;
import cs3500.music.util.MidiConversion;

import java.awt.event.*;
import java.util.Map;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.util.TreeMap;

/**
 * Represents the frame for the GUI view. Holds the {@link GuiContainer} to display both the
 * editor and piano view of the currently opened piece in the model.
 */
public class GuiView extends JFrame implements MusicEditorView {
  private static final int WIDTH = 1100;
  private final GuiContainer container;
  private Map<Integer, Runnable> runs;

  /**
   * Constructs a new {@code GuiView} using the given model to display notes in the
   * different views contained in the window.
   *
   * @param model the model to be represented in the view
   * @throws IllegalArgumentException given model is uninitialized
   */
  protected GuiView(MusicEditorOperations model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Given model is uninitialized.");
    }
    this.container = new GuiContainer(model, WIDTH);
    this.getContentPane().add(container);
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.pack();
    this.setPreferredSize(new Dimension(WIDTH, this.container.getHeight()));
    this.setKeyEvents();
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void initialize() {
    this.setVisible(true);
    this.setResizable(false);
  }

  @Override
  public String getLog() {
    return this.container.getLog();
  }

  protected void updateCursor(boolean forward) {
    this.container.updatePosition(forward);
  }

  private void setKeyEvents() {
    this.runs = new TreeMap<>();
    this.runs.put(KeyEvent.VK_RIGHT, () -> {
      container.updatePosition(true);
    });
    this.runs.put(KeyEvent.VK_LEFT, () -> {
      container.updatePosition(false);
    });
    this.runs.put(KeyEvent.VK_HOME, () -> {
      container.jumpToBeginning();
    });
    this.runs.put(KeyEvent.VK_END, () -> {
      container.jumpToEnd();
    });
  }

  @Override
  public Map<Integer, Runnable> getKeyEvents() {
    return this.runs;
  }

  @Override
  public void setListeners(MusicEditorController controls, KeyListener keys) {
    this.addKeyListener(keys);
    MouseListener m = new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        Integer[] note = container.getNote(e);
        if (note != null) {
          controls.addNote(note[MidiConversion.NOTE_START], note[MidiConversion.NOTE_END],
              note[MidiConversion.NOTE_INSTRUMENT], note[MidiConversion.NOTE_PITCH],
              note[MidiConversion.NOTE_VOLUME]);
          update();
        }
      }
    };
    this.addMouseListener(m);
  }

  @Override
  public void update() {
    this.container.updatePanels();
    this.container.scrollToggle(false);
    this.container.updatePosition(true);
    this.container.scrollToggle(true);
  }

  protected int getCursorPosition() {
    return this.container.getCursorPosition();
  }
}