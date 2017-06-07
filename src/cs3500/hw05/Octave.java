package cs3500.hw05;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Represents an octave in a piece.
 */
public class Octave {
  private Map<Pitch, List<Note>> pitches;

  /**
   * Default constructor.
   * Creates a new {@code Octave} object.
   */
  public Octave() {
    this.pitches = new HashMap<>();
    for (Pitch p : Pitch.values()) {
      this.pitches.put(p, new ArrayList<>());
    }
  }

  /**
   * Copy constructor.
   * Creates a copy of the given {@code Octave} object.
   *
   * @param other    the octave to be copied
   */
  public Octave(Octave other) {
    this.pitches = new HashMap<>();
    for (Pitch p : Pitch.values()) {
      List<Note> newNotes = new ArrayList<>();
      List<Note> pitchList = other.pitches.get(p);
      for (Note n : pitchList) {
        newNotes.add(new Note(n));
      }
      this.pitches.put(p, newNotes);
    }
  }

  @Override
  public String toString() {
    return "";
  }

  /**
   * Checks if this octave is empty, or has no notes.
   *
   * @return true if this octave is empty, false otherwise
   */
  public boolean emptyOctave() {
    for (List<Note> list : this.pitches.values()) {
      if (list.size() > 0) {
        return false;
      }
    }
    return true;
  }

  /**
   * Adds a new note to this octane at the given pitch, with the specified duration and
   * starting position in the piece.
   *
   * @param pitch      the pitch of the note
   * @param duration   the duration of the note (measured in beats)
   * @param position   the starting position of the note
   * @throws IllegalArgumentException if the given pitch is uninitialized, if the duration or
   * position are negative, or if s note already exists at the given position
   */
  public void add(Pitch pitch, int duration, int position) throws IllegalArgumentException {
    this.checkPitchException(pitch);
    List<Note> pitchList = this.pitches.get(pitch);
    for (Note n : pitchList) {
      if (n.samePosition(position)) {
        throw new IllegalArgumentException("Note already exists at given position.");
      }
    }
    this.addNoteInOrder(pitch, new Note(position, duration));
    this.checkForOverlays(pitch);
  }

  /**
   * Removes the note from this piece at the given pitch with the same starting position.
   *
   * @param pitch      the pitch of the note to be removed
   * @param position   the starting position of the note to be removed
   * @throws IllegalArgumentException if the given pitch is uninitialized, or if there is no note
   * at the given position
   */
  public void remove(Pitch pitch, int position) throws IllegalArgumentException {
    this.checkPitchException(pitch);
    List<Note> pitchList = this.pitches.get(pitch);
    for (Note n : pitchList) {
      if (n.samePosition(position)) {
        pitchList.remove(n);
        return;
      }
    }
    throw new IllegalArgumentException("There is no note at position " + position + ".");
  }

  /**
   * Edits the pitch of a note at the given pitch and starting position.
   *
   * @param pitch      the pitch of the note to be edited
   * @param position   the starting position of the note to be edited
   * @param newPitch   the new pitch of the edited note
   * @throws IllegalArgumentException if either of the given pitches are uninitialized, or if
   * there is no note at the given position
   */
  public void editPitch(Pitch pitch, int position, Pitch newPitch) throws IllegalArgumentException {
    this.checkPitchException(pitch);
    this.checkPitchException(newPitch);
    List<Note> pitchList = this.pitches.get(pitch);
    for (Note n : pitchList) {
      if (n.samePosition(position)) {
        this.addNoteInOrder(newPitch, pitchList.remove(pitchList.indexOf(n)));
        return;
      }
    }
    throw new IllegalArgumentException("Note does not exist at given position.");
  }

  /**
   * Edits the pitch of a note at the given pitch and starting position.
   *
   * @param pitch         the pitch of the note to be edited
   * @param position      the starting position of the note to be edited
   * @param newPosition   the new position of the edited note
   * @throws IllegalArgumentException if the given pitch is uninitialized, if there is no note at
   * the given position, or if the new position is negative
   */
  public void editPosition(Pitch pitch, int position, int newPosition)
                           throws IllegalArgumentException {
    this.checkPitchException(pitch);
    List<Note> pitchList = this.pitches.get(pitch);
    for (Note n : pitchList) {
      if (n.samePosition(newPosition)) {
        throw new IllegalArgumentException("Note already exists at the new position.");
      } else if (n.samePosition(position)) {
        n.setPosition(newPosition);
        this.checkForOverlays(pitch);
        return;
      }
    }
    throw new IllegalArgumentException("Note does not exist at given position.");
  }

  /**
   * Edits the pitch of a note at the given pitch and starting position.
   *
   * @param pitch         the pitch of the note to be edited
   * @param position      the starting position of the note to be edited
   * @param newDuration   the new duration of the edited note
   * @throws IllegalArgumentException if the given pitch is uninitialized, if there is no note at
   * the given position, or if the new duration is negative
   */
  public void editDuration(Pitch pitch, int position, int newDuration)
                           throws IllegalArgumentException {
    this.checkPitchException(pitch);
    List<Note> pitchList = this.pitches.get(pitch);
    for (Note n : pitchList) {
      if (n.samePosition(position)) {
        n.setDuration(newDuration);
        this.checkForOverlays(pitch);
        return;
      }
    }
    throw new IllegalArgumentException("Note does not exist at given position.");
  }

  /**
   * Helper to the add, remove, editPitch, editPosition, and editDuration methods. Checks if
   * the given pitch is uninitialized, and if so, throws an exception.
   *
   * @param pitch   the pitch to be checked
   */
  private void checkPitchException(Pitch pitch) {
    if (pitch == null) {
      throw new IllegalArgumentException("Pitch is uninitialized.");
    }
  }

  /**
   * Helper to the add and editPitch methods. Adds the given note at a given pitch in order
   * of starting times.
   *
   * @param pitch   the pitch the note is at
   * @param note    the note to be added
   */
  private void addNoteInOrder(Pitch pitch, Note note) {
    List<Note> pitchList = this.pitches.get(pitch);
    int addIndex = 0;
    for (int i = 0; i < pitchList.size(); i++) {
      if (!pitchList.get(i).startsBefore(note)) {
        addIndex = i;
        break;
      }
    }
    pitchList.add(addIndex, note);
    this.checkForOverlays(pitch);
  }

  /**
   * Helper to the addNoteInOrder method. Checks for overlaying errors in the octave at the given
   * pitch from adding new notes.
   *
   * @param pitch   the pitch to be checked
   */
  private void checkForOverlays(Pitch pitch) {
    List<Note> pitchList = this.pitches.get(pitch);
    for (int i = 1; i < pitchList.size(); i++) {
      pitchList.get(i - 1).overlay(pitchList.get(i));
    }
  }
}
