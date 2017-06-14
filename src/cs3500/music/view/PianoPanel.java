package cs3500.music.view;
import cs3500.music.model.josh.Pitch;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

/**
 * A dummy view that simply draws a string
 */
public class PianoPanel extends JPanel {
  private int numOctaves;
  private int numKeys;
  private static final int KEY_WIDTH = 15;
  private static final int KEY_HEIGHT = 200;
  int yellow = 0;

  protected PianoPanel() {
    this.numOctaves = 10;
    this.numKeys = 0;
    for (Pitch p : Pitch.values()) {
      if (!p.isSharp()) {
        this.numKeys += 1;
      }
    }
    /*this.setFocusable(true);
    this.requestFocusInWindow();
    this.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {

      }

      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 39) {
          yellow += 5;
        } else if (e.getKeyCode() == 37) {
          yellow -= 5;
        }
        repaint();
      }

      @Override
      public void keyReleased(KeyEvent e) {

      }
    });*/
  }

  private int getStartPos() {
    int windowWidth = getWidth();
    int pianoWidth = numOctaves * numKeys * KEY_WIDTH;
    return (windowWidth - pianoWidth) / 2;
  }

  @Override
  protected void paintComponent(Graphics g){
    // Handle the default painting
    super.paintComponent(g);
    // Look for more documentation about the Graphics class,
    // and methods on it that may be useful
    int position = getStartPos();
    for (int i = 0; i < 10; i++) {
      position = drawOctave(g, position);
    }
    g.setColor(Color.red);
    g.fillRect(yellow, 0, 5, KEY_HEIGHT);
  }

  private int drawOctave(Graphics g, int startPos) {
    int position = startPos;
    for (Pitch p : Pitch.values()) {
      if (!p.isSharp()) {
        g.setColor(Color.white);
        g.fillRect(position, 0, KEY_WIDTH, KEY_HEIGHT);
        g.setColor(Color.black);
        g.drawRect(position, 0, KEY_WIDTH, KEY_HEIGHT);
        position += KEY_WIDTH;
      }
    }
    int sharpKeyWidth = (int) (KEY_WIDTH * 0.5);
    int sharpKeyHeight = (int) (KEY_HEIGHT * 0.6);
    position = startPos;
    for (Pitch p : Pitch.values()) {
      if (p.isSharp()) {
        g.setColor(Color.black);
        g.fillRect(position - ((3 * sharpKeyWidth) / 4), 0, sharpKeyWidth, sharpKeyHeight);
        g.drawRect(position - ((3 * sharpKeyWidth) / 4), 0, sharpKeyWidth, sharpKeyHeight);
      } else {
        position += KEY_WIDTH;
      }
    }
    return position;
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1100, KEY_HEIGHT + 50);
  }


}
