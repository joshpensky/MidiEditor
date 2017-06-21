package cs3500.music.view;

import cs3500.music.model.MusicEditorOperations;
import cs3500.music.util.MidiConversion;

import javax.swing.JViewport;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;

import java.util.List;

/**
 * Represents the panel in {@link GuiContainer} that contains the editor view. The editor view
 * displays all of the notes currently opened in the model, as well as the current position of
 * the editor cursor.
 */
public class EditorPanel extends JViewport {
  private static final Color COLOR_CURSOR = Color.decode("#FF3362");
  private static final Color COLOR_NOTE_ONSET = Color.decode("#2079E5");
  private static final Color COLOR_NOTE_SUSTAIN = Color.decode("#FF972F");
  private static final Color COLOR_LINES = Color.decode("#E3E3E3");
  private static final Color COLOR_LINES_DARK = Color.decode("#95989A");
  private static final Color COLOR_TEXT = Color.decode("#5E6162");

  private static final int SCROLL_PADDING = 5;
  private static final int START_HEIGHT = 30;
  private static final int START_WIDTH = 40;
  private static final int CELL_WIDTH = 30;
  private int cellHeight = 5;


  private final StringBuilder log;
  private List<Integer[]> notes;
  private int highPitch;
  private int lowPitch;
  private int numRows;
  private int pieceLength;
  private int cursorPosition;
  private int scrollOffset;
  private boolean reachedEnd = false;

