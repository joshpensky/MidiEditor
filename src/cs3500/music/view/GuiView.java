package cs3500.music.view;

import cs3500.music.model.MusicEditorOperations;

import java.awt.event.KeyEvent;
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
   * @param model   the model to be represented in the view
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
    setRunnable();
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
    this.runs.put(39, () -> {container.updatePosition(true);});
    this.runs.put(37, () -> {container.updatePosition(false);});
    this.runs.put(36, () -> {container.goToBegining();});
    this.runs.put(35, () -> {container.goToEnd();});
  }

  @Override
  public void doKeyEvent(KeyEvent e) {
    int code  = e.getKeyCode();
    Runnable r = runs.getOrDefault(code, null);
    if (r != null) {
      r.run();
    }
  }
}

