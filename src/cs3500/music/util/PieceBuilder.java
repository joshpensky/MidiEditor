package cs3500.music.util;

import cs3500.music.model.josh.Piece;

/**
 * IF meant to be a piece builder, NEED to add a method to Editor that
 * takes in a new Piece, which means parametrizing over the Piece class
 */
public class PieceBuilder implements CompositionBuilder<Piece> {
  Piece piece;

  public PieceBuilder(String title) {
    this.piece = new Piece(title);
  }

  @Override
  public Piece build() {
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
  }
}
