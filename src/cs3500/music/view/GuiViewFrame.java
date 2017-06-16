package cs3500.music.view;

import cs3500.music.model.MusicEditorOperations;

import javax.swing.JFrame;

import java.awt.Dimension;

/**
 * Represents the frame for the GUI view. Holds the {@link GuiContainer} to display both the
 * editor and piano view of the currently opened piece in the model.
 */
public class GuiViewFrame extends JFrame implements MusicEditorView {
  private static final int WIDTH = 1100;

  private final GuiContainer container;
  private final MusicEditorOperations model;

  /**
   * Constructs a new {@code GuiViewFrame} using the given model to display notes in the
   * different views contained in the window.
   *
   * @param model   the model to be represented in the view
   */
  protected GuiViewFrame(MusicEditorOperations model) {
    this.model = model;
    this.container = new GuiContainer(this.model, WIDTH);
    this.getContentPane().add(container);
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.pack();
    this.setPreferredSize(new Dimension(WIDTH, this.container.getHeight()));
  }

  @Override
  public void initialize(){
    this.setVisible(true);
    this.setResizable(false);
  }

  @Override
  public String getLog() {
    return this.container.getLog();
  }
}

