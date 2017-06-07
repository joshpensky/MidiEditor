package cs3500.hw05;

/**
 * Represents all of the operations that the model of a Midi Editor should have.
 * Notice that the {@link EditorOperations} interface is parametrized over the
 * representation for a piece of music. This leaves the implementation of the
 * model open for interpretation.
 */
public interface EditorOperations<K> {
  void create(String title, int measure) throws IllegalArgumentException;

  void create(String title, K piece1, K piece2) throws IllegalArgumentException;

  void open(K piece) throws IllegalArgumentException;

  void print();

  void close() throws IllegalStateException;

  void addNote(int octave, Pitch pitch, int duration, int position)
      throws IllegalArgumentException;

  void removeNote(int octave, Pitch pitch, int position)
      throws IllegalArgumentException;

  void editPitch(int octave, Pitch pitch, int position, Pitch newPitch)
      throws IllegalArgumentException;

  void editDuration(int octave, Pitch pitch, int position, int newDuration)
      throws IllegalArgumentException;

  void editPosition(int octave, Pitch pitch, int position, int newPosition)
      throws IllegalArgumentException;
}