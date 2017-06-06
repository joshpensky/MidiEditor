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
    if (pieces == null) {
      throw new IllegalArgumentException();
    }
    this.pieces = new ArrayList<>();
    for (Piece p : pieces) {
      if (p == null) {
        throw new IllegalArgumentException();
      }
      this.pieces.add(p);
    }
    this.opened = null;
  }

  @Override
  public void create(String title, int measure) throws IllegalArgumentException {
     return;
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
  public void addNote(OctaveType octave, Pitch pitch, int duration, int position)
                      throws IllegalArgumentException {
    return;
  }

  @Override
  public void removeNote(OctaveType octave, Pitch pitch, int position)
                         throws IllegalArgumentException {
    return;
  }

  @Override
  public void editPitch(OctaveType octave, Pitch pitch, int position, Pitch newPitch)
                        throws IllegalArgumentException {
    return;
  }

  @Override
  public void editDuration(OctaveType octave, Pitch pitch, int position, int newDuration)
                           throws IllegalArgumentException {
    return;
  }

  @Override
  public void editPosition(OctaveType octave, Pitch pitch, int position, int newPosition)
                           throws IllegalArgumentException {
    return;
  }
}
