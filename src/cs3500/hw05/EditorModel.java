package cs3500.hw05;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the model for a Midi Editor and an implementation of the EditorOperations interface.
 */
public class EditorModel implements EditorOperations {
  private List<Piece> pieces;
  private Piece opened;

  /**
   * Creates a new {@code EditorModel} with no pieces in memory.
   */
  public EditorModel() {
    this.pieces = new ArrayList<>();
    this.opened = null;
  }

  @Override
  public void create(String title) throws IllegalArgumentException {
    if (this.getPieceFromMemory(title) != null) {
      throw new IllegalArgumentException("Piece already exists with given title.");
    }
    Piece next = new Piece(title);
    this.pieces.add(0, next);
    this.opened = next;
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

  @Override
  public void overlay(String overlayTitle) throws IllegalStateException,
        IllegalArgumentException {
    this.openedPieceException();
    Piece toOverlay = this.getPieceFromMemory(overlayTitle);
    if (toOverlay == null) {
      throw new IllegalArgumentException("There is no piece with the given title, \""
          + overlayTitle + "\".");
    } else if (toOverlay.equals(this.opened)) {
      throw new IllegalArgumentException("Cannot overlay piece with itself.");
    }
    this.opened.overlay(new Piece(toOverlay));
  }

  /**
   * Helper to the print, close, addNote, removeNote, editPitch, editPosition, editDuration,
   * and overlay methods. Checks if there is currently a piece opened, and if not throws an
   * exception.
   *
   * @throws IllegalStateException if there is currently no piece opened
   */
  private void openedPieceException() throws IllegalStateException {
    if (this.opened == null) {
      throw new IllegalStateException("There is no piece currently open.");
    }
  }
}
