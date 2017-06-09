package cs3500.hw05;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the model for a Midi Editor and an implementation of the EditorOperations interface.
 */
public class EditorModel implements EditorOperations<Piece> {
  private List<Piece> pieces;
  private Piece opened;

  /**
   * Creates a new {@code EditorModel} with no pieces in memory.
   */
  public EditorModel() {
    this.pieces = new ArrayList<>();
    this.opened = null;
  }

  /**
   * Creates a new {@code EditorModel} with the given pieces in memory to be opened or edited.
   *
   * @param pieces   the pieces to be added to the model's memory
   * @throws IllegalArgumentException if any of the given pieces are uninitialized or share the
   * name of another
   */
  public EditorModel(Piece... pieces) throws IllegalArgumentException {
    this();
    for (Piece p : pieces) {
      if (p == null) {
        throw new IllegalArgumentException("A given piece was uninitialized.");
      } else if (this.getPieceFromMemory(p.getTitle()) != null) {
        throw new IllegalArgumentException("Trying to add two pieces with the same title, \""
            + p.getTitle() + "\".");
      }
      this.pieces.add(new Piece(p));
    }
  }

  @Override
  public void create(String title, int measure) throws IllegalArgumentException {
    if (this.getPieceFromMemory(title) != null) {
      throw new IllegalArgumentException("Piece already exists with given title.");
    }
    Piece next = new Piece(title, measure);
    this.pieces.add(0, next);
    this.opened = next;
  }

  @Override
  public void create(String title, Piece piece1, Piece piece2)
      throws IllegalArgumentException {
    if (this.getPieceFromMemory(title) != null) {
      throw new IllegalArgumentException("Piece already exists with the given title.");
    } else if (piece1 == null || piece2 == null) {
      throw new IllegalArgumentException("Given piece is uninitialized.");
    }
    Piece newPiece = new Piece(piece1);
    newPiece.merge(title, new Piece(piece2));
    this.opened = newPiece;
  }

  @Override
  public void open(String title) throws IllegalArgumentException {
    Piece piece = this.getPieceFromMemory(title);
    if (piece == null) {
      throw new IllegalArgumentException("There is no piece that exists with the given title, \""
          + title + "\".");
    }
    this.opened = piece;
  }

  @Override
  public void open(Piece piece) throws IllegalArgumentException {
    if (piece == null) {
      throw new IllegalArgumentException("Cannot open an uninitialized piece.");
    } else if (this.getPieceFromMemory(piece.getTitle()) != null) {
      throw new IllegalArgumentException("Piece already exists with the given title.");
    }
    Piece newPiece = new Piece(piece);
    this.pieces.add(0, newPiece);
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
  private Piece getPieceFromMemory(String title) throws IllegalArgumentException {
    for (Piece p : this.pieces) {
      if (p.getTitle().equals(title)) {
        return p;
      }
    }
    return null;
  }

  @Override
  public String view() {
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
