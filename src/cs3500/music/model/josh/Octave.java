package cs3500.music.model.josh;

import cs3500.music.util.MidiConversion;

import java.util.*;

/**
 * Represents an octave in a piece.
 */
final class Octave {
  private Map<Pitch, List<Note>> pitches;

  /**
   * Default constructor.
   * Creates a new {@code Octave} object.
   */
  Octave() {
    this.pitches = new TreeMap<>();
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
    this.pitches = new TreeMap<>();
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
  public int hashCode() {
    return this.pitches.hashCode();
  }

  /**
   * Creates a 2-dimensional list of Strings, or table of Strings, representing an octave. The
   * inner lists (the columns) represent the different pitches, and the notes in those pitches.
   *
   * @param octaveNum   the number of this octane
   * @param padding     the amount of spaced padding for each String in the list
   * @return a 2-dimensional list of Strings representing this octane
   */
  List<List<String>> getOctaveTable(int octaveNum, int padding) {
    String empty = Utils.padString("", padding, Utils.Alignment.CENTER);
    List<List<String>> builder = new ArrayList<>();
    int maxLength = this.length();
    for (Pitch p : Pitch.values()) {
      builder.add(getPitchColumn(p, octaveNum, padding, maxLength));
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
  private List<String> getPitchColumn(Pitch pitch, int octaveNum, int padding, int length) {
    List<String> pitchCol = new ArrayList<>();
    List<Note> notes = this.pitches.get(pitch);
    String empty = Utils.padString("", padding, Utils.Alignment.CENTER);
    for (int i = 0; i <= length; i++) {
      pitchCol.add(empty);
    }
    String onset = Utils.padString("X", padding, Utils.Alignment.CENTER);
    String sustain = Utils.padString("|", padding, Utils.Alignment.CENTER);
    for (Note n : notes) {
      int start = n.getStartPos();
      int end = n.getEndPos();
      pitchCol.set(start, onset);
      for (int i = start + 1; i <= end; i++) {
        pitchCol.set(i, sustain);
      }
    }
    pitchCol.add(0, Utils.padString(pitch.toString() + octaveNum, padding, Utils.Alignment.CENTER));
    return pitchCol;
  }

  /**
   * Checks if this octave is empty, or has no notes.
   *
   * @return true if this octave is empty, false otherwise
   */
  boolean isEmpty() {
    for (List<Note> list : this.pitches.values()) {
      if (list.size() != 0) {
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
  int length() {
    if (this.isEmpty()) {
      return 0;
    }
    int longest = 0;
    for (Pitch p : this.pitches.keySet()) {
      List<Note> pitchList = this.pitches.get(p);
      int length = 0;
      for (Note note : pitchList) {
        length = Math.max(length, note.getEndPos());
      }
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
   *                                  position are negative, if the duration is zero, or if a note
   *                                  already exists at the given position
   */
  void addNote(Pitch pitch, int position, int duration, int instrument, int volume) throws
    IllegalArgumentException {
    this.checkPitchException(pitch);
    this.addNoteInOrder(pitch, new Note(position, duration, instrument, volume));
  }

  /**
   * Removes the note from this piece at the given pitch with the same starting position.
   *
   * @param pitch      the pitch of the note to be removed
   * @param position   the starting position of the note to be removed
   * @throws IllegalArgumentException if the given pitch is uninitialized, or if there is no note
   *                                  at the given position
   */
  void removeNote(Pitch pitch, int position) throws IllegalArgumentException {
    this.checkPitchException(pitch);
    List<Note> pitchList = this.pitches.get(pitch);
    for (Note n : pitchList) {
      if (n.getStartPos() == position) {
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
   *                                  there is no note at the given position
   */
  void editPitch(Pitch pitch, int position, Pitch newPitch) throws IllegalArgumentException {
    this.checkPitchException(pitch);
    this.checkPitchException(newPitch);
    if (!pitch.equals(newPitch)) {
      List<Note> pitchList = this.pitches.get(pitch);
      for (Note n : pitchList) {
        if (n.getStartPos() == position) {
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
   *                                  the given position, or if the new position is negative
   */
  void editPosition(Pitch pitch, int position, int newPosition) throws IllegalArgumentException {
    this.checkPitchException(pitch);
    if (position != newPosition) {
      List<Note> pitchList = this.pitches.get(pitch);
      for (Note n : pitchList) {
        if (n.getStartPos() == position) {
          Note test = new Note(n);
          test.setStartPos(newPosition);
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
   *                                  the given position, or if the new duration is negative or zero
   */
  void editDuration(Pitch pitch, int position, int newDuration) throws IllegalArgumentException {
    this.checkPitchException(pitch);
    List<Note> pitchList = this.pitches.get(pitch);
    for (Note n : pitchList) {
      if (n.getStartPos() == position) {
        n.setDuration(newDuration);
        return;
      }
    }
    throw new IllegalArgumentException("Note does not exist at given position.");
  }

  /**
   * Helper to the add, remove, editNotePitch, editNotePosition, and editNoteDuration methods.
   * Checks if the given pitch is uninitialized, and if so, throws an exception.
   *
   * @param pitch   the pitch to be checked
   */
  private void checkPitchException(Pitch pitch) {
    if (pitch == null) {
      throw new IllegalArgumentException("Pitch is uninitialized.");
    }
  }

  /**
   * Helper to the add and editNotePitch methods. Adds the given note at a given pitch in order
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
      int comparison = Integer.compare(pitchList.get(i).getStartPos(), note.getStartPos());
      if (comparison == 0 && pitchList.get(i).getInstrument() == note.getInstrument()) {
        throw new IllegalArgumentException("Note already exists at this position.");
      } else if (comparison > 0) {
        break;
      }
      addIndex++;
    }
    pitchList.add(addIndex, note);
  }

  /**
   * Creates copies of all of the notes from the given octave and adds them to this octave if a
   * note does not already exist at the same position.
   *
   * @param other   the octave to be overlaid
   * @throws IllegalArgumentException if given octave is uninitialized
   */
  void overlay(Octave other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("Given octave is uninitialized.");
    }
    for (Pitch p : this.pitches.keySet()) {
      for (Note n : other.pitches.get(p)) {
        try {
          this.addNoteInOrder(p, new Note(n));
        } catch (IllegalArgumentException e) {
          // Note already exists at same position in this octave, do not add it to overlay
        }
      }
    }
  }

  /**
   * Moves all notes in this octave a given distance, either positive or negative. If the
   * distance is 0, it does not move anything.
   *
   * @param distance   the distance (measured in beats) to move all notes in the octave
   * @throws IllegalArgumentException if moving a note the given distance results in a negative
   *                                  position
   */
  void move(int distance) {
    if (distance != 0) {
      for (Pitch p : this.pitches.keySet()) {
        List<Note> pitchList;
        if (distance < 0) {
          pitchList = this.pitches.get(p);
        } else {
          pitchList = Utils.reverse(this.pitches.get(p));
        }
        for (Note n : pitchList) {
          n.setStartPos(n.getStartPos() + distance);
        }
      }
    }
  }

  protected List<Integer[]> getNotes(int octave) {
    List<Integer[]> notes = new ArrayList<>();
    for (Pitch p : this.pitches.keySet()) {
      for (Note n : this.pitches.get(p)) {
        Integer[] arr = n.getArray();
        arr[3] = MidiConversion.getMidiPitch(octave, p);
        notes.add(arr);
      }
    }
    return notes;
  }

  public List<Integer[]> getNotesAtBeat(int octave, int beat) {
    List<Integer[]> notes = new ArrayList<>();
    for (Pitch p : this.pitches.keySet()) {
      for (Note n : this.pitches.get(p)) {
        if (n.getStartPos() <= beat && n.getEndPos() >= beat) {
          Integer[] arr = n.getArray();
          arr[3] = MidiConversion.getMidiPitch(octave, p);
          notes.add(arr);
        }
      }
    }
    return notes;
  }
}
