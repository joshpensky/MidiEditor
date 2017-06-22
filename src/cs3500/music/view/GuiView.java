package cs3500.music.view;

import cs3500.music.controller.MusicEditorController;
import cs3500.music.model.MusicEditorOperations;
import cs3500.music.util.MidiConversion;

import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
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
    this.setRunnable();
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
//  @Override
//  public AMEKeyListener getKeyListener() {
//    return this.runs;
//  }
//
//  @Override
//  public AMEMouseListener getMouseListener() {
//    return null;
//  }

  private void setRunnable() {
    this.runs = new TreeMap<>();
    this.runs.put(39, () -> {
      container.updatePosition(true);
    });
    this.runs.put(37, () -> {
      container.updatePosition(false);
    });
    this.runs.put(36, () -> {
      container.jumpToBeginning();
    });
    this.runs.put(35, () -> {
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
    MouseListener m = new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        //stub
      }

      @Override
      public void mousePressed(MouseEvent e) {
        Integer[] note = container.getNote(e);
        if (note != null) {
          controls.addNote(note[MidiConversion.NOTE_START], note[MidiConversion.NOTE_END],
            note[MidiConversion.NOTE_INSTRUMENT], note[MidiConversion.NOTE_PITCH],
            note[MidiConversion.NOTE_VOLUME]);
          container.updatePosition(true);
          container.updatePanels();
        }
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        //stub
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        //stub
      }

      @Override
      public void mouseExited(MouseEvent e) {
        //stub
      }
    };
    this.addMouseListener(m);
  }

  @Override
  public void update(List<Integer[]> notes, int tempo, int length) {

  }

  protected GuiContainer getContainer() {
    return this.container;
  }

  protected int getCursorPosition() {
    return this.container.getCursorPosition();
  }
}