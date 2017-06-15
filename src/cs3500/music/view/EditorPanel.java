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
  private int noteSize = 15;
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

    this.notes = getTemp();
    pieceLength = this.notes.size();
    g.setColor(Color.black);
    String temp_note_name = "";
    this.pitchLineHeight = getHeight() / this.notes.size();

    for (int i = 0; i < notes.size(); i++) {

      temp_note_name = this.getNoteName(this.notes.get(i));
      g.drawString(temp_note_name, 1, (i * pitchLineHeight) + (int) (.5 * pitchLineHeight) + startHeight);
    }

    g.drawRect(startWidth, startHeight, pieceLength  * noteSize, (this.notes.size() * pitchLineHeight)-1);

    for (int i = 0; i < pieceLength; i++) {
        if (i % 4 == 0) {
            g.drawString(Integer.toString(i), startWidth + (i * noteSize), startHeight - 1);
            g.drawLine(startWidth + (i * noteSize), startHeight, startWidth + (i * noteSize),
                    (this.notes.size() * pitchLineHeight)-1 );
        }
    }

    for (int i = 0; i < this.notes.size(); i++) {
        g.drawLine(startWidth, (i * pitchLineHeight) +  startHeight, (pieceLength * noteSize) + startWidth,
                (i * pitchLineHeight) +  startHeight);
    }





  }

  private String getNoteName(Integer[] note) {
    String pitch = MidiConversion.getPitch(note[4]).toString();
    String octave = Integer.toString(MidiConversion.getOctave(note[4]));

    return pitch+octave;
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
      for (int i = 0; i <10; i++) {
          temp.add(a.clone());

      }

      for (Integer[] c : temp) {
          c[4] = (int)(Math.random() *127 + 1);
      }
    return temp;
  }
}
