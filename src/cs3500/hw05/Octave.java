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
  Octave() {
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
   * @throws IllegalArgumentException if the given octave is uninitialized
   */
  Octave(Octave other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("Given octave is uninitialized.");
    }
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (!(o instanceof Octave)) {
      return false;
    }
    Octave other = (Octave) o;
    for (Pitch p : this.pitches.keySet()) {
      List<Note> thisPitch = this.pitches.get(p);
      List<Note> otherPitch = other.pitches.get(p);
      for (Note n : thisPitch) {
        if (!otherPitch.contains(n)) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public String toString() {
    // TODO
    return "";
  }

  /**
   * Checks if this octave is empty, or has no notes.
   *
   * @return true if this octave is empty, false otherwise
   */
  public boolean isEmpty() {
    for (List<Note> list : this.pitches.values()) {
      if (list.isEmpty()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Gets the length of this octave.
   *
   * @return the length of this octave
   */
  int getLength() {
    int longest = 0;
    for (Pitch p : this.pitches.keySet()) {
      List<Note> pitchList = this.pitches.get(p);
      int length = pitchList.get(pitchList.size() - 1).getEndPoint();
      longest = Math.max(longest, length);
    }
    return longest;
  }

  /**
   * Adds a new note to this octane at the given pitch, with the specified duration and
   * starting position in the piece.
   *
   * @param pitch      the pitch of the note
   * @param position   the starting position of the note
   * @param duration   the duration of the note (measured in beats)
   * @throws IllegalArgumentException if the given pitch is uninitialized, if the duration or
   * position are negative, if the duration is zero, or if a note already exists at the given
   * position
   */
  void addNote(Pitch pitch, int position, int duration) throws IllegalArgumentException {
    this.checkPitchException(pitch);
    this.addNoteInOrder(pitch, new Note(position, duration));
  }

  /**
   * Removes the note from this piece at the given pitch with the same starting position.
   *
   * @param pitch      the pitch of the note to be removed
   * @param position   the starting position of the note to be removed
   * @throws IllegalArgumentException if the given pitch is uninitialized, or if there is no note
   * at the given position
   */
  void removeNote(Pitch pitch, int position) throws IllegalArgumentException {
    this.checkPitchException(pitch);
    List<Note> pitchList = this.pitches.get(pitch);
    for (Note n : pitchList) {
      if (n.getPosition() == position) {
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
  void editPitch(Pitch pitch, int position, Pitch newPitch) throws IllegalArgumentException {
    this.checkPitchException(pitch);
    this.checkPitchException(newPitch);
    if (!pitch.equals(newPitch)) {
      List<Note> pitchList = this.pitches.get(pitch);
      for (Note n : pitchList) {
        if (n.getPosition() == position) {
          Note test = new Note(n);
          this.addNoteInOrder(newPitch, test);
          pitchList.remove(n);
          return;
        }
      }
      throw new IllegalArgumentException("Note does not exist at given position.");
    }
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
  void editPosition(Pitch pitch, int position, int newPosition) throws IllegalArgumentException {
    this.checkPitchException(pitch);
    if (position != newPosition) {
      List<Note> pitchList = this.pitches.get(pitch);
      for (Note n : pitchList) {
        if (n.getPosition() == position) {
          Note test = new Note(n);
          test.setPosition(newPosition);
          this.addNoteInOrder(pitch, test);
          pitchList.remove(n);
          return;
        }
      }
      throw new IllegalArgumentException("Note does not exist at given position.");
    }
  }

  /**
   * Edits the pitch of a note at the given pitch and starting position.
   *
   * @param pitch         the pitch of the note to be edited
   * @param position      the starting position of the note to be edited
   * @param newDuration   the new duration of the edited note
   * @throws IllegalArgumentException if the given pitch is uninitialized, if there is no note at
   * the given position, or if the new duration is negative or zero
   */
  void editDuration(Pitch pitch, int position, int newDuration) throws IllegalArgumentException {
    this.checkPitchException(pitch);
    List<Note> pitchList = this.pitches.get(pitch);
    for (Note n : pitchList) {
      if (n.getPosition() == position) {
        n.setDuration(newDuration);
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
   * @throws IllegalArgumentException if the given note is uninitialized
   */
  private void addNoteInOrder(Pitch pitch, Note note) throws IllegalArgumentException {
    if (note == null) {
      throw new IllegalArgumentException("Cannot add uninitialized note.");
    }
    List<Note> pitchList = this.pitches.get(pitch);
    int addIndex = 0;
    for (int i = 0; i < pitchList.size(); i++) {
      int comparison = Integer.compare(pitchList.get(i).getPosition(), note.getPosition());
      if (comparison == 0) {
        throw new IllegalArgumentException("Note already exists at this position.");
      } else if (comparison < 0) {
        addIndex = i;
        break;
      }
    }
    pitchList.add(addIndex, note);
  }

  /**
   * Creates copies of all of the notes from the given octave and adds them to this octave if a
   * note does not already exist at the same position.
   *
   * @param other   the octave to be merged with
   * @throws IllegalArgumentException if given octave is uninitialized
   */
  void merge(Octave other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("Given octave is uninitialized.");
    }
    for (Pitch p : this.pitches.keySet()) {
      for (Note n : other.pitches.get(p)) {
        try {
          this.addNoteInOrder(p, new Note(n));
        } catch (IllegalArgumentException e) {
          // Note already exists at same position in this octave, do not add it to merge
        }
      }
    }
  }
}
