package cs3500.music.util;

import cs3500.music.model.EditorOperations;
import cs3500.music.model.josh.Piece;

/**
 * Building a piece using a given model and the methods from that model. Returns a Piece instead
 * of the modified model.
 */
public class PieceBuilder2 implements CompositionBuilder<Piece> {
  EditorOperations model;

  public PieceBuilder2(EditorOperations model, String title) {
    this.model = model;
    this.model.create(title);
  }

  @Override
  public Piece build() {
    // means parametrizing over Piece type in the interface
    // in order to return and open this in a model
    //return this.model.getOpened();
    return null;
  }

  @Override
  public CompositionBuilder<Piece> setTempo(int tempo) {
    this.model.setTempo(tempo);
    return this;
  }

  @Override
  public CompositionBuilder<Piece> addNote(int start, int end, int instrument,
                                           int pitch, int volume) {
    this.model.addNote(start, end, instrument, pitch, volume);
    return this;
  }
}
