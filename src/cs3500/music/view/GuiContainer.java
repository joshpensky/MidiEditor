package cs3500.music.view;

import cs3500.music.model.EditorOperations;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

  protected GuiContainer(EditorOperations model, int width) {
    this.setLayout(new BorderLayout(0, 0));
    this.model = model;

    int contHeight = 500;
    this.editorPanel = new EditorPanel(this.model, width, contHeight);
    this.editorContainer = new JScrollPane(this.editorPanel,
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    this.editorContainer.setPreferredSize(new Dimension(width, contHeight));

    this.pianoPanel = new PianoPanel(this.model.getNotesAtBeat(0), width);

    this.add(editorContainer, BorderLayout.NORTH);
    this.add(pianoPanel, BorderLayout.SOUTH);

    this.setFocusable(true);
    this.requestFocusInWindow();

    this.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {}

      @Override
      public void keyPressed(KeyEvent e) {
        updatePosition(e);
      }

      @Override
      public void keyReleased(KeyEvent e) {}
    });
  }

  private void updatePosition(KeyEvent e) {
    if (e.getKeyCode() == 39 || e.getKeyCode() == 37) {
      int beat = this.editorPanel.updateCursor(e.getKeyCode() == 39);
      this.pianoPanel.updateNotes(this.model.getNotesAtBeat(beat));
      repaint();
    }
  }
}
