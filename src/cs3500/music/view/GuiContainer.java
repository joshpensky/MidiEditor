package cs3500.music.view;

import cs3500.music.model.EditorOperations;
import cs3500.music.model.josh.Pitch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by josh_jpeg on 6/15/17.
 */
public class GuiContainer extends JPanel {
  private final PianoPanel pianoPanel; // You may want to refine this to a subtype of JPanel
  private final EditorPanel editorPanel;
  private final JScrollPane editorContainer;
  private final EditorOperations model;

  protected GuiContainer(EditorOperations model) {
    this.setLayout(new BorderLayout(0, 0));
    this.model = model;

    this.editorPanel = new EditorPanel(this.model);
    this.editorContainer = new JScrollPane(this.editorPanel,
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.editorContainer.setPreferredSize(new Dimension(WIDTH, 500));

    this.pianoPanel = new PianoPanel();

    this.add(editorContainer, BorderLayout.NORTH);
    this.add(pianoPanel, BorderLayout.SOUTH);

    this.setFocusable(true);
    this.requestFocusInWindow();

    this.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {

      }

      @Override
      public void keyPressed(KeyEvent e) {
        updateCursor(e);
      }

      @Override
      public void keyReleased(KeyEvent e) {

      }
    });
  }

  private void updateCursor(KeyEvent e) {
    if (e.getKeyCode() == 39 || e.getKeyCode() == 37) {
      this.editorPanel.updateCursor(e.getKeyCode() == 39);
    }
  }
}
