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
  private final StringBuilder log;
  private final PianoPanel pianoPanel;
  private final EditorPanel editorPanel;
  private final MusicEditorOperations model;

  /**
   * Constructs a new {@code GuiContainer} using the given model. Sets the width of the container
   * to the given width.
   *
   * @param model   the model be represented in the panels held in the container
   * @param width   the preferred width of the container
   * @throws IllegalArgumentException if the given model is uninitialized, or the given width is
   *                                  negative or zero
   */
  protected GuiContainer(MusicEditorOperations model, int width) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Given model is uninitialized.");
    } else if (width <= 0) {
      throw new IllegalArgumentException("Width cannot be negative or zero.");
    }
    this.log = new StringBuilder();
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
      public void keyTyped(KeyEvent e) {
        // No actions to be taken on key type
      }

      @Override
      public void keyPressed(KeyEvent e) {
        updatePosition(e);
      }

      @Override
      public void keyReleased(KeyEvent e) {
        // No actions to be taken on key release
      }
    });
  }

  /**
   * Updates the position of the cursor in the editor panel, as well as the keys highlighted in
   * the piano panel, based on key press.
   * Left arrow will move the cursor back one beat, while the right arrow will move the cursor
   * forward one beat.
   *
   * @param e   the key event created from pressing a key on the keyboard
   */
  private void updatePosition(KeyEvent e) {
    if (e.getKeyCode() == 39 || e.getKeyCode() == 37) {
      int beat = this.editorPanel.updateCursor(e.getKeyCode() == 39);
      this.pianoPanel.updateHighlights(this.model.getNotesAtBeat(beat));
      repaint();
    }
  }

  /**
   * Gets the log of all drawing operations that have occurred in this container at the point of
   * this method being called.
   *
   * @return the log of operations as a String
   */
  protected String getLog() {
    return this.log.append(this.editorPanel.getLog()).append(this.pianoPanel.getLog()).toString();
  }
}
