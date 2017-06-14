package cs3500.music.view;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


/**
 * Created by josh_jpeg on 6/14/17.
 */
public class EditorPanel extends JViewport {
  private ArrayList<Integer[]> notes;

  @Override
  protected void paintComponent(Graphics g){
    // Handle the default painting
    super.paintComponent(g);
    // Look for more documentation about the Graphics class,
    // and methods on it that may be useful
    g.setColor(Color.red);
    g.drawRect(0, 0, getWidth()+ 300, getHeight());

    this.notes =
    g.setColor(Color.black);
    String temp_note_name = "";
    for (int i = 0; i < notes.size(); i++) {
      temp_note_name = this.getNoteName(this.notes.get(i-1));
      g.drawString(temp_note_name, 1, (i+1)*10);
    }

  }

  private String getNoteName(Integer[] note) {
    String pitch = Utils.getPitch(note[4]).toString();
    String octave = Integer.toString(Utils.getOctave(note[4]));

    return pitch+octave;
  }

  /*@Override
  public Dimension getPreferredSize() {
    return new Dimension(1100, 500);
  }*/


}
