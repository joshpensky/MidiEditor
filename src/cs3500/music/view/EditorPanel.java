package cs3500.music.view;
import cs3500.music.model.EditorOperations;
import cs3500.music.util.MidiConversion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by josh_jpeg on 6/14/17.
 */
public class EditorPanel extends JViewport {
  private static List<Integer[]> notes;
  private final EditorOperations model;
  private int pieceLength = 0;
  private static final int START_HEIGHT = 40;
  private static final int START_WIDTH = 30;
  private static final int CELL_WIDTH = 30;
  private int cellHeight = 5;
  private int cursorPosition;

  EditorPanel(EditorOperations model) {
    this.model = model;
    this.cursorPosition = 0;
    this.notes = this.model.getNotes();
    this.pieceLength = this.model.totalPieceLength();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int highest = this.getHighestPitch(this.notes);
    int lowest = this.getLowestPitch(this.notes);
    int numRows = highest - lowest + 1;

    System.out.println(highest + "   " + lowest);
    this.cellHeight = getPitchHeight(numRows);

    this.addAllNotes(g, highest);
    this.constructGrid(g, highest, lowest, numRows);
    this.drawCursor(g, numRows);

    System.out.println(this.model.getNotesAtBeat(20000).toString());
  }

  public void updateCursor(boolean forward) {
    if (forward) {
      this.cursorPosition = Math.min(this.pieceLength - 1, this.cursorPosition + 1);
    } else {
      this.cursorPosition = Math.max(0, this.cursorPosition - 1);
    }
    repaint();
  }

  private void constructGrid(Graphics g, int highest, int lowest, int numRows) {
    g.setColor(Color.black);
    for (int i = highest; i >= lowest; i--) {
      g.drawString(this.getNoteName(i), 1, ((highest - i) * this.cellHeight) + (int)
          (.5 * this.cellHeight) + START_HEIGHT);
    }

    g.drawRect(START_WIDTH, START_HEIGHT, pieceLength * CELL_WIDTH,
        (numRows * this.cellHeight));

    for (int i = 0; i < pieceLength; i++) {
      if (i % 4 == 0) {
        g.drawString(Integer.toString(i), START_WIDTH + (i * CELL_WIDTH), START_HEIGHT - 10);
        g.drawLine(START_WIDTH + (i * CELL_WIDTH), START_HEIGHT, START_WIDTH + (i * CELL_WIDTH),
            START_HEIGHT + (numRows * this.cellHeight) );
      }
    }

    for (int i = 0; i <= numRows; i++) {
      g.drawLine(START_WIDTH, (i * this.cellHeight) +  START_HEIGHT,
          (pieceLength * CELL_WIDTH) + START_WIDTH,
          (i * this.cellHeight) +  START_HEIGHT);
    }
  }

  private String getNoteName(int note) {
    String pitch = MidiConversion.getPitch(note).toString();
    String octave = Integer.toString(MidiConversion.getOctave(note));

    return pitch + octave;
  }

  private void drawCursor(Graphics g, int numRows) {
    int cursorWidth = 4;
    int cursorHeadDiameter = 14;
    int cursorHeadCutDiameter = 8;
    g.setColor(Color.red);
    g.fillRect(START_WIDTH + (this.cursorPosition * CELL_WIDTH) - (cursorWidth / 2),
        START_HEIGHT, cursorWidth, numRows * this.cellHeight);
    g.fillOval(START_WIDTH + (this.cursorPosition * CELL_WIDTH) - (cursorHeadDiameter / 2),
        START_HEIGHT - (cursorHeadDiameter / 2), cursorHeadDiameter, cursorHeadDiameter);
    g.setColor(Color.white);
    g.fillOval(START_WIDTH + (this.cursorPosition * CELL_WIDTH) - (cursorHeadCutDiameter / 2),
      START_HEIGHT - (cursorHeadCutDiameter / 2), cursorHeadCutDiameter, cursorHeadCutDiameter);
  }

  /*@Override
  public Dimension getPreferredSize() {
    return new Dimension(1100, 500);
  }*/


  private List<Integer[]> getTemp() {
    List<Integer[]> temp = new ArrayList<>();
    Integer[] a = new Integer[5];
    a[0] = 0;
    a[1] = 2;
    a[2] = 1;
    a[3] = 64;
    a[4] = 72;
    for (int i = 0; i <5; i++) {
      temp.add(a.clone());

    }

    for (Integer[] c : temp) {
      c[0] = (int)(Math.random() * 10);
      c[1] = (int)(Math.random() * 3) + c[0];
      c[4] = (int)(Math.random() * 2 )+ 50;
    }
    return temp;
  }

  private int getPitchHeight(int length) {
    int cellHeight = (getHeight() - START_HEIGHT) / length;
    if (cellHeight < 20) {
      return 20;
    }
    return cellHeight;
  }

  private int getLowestPitch(List<Integer[]> loi) {
    int low = 128;
    for (Integer[] i : loi) {
      low = Math.min(low, i[3]);
    }
    return low;
  }

  private int getHighestPitch(List<Integer[]> loi) {
    int high = 0;
    for (Integer[] i : loi) {
      high = Math.max(high, i[3]);
    }
    return high;
  }


  private void addAllNotes(Graphics g, int highNote) {
    Integer[] temp;
    for (int i = 0; i < this.notes.size(); i++) {
      temp = this.notes.get(i);
      g.setColor(Color.green);
      g.fillRect(START_WIDTH + (temp[0] + 1) * CELL_WIDTH,
          START_HEIGHT + (highNote - temp[3]) * this.cellHeight,
          (temp[1] - temp[0] - 1) * CELL_WIDTH, this.cellHeight);
      g.setColor(Color.BLACK);

      g.fillRect(START_WIDTH + (temp[0] * CELL_WIDTH),
          START_HEIGHT + (highNote - temp[3]) * this.cellHeight, CELL_WIDTH, this.cellHeight);

    }


//    for (Integer[] i : this.notes) {
//      g.setColor(Color.black);
//      g.fillRect(i[0]);
//    }
  }
}
