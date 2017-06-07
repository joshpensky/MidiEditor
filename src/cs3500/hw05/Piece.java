package cs3500.hw05;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a musical piece that can be edited in the editor.
 */
public class Piece {
  private Map<Integer, Octave> octaves;
  private String title;
  private int measure;

  /**
   * Default constructor.
   * Constructs a new {@code Piece} object with a title and a measure.
   *
   * @param title      the title of the piece
   * @param measure    the measure of the piece (usually 3 or 4 beats)
   * @throws IllegalArgumentException if the given title is uninitialized, or if the measure is
   * negative
   */
  public Piece(String title, int measure) throws IllegalArgumentException {
    if (title == null) {
      throw new IllegalArgumentException("Cannot set piece to an uninitialized title.");
    } else if (measure < 0) {
      throw new IllegalArgumentException("Cannot set piece's measure to negative value.");
    }
    this.title = title;
    this.measure = measure;
    this.octaves = new HashMap<>();
    for (int i = 1; i <= 10; i++) {
      this.octaves.put(i, new Octave());
    }
  }

  /**
   * Copy constructor.
   * Constructs a copy of the given {@code Piece} object.
   *
   * @param other      the piece to be copied
   * @throws IllegalArgumentException if the given piece is uninitialized
   */
  public Piece(Piece other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("Cannot duplicate uninitialized piece.");
    }
    this.title = other.title;
    this.measure = other.measure;
    this.octaves = new HashMap<>();
    for (int i = 1; i <= 10; i++) {
      this.octaves.put(i, new Octave(other.octaves.get(i)));
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (!(o instanceof Piece)) {
      return false;
    }
    Piece other = (Piece) o;
    for (Integer i : this.octaves.keySet()) {
      if (!this.octaves.get(i).equals(other.octaves.get(i))) {
        return false;
      }
    }
    return this.title.equals(other.title)
        && this.measure == other.measure;
  }

  @Override
  public String toString() {
    if (this.isEmpty()) {
      return "";
    }
    return this.stringBuilder();
  }

  /**
   * Helper to the toString method. Creates a String representation of this piece, but only
   * with octaves being used.
   *
   * @return a String representation of this piece
   */
  private String stringBuilder() {
    List<List<String>> arr = this.getPieceTable();
    int octaveLength = arr.get(0).size();
    int lineNumPadding = Integer.toString(octaveLength).length();
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < octaveLength; i++) {
      if (i == 0) {
        builder.append(Utils.padString("", lineNumPadding, Utils.Alignment.RIGHT));
      } else {
        builder.append(Utils.padString(Integer.toString(i - 1), lineNumPadding,
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
    String empty = Utils.padString("", 5, Utils.Alignment.CENTER);
    int maxLength = 0;
    for (int i = 1; i <= 10; i++) {
      Octave octave = this.octaves.get(i);
      if (!octave.isEmpty()) {
        builder.add(octave.getOctaveTable(i, 5));
        maxLength = Math.max(maxLength, octave.size());
      }
    }
    List<List<String>> piece = new ArrayList<>();
    for (List<List<String>> octaveList : builder) {
      for (List<String> pitchCol : octaveList) {
        while (pitchCol.size() <= maxLength) {
          pitchCol.add(empty);
        }
        piece.add(pitchCol);
      }
    }
    return piece;
  }

  /**
   * Checks if this piece is empty, or has no notes.
   *
   * @return true if this piece is empty, false otherwise
   */
  private boolean isEmpty() {
    for (Integer i : this.octaves.keySet()) {
      if (!this.octaves.get(i).isEmpty()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Adds a new note to the piece.
   *
   * @throws IllegalArgumentException if the given note is uninitialized
   */
  public void addNote(int octave, Pitch pitch, int position, int duration)
      throws IllegalArgumentException {
    checkOctaveException(octave);
    this.octaves.get(octave).addNote(pitch, position, duration);
  }

  /**
   * Removes the given note from the piece, if possible.
   *
   * @throws IllegalArgumentException if the given note is uninitialized, or if the note does not
   * exist in the piece
   */
  public void removeNote(int octave, Pitch pitch, int position) throws IllegalArgumentException {
    checkOctaveException(octave);
    this.octaves.get(octave).removeNote(pitch, position);
  }

  /**
   * Edits the pitch of a given note from the piece, if possible.
   *
   * @param pitch   the new pitch of the note
   * @param octave  the octave of the new pitch
   * @throws IllegalArgumentException if the given note or pitch are uninitialized, or if the note
   * does not exist in the piece
   */
  public void editPitch(int octave, Pitch pitch, int position, Pitch newPitch)
      throws IllegalArgumentException {
    checkOctaveException(octave);
    this.octaves.get(octave).editPitch(pitch, position, newPitch);
  }

  /**
   * Edits the position of a given note from the piece, if possible.
   *
   * @param position   the new position of the note
   * @throws IllegalArgumentException if the given note is uninitialized, the position is
   * negative, or if the note does not exist in the piece
   */
  public void editPosition(int octave, Pitch pitch, int position, int newPosition)
      throws IllegalArgumentException {
    checkOctaveException(octave);
    this.octaves.get(octave).editPosition(pitch, newPosition, position);
  }

  /**
   * Edits the duration of a given note from the piece, if possible.
   *
   * @throws IllegalArgumentException if the given note is uninitialized, the duration is
   * negative, or if the note does not exist in the piece
   */
  public void editDuration(int octave, Pitch pitch, int position, int newDuration)
      throws IllegalArgumentException {
    checkOctaveException(octave);
    this.octaves.get(octave).editDuration(pitch, position, newDuration);
  }

  /**
   * Helper to the addNote, removeNote, editPitch, editPosition, and editDuration methods.
   * Checks if the given octave exists in a piece.
   *
   * @param octave   the octave to be checked
   */
  private void checkOctaveException(int octave) {
    if (!this.octaves.keySet().contains(octave)) {
      throw new IllegalArgumentException("Given octave does not exist.");
    }
  }

  /**
   * Merges this piece with the given one, without keeping the same references to the given piece.
   *
   * @param title      the new title of the merged piece
   * @param other      the piece to be merged with
   * @throws IllegalArgumentException if the given title or piece are uninitialized, or if the
   * measures of this and the given pieces aren't the same
   */
  public void merge(String title, Piece other) throws IllegalArgumentException {
    if (title == null) {
      throw new IllegalArgumentException("Cannot set merged piece to uninitialized title.");
    } else if (other == null) {
      throw new IllegalArgumentException("Cannot merge with uninitialized piece.");
    } else if (other.measure != this.measure) {
      throw new IllegalArgumentException("The measures of both pieces do not match.");
    }
    this.title = title;
    for (Integer i : this.octaves.keySet()) {
      this.octaves.get(i).merge(new Octave(other.octaves.get(i)));
    }
  }

  /**
   * Checks if the given title is the same as this piece's title.
   *
   * @param title   the title to be checked
   * @return true if the titles match, false otherwise
   * @throws IllegalArgumentException if the given title is uninitialized
   */
  boolean sameTitle(String title) throws IllegalArgumentException {
    if (title == null) {
      throw new IllegalArgumentException("Given title is uninitialized.");
    }
    return this.title.equals(title);
  }
}
