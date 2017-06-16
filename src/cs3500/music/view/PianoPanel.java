package cs3500.music.view;

import cs3500.music.model.Pitch;
import cs3500.music.util.MidiConversion;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents the panel in {@link GuiContainer} that represents the piano, or keyboard, view.
 * Displays a 10-octave, 120-key keyboard.
 */
public class PianoPanel extends JPanel {
  private static final Color COLOR_KEY_OUTLINE = Color.decode("#95989A");
  private static final Color COLOR_KEY_PRESS = Color.decode("#FFC802");

  private static final int KEY_WIDTH = 15;
  private static final int KEY_HEIGHT = 200;

  private Map<Integer, List<Pitch>> highlights;
  private int numOctaves;
  private int numKeys;

  protected PianoPanel(List<Integer[]> notes, int width) {
    this.updateHighlights(notes);
    this.numOctaves = 10;
    this.numKeys = 0;
    for (Pitch p : Pitch.values()) {
      if (!p.isSharp()) {
        this.numKeys += 1;
      }
    }
    this.setPreferredSize(new Dimension(width, KEY_HEIGHT + 30));
  }

  @Override
  protected void paintComponent(Graphics g){
    // Handle the default painting
    super.paintComponent(g);
    // Look for more documentation about the Graphics class,
    // and methods on it that may be useful
    int position = getStartPos();
    for (int i = 0; i < 10; i++) {
      position = drawOctave(g, position, this.highlights.getOrDefault(i, new ArrayList<>()));
    }
  }

  private int getStartPos() {
    int windowWidth = getWidth();
    int pianoWidth = numOctaves * numKeys * KEY_WIDTH;
    return (windowWidth - pianoWidth) / 2;
  }

  private int drawOctave(Graphics g, int startPos, List<Pitch> highlighted) {
    int position = startPos;
    for (Pitch p : Pitch.values()) {
      if (!p.isSharp()) {
        if (highlighted.contains(p)) {
          g.setColor(COLOR_KEY_PRESS);
        } else {
          g.setColor(Color.white);
        }
        g.fillRect(position, 0, KEY_WIDTH, KEY_HEIGHT);
        g.setColor(COLOR_KEY_OUTLINE);
        g.drawRect(position, 0, KEY_WIDTH, KEY_HEIGHT);
        position += KEY_WIDTH;
      }
    }
    int sharpKeyWidth = (int) (KEY_WIDTH * 0.5);
    int sharpKeyHeight = (int) (KEY_HEIGHT * 0.6);
    position = startPos;
    for (Pitch p : Pitch.values()) {
      if (p.isSharp()) {
        if (highlighted.contains(p)) {
          g.setColor(COLOR_KEY_PRESS);
        } else {
          g.setColor(Color.black);
        }
        g.fillRect(position - ((3 * sharpKeyWidth) / 4), 0, sharpKeyWidth, sharpKeyHeight);
        g.setColor(COLOR_KEY_OUTLINE);
        g.drawRect(position - ((3 * sharpKeyWidth) / 4), 0, sharpKeyWidth, sharpKeyHeight);
      } else {
        position += KEY_WIDTH;
      }
    }
    return position;
  }

  protected void updateHighlights(List<Integer[]> notes) {
    this.highlights = new TreeMap<>();
    for (Integer[] note : notes) {
      int octave = MidiConversion.getOctave(note[MidiConversion.NOTE_PITCH]);
      Pitch pitch = MidiConversion.getPitch(note[MidiConversion.NOTE_PITCH]);
      if (this.highlights.containsKey(octave)) {
        this.highlights.get(octave).add(pitch);
      } else {
        this.highlights.put(octave, new ArrayList<>(Arrays.asList(pitch)));
      }
    }
  }
}
