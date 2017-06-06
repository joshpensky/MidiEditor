package cs3500.hw05;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a musical piece that can be edited in the editor.
 */
public class Piece {
  private List<Note> notes;
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
    this.notes = new ArrayList<>();
  }

  /**
   * Starter Constructor.
   * Constructs a new {@code Piece} object using the given notes.
   *
   * @param title       the title of the piece
   * @param measure    the measure of the piece (usually 3 or 4 beats)
   * @param notes      the notes to be added to the piece
   * @throws IllegalArgumentException if the given title or notes are uninitialized, or if the
   * measure is negative
   */
  public Piece(String title, int measure, Note... notes) throws IllegalArgumentException {
    if (title == null) {
      throw new IllegalArgumentException("Cannot set piece to an uninitialized title.");
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
    this.title = title;
    this.measure = measure;
    this.notes = new ArrayList<>();
    for (Note n : notes) {
      this.notes.add(new Note(n));
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
    this.notes = new ArrayList<>();
    for (Note n : piece.notes) {
      this.notes.add(new Note(n));
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
    for (Note n : other.notes) {
      if (!this.notes.contains(n)) {
        return false;
      }
    }
    return this.measure == other.measure;
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
   * @param n      the note to be added
   * @throws IllegalArgumentException if the given note is uninitialized
   */
  public void addNote(Note n) throws IllegalArgumentException {
    if (n == null) {
      throw new IllegalArgumentException("Cannot add uninitialized note.");
    } else if (this.notes.contains(n)) {
      throw new IllegalArgumentException("Piece already contains the given note.");
    }
    this.notes.add(n);
  }

  /**
   * Removes the given note from the piece, if possible.
   *
   * @param n      the note to be removed
   * @throws IllegalArgumentException if the given note is uninitialized, or if the note does not
   * exist in the piece
   */
  public void removeNote(Note n) throws IllegalArgumentException {
    this.noteExistsCheck(n, "Cannot remove uninitialized note.");
    this.notes.remove(n);
  }

  /**
   * Edits the pitch of a given note from the piece, if possible.
   *
   * @param n       the note to be edited
   * @param pitch   the new pitch of the note
   * @param octave  the octave of the new pitch
   * @throws IllegalArgumentException if the given note or pitch are uninitialized, or if the note
   * does not exist in the piece
   */
  public void editPitch(Note n, Pitch pitch, Octave octave) throws IllegalArgumentException {
    this.noteExistsCheck(n, "Cannot edit pitch of uninitialized note.");
    this.notes.get(this.notes.indexOf(n)).setPitch(pitch, octave);
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
    this.noteExistsCheck(n, "Cannot edit duration of uninitialized note.");
    this.notes.get(this.notes.indexOf(n)).setDuration(duration);
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
    this.noteExistsCheck(n,"Cannot edit position of uninitialized note.");
    this.notes.get(this.notes.indexOf(n)).setPosition(position);
  }

  /**
   * Helper to the removeNote, editPitch, editDuration, and editPosition methods. Checks if the
   * note is null or the piece does not contain the given note, and, if so, throws an exception.
   * @param n         the note to be checked
   * @param nullMsg   the message to be thrown with the error if the note is uninitialized
   * @throws IllegalArgumentException if the note is uninitialized or this piece does not contain
   * the given note
   */
  private void noteExistsCheck(Note n, String nullMsg) {
    if (n == null) {
      throw new IllegalArgumentException(nullMsg);
    } else if (!this.notes.contains(n)) {
      throw new IllegalArgumentException("Note does not exist in piece.");
    }
  }

  /**
   * Merges this piece with the given one, combing all notes together.
   * @param name       the new title of the merged piece
   * @param other      the piece to be merged with
   * @throws IllegalArgumentException if the given title or piece are uninitialized
   */
  public void merge(String name, Piece other) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Cannot set piece to an uninitialized title.");
    } else if (other == null) {
      throw new IllegalArgumentException("Cannot merge with uninitialized piece.");
    }
    this.title = name;
    if (!other.equals(this)) {
      for (Note n : other.notes) {
        if (!this.notes.contains(n)) {
          this.notes.add(new Note(n));
        }
      }
    }
  }
}
