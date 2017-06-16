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

  private static final int SCROLL_PADDING = 3;
  private static final int START_HEIGHT = 30;
  private static final int START_WIDTH = 40;
  private static final int CELL_WIDTH = 30;
  private int cellHeight = 5;

  private final MusicEditorOperations model;
  private List<Integer[]> notes;
  private int highPitch;
  private int lowPitch;
  private int numRows;
  private int pieceLength;
  private int cursorPosition;
  private int offset;
  private boolean reachedEnd = false;
  private StringBuilder log;

  protected EditorPanel(MusicEditorOperations model, int width, int height) {
    this.model = model;
    this.notes = this.model.getNotes();
    this.highPitch = this.getHighestPitch();
    this.lowPitch = this.getLowestPitch();
    this.numRows = highPitch - lowPitch + 1;
    this.pieceLength = this.model.totalPieceLength();
    this.cursorPosition = 0;
    this.offset = 0;
    this.cellHeight = getCellHeight(height, this.numRows);
    this.setPreferredSize(new Dimension(width,
        START_HEIGHT + (this.numRows * this.cellHeight) + 5));
    this.log = new StringBuilder();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int offsetX = (this.offset * CELL_WIDTH);
    this.addAllNotes(g, highPitch, offsetX);
    this.constructGrid(g, highPitch, lowPitch, numRows, offsetX);
    this.drawCursor(g, numRows, offsetX);
  }

  protected int updateCursor(boolean forward) {
    int startingPos = this.cursorPosition;
    int cellsShown = (this.getWidth() - START_WIDTH) / CELL_WIDTH;
    if (forward) {
      this.cursorPosition = Math.min(this.pieceLength, this.cursorPosition + 1);
      if ((cellsShown + this.offset - 2) >= this.pieceLength) {
        this.reachedEnd = true;
      }
    } else {
      this.cursorPosition = Math.max(0, this.cursorPosition - 1);
    }
    this.updateOffset(this.cursorPosition - startingPos);
    return this.cursorPosition;
  }

  private void updateOffset(int cursorChange) {
    if (cursorChange < 0) {
      if (START_WIDTH + (this.cursorPosition * CELL_WIDTH)
          <= START_WIDTH + ((this.offset + SCROLL_PADDING) * CELL_WIDTH)) {
        this.offset = Math.max(0, this.offset - 1);
        this.reachedEnd = false;
      }
    } else if (cursorChange > 0) {
      if ((START_WIDTH + ((this.cursorPosition + SCROLL_PADDING) * CELL_WIDTH) > getWidth())
          && !this.reachedEnd) {
        this.offset += 1;
      }
    }
  }

  private void constructGrid(Graphics g, int highest, int lowest, int numRows, int offsetX) {
    // Measure lines (vertical)
    for (int i = 0; i <= this.pieceLength; i++) {
      if (i % 4 == 0) {
        g.setColor(COLOR_LINES_DARK);
        g.drawString(Integer.toString(i), START_WIDTH + (i * CELL_WIDTH) - offsetX,
            START_HEIGHT - 10);
        g.drawLine(START_WIDTH + (i * CELL_WIDTH) - offsetX, START_HEIGHT,
            START_WIDTH + (i * CELL_WIDTH) - offsetX,
            START_HEIGHT + (numRows * this.cellHeight));
      }
    }

    // Pitch lines (horizontal)
    for (int i = 0; i <= numRows; i++) {
      if (i == 0 || i == numRows) {
        g.setColor(COLOR_LINES_DARK);
      } else {
        g.setColor(COLOR_LINES);
      }
      g.drawLine(START_WIDTH - offsetX, (i * this.cellHeight) +  START_HEIGHT,
          (pieceLength * CELL_WIDTH) + START_WIDTH - offsetX,
          (i * this.cellHeight) +  START_HEIGHT);
    }

    // Pitch names
    g.setColor(Color.white);
    g.fillRect(0, 0, START_WIDTH, getHeight());
    g.setColor(COLOR_LINES_DARK);
    g.drawLine(START_WIDTH, START_HEIGHT, START_WIDTH, START_HEIGHT + (numRows * this.cellHeight));
    g.setColor(COLOR_TEXT);
    for (int i = highest; i >= lowest; i--) {
      g.drawString(this.getPitchName(i), 1,
          (int) ((highest - i + 0.5) * this.cellHeight) + START_HEIGHT);
    }
  }

  /**
   * Gets the name of the midi pitch [0, 127] as a String. For example, 60 (middle-c) would
   * return "C4".
   *
   * @param midiPitch   the pitch as given to the midi
   * @return the name of the pitch converted to a string
   */
  private String getPitchName(int midiPitch) throws IllegalArgumentException {
    return MidiConversion.getPitch(midiPitch).toString()
        + Integer.toString(MidiConversion.getOctave(midiPitch));
  }

  private void drawCursor(Graphics g, int numRows, int offsetX) {
    int cursorWidth = 4;
    int headDiameter = 14;
    int headCutDiameter = 8;
    g.setColor(COLOR_CURSOR);
    g.fillRect(START_WIDTH + (this.cursorPosition * CELL_WIDTH) - (cursorWidth / 2) - offsetX,
        START_HEIGHT, cursorWidth, numRows * this.cellHeight);
    g.fillOval(START_WIDTH + (this.cursorPosition * CELL_WIDTH) - (headDiameter / 2) - offsetX,
        START_HEIGHT - (headDiameter / 2), headDiameter, headDiameter);
    g.setColor(Color.white);
    g.fillOval(START_WIDTH + (this.cursorPosition * CELL_WIDTH) - (headCutDiameter / 2) - offsetX,
        START_HEIGHT - (headCutDiameter / 2), headCutDiameter, headCutDiameter);
  }

  /**
   * Gets the height of all cells.
   *
   * @param height    the height of the window
   * @param numRows   the number of rows in this piece
   * @return the scaled height of a single cell
   */
  private int getCellHeight(int height, int numRows) {
    return Math.min(20, (height - START_HEIGHT) / numRows);
  }

  private int getLowestPitch() {
    int low = 128;
    for (Integer[] note : this.notes) {
      low = Math.min(low, note[MidiConversion.NOTE_PITCH]);
    }
    return low;
  }

  private int getHighestPitch() {
    int high = 0;
    for (Integer[] note : this.notes) {
      high = Math.max(high, note[MidiConversion.NOTE_PITCH]);
    }
    return high;
  }

  private void addAllNotes(Graphics g, int highNote, int offsetX) {
    for (Integer[] note : this.notes) {
      int start = note[MidiConversion.NOTE_START];
      int end = note[MidiConversion.NOTE_END];
      int pitch = note[MidiConversion.NOTE_PITCH];
      g.setColor(COLOR_NOTE_SUSTAIN);
      g.fillRect(START_WIDTH + ((start + 1) * CELL_WIDTH) - offsetX,
          START_HEIGHT + (highNote - pitch) * this.cellHeight,
          (end - start - 1) * CELL_WIDTH, this.cellHeight);
      g.setColor(COLOR_NOTE_ONSET);
      g.fillRect(START_WIDTH + (start * CELL_WIDTH) - offsetX,
          START_HEIGHT + (highNote - pitch) * this.cellHeight,
          CELL_WIDTH, this.cellHeight);
    }
  }

  protected String getLog() {
    return this.log.toString();
  }
}
