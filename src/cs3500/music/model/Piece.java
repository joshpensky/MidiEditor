package cs3500.music.model;

import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a musical piece that can be edited in the editor.
 */
public final class Piece {
  private Map<Integer, Octave> octaves;
  private int tempo;

  /**
   * Default constructor.
   * Constructs a new {@code Piece} object.
   */
  protected Piece() {
    this.setTempo(0);
    this.octaves = new TreeMap<>();
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
  protected Piece(Piece other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("Cannot duplicate uninitialized piece.");
    }
    this.tempo = other.tempo;
    this.octaves = new TreeMap<>();
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
    return this.tempo == other.tempo;
  }

  @Override
  public int hashCode() {
    int result = this.octaves.hashCode();
    result = 31 * result + this.tempo;
    return result;
  }

  /**
   * Gets the length of this piece (the length of the longest octave).
   *
   * @return the length of this piece (measured in beats)
   */
  protected int length() {
    int length = 0;
    for (Integer i : this.octaves.keySet()) {
      length = Math.max(length, this.octaves.get(i).length());
    }
    return length;
  }

  /**
   * Adds a new note to the piece at the given location details.
   *
   * @param octave       the octave of the note
   * @param pitch        the pitch of the note
   * @param position     the starting position of the note (measured in beats)
   * @param duration     the duration of the note (measured in beats)
   * @param instrument   the instrument the note is played in [0, 127]
   * @param volume       the volume at which the note is played [0, 127]
   * @throws IllegalArgumentException if the given octave is not in range [1, 10], the pitch
   *                                  is uninitialized, the starting position is negative, the
   *                                  duration is 0 or negative, the instrument is not in range
   *                                  [0, 127], the volume is not in range [0, 127], or if a note
   *                                  already exists at the given position in the same pitch
   *                                  played on the same instrument
   */
  protected void addNote(int octave, Pitch pitch, int position, int duration, int instrument,
                         int volume) throws IllegalArgumentException {
    checkOctaveException(octave);
    this.octaves.get(octave).addNote(pitch, position, duration, instrument, volume);
  }

  /**
   * Removes the given note from the piece, if possible.
   *
   * @param octave       the octave of the note
   * @param pitch        the pitch of the note
   * @param position     the starting position of the note (measured in beats)
   * @param instrument   the instrument the note is played in [0, 127]
   * @throws IllegalArgumentException if the given octave is not in range [1, 10], the pitch
   *                                  is uninitialized, the starting position is negative, the
   *                                  duration is 0 or negative, the instrument is not in range
   *                                  [0, 127], the volume is not in range [0, 127], or if no note
   *                                  exists at the given position in the same pitch
   *                                  played on the same instrument
   */
  protected void removeNote(int octave, Pitch pitch, int position, int instrument)
      throws IllegalArgumentException {
    checkOctaveException(octave);
    this.octaves.get(octave).removeNote(pitch, position, instrument);
  }

  /**
   * Edits the pitch of a given note from the piece, if possible.
   *
   * @param octave       the octave of the note
   * @param pitch        the pitch of the note
   * @param position     the starting position of the note (measured in beats)
   * @param instrument   the instrument the note is played in [0, 127]
   * @param newPitch     the new pitch of the note
   * @throws IllegalArgumentException if the given octave is not in range [1, 10], the pitch
   *                                  is uninitialized, the starting position is negative, the
   *                                  instrument is not in range [0, 127], the new pitch is
   *                                  uninitialized, if no note exists at the given position, or
   *                                  a note already exists at the given position in the new pitch
   */
  protected void editPitch(int octave, Pitch pitch, int position, int instrument, Pitch newPitch)
      throws IllegalArgumentException {
    checkOctaveException(octave);
    this.octaves.get(octave).editPitch(pitch, position, instrument, newPitch);
  }

  /**
   * Edits the position of a given note from the piece, if possible.
   *
   * @param octave        the octave of the note
   * @param pitch         the pitch of the note
   * @param position      the starting position of the note (measured in beats)
   * @param instrument    the instrument the note is played in [0, 127]
   * @param newPosition   the new starting position of the note (measured in beats)
   * @throws IllegalArgumentException if the given octave is not in range [1, 10], the pitch
   *                                  is uninitialized, the starting position is negative, the
   *                                  instrument is not in range [0, 127], the new position is
   *                                  negative, if no note exists at the given position, or a
   *                                  note already exists at the new position in the given pitch
   */
  protected void editPosition(int octave, Pitch pitch, int position, int instrument,
                              int newPosition)
      throws IllegalArgumentException {
    checkOctaveException(octave);
    this.octaves.get(octave).editPosition(pitch, position, instrument, newPosition);
  }

  /**
   * Edits the duration of a given note from the piece, if possible.
   *
   * @param octave        the octave of the note
   * @param pitch         the pitch of the note
   * @param position      the starting position of the note (measured in beats)
   * @param instrument    the instrument the note is played in [0, 127]
   * @param newDuration   the new duration of the note (measured in beats)
   * @throws IllegalArgumentException if the given octave is not in range [1, 10], the pitch
   *                                  is uninitialized, the starting position is negative, the
   *                                  instrument is not in range [0, 127], the new duration is
   *                                  zero or negative, or if no note exists at the given position
   */
  protected void editDuration(int octave, Pitch pitch, int position, int instrument,
                              int newDuration)
      throws IllegalArgumentException {
    checkOctaveException(octave);
    this.octaves.get(octave).editDuration(pitch, position, instrument, newDuration);
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
   * Sets the tempo of this piece.
   *
   * @param tempo   the tempo to set the piece
   * @throws IllegalArgumentException if the given tempo is negative
   */
  public void setTempo(int tempo) throws IllegalArgumentException {
    if (tempo < 0) {
      throw new IllegalArgumentException("Cannot set negative tempo.");
    }
    this.tempo = tempo;
  }

  /**
   * Gets the tempo of this piece.
   *
   * @return the tempo of this piece
   */
  protected int getTempo() {
    return this.tempo;
  }

  /**
   * Returns a list of note data for every note in this piece. Note data is arranged the same as
   * described in {@link MusicEditorOperations#getNotes()}.
   *
   * @return a list of note data for every note in this piece
   */
  protected List<Integer[]> getNotes() {
    List<List<Integer[]>> allOctaves = new ArrayList<>();
    for (Integer i : this.octaves.keySet()) {
      allOctaves.add(this.octaves.get(i).getNotes(i));
    }
    List<Integer[]> allNotes = new ArrayList<>();
    for (List<Integer[]> list : allOctaves) {
      for (Integer[] arr : list) {
        allNotes.add(arr);
      }
    }
    return allNotes;
  }

  /**
   * Returns a list of note data for every note in this piece at the given beat. Data is arranged
   * the same as described in {@link MusicEditorOperations#getNotes()}.
   *
   * @param beat   the beat to check for notes
   * @return a list of note data for every note in this piece at the given beat
   */
  protected List<Integer[]> getNotesAtBeat(int beat) {
    List<List<Integer[]>> allOctaves = new ArrayList<>();
    for (Integer i : this.octaves.keySet()) {
      allOctaves.add(this.octaves.get(i).getNotesAtBeat(i, beat));
    }
    List<Integer[]> allNotes = new ArrayList<>();
    for (List<Integer[]> list : allOctaves) {
      for (Integer[] arr : list) {
        allNotes.add(arr);
      }
    }
    return allNotes;
  }
}
