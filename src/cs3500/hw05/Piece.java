package cs3500.hw05;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a musical piece that can be edited in the editor.
 */
public class Piece {
  private List<Note> notes;
  private String name;
  private int measure;

  /**
   * Constructs a brand new {@code Piece} object with a name and a measure.
   *
   * @param name       the name of the piece
   * @param measure    the measure of the piece (usually 3 or 4 beats)
   * @throws IllegalArgumentException if the given name is uninitialized, or if the measure is
   * negative
   */
  public Piece(String name, int measure) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Cannot set piece to an uninitialized name.");
    } else if (measure < 0) {
      throw new IllegalArgumentException("Cannot set piece's measure to negative value.");
    }
    this.name = name;
    this.measure = measure;
    this.notes = new ArrayList<>();
  }

  /**
   * Constructs a new {@code Piece} object using the given notes.
   *
   * @param name       the name of the piece
   * @param measure    the measure of the piece (usually 3 or 4 beats)
   * @param notes      the notes to be added to the piece
   * @throws IllegalArgumentException if the given name or notes are uninitialized, or if the
   * measure is negative
   */
  public Piece(String name, int measure, Note... notes) throws IllegalArgumentException{
    if (name == null) {
      throw new IllegalArgumentException("Cannot set piece to an uninitialized name.");
    } else if (measure < 0) {
      throw new IllegalArgumentException("Cannot set piece's measure to negative value.");
    } else if (notes == null) {
      throw new IllegalArgumentException("Cannot give piece uninitialized notes.");
    }
    for (Note n : notes) {
      if (n == null) {
        throw new IllegalArgumentException("Cannot give piece uninitialized notes.");
      }
    }
    this.name = name;
    this.measure = measure;
    this.notes = new ArrayList<>();
    for (Note n : notes) {
      this.notes.add(new Note(n));
    }
  }

  /**
   * Constructs a duplicate {@code Piece} using the given piece, but with different references.
   *
   * @param name       the name of the piece
   * @param piece      the piece to be copied
   * @throws IllegalArgumentException if the given name or piece are uninitialized
   */
  public Piece(String name, Piece piece) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Cannot set piece to an uninitialized name.");
    } else if (piece == null) {
      throw new IllegalArgumentException("Cannot duplicate uninitialized piece.");
    }
    this.name = name;
    this.measure = piece.measure;
    this.notes = new ArrayList<>();
    for (Note n : piece.notes) {
      this.notes.add(new Note(n));
    }
  }

  @Override
  public boolean equals(Object o) {
    return false;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public String toString() {
    return "";
  }

  /**
   * Adds a new note to the piece.
   *
   * @param n      the note to be added
   * @throws IllegalArgumentException if the given note is uninitialized
   */
  public void addNote(Note n) throws IllegalArgumentException {

  }

  /**
   * Removes the given note from the piece, if possible.
   *
   * @param n      the note to be removed
   * @throws IllegalArgumentException if the given note is uninitialized, or if the note does not
   * exist in the piece
   */
  public void removeNote(Note n) throws IllegalArgumentException {

  }

  /**
   * Edits the pitch of a given note from the piece, if possible.
   *
   * @param n       the note to be edited
   * @param pitch   the new pitch of the note
   * @throws IllegalArgumentException if the given note or pitch are uninitialized, or if the note
   * does not exist in the piece
   */
  public void editPitch(Note n, PitchType pitch) throws IllegalArgumentException {

  }

  /**
   * Edits the duration of a given note from the piece, if possible.
   *
   * @param n          the note to be edited
   * @param duration   the new duration of the note
   * @throws IllegalArgumentException if the given note is uninitialized, the duration is
   * negative, or if the note does not exist in the piece
   */
  public void editDuration(Note n, int duration) throws IllegalArgumentException {

  }

  /**
   * Edits the position of a given note from the piece, if possible.
   *
   * @param n          the note to be edited
   * @param position   the new position of the note
   * @throws IllegalArgumentException if the given note is uninitialized, the position is
   * negative, or if the note does not exist in the piece
   */
  public void editPosition(Note n, int position) throws IllegalArgumentException {

  }

  /**
   * Merges this piece with the given one, combing all notes together.
   * @param name       the new name of the merged piece
   * @param other      the piece to be merged with
   * @throws IllegalArgumentException if the given name or piece are uninitialized
   */
  public void merge(String name, Piece other) throws IllegalArgumentException {

  }
}
