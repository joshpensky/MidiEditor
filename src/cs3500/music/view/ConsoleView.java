package cs3500.music.view;

import cs3500.music.controller.MusicEditorController;
import cs3500.music.model.MusicEditorOperations;
import cs3500.music.model.Pitch;
import cs3500.music.model.Utils;
import cs3500.music.util.MidiConversion;

import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents the text view for a music editor model. Prints the String representation of the
 * currently opened piece to the console.
 */
public class ConsoleView implements MusicEditorView {
  private final MusicEditorOperations model;
  private final Appendable app;
  private final StringBuilder log;

  /**
   * Represents the builder class for a ConsoleView. Defaults the appendable of the ConsoleView to
   * the console (System.out), but allows for the appendable to be changed.
   */
  public static final class Builder {
    private MusicEditorOperations model;
    private Appendable app;

    /**
     * Constructs a new {@code Builder} for a ConsoleView.
     *
     * @param model   the model to be represented musically using MIDI
     * @throws IllegalArgumentException if the given model is uninitialized
     */
    public Builder(MusicEditorOperations model) throws IllegalArgumentException {
      if (model == null) {
        throw new IllegalArgumentException("Given model is uninitialized.");
      }
      this.model = model;
      this.app = null;
    }

    /**
     * Sets the appendable for a new ConsoleView to the given one.
     *
     * @param app   the appendable to be set for the ConsoleView
     * @return this builder
     * @throws IllegalArgumentException if the given appendable is uninitialized
     */
    public Builder setAppendable(Appendable app) throws IllegalArgumentException {
      if (app == null) {
        throw new IllegalArgumentException("Given appendable object is uninitialized.");
      }
      this.app = app;
      return this;
    }

    /**
     * Returns a new ConsoleView with the given specifications set in this builder. If no appendable
     * has been set, it is defaulted to the console (System.out).
     *
     * @return a new ConsoleView with this builder's instructions
     */
    public ConsoleView build() {
      if (this.app == null) {
        this.app = new OutputStreamWriter(System.out);
      }
      return new ConsoleView(this);
    }
  }

  /**
   * Constructs a new {@code ConsoleView} using an instance of the nested builder class.
   *
   * @param builder   the builder for this ConsoleView
   */
  private ConsoleView(Builder builder) {
    this.model = builder.model;
    this.app = builder.app;
    this.log = new StringBuilder();
  }

  @Override
  public void initialize() {
    try {
      if (this.model.getLength() > 0) {
        this.app.append(this.stringBuilder());
      }
    } catch (IOException e) {
      this.log.append("Encountered fatal IOException: " + e.getMessage() + "\n");
    }
  }

