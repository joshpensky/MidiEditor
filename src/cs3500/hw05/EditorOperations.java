package cs3500.hw05;

/**
 * Represents all of the operations that the model of a Midi Editor should have.
 */
public interface EditorOperations {
  /**
   * Creates a new piece of music with the given title and measure, and opens it.
   *
   * @param title     the title of the new piece
   * @throws IllegalArgumentException if the given title is uninitialized, or if a piece already
   * exists in memory with the given title
   */
  void create(String title) throws IllegalArgumentException;

  /**
   * Replaces the currently opened piece with the piece in memory with the given title.
   *
   * @param title   the title of the piece to be opened
   * @throws IllegalArgumentException if the given title is uninitialized, or if there is no
   * piece with the given title in memory
   */
  void open(String title) throws IllegalArgumentException;

  /**
   * Returns a String representation of the current state of the currently opened piece, or an
   * empty string if there is no piece opened.
   *
   * @return a String representation of the opened piece
   */
  String view();

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
   * @param duration   the duration of the note (measured in beats)
   * @throws IllegalStateException if there is no currently opened piece
   * @throws IllegalArgumentException if the given octave isn't between 1 and 10 (inclusive), the
   * given pitch is uninitialized, the position or duration are negative, or the duration is zero
   */
  void addNote(int octave, Pitch pitch, int position, int duration)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Removes a note from the currently opened piece in the given octave at the given pitch at the
   * given starting position.
   *
   * @param octave     the octave of the pitch
   * @param pitch      the pitch at which the note is played
   * @param position   the starting position of the note to be removed
   * @throws IllegalStateException if there is no currently opened piece
   * @throws IllegalArgumentException if the given octave isn't between 1 and 10 (inclusive), the
   * given pitch is uninitialized, the position is negative, or if there is no note at the given
   * location
   */
  void removeNote(int octave, Pitch pitch, int position)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Changes the pitch of a note from the currently opened piece at the given location.
   *
   * @param octave     the octave of the pitch the note is in
   * @param pitch      the pitch at which the note is played
   * @param position   the starting position of the note
   * @param newPitch   the new pitch the note will be played at
   * @throws IllegalStateException if there is no currently opened piece
   * @throws IllegalArgumentException if the given octave isn't between 1 and 10 (inclusive), either
   * of the given pitches are uninitialized, the position is negative, or there is no note at the
   * given location
   */
  void editPitch(int octave, Pitch pitch, int position, Pitch newPitch)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Changes the position of a note from the currently opened piece at the given location.
   *
   * @param octave        the octave of the pitch the note is in
   * @param pitch         the pitch at which the note is played
   * @param position      the starting position of the note
   * @param newPosition   the new starting position of the note
   * @throws IllegalStateException if there is no currently opened piece
   * @throws IllegalArgumentException if the given octave isn't between 1 and 10 (inclusive), the
   * given pitch is uninitialized, either of the positions are negative, or there is no note at the
   * given location
   */
  void editPosition(int octave, Pitch pitch, int position, int newPosition)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Changes the position of a note from the currently opened piece at the given location.
   *
   * @param octave        the octave of the pitch the note is in
   * @param pitch         the pitch at which the note is played
   * @param position      the starting position of the note
   * @param newDuration   the new duration of the note (measured in beats)
   * @throws IllegalStateException if there is no currently opened piece
   * @throws IllegalArgumentException if the given octave isn't between 1 and 10 (inclusive), the
   * given pitch is uninitialized, the position or new duration are negative, the new duration is
   * zero, or there is no note at the given location
   */
  void editDuration(int octave, Pitch pitch, int position, int newDuration)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Overlays another piece of music in memory over the currently opened one.
   *
   * @param overlayTitle   the title of the piece to be overlaid
   * @throws IllegalStateException if there is no currently opened piece
   * @throws IllegalArgumentException if the given title is uninitialized, there is no
   * piece in memory with the overlay's title, or the user is attempting to overlay the same
   * piece on top of itself
   */
  void overlay(String overlayTitle) throws IllegalStateException, IllegalArgumentException;
}