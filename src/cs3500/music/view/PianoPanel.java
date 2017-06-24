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
  private static final double SHARP_KEY_MULTIPLIER = 0.5;

  private final StringBuilder log;
  private Map<Integer, List<Pitch>> highlights;
  private int numOctaves;
  private int numKeys;
  private Map<Integer, Map<Pitch, MouseArea>> keyMouseAreas;

  /**
   * Constructs a new {@code PianoPanel} with the given width, and highlighting all of the given
   * notes on the piano.
   *
   * @param notes   the notes to be highlighted on the piano
   * @param width   the width of the panel
   * @throws IllegalArgumentException if the given list of notes is or contains an null, or the
   *                                  given width is negative or zero
   */
  protected PianoPanel(List<Integer[]> notes, int width) throws IllegalArgumentException {
    if (width <= 0) {
      throw new IllegalArgumentException("Cannot pass negative or zero width.");
    }
    if (notes == null || notes.contains(null)) {
      throw new IllegalArgumentException("Cannot pass null list of notes");
    }
    this.updateHighlights(notes);
    this.numOctaves = 10;
    this.numKeys = 0;
    for (Pitch p : Pitch.values()) {
      if (!p.isSharp()) {
        this.numKeys += 1;
      }
    }
    this.setPreferredSize(new Dimension(width, KEY_HEIGHT + 30));
    this.initKeyMouseAreas(width);
    this.log = new StringBuilder();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int position = getStartPos(getWidth());
    for (int i = 0; i < 10; i++) {
      position = drawOctave(g, position, i, this.highlights.getOrDefault(i, new ArrayList<>()));
    }
  }

  /**
   * Initializes the mouse areas for the keys on the piano view for use with mouse events.
   *
   * @param windowWidth   the width of the window
   */
  private void initKeyMouseAreas(int windowWidth) {
    this.keyMouseAreas = new TreeMap<>();
    for (int i = 1; i <= 10; i++) {
      Map<Pitch, MouseArea> pitchMap = new TreeMap<>();
      for (Pitch p : Pitch.values()) {
        pitchMap.put(p, null);
      }
      this.keyMouseAreas.put(i, pitchMap);
    }
    int position = getStartPos(windowWidth);
    for (int i = 0; i < 10; i++) {
      position = drawOctave(null, position, i, new ArrayList<>());
    }
  }

  /**
   * Gets the starting position of the piano drawing, so that the drawing of the piano would be
   * centered in the panel.
   *
   * @return the starting position of the piano drawing
   */
  private int getStartPos(int windowWidth) {
    int pianoWidth = numOctaves * numKeys * KEY_WIDTH;
    return (windowWidth - pianoWidth) / 2;
  }

  /**
   * Draws a single octave on a piano, starting at the given position on the x-axis. Highlights
   * any keys in the octave if the respective pitches are in the given list. IF the given
   * graphics is null, it adds the mouse areas for the keys to be recognized by mouse events.
   *
   * @param g             the graphics object to draw on
   * @param startPos      the starting x-position of the drawing of this octave
   * @param octave        the number of current octave being drawn
   * @param highlighted   the list of pitches to be highlighted in this octave
   * @return the ending x-position of the draw
   * @throws IllegalArgumentException if the given list of pitches is or contains null
   */
  private int drawOctave(Graphics g, int startPos, int octave, List<Pitch> highlighted)
      throws IllegalArgumentException {
    if (highlighted == null || highlighted.contains(null)) {
      throw new IllegalArgumentException("Given list of pitches is or contains null.");
    }
    int position = startPos;
    for (Pitch p : Pitch.values()) {
      if (!p.isSharp()) {
        if (g == null) {
          this.keyMouseAreas.get(octave + 1).replace(p,
              new MouseArea(new Posn(position, 0), new Posn(position + KEY_WIDTH, KEY_HEIGHT)));
        } else {
          g.setColor(this.getKeyColor(false, highlighted.contains(p)));
          g.fillRect(position, 0, KEY_WIDTH, KEY_HEIGHT);
          g.setColor(COLOR_KEY_OUTLINE);
          g.drawRect(position, 0, KEY_WIDTH, KEY_HEIGHT);
        }
        position += KEY_WIDTH;
      }
    }
    int sharpKeyWidth = (int) (KEY_WIDTH * SHARP_KEY_MULTIPLIER);
    int sharpKeyHeight = (int) (KEY_HEIGHT * SHARP_KEY_MULTIPLIER);
    position = startPos;
    for (Pitch p : Pitch.values()) {
      if (p.isSharp()) {
        if (g == null) {
          this.keyMouseAreas.get(octave + 1).replace(p,
              new MouseArea(new Posn(position - ((3 * sharpKeyWidth) / 4), 0),
              new Posn(position + (sharpKeyWidth / 4), sharpKeyHeight)));
        } else {
          g.setColor(this.getKeyColor(true, highlighted.contains(p)));
          g.fillRect(position - ((3 * sharpKeyWidth) / 4), 0, sharpKeyWidth, sharpKeyHeight);
          g.setColor(COLOR_KEY_OUTLINE);
          g.drawRect(position - ((3 * sharpKeyWidth) / 4), 0, sharpKeyWidth, sharpKeyHeight);
        }
      } else {
        position += KEY_WIDTH;
      }
    }
    return position;
  }

  /**
   * Calculates which key has been clicked on the piano and returns the MIDI representation of
   * that key's pitch.
   *
   * @param mouseX   the x-position of current cursor position
   * @param mouseY   the y-position of current cursor position
   * @return the MIDI representation of pitch
   */
  protected int getPitch(int mouseX, int mouseY) {
    for (Integer oct : keyMouseAreas.keySet()) {
      Map<Pitch, MouseArea> pitchMap = keyMouseAreas.get(oct);
      for (Pitch p : Pitch.values()) {
        if (p.isSharp() && pitchMap.get(p).mouseWithinArea(mouseX, mouseY)) {
          return MidiConversion.getMidiPitch(oct, p);
        }
      }
      for (Pitch p : Pitch.values()) {
        if (!p.isSharp() && pitchMap.get(p).mouseWithinArea(mouseX, mouseY)) {
          return MidiConversion.getMidiPitch(oct, p);
        }
      }
    }
    return -1;
  }

  /**
   * Gets the color of a key on a keyboard, depending on whether or not it's a sharp key or that
   * key is being played.
   *
   * @param isSharp       true if the key is sharp (or flat), false otherwise
   * @param highlighted   true if the key is being played, false otherwise
   * @return the color of the key
   */
  private Color getKeyColor(boolean isSharp, boolean highlighted) {
    if (highlighted) {
      return COLOR_KEY_PRESS;
    } else if (isSharp) {
      return Color.black;
    } else {
      return Color.white;
    }
  }

  /**
   * Updates the piano with the given notes, so that the pitches and octaves of the given notes
   * are highlighted on the display.
   *
   * @param notes   all of the notes to be highlighted on the piano
   * @throws IllegalArgumentException if the given list of notes is or contains null
   */
  protected void updateHighlights(List<Integer[]> notes) throws IllegalArgumentException {
    if (notes == null || notes.contains(null)) {
      throw new IllegalArgumentException("Cannot pass uninitialized note or list of notes.");
    }
    this.highlights = new TreeMap<>();
    for (Integer[] note : notes) {
      int octave = MidiConversion.getOctave(note[MidiConversion.NOTE_PITCH]) - 1;
      Pitch pitch = MidiConversion.getPitch(note[MidiConversion.NOTE_PITCH]);
      if (this.highlights.containsKey(octave)) {
        this.highlights.get(octave).add(pitch);
      } else {
        this.highlights.put(octave, new ArrayList<>(Arrays.asList(pitch)));
      }
    }
  }

  /**
   * Returns a log of all of the drawing necessary for the piano.
   *
   * @return the log of operations as a String
   */
  protected String getLog() {
    return this.log.toString();
  }
}
