package cs3500.music.view;

import cs3500.music.model.EditorOperations;

import java.awt.*;
import java.awt.event.MouseListener; // Possibly of interest for handling mouse events

import javax.swing.*;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends javax.swing.JFrame implements ViewInterface {
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
  }

  @Override
  public void initialize(){
    this.setVisible(true);
    this.setResizable(false);
  }

  @Override
  public Dimension getPreferredSize(){
    return new Dimension(WIDTH, 750);
  }

  protected EditorOperations getModel() {
    return model;
  }
}

