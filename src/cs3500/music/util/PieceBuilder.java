package cs3500.music.util;

import cs3500.music.model.josh.Piece;

/**
 * Building a piece using only the constructor and methods from the {@link Piece} class.
 */
public final class PieceBuilder implements CompositionBuilder<Piece> {
  Piece piece;

  public PieceBuilder() {
    this.piece = new Piece("New Piece");
  }

  @Override
  public Piece build() {
    // means parametrizing over Piece type in the interface
    // in order to add this piece to the current model
    return this.piece;
  }

  @Override
  public CompositionBuilder<Piece> setTempo(int tempo) {
    this.piece.setTempo(tempo);
    return this;
  }

  @Override
  public CompositionBuilder<Piece> addNote(int start, int end, int instrument,
                                           int pitch, int volume) {
    this.piece.addNote(start, end, instrument, pitch, volume);
    return this;
  }
}
