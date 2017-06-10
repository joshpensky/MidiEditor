package cs3500.hw05;

/**
 * Represents all of the operations that the model of a Midi Editor should have.
 */
public interface EditorOperations {
  /**
   * Creates a new piece of music with the given title, and opens it.
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
   * Creates a copy of an existing piece in memory with a new title, and opens it.
   *
   * @param toCopy      the title of the piece to be copied
   * @param newTitle   the new title of the copied piece
   * @throws IllegalArgumentException if either of the given titles are uninitialized, if there
   * is no piece with the given title in memory, or if a piece already exists in memory with the
   * given new title
   */
  void copy(String toCopy, String newTitle) throws IllegalArgumentException;

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
   * Lists the titles of all pieces currently in memory of the model.
   */
  String list();

  /**
   * Adds a new note to the currently opened piece in the given octave at the given pitch.
   *
   * @param octave     the octave of the pitch
   * @param pitch      the pitch at which the note is played
   * @param position   the starting position of the new note
   * @param duration   the duration of the note (measured in beats), including the onset
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
  void editNotePitch(int octave, Pitch pitch, int position, Pitch newPitch)
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
  void editNotePosition(int octave, Pitch pitch, int position, int newPosition)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Changes the position of a note from the currently opened piece at the given location.
   *
   * @param octave        the octave of the pitch the note is in
   * @param pitch         the pitch at which the note is played
   * @param position      the starting position of the note
   * @param newDuration   the new duration of the note (measured in beats), including the onset
   * @throws IllegalStateException if there is no currently opened piece
   * @throws IllegalArgumentException if the given octave isn't between 1 and 10 (inclusive), the
   * given pitch is uninitialized, the position or new duration are negative, the new duration is
   * zero, or there is no note at the given location
   */
  void editNoteDuration(int octave, Pitch pitch, int position, int newDuration)
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