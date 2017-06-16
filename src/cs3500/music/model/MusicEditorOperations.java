package cs3500.music.model;

import java.util.List;

/**
 * Represents all of the operations that the model of a Midi Editor should have.
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
   * Adds a new note to the currently opened piece in the given octave at the given pitch.
   *
   * @param octave     the octave of the pitch
   * @param pitch      the pitch at which the note is played
   * @param position   the starting position of the new note
   * @param duration   the duration of the note (measured in beats), including the onset
   * @throws IllegalStateException if there is no currently opened piece
   * @throws IllegalArgumentException if the given octave isn't between 1 and 10 (inclusive), the
   *                                  given pitch is uninitialized, the position or duration are
   *                                  negative, or the duration is zero
   */
  void addNote(int start, int end, int instrument, int pitch, int volume)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Removes a note from the currently opened piece at the given location details.
   *
   * @param octave     the octave of the pitch
   * @param pitch      the pitch at which the note is played
   * @param position   the starting position of the note to be removed
   * @throws IllegalStateException if there is no currently opened piece
   * @throws IllegalArgumentException if the given octave isn't between 1 and 10 (inclusive), the
   *                                  given pitch is uninitialized, the position is negative, or if
   *                                  there is no note at the given location
   */
  void removeNote(int start, int instrument, int pitch)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Changes the pitch of a note from the currently opened piece at the given location details.
   *
   * @param octave     the octave of the pitch the note is in
   * @param pitch      the pitch at which the note is played
   * @param position   the starting position of the note
   * @param newPitch   the new pitch the note will be played at
   * @throws IllegalStateException if there is no currently opened piece
   * @throws IllegalArgumentException if the given octave isn't between 1 and 10 (inclusive), either
   *                                  of the given pitches are uninitialized, the position is
   *                                  negative, or there is no note at the given location
   */
  void editNotePitch(int start, int instrument, int pitch, int editedPitch)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Changes the position of a note from the currently opened piece at the given location details.
   *
   * @param octave        the octave of the pitch the note is in
   * @param pitch         the pitch at which the note is played
   * @param position      the starting position of the note
   * @param newPosition   the new starting position of the note
   * @throws IllegalStateException if there is no currently opened piece
   * @throws IllegalArgumentException if the given octave isn't between 1 and 10 (inclusive), the
   *                                  given pitch is uninitialized, either of the positions are
   *                                  negative, or there is no note at the given location
   */
  void editNotePosition(int start, int instrument, int pitch, int editedStart)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Changes the position of a note from the currently opened piece at the given location details.
   *
   * @param octave        the octave of the pitch the note is in
   * @param pitch         the pitch at which the note is played
   * @param position      the starting position of the note
   * @param newDuration   the new duration of the note (measured in beats), including the onset
   * @throws IllegalStateException if there is no currently opened piece
   * @throws IllegalArgumentException if the given octave isn't between 1 and 10 (inclusive), the
   *                                  given pitch is uninitialized, the position or new duration are
   *                                  negative, the new duration is zero, or there is no note at
   *                                  the given location
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