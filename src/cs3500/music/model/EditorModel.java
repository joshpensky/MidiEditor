package cs3500.music.model;

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

  @Override
  public void copy(String toCopy, String newTitle) throws IllegalArgumentException {
    Piece piece = this.getPieceFromMemory(toCopy);
    if (piece == null) {
      throw new IllegalArgumentException("There is no piece to copy that exists with the given "
        + "title, \"" + toCopy + "\".");
    } else if (this.getPieceFromMemory(newTitle) != null) {
      throw new IllegalArgumentException("Piece already exists with given new title.");
    }
    Piece copy = new Piece(newTitle);
    copy.overlay(piece);
    this.pieces.add(0, copy);
    this.opened = copy;
  }

  /**
   * Helper to the create, open, and copy methods. Gets the piece from the list of pieces in the
   * model, or null if no such piece exists.
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
    this.openedPieceException();
    return this.opened.toString();
  }

  @Override
  public void close() throws IllegalStateException {
    this.openedPieceException();
    this.opened = null;
  }

  @Override
  public String list() {
    StringBuilder builder = new StringBuilder();
    for (Piece p : this.pieces) {
      if (p.equals(opened)) {
        builder.append(">  ");
      } else {
        builder.append("   ");
      }
      builder.append(p.getTitle() + "\n");
    }
    return builder.toString();
  }

  @Override
  public void addNote(int octave, Pitch pitch, int position, int duration)
      throws IllegalStateException, IllegalArgumentException {
    this.openedPieceException();
    this.opened.addNote(octave, pitch, position, duration);
  }

  @Override
  public void removeNote(int octave, Pitch pitch, int position)
      throws IllegalStateException, IllegalArgumentException {
    this.openedPieceException();
    this.opened.removeNote(octave, pitch, position);
  }

  @Override
  public void editNotePitch(int octave, Pitch pitch, int position, Pitch newPitch)
      throws IllegalStateException, IllegalArgumentException {
    this.openedPieceException();
    this.opened.editPitch(octave, pitch, position, newPitch);
  }

  @Override
  public void editNotePosition(int octave, Pitch pitch, int position, int newPosition)
      throws IllegalStateException, IllegalArgumentException {
    this.openedPieceException();
    this.opened.editPosition(octave, pitch, position, newPosition);
  }

  @Override
  public void editNoteDuration(int octave, Pitch pitch, int position, int newDuration)
      throws IllegalStateException, IllegalArgumentException {
    this.openedPieceException();
    this.opened.editDuration(octave, pitch, position, newDuration);
  }

  @Override
  public void overlay(String overlayTitle) throws IllegalStateException, IllegalArgumentException {
    this.openedPieceException();
    Piece toOverlay = this.getPieceFromMemory(overlayTitle);
    if (toOverlay == null) {
      throw new IllegalArgumentException("There is no piece with the given title, \""
        + overlayTitle + "\".");
    }
    this.opened.overlay(new Piece(toOverlay));
  }

  @Override
  public void addToEnd(String title) throws IllegalStateException, IllegalArgumentException {
    this.openedPieceException();
    Piece toAdd = this.getPieceFromMemory(title);
    if (toAdd == null) {
      throw new IllegalArgumentException("There is no piece with the given title, \""
        + title + "\".");
    }
    toAdd = new Piece(toAdd);
    toAdd.move(this.opened.length());
    this.opened.overlay(toAdd);
  }

  /**
   * Helper to the print, close, addNote, removeNote, editNotePitch, editNotePosition,
   * editNoteDuration, and overlay methods. Checks if there is currently a piece opened, and if
   * not throws an exception.
   *
   * @throws IllegalStateException if there is currently no piece opened
   */
  private void openedPieceException() throws IllegalStateException {
    if (this.opened == null) {
      throw new IllegalStateException("There is no piece currently open.");
    }
  }
}