  /**
   * Helper to the initialize method. Creates a String representation of this piece, but only
   * with the octaves with notes included. If pitches on either end of the table do not have
   * notes, those pitches should not be included either.
   *
   * @return a String representation of this piece
   */
  private String stringBuilder() {
    List<List<String>> arr = this.getPieceTable();
    int octaveLength = arr.get(0).size();
    int lineNumPadding = octaveLength - 2;
    if (lineNumPadding < 0) {
      lineNumPadding = 0;
    }
    int lineNumPaddingString = Integer.toString(lineNumPadding).length();
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < octaveLength; i++) {
      if (i == 0) {
        builder.append(Utils.padString("", lineNumPaddingString, Utils.Alignment.RIGHT));
      } else {
        builder.append(Utils.padString(Integer.toString(i - 1), lineNumPaddingString,
            Utils.Alignment.RIGHT));
      }
      builder.append("  ");
      for (List<String> pitchCol : arr) {
        builder.append(pitchCol.get(i));
      }
      builder.append("\n");
    }
    return builder.toString();
  }

  /**
   * Helper to the stringBuilder method. Forms a 2-dimensional list of Strings that represents the
   * piece, with notes being represented as {@code X}'s for onsets and {@code I}'s for sustains.
   *
   * @return a 2-dimensional list of Strings representing this piece
   */
  private List<List<String>> getPieceTable() {
    List<List<List<String>>> builder = new ArrayList<>();
    int columnWidth = 5;
    String empty = Utils.padString("", columnWidth, Utils.Alignment.CENTER);
    int maxLength = this.model.getLength();
    Map<Integer, Map<Pitch, List<Integer[]>>> noteMap = sortNotesToMap();
    for (Integer octave : noteMap.keySet()) {
      Map<Pitch, List<Integer[]>> pitchMap = noteMap.get(octave);
      builder.add(getOctaveTable(octave, pitchMap, columnWidth));
    }
    List<List<String>> piece = new ArrayList<>();
    for (List<List<String>> octaveList : builder) {
      for (List<String> pitchCol : octaveList) {
        while (pitchCol.size() - 1 <= maxLength) {
          pitchCol.add(empty);
        }
        piece.add(pitchCol);
      }
    }
    return this.removeEmptyEndColumns(piece, empty, true);
  }

  /**
   * Gets all notes from the model and sorts them into a {@code Map}. The outermost layer of keys
   * are the octave numbers. The next layer of keys are all pitches belonging to each octave.
   * Finally, the values relating to each pitch are lists of note data for that respective pitch
   * and octave.
   *
   * @return a map of sorted note data
   */
  private Map<Integer, Map<Pitch, List<Integer[]>>> sortNotesToMap() {
    List<Integer[]> notes = this.model.getNotes();
    Map<Integer, Map<Pitch, List<Integer[]>>> octaveMap = new TreeMap<>();
    for (int i = 1; i <= 10; i++) {
      Map<Pitch, List<Integer[]>> pitchMap = new TreeMap<>();
      for (Pitch p : Pitch.values()) {
        pitchMap.put(p, new ArrayList<>());
      }
      octaveMap.put(i, pitchMap);
    }
    for (Integer[] note : notes) {
      int octave = MidiConversion.getOctave(note[MidiConversion.NOTE_PITCH]);
      Pitch pitch = MidiConversion.getPitch(note[MidiConversion.NOTE_PITCH]);
      if (!octaveMap.containsKey(octave)) {
        octaveMap.put(octave, new TreeMap<>());
      }
      Map<Pitch, List<Integer[]>> pitchMap = octaveMap.get(octave);
      if (!pitchMap.containsKey(pitch)) {
        pitchMap.put(pitch, new ArrayList<>());
      }
      pitchMap.get(pitch).add(note);
    }
    return octaveMap;
  }

  /**
   * Creates a 2-dimensional list of Strings, or table of Strings, representing an octave. The
   * inner lists (the columns) represent the different pitches, and the notes in those pitches.
   *
   * @param octaveNum   the number of this octane
   * @param padding     the amount of spaced padding for each String in the list
   * @return a 2-dimensional list of Strings representing this octane
   */
  private List<List<String>> getOctaveTable(int octaveNum, Map<Pitch, List<Integer[]>> pitchMap,
                                            int padding) {
    String empty = Utils.padString("", padding, Utils.Alignment.CENTER);
    List<List<String>> builder = new ArrayList<>();
    int maxLength = this.model.getLength();
    for (Pitch p : Pitch.values()) {
      builder.add(getPitchColumn(p, pitchMap.get(p), octaveNum, padding, maxLength));
    }
    for (List<String> pitchCol : builder) {
      while (pitchCol.size() < maxLength) {
        pitchCol.add(empty);
      }
    }
    return builder;
  }

  /**
   * Helper to the getOctaveTable method. Creates a list of Strings that represents a single
   * pitch within this octave. Notes are represented with {@code X}'s for onsets and {@code |}'s
   * for sustains. Rests are represented with empty spaces.
   *
   * @param pitch       the pitch being represented
   * @param octaveNum   the number of this octane
   * @param padding     the amount of spaced padding for each String in the list
   * @param length      the length of the returned list
   * @return a list of Strings representing the given pitch in this octane
   */
  private List<String> getPitchColumn(Pitch pitch, List<Integer[]> notes,
                                      int octaveNum, int padding, int length) {
    List<String> pitchCol = new ArrayList<>();
    String empty = Utils.padString("", padding, Utils.Alignment.CENTER);
    for (int i = 0; i <= length; i++) {
      pitchCol.add(empty);
    }
    String onset = Utils.padString("X", padding, Utils.Alignment.CENTER);
    String sustain = Utils.padString("|", padding, Utils.Alignment.CENTER);
    for (Integer[] n : notes) {
      int start = n[MidiConversion.NOTE_START];
      int end = n[MidiConversion.NOTE_END];
      pitchCol.set(start, onset);
      for (int i = start + 1; i <= end; i++) {
        pitchCol.set(i, sustain);
      }
    }
    pitchCol.add(0, Utils.padString(pitch.toString() + octaveNum, padding, Utils.Alignment.CENTER));
    return pitchCol;
  }

  /**
   * Helper to the getPieceTable method. Removes empty columns on both ends of the list
   * generated by the getPieceTable method. An empty column qualifies as a column entirely
   * composed of rests, with no notes played.
   *
   * @param piece         the list generated by the getPieceTable method
   * @param emptyString   the String representing a rest, or empty
   * @param firstSearch   true if this is the first call to the function;
   *                      otherwise, this is the recursive call and will return the new list
   * @return the given list of list of strings without the empty columns on both ends
   */
  private List<List<String>> removeEmptyEndColumns(List<List<String>> piece, String emptyString,
                                                   boolean firstSearch) {
    int colsToRemove = -1;
    for (int p = 0; p < piece.size(); p++) {
      if (colsToRemove > -1) {
        break;
      }
      List<String> pitchCol = piece.get(p);
      for (int i = 1; i < pitchCol.size(); i++) {
        if (!pitchCol.get(i).equals(emptyString)) {
          colsToRemove = p;
          break;
        }
      }
    }
    while (colsToRemove > 0) {
      piece.remove(0);
      colsToRemove -= 1;
    }
    if (firstSearch) {
      return removeEmptyEndColumns(Utils.reverse(piece), emptyString, false);
    } else {
      return Utils.reverse(piece);
    }
  }

  @Override
  public String getLog() {
    return this.log.toString();
  }

  @Override
  public Map<Integer, Runnable> getKeyEventRunnables() {
    // no key events
    return new TreeMap<>();
  }

  @Override
  public void addListeners(MusicEditorController controller, KeyListener keyListener) {
    // no listeners to set
    return;
  }

  @Override
  public void update() {
    // nothing to update
    return;
  }
}
