package cs3500.music.view;
import javax.swing.*;
import java.awt.*;

/**
 * Created by josh_jpeg on 6/14/17.
 */
public class EditorPanel extends JViewport {

  @Override
  protected void paintComponent(Graphics g){
    // Handle the default painting
    super.paintComponent(g);
    // Look for more documentation about the Graphics class,
    // and methods on it that may be useful
    g.setColor(Color.red);
    g.drawRect(0, 0, getWidth() + 300, getHeight());
  }

  /*@Override
  public Dimension getPreferredSize() {
    return new Dimension(1100, 500);
  }*/


}
