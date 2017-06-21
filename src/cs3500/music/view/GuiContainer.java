package cs3500.music.view;

import cs3500.music.model.MusicEditorOperations;
import cs3500.music.util.MidiConversion;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents the main container panel in the {@link GuiView}. This panel contains the
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
    this.addMouseListener(new MouseListener() {

      @Override
      public void mouseClicked(MouseEvent e) {
      }

      @Override
      public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY() - editorContainer.getHeight();
        int pit = pianoPanel.getPitch(x, y);
        System.out.println(x + " " + y);
        if (pit > 0) {
          int start = editorPanel.getCursorPosition();
          System.out.print("note " + start + " " + (start + 1) + " " + 1 + " ");
          System.out.println(pit + " " + 64);
        } else {
          System.out.println("Nope");
        }
      }

      @Override
      public void mouseReleased(MouseEvent e) {

      }

      @Override
      public void mouseEntered(MouseEvent e) {

      }

      @Override
      public void mouseExited(MouseEvent e) {

      }
    });
  }

  /**
   * Updates the position of the cursor in the editor panel, as well as the keys highlighted in
   * the piano panel, based on key press.
   * Left arrow will move the cursor back one beat, while the right arrow will move the cursor
   * forward one beat.
   *
   * @param forward   the key event created from pressing a key on the keyboard
   */
  protected void updatePosition(boolean forward) {
    int beat = this.editorPanel.updateCursor(forward);
    this.pianoPanel.updateHighlights(this.model.getNotesAtBeat(beat));
    repaint();
  }

  protected void jumpToBeginning() {
    while (this.editorPanel.getCursorPosition() > 0) {
      this.editorPanel.updateCursor(false);
    }
    this.pianoPanel.updateHighlights(this.model.getNotesAtBeat(0));
    repaint();
  }

  protected void jumpToEnd() {
    while (this.editorPanel.getCursorPosition() < this.model.getLength()) {
      this.editorPanel.updateCursor(true);
    }
    this.pianoPanel.updateHighlights(this.model.getNotesAtBeat(this.model.getLength() - 1));
    repaint();
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
