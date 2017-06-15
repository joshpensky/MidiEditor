package cs3500.music.view;

import cs3500.music.model.EditorOperations;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Dimension;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends JFrame implements ViewInterface {
  private static final int WIDTH = 1100;

  private final JPanel container;
  private final EditorOperations model;

  /**
   * Creates new GuiView
   */
  public GuiViewFrame(EditorOperations model) {
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

  protected EditorOperations getModel() {
    return model;
  }
}

