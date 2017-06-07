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
      this.pieces.add(new Piece(p));
    }
  }

  @Override
  public void create(String title, int measure) throws IllegalArgumentException {
    if (getPieceFromTitle(title) != null) {
      throw new IllegalArgumentException("Piece already exists with given title.");
    }
    Piece next = new Piece(title, measure);
    this.pieces.add(0, next);
    this.opened = next;
  }

  @Override
  public void create(String title, Piece piece1, Piece piece2)
      throws IllegalArgumentException {
    if (getPieceFromTitle(title) != null) {
      throw new IllegalArgumentException("Piece already exists with the given title.");
    } else if (piece1 == null || piece2 == null) {
      throw new IllegalArgumentException("Given piece is uninitialized.");
    }
    Piece newPiece = new Piece(piece1);
    newPiece.merge(title, new Piece(piece2));
    this.opened = newPiece;
  }

  /**
   * Helper to the create and open methods. Gets the piece from the list of pieces in the model,
   * or null if no such piece exists.
   *
   * @param title   the title of the desired piece
   * @return the piece with the matching title, or null if no piece has the given title
   * @throws IllegalArgumentException if the given title is uninitialized
   */
  private Piece getPieceFromTitle(String title) throws IllegalArgumentException {
    for (Piece p : this.pieces) {
      if (p.sameTitle(title)) {
        return p;
      }
    }
    return null;
  }

  @Override
  public void open(String title) throws IllegalArgumentException {
    Piece piece = this.getPieceFromTitle(title);
    if (piece == null) {
      throw new IllegalArgumentException("There is no piece that exists with the given title, \""
          + title + "\".");
    }
    this.opened = piece;
  }

  @Override
  public String print() {
    try {
      this.openedPieceException();
    } catch (IllegalStateException e) {
      return "";
    }
    return this.opened.toString();
  }

  @Override
  public void close() throws IllegalStateException {
    this.openedPieceException();
    this.opened = null;
  }

  @Override
  public void addNote(int octave, Pitch pitch, int position, int duration)
      throws IllegalStateException, IllegalArgumentException {
    this.openedPieceException();
    this.opened.addNote(octave, pitch, duration, position);
  }

  @Override
  public void removeNote(int octave, Pitch pitch, int position)
      throws IllegalStateException, IllegalArgumentException {
    this.openedPieceException();
    this.opened.removeNote(octave, pitch, position);
  }

  @Override
  public void editPitch(int octave, Pitch pitch, int position, Pitch newPitch)
      throws IllegalStateException, IllegalArgumentException {
    this.openedPieceException();
    this.opened.editPitch(octave, pitch, position, newPitch);
  }

  @Override
  public void editPosition(int octave, Pitch pitch, int position, int newPosition)
    throws IllegalStateException, IllegalArgumentException {
    this.openedPieceException();
    this.opened.editPosition(octave, pitch, position, newPosition);
  }

  @Override
  public void editDuration(int octave, Pitch pitch, int position, int newDuration)
      throws IllegalStateException, IllegalArgumentException {
    this.openedPieceException();
    this.opened.editDuration(octave, pitch, position, newDuration);
  }

  /**
   * Helper to the print, close, addNote, removeNote, editPitch, editPosition, and editDuration
   * methods. Checks if there is currently a piece opened, and if not throws an exception.
   *
   * @throws IllegalStateException if there is currently no piece opened
   */
  private void openedPieceException() throws IllegalStateException {
    if (this.opened == null) {
      throw new IllegalStateException("There is no piece currently open.");
    }
  }
}
