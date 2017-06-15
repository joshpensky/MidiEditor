package cs3500.music.view;
import cs3500.music.model.EditorOperations;
import cs3500.music.util.MidiConversion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by josh_jpeg on 6/14/17.
 */
public class EditorPanel extends JViewport {
  private static List<Integer[]> notes;
  private final EditorOperations model;
  private int pieceLength = 0;
  private int pitchLineHeight = 5;
  private int startHeight = 40;
  private int noteSize = 30;
  private int startWidth = 30;

  EditorPanel(EditorOperations model) {
    this.model = model;
  }

  @Override
  protected void paintComponent(Graphics g){
    // Handle the default painting
    super.paintComponent(g);

    // Look for more documentation about the Graphics class,
    // and methods on it that may be useful
    g.setColor(Color.red);
    g.drawRect(0, 0, getWidth()+ 300, getHeight());



    this.notes = this.model.getNotes();
    int highest = this.getHighestPitch(this.notes);
    int lowest = this.getLowestPitch(this.notes);

    System.out.println(highest + "   " + lowest);
    pieceLength = this.model.totalPieceLength();

    String temp_note_name = "";
    this.pitchLineHeight = getPitchHeight(this.notes.size());

    this.addAllNotes(g, highest);
    this.constructGrid(g, highest, lowest, temp_note_name, pieceLength);

    System.out.println(this.model.getNotesAtBeat(20000).toString());
  }

  private void constructGrid(Graphics g, int highest, int lowest, String temp_note_name,
                             int pieceLength) {
    g.setColor(Color.black);
    for (int i = highest; i >= lowest; i--) {
      temp_note_name = this.getNoteName(i);
      g.drawString(temp_note_name, 1, ((highest - i) * pitchLineHeight) + (int)
          (.5 * pitchLineHeight) + startHeight);
    }

    g.drawRect(startWidth, startHeight, pieceLength  * noteSize,
        (this.notes.size() * pitchLineHeight)-1);

    for (int i = 0; i < pieceLength; i++) {
      if (i % 4 == 0) {
        g.drawString(Integer.toString(i), startWidth + (i * noteSize), startHeight - 1);
        g.drawLine(startWidth + (i * noteSize), startHeight, startWidth + (i * noteSize),
            (this.notes.size() * pitchLineHeight)-1 );
      }
    }

    for (int i = 0; i < this.notes.size(); i++) {
      g.drawLine(startWidth, (i * pitchLineHeight) +  startHeight,
          (pieceLength * noteSize) + startWidth,
          (i * pitchLineHeight) +  startHeight);
    }
  }
  private String getNoteName(int note) {
    String pitch = MidiConversion.getPitch(note).toString();
    String octave = Integer.toString(MidiConversion.getOctave(note));

    return pitch + octave;
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
    if (getHeight() / length <  20) {
      return 20;
    } else {
      return  getHeight() / this.notes.size();
    }
  }


  private int getLowestPitch(List<Integer[]> loi) {
    int temp = 128;
    for (Integer[] i : loi) {
      /*if (i[3] < temp) {
        temp = i[3];
      }*/
      System.out.println(i[3]);
      temp = Math.min(temp, i[3]);
    }
    return temp;
  }

  private int getHighestPitch(List<Integer[]> loi) {
    int temp = 0;
    for (Integer[] i : loi) {
      if (i[3] > temp) {
        temp = i[3];
      }
    }
    return temp;
  }


  private void addAllNotes(Graphics g, int highNote) {
    Integer[] temp;
    for (int i = 0; i < this.notes.size(); i++) {
      temp = this.notes.get(i);
      g.setColor(Color.green);
      g.fillRect(startWidth + (temp[0] + 1) * noteSize,
          startHeight + (highNote - temp[3]) * pitchLineHeight,
          (temp[1] - temp[0] - 1) * noteSize, pitchLineHeight);
      g.setColor(Color.BLACK);

      g.fillRect(startWidth + (temp[0] * noteSize),
          startHeight + (highNote - temp[3]) * pitchLineHeight, noteSize, pitchLineHeight);

    }


//    for (Integer[] i : this.notes) {
//      g.setColor(Color.black);
//      g.fillRect(i[0]);
//    }
  }
}
