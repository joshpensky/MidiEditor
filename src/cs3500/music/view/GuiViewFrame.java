package cs3500.music.view;

import cs3500.music.model.MusicEditorOperations;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Dimension;

/**
 * Represents the frame for the GUI view. Holds the {@link GuiContainer} to display both the
 * editor and piano view of the currently opened piece in the model.
 */
public class GuiViewFrame extends JFrame implements MusicEditorView {
  private static final int WIDTH = 1200;

  private final JPanel container;
  private final MusicEditorOperations model;

  /**
   * Creates new GuiView
   */
  public GuiViewFrame(MusicEditorOperations model) {
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

  protected MusicEditorOperations getModel() {
    return model;
  }
}

