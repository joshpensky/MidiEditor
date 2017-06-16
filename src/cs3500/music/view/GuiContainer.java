package cs3500.music.view;

import cs3500.music.model.MusicEditorOperations;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Represents the main container panel in the {@link GuiViewFrame}. This panel contains the
 * editor panel (encapsulated in a scroll pane), as well as the piano panel.
 */
public class GuiContainer extends JPanel {
  private final PianoPanel pianoPanel; // You may want to refine this to a subtype of JPanel
  private final EditorPanel editorPanel;
  private final MusicEditorOperations model;

  protected GuiContainer(MusicEditorOperations model, int width) {
    if (model == null) {
      throw new IllegalArgumentException("Given model is uninitialized.");
    } else if (width <= 0) {
      throw new IllegalArgumentException("Width cannot be negative or zero.");
    }
    this.model = model;
    this.setLayout(new BorderLayout(0, 0));
    int contHeight = 500;
    // Adds the editor panel to a scroll pane, then adds the scroll pane
    this.editorPanel = new EditorPanel(this.model, width, contHeight);
    JScrollPane editorContainer = new JScrollPane(this.editorPanel,
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    editorContainer.setPreferredSize(new Dimension(width, contHeight));
    this.add(editorContainer, BorderLayout.NORTH);
    // Adds the piano panel and starts piano at note 0
    this.pianoPanel = new PianoPanel(this.model.getNotesAtBeat(0), width);
    this.add(this.pianoPanel, BorderLayout.SOUTH);
    // Adds the key listener to the container for moving the cursor
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
      this.pianoPanel.updateHighlights(this.model.getNotesAtBeat(beat));
      repaint();
    }
  }
}
