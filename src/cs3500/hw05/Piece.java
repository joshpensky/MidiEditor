package cs3500.hw05;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a musical piece that can be edited in the editor.
 */
public class Piece {
  private Map<Integer, Octave> octaves;
  private String title;
  private int measure;

  /**
   * New Constructor.
   * Constructs a brand new {@code Piece} object with a title and a measure.
   *
   * @param title       the title of the piece
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
   * Duplicate Constructor.
   * Constructs a duplicate {@code Piece} using the given piece, but with different references.
   *
   * @param title       the title of the piece
   * @param piece      the piece to be copied
   * @throws IllegalArgumentException if the given title or piece are uninitialized
   */
  public Piece(String title, Piece piece) throws IllegalArgumentException {
    if (title == null) {
      throw new IllegalArgumentException("Cannot set piece to an uninitialized title.");
    } else if (piece == null) {
      throw new IllegalArgumentException("Cannot duplicate uninitialized piece.");
    }
    this.title = title;
    this.measure = piece.measure;
    this.octaves = new HashMap<>();
    for (int i = 1; i <= 10; i++) {
      this.octaves.put(i, new Octave());
    }
    // TODO
    // Create copy of piece for octaves
  }

  @Override
  public boolean equals(Object o) {
    // TODO
    return false;
  }

  @Override
  public int hashCode() {
    // TODO
    return 0;
  }

  @Override
  public String toString() {
    // TODO
    return "";
  }

  /**
   * Adds a new note to the piece.
   *
   * @throws IllegalArgumentException if the given note is uninitialized
   */
  public void addNote(int octave, Pitch pitch, int position, int duration)
                      throws IllegalArgumentException {
    checkOctaveException(octave);
    this.octaves.get(octave).add(pitch, position, duration);
  }

  /**
   * Removes the given note from the piece, if possible.
   *
   * @throws IllegalArgumentException if the given note is uninitialized, or if the note does not
   * exist in the piece
   */
  public void removeNote(int octave, Pitch pitch, int position)
                         throws IllegalArgumentException {
    checkOctaveException(octave);
    this.octaves.get(octave).remove(pitch, position);
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
    this.octaves.get(octave).add(pitch, newPosition, position);
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

  private void checkOctaveException(int octave) {
    if (octave < 1 || octave > 10) {
      throw new IllegalArgumentException("Given octave does not exist.");
    }
  }

  /**
   * Merges this piece with the given one, combing all notes together.
   * @param name       the new title of the merged piece
   * @param other      the piece to be merged with
   * @throws IllegalArgumentException if the given title or piece are uninitialized
   */
  public void merge(String name, Piece other) throws IllegalArgumentException {
    // TODO
    return;
  }
}
