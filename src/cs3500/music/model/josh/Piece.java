package cs3500.music.model.josh;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a musical piece that can be edited in the editor.
 */
public final class Piece {
  private Map<Integer, Octave> octaves;
  private String title;
  private int tempo;

  /**
   * Default constructor.
   * Constructs a new {@code Piece} object with a title and a measure.
   *
   * @param title      the title of the piece
   * @throws IllegalArgumentException if the given title is uninitialized
   */
  public Piece(String title) throws IllegalArgumentException {
    if (title == null) {
      throw new IllegalArgumentException("Cannot set piece to an uninitialized title.");
    }
    this.title = title;
    this.setTempo(0);
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
    this.tempo = other.tempo;
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
        && this.tempo == other.tempo;
  }

  @Override
  public int hashCode() {
    int result = this.octaves.hashCode();
    result = 31 * result + this.title.hashCode();
    return result;
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
    int maxLength = this.length();
    for (int i = 1; i <= 10; i++) {
      Octave octave = this.octaves.get(i);
      builder.add(octave.getOctaveTable(i, columnWidth));
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
   * Gets the length of this piece (the length of the longest octave).
   *
   * @return the length of this piece (measured in beats)
   */
  int length() {
    int length = 0;
    for (Integer i : this.octaves.keySet()) {
      length = Math.max(length, this.octaves.get(i).length());
    }
    return length;
  }

  /**
   * Adds a new note to the piece.
   *
   * @throws IllegalArgumentException if the given note is uninitialized
   */
  public void addNote(int start, int end, int instrument, int pitch, int volume)
      //int octave, Pitch pitch, int position, int duration, int instrument, int volume)
      throws IllegalArgumentException {
    int octave = Utils.getOctave(pitch);
    checkOctaveException(octave);
    this.octaves.get(octave).addNote(Utils.getPitch(pitch), start, Utils.getDuration(start, end),
        instrument, volume);
  }

  /**
   * Removes the given note from the piece, if possible.
   *
   * @throws IllegalArgumentException if the given note is uninitialized, or if the note does not
   *                                  exist in the piece
   */
  void removeNote(int octave, Pitch pitch, int position) throws IllegalArgumentException {
    checkOctaveException(octave);
    this.octaves.get(octave).removeNote(pitch, position);
  }

  /**
   * Edits the pitch of a given note from the piece, if possible.
   *
   * @param pitch   the new pitch of the note
   * @param octave  the octave of the new pitch
   * @throws IllegalArgumentException if the given note or pitch are uninitialized, or if the note
   *                                  does not exist in the piece
   */
  void editPitch(int octave, Pitch pitch, int position, Pitch newPitch)
    throws IllegalArgumentException {
    checkOctaveException(octave);
    this.octaves.get(octave).editPitch(pitch, position, newPitch);
  }

  /**
   * Edits the position of a given note from the piece, if possible.
   *
   * @param position   the new position of the note
   * @throws IllegalArgumentException if the given note is uninitialized, the position is
   *                                  negative, or if the note does not exist in the piece
   */
  void editPosition(int octave, Pitch pitch, int position, int newPosition)
    throws IllegalArgumentException {
    checkOctaveException(octave);
    this.octaves.get(octave).editPosition(pitch, position, newPosition);
  }

  /**
   * Edits the duration of a given note from the piece, if possible.
   *
   * @throws IllegalArgumentException if the given note is uninitialized, the duration is
   *                                  negative, or if the note does not exist in the piece
   */
  void editDuration(int octave, Pitch pitch, int position, int newDuration)
    throws IllegalArgumentException {
    checkOctaveException(octave);
    this.octaves.get(octave).editDuration(pitch, position, newDuration);
  }

  /**
   * Helper to the addNote, removeNote, editNotePitch, editNotePosition, and editNoteDuration
   * methods. Checks if the given octave exists in a piece.
   *
   * @param octave   the octave to be checked
   */
  private void checkOctaveException(int octave) {
    if (!this.octaves.keySet().contains(octave)) {
      throw new IllegalArgumentException("Given octave does not exist.");
    }
  }

  /**
   * Overlays the given piece on this one, creating different references than the given piece.
   *
   * @param other      the piece to be overlaid on top
   * @throws IllegalArgumentException if the given piece is uninitialized
   */
  void overlay(Piece other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("Cannot merge with uninitialized piece.");
    }
    for (Integer i : this.octaves.keySet()) {
      this.octaves.get(i).overlay(new Octave(other.octaves.get(i)));
    }
  }

  /**
   * Moves all octaves in this piece a given distance, either positive or negative. If the
   * distance is 0, it does not move anything.
   *
   * @param distance   the distance (measured in beats) to move all notes in the octave
   * @throws IllegalArgumentException if moving a note the given distance results in a negative
   *                                  position
   */
  void move(int distance) {
    if (distance != 0) {
      for (Integer i : this.octaves.keySet()) {
        this.octaves.get(i).move(distance);
      }
    }
  }

  /**
   * Gets the title of this piece.
   *
   * @return the title of this piece
   */
  String getTitle() {
    return this.title;
  }

  int getTempo() {
    return this.tempo;
  }

  public void setTempo(int tempo) {
    if (tempo < 0) {
      throw new IllegalArgumentException("Cannot set negative tempo.");
    }
    this.tempo = tempo;
  }
}
