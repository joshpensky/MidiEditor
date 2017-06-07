package cs3500.hw05;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the model for a Midi Editor and an implementation of the EditorOperations interface.
 */
public class EditorModel implements EditorOperations<Piece> {
  private List<Piece> pieces;
  private Piece opened;

  public EditorModel() {
    this.pieces = new ArrayList<>();
    this.opened = null;
  }

  public EditorModel(Piece... pieces) throws IllegalArgumentException {
    this();
    for (Piece p : pieces) {
      if (p == null) {
        throw new IllegalArgumentException("A given piece was uninitialized.");
      }
      this.pieces.add(p);
    }
  }

  @Override
  public void create(String title, int measure) throws IllegalArgumentException {

  }

  @Override
  public void create(String title, Piece piece1, Piece piece2) throws IllegalArgumentException {
    return;
  }

  @Override
  public void open(Piece piece) throws IllegalArgumentException {
    return;
  }

  @Override
  public void print() {
    return;
  }

  @Override
  public void close() throws IllegalStateException {
    return;
  }

  @Override
  public void addNote(int octave, Pitch pitch, int duration, int position)
                      throws IllegalArgumentException {
    return;
  }

  @Override
  public void removeNote(int octave, Pitch pitch, int position)
                         throws IllegalArgumentException {
    return;
  }

  @Override
  public void editPitch(int octave, Pitch pitch, int position, Pitch newPitch)
                        throws IllegalArgumentException {
    return;
  }

  @Override
  public void editDuration(int octave, Pitch pitch, int position, int newDuration)
                           throws IllegalArgumentException {
    return;
  }

  @Override
  public void editPosition(int octave, Pitch pitch, int position, int newPosition)
                           throws IllegalArgumentException {
    return;
  }
}