  /**
   * Constructs a new {@code EditorPanel} using the given model. Sets the width and height of the
   * panel to the given width and height. Displays the notes that are currently in view.
   *
   * @param model    the model to be represented in the editor view
   * @param width    the desired width for the panel
   * @param height   the desired height for the panel
   * @throws IllegalArgumentException if the given model is uninitialized, or the width or height
   *                                  are negative or zero
   */
  protected EditorPanel(MusicEditorOperations model, int width, int height)
      throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Given model is uninitialized.");
    } else if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and height must be positive and non-zero.");
    }

    this.notes = model.getNotes();
    this.highPitch = this.getHighestPitch();
    this.lowPitch = this.getLowestPitch();
    this.numRows = highPitch - lowPitch + 1;
    this.pieceLength = model.getLength();
    this.cursorPosition = 0;
    this.scrollOffset = 0;
    this.cellHeight = getCellHeight(height);
    this.setPreferredSize(new Dimension(width,
        START_HEIGHT + (this.numRows * this.cellHeight) + 5));
    this.log = new StringBuilder();
  }

  /**
   * Gets the height of all cells, scaling for the height of this window. Will always have a
   * minimum size of 20, or the maximum size of 50.
   *
   * @param height    the height of the window
   * @return the scaled height of a single cell
   */
  private int getCellHeight(int height) {
    if (this.numRows == 0) {
      return 20;
    }
    return Math.max(20, Math.min(50, (height - START_HEIGHT) / this.numRows));
  }

  /**
   * Gets the lowest pitch in the currently opened piece in the model.
   *
   * @return the lowest pitch from the piece in the model
   */
  private int getLowestPitch() {
    int low = 127;
    for (Integer[] note : this.notes) {
      low = Math.min(low, note[MidiConversion.NOTE_PITCH]);
    }
    return low;
  }

  /**
   * Gets the highest pitch in the currently opened piece in the model.
   *
   * @return the highest pitch from the piece in the model
   */
  private int getHighestPitch() {
    int high = 0;
    for (Integer[] note : this.notes) {
      high = Math.max(high, note[MidiConversion.NOTE_PITCH]);
    }
    return high;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int offsetX = (this.scrollOffset * CELL_WIDTH);
    this.drawNotes(g, offsetX);
    this.drawGrid(g, offsetX);
    this.drawCursor(g, offsetX);
    this.drawProgress(g);
  }

  /**
   * Helper to the paintComponent method. Draws all of the notes in memory of the model onto the
   * panel, adjusting the x-positions of every note to account for the current x-offset of the
   * horizontal scroll in the window.
   *
   * @param g           the graphics object to draw on
   * @param offsetX     the current x-offset for scrolling of the piece
   * @throws IllegalArgumentException if the given graphics object is uninitialized
   */
  private void drawNotes(Graphics g, int offsetX) {
    for (Integer[] note : this.notes) {
      int start = note[MidiConversion.NOTE_START];
      int end = note[MidiConversion.NOTE_END];
      int pitch = note[MidiConversion.NOTE_PITCH];
      g.setColor(COLOR_NOTE_SUSTAIN);
      g.fillRect(START_WIDTH + ((start + 1) * CELL_WIDTH) - offsetX,
          START_HEIGHT + (this.highPitch - pitch) * this.cellHeight,
          (end - start - 1) * CELL_WIDTH, this.cellHeight);
      g.setColor(COLOR_NOTE_ONSET);
      g.fillRect(START_WIDTH + (start * CELL_WIDTH) - offsetX,
          START_HEIGHT + (this.highPitch - pitch) * this.cellHeight,
          CELL_WIDTH, this.cellHeight);
    }
  }

  /**
   * Helper to the paintComponent method. Constructs and draws the grid for the editor view,
   * taking into account the current horizontal scroll of the view. The pitch names on the far
   * left side will always stay at the same position, no matter the offset value.
   *
   * @param g         the graphics object to draw on
   * @param offsetX   the current x-offset for scrolling of the piece
   */
  private void drawGrid(Graphics g, int offsetX) {
    this.drawMeasureLines(g, offsetX);
    this.drawPitchLines(g, offsetX);
    this.drawPitchNames(g);
  }

  /**
   * Helper to the drawGrid method. Draws the measure (vertical) lines every four beats in the
   * view. If the final beat of the piece is not divisible by 4, draws the line anyway to display
   * the ending of the piece.
   *
   * @param g         the graphics object to draw on
   * @param offsetX   the current x-offset for scrolling of the piece
   */
  private void drawMeasureLines(Graphics g, int offsetX) {
    for (int i = 0; i <= this.pieceLength; i++) {
      if (i % 4 == 0 || i == this.pieceLength) {
        g.setColor(COLOR_LINES_DARK);
        if (i != this.pieceLength || this.pieceLength % 4 == 0) {
          g.drawString(Integer.toString(i), START_WIDTH + (i * CELL_WIDTH) - offsetX,
              START_HEIGHT - 10);
        }
        g.drawLine(START_WIDTH + (i * CELL_WIDTH) - offsetX, START_HEIGHT,
            START_WIDTH + (i * CELL_WIDTH) - offsetX,
            START_HEIGHT + (this.numRows * this.cellHeight));
      }
    }
  }

  /**
   * Helper to the drawGrid method. Draws the pitch (horizontal) lines that form the rows for
   * every pitch for note cells to be placed in.
   *
   * @param g         the graphics object to draw on
   * @param offsetX   the current x-offset for scrolling of the piece
   */
  private void drawPitchLines(Graphics g, int offsetX) {
    for (int i = 0; i <= this.numRows; i++) {
      if (i == 0 || i == this.numRows) {
        g.setColor(COLOR_LINES_DARK);
      } else {
        g.setColor(COLOR_LINES);
      }
      g.drawLine(START_WIDTH - offsetX, (i * this.cellHeight) +  START_HEIGHT,
          (this.pieceLength * CELL_WIDTH) + START_WIDTH - offsetX,
          (i * this.cellHeight) +  START_HEIGHT);
    }
  }

  /**
   * Helper to the drawGrid method. Draws the pitch names alongside their corresponding rows in
   * the grid.
   *
   * @param g   the graphics object to draw on
   */
  private void drawPitchNames(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, START_WIDTH, getHeight());
    g.setColor(COLOR_LINES_DARK);
    g.drawLine(START_WIDTH, START_HEIGHT, START_WIDTH, START_HEIGHT + (numRows * this.cellHeight));
    g.setColor(COLOR_TEXT);
    for (int i = this.highPitch; i >= this.lowPitch; i--) {
      g.drawString(MidiConversion.getPitchName(i), 1,
          (int) ((this.highPitch - i + 0.5) * this.cellHeight) + START_HEIGHT);
    }
  }

  /**
   * Helper to the paintComponent method. Draws the cursor (red line) onto the panel, adjusting
   * for the current x-value of the panel's horizontal scroll.
   *
   * @param g         the graphics object to draw on
   * @param offsetX   the current x-offset for scrolling of the piece
   */
  private void drawCursor(Graphics g, int offsetX) {
    int cursorWidth = 4;
    int headDiameter = 14;
    int headCutDiameter = 8;
    g.setColor(COLOR_CURSOR);
    g.fillRect(START_WIDTH + (this.cursorPosition * CELL_WIDTH) - (cursorWidth / 2) - offsetX,
        START_HEIGHT, cursorWidth, this.numRows * this.cellHeight);
    g.fillOval(START_WIDTH + (this.cursorPosition * CELL_WIDTH) - (headDiameter / 2) - offsetX,
        START_HEIGHT - (headDiameter / 2), headDiameter, headDiameter);
    g.setColor(Color.white);
    g.fillOval(START_WIDTH + (this.cursorPosition * CELL_WIDTH) - (headCutDiameter / 2) - offsetX,
        START_HEIGHT - (headCutDiameter / 2), headCutDiameter, headCutDiameter);
  }

  /**
   * Helper to the paintComponent method. Draws a progress bar across the top of the view,
   * representing where the current position of the cursor is in the piece.
   *
   * @param g   the graphics object to draw on
   */
  private void drawProgress(Graphics g) {
    g.setColor(COLOR_CURSOR);
    g.fillRect(0, 0,
        (int) ((this.cursorPosition / (double) this.pieceLength) * this.getWidth()), 2);
  }

  /**
   * Updates the current position of the cursor, based on the given direction to go forward or
   * backward. Stops at beat 0 and the length of the piece in the model.
   *
   * @param forward   moves the cursor forward a single beat if true, otherwise moves the cursor
   *                  back a single beat
   * @return the current position of the moved cursor
   */
  protected int updateCursor(boolean forward) {
    int startingPos = this.cursorPosition;
    int cellsShown = (this.getWidth() - START_WIDTH) / CELL_WIDTH;
    if (forward) {
      this.cursorPosition = Math.min(this.pieceLength, this.cursorPosition + 1);
      if ((cellsShown + this.scrollOffset - 2) >= this.pieceLength) {
        this.reachedEnd = true;
      }
    } else {
      this.cursorPosition = Math.max(0, this.cursorPosition - 1);
    }
    this.updateScrollOffset(this.cursorPosition - startingPos);
    return this.cursorPosition;
  }

  protected int cursorToBegining() {
    int startPos = cursorPosition;
    this.cursorPosition = 0;
    this.updateScrollOffset(this.cursorPosition - startPos);
    return this.cursorPosition;
  }

  protected int cursorToEnd() {
    int startPos = cursorPosition;
    this.cursorPosition = this.pieceLength - 1;
    this.updateScrollOffset(this.cursorPosition - startPos);
    return this.cursorPosition;
  }

  /**
   * Updates the scroll offset of the window when the cursor position has changed. If the last
   * note is in view of the window, the scroll offset will remain the same until the cursor moves
   * the opposite direction until the last note is no longer visible.
   *
   * @param cursorChange the difference between the current and previous position of the cursor
   */
  private void updateScrollOffset(int cursorChange) {
    if (cursorChange < 0) {
      if (START_WIDTH + (this.cursorPosition * CELL_WIDTH)
          <= START_WIDTH + ((this.scrollOffset + SCROLL_PADDING) * CELL_WIDTH)) {
        this.scrollOffset = Math.max(0, this.scrollOffset - 1);
        this.reachedEnd = false;
      }
    } else if (cursorChange > 0) {
      if ((START_WIDTH + ((this.cursorPosition + SCROLL_PADDING) * CELL_WIDTH) > getWidth())
          && !this.reachedEnd) {
        this.scrollOffset += 1;
      }
    }
  }

  /**
   * Returns a log of all of the drawing necessary for the editor view.
   *
   * @return the log of operations as a String
   */
  protected String getLog() {
    return this.log.toString();
  }

  protected int getCursorPosition() {
    return this.cursorPosition;
  }
}
