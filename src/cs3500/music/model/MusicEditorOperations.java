package cs3500.music.model;

import java.util.List;

/**
 * Represents all of the operations that the model of a Midi Editor should have, including
 * creating new pieces, adding/removing/editing notes, setting tempo, getting the length of a
 * piece, getting notes, and creating a String representation of the currently opened piece.
 */
public interface MusicEditorOperations {
  /**
   * Creates a new piece of music and opens it.
   */
  void create();

  /**
   * Returns a String representation of the current state of the currently opened piece, or an
   * empty string if there is no piece opened.
   *
   * @return a String representation of the opened piece
   * @throws IllegalStateException if there is no currently opened piece
   */
  String view() throws IllegalStateException;

  /**
   * Adds a new note to the currently opened piece at the given location details.
   *
   * @param start        the starting position of the note (measured in beats)
   * @param end          the ending position of the note (measured in beats)
   * @param instrument   the instrument the note is played in [0, 127]
   * @param pitch        the pitch at which the note is played [0, 127]
   * @param volume       the volume at which the note is played [0, 127]
   * @throws IllegalStateException if there is no currently opened piece
   * @throws IllegalArgumentException if the given start is negative, the end is negative or
   *                                  less than the given start position, the instrument isn't in
   *                                  the range [0, 127], the pitch isn't in range [0, 127], the
   *                                  volume isn't in range [0, 127], or if a note already exists
   *                                  at the start position with the same pitch and instrument
   */
  void addNote(int start, int end, int instrument, int pitch, int volume)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Removes a note from the currently opened piece at the given location details.
   *
   * @param start          the starting position of the note (measured in beats)
   * @param instrument     the instrument the note is played in [0, 127]
   * @param pitch          the pitch at which the note is played [0, 127]
   * @throws IllegalStateException if there is no currently opened piece
   * @throws IllegalArgumentException if the given start is negative, the instrument isn't in the
   *                                  range [0, 127], the pitch isn't in range [0, 127], or if no
   *                                  note exists at the start position with the same instrument
   *                                  and pitch
   */
  void removeNote(int start, int instrument, int pitch)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Changes the pitch of a note from the currently opened piece at the given location details.
   *
   * @param start          the starting position of the note (measured in beats)
   * @param instrument     the instrument the note is played in [0, 127]
   * @param pitch          the pitch at which the note is played [0, 127]
   * @param editedPitch    the new pitch of the note [0, 127]
   * @throws IllegalStateException if there is no currently opened piece
   * @throws IllegalArgumentException if the given start is negative, the instrument isn't in the
   *                                  range [0, 127], the pitch isn't in range [0, 127], the
   *                                  edited pitch is not in the same octave as the existing, if
   *                                  no note exists at the specified location, or a note of the
   *                                  same instrument already exists with the same start at the
   *                                  new pitch location
   */
  void editNotePitch(int start, int instrument, int pitch, int editedPitch)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Changes the position of a note from the currently opened piece at the given location details.
   *
   * @param start          the starting position of the note (measured in beats)
   * @param instrument     the instrument the note is played in [0, 127]
   * @param pitch          the pitch at which the note is played [0, 127]
   * @param editedStart    the new start position of the note (measured in beats)
   * @throws IllegalStateException if there is no currently opened piece
   * @throws IllegalArgumentException if the given start is negative, the instrument isn't in the
   *                                  range [0, 127], the pitch isn't in range [0, 127], the
   *                                  edited start position is negative or greater than the end
   *                                  position of the existing note, if no note exists at the
   *                                  specified location, or a note of the same instrument and
   *                                  pitch already exists with the same new start
   */
  void editNotePosition(int start, int instrument, int pitch, int editedStart)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Changes the position of a note from the currently opened piece at the given location details.
   *
   * @param start        the starting position of the note (measured in beats)
   * @param instrument   the instrument the note is played in [0, 127]
   * @param pitch        the pitch at which the note is played [0, 127]
   * @param editedEnd    the new end position of the note (measured in beats)
   * @throws IllegalStateException if there is no currently opened piece
   * @throws IllegalArgumentException if the given start is negative, the instrument isn't in the
   *                                  range [0, 127], the pitch isn't in range [0, 127], the
   *                                  edited end position is negative or less than the start
   *                                  position, or if no note exists at the specified location
   */
  void editNoteDuration(int start, int instrument, int pitch, int editedEnd)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Sets the tempo of the currently opened piece.
   *
   * @param tempo   the tempo to set the piece
   * @throws IllegalStateException if there is no currently opened piece
   * @throws IllegalArgumentException if the given tempo is negative
   */
  void setTempo(int tempo) throws IllegalStateException, IllegalArgumentException;

  /**
   * Gets the tempo of the currently opened piece.
   *
   * @return the tempo of the currently opened piece
   * @throws IllegalStateException if there is no currently opened piece
   */
  int getTempo() throws IllegalStateException;

  /**
   * Returns a list of note data for every note in the model. Note data is arranged as follows:
   * <table>
   *   <tr>
   *     <th>INDEX</th>
   *     <th>REPRESENTATION</th>
   *   </tr>
   *   <tr>
   *     <th>0, or {@code MidiConversion.NOTE_START}</th>
   *     <th>starting position</th>
   *   </tr>
   *   <tr>
   *     <th>1, or {@code MidiConversion.NOTE_END}</th>
   *     <th>ending position</th>
   *   </tr>
   *   <tr>
   *     <th>2, or {@code MidiConversion.NOTE_INSTRUMENT}</th>
   *     <th>instrument</th>
   *   </tr>
   *   <tr>
   *     <th>3, or {@code MidiConversion.NOTE_PITCH}</th>
   *     <th>pitch</th>
   *   </tr>
   *   <tr>
   *     <th>4, or {@code MidiConversion.NOTE_VOLUME}</th>
   *     <th>volume</th>
   *   </tr>
   * </table>
   *
   * @return a list of note data for every note in the currently opened piece
   * @throws IllegalStateException if there is no currently opened piece
   */
  List<Integer[]> getNotes() throws IllegalStateException;

  /**
   * Returns a list of note data for every note in the model at the given beat. Data is arranged
   * the same as described in {@link MusicEditorOperations#getNotes()}.
   *
   * @return a list of note data for every note in the currently opened piece at the given beat
   * @throws IllegalStateException if there is no currently opened piece
   */
  List<Integer[]> getNotesAtBeat(int beat) throws IllegalStateException;

  /**
   * Gets the length of the currently opened piece.
   *
   * @return the length of the currently opened piece
   * @throws IllegalStateException if there is no currently opened piece
   */
  int getLength() throws IllegalStateException;
}