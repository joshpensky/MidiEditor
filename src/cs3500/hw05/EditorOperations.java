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

  /**
   * Replaces the currently opened piece with the piece with the given title.
   *
   * @param title   the title of the piece to be opened
   * @throws IllegalArgumentException if the given title is uninitialized, or if there is no
   * piece with the given title in memory
   */
  void open(String title) throws IllegalArgumentException;

  /**
   * Returns the current state of the currently opened piece, or an empty string if there is no
   * piece opened.
   *
   * @return a String representation of the opened piece
   */
  String print();

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
   * @param duration   the duration of the note
   * @throws IllegalStateException if there is no currently opened piece
   * @throws IllegalArgumentException if the given octave isn't between 1 and 10 (inclusive), the
   * given pitch is uninitialized, the position or duration are negative, or if the duration is zero
   */
  void addNote(int octave, Pitch pitch, int position, int duration)
      throws IllegalStateException, IllegalArgumentException;

  void removeNote(int octave, Pitch pitch, int position)
      throws IllegalStateException, IllegalArgumentException;

  void editPitch(int octave, Pitch pitch, int position, Pitch newPitch)
      throws IllegalStateException, IllegalArgumentException;

  void editPosition(int octave, Pitch pitch, int position, int newPosition)
      throws IllegalStateException, IllegalArgumentException;

  void editDuration(int octave, Pitch pitch, int position, int newDuration)
      throws IllegalStateException, IllegalArgumentException;
}