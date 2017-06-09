package cs3500.hw05;

/**
 * Represents all of the operations that the model of a Midi Editor should have.
 */
public interface EditorOperations<K> {
  /**
   * Creates a new piece of music with the given title and measure, and opens it.
   *
   * @param title     the title of the new piece
   * @param measure   the measure of the new piece (usually 3 or 4 beats)
   * @throws IllegalArgumentException if the given title is uninitialized, the measure is
   * negative, or if a piece already exists in memory with the given title
   */
  void create(String title, int measure) throws IllegalArgumentException;

  /**
   * Creates a new piece of music of the given title by merging the two given pieces, and opens it.
   *
   * @param title    the new title of the merged piece
   * @param piece1   the first piece to be merged
   * @param piece2   the second piece to be merged
   * @throws IllegalArgumentException if the given title or pieces are uninitialized, the pieces
   * have different measures, or if a piece already exists in memory with the given title
   */
  void create(String title, K piece1, K piece2) throws IllegalArgumentException;
  // TODO
  // CHANGE CREATE TO MERGE
  // CHANGE PIECES TO TITLES

  /**
   * Replaces the currently opened piece with the piece in memory with the given title.
   *
   * @param title   the title of the piece to be opened
   * @throws IllegalArgumentException if the given title is uninitialized, or if there is no
   * piece with the given title in memory
   */
  void open(String title) throws IllegalArgumentException;

  /**
   * Adds a new piece to memory and opens it, replacing the currently opened piece.
   *
   * @param piece   the piece to be opened
   * @throws IllegalArgumentException if the given piece is uninitialized, or a piece already
   * exists in memory with the given name
   */
  void open(K piece) throws IllegalArgumentException;

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
}