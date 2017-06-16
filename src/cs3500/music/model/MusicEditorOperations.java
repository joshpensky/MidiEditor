package cs3500.music.model;

import java.util.List;

/**
 * Represents all of the operations that the model of a Midi Editor should have.
 */
public interface MusicEditorOperations {
  /**
   * Creates a new piece of music with the given title, and opens it.
   *
   * @param title     the title of the new piece
   * @throws IllegalArgumentException if the given title is uninitialized, or if a piece already
   *                                  exists in memory with the given title
   */
  void create() throws IllegalArgumentException;

  /**
   * Returns a String representation of the current state of the currently opened piece, or an
   * empty string if there is no piece opened.
   *
   * @return a String representation of the opened piece
   * @throws IllegalStateException if there is no currently opened piece
   */
  String view() throws IllegalStateException;

  /**
   * Closes out of the piece currently being worked on.
   *
   * @throws IllegalStateException if there is no currently opened piece
   */
  void close() throws IllegalStateException;

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

  List<Integer[]> getNotes();

  List<Integer[]> getNotesAtBeat(int beat);

  int totalPieceLength();

  int getTempo();
}