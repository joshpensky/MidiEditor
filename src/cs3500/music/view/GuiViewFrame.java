package cs3500.music.view;

import java.awt.*;
import java.awt.event.MouseListener; // Possibly of interest for handling mouse events

import javax.swing.*;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends javax.swing.JFrame implements ViewInterface {
  private static final int WIDTH = 1100;

  private final JPanel container;
  private final JPanel pianoPanel; // You may want to refine this to a subtype of JPanel
  private final JViewport editorPanel;
  private final JScrollPane editorContainer;

  /**
   * Creates new GuiView
   */
  public GuiViewFrame() {
    this.container = new JPanel(new BorderLayout(0, 0));//FlowLayout(FlowLayout.CENTER, 0, 0));
    this.editorPanel = new EditorPanel();
    this.editorContainer = new JScrollPane(this.editorPanel,
      JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
      JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.editorContainer.setPreferredSize(new Dimension(WIDTH, 500));
    this.pianoPanel = new PianoPanel();
    container.add(editorContainer, BorderLayout.NORTH);
    container.add(pianoPanel, BorderLayout.SOUTH);
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
}
