package cs3500.music.util;

import cs3500.music.model.EditorOperations;
import cs3500.music.model.josh.EditorModel;

/**
 * Creates a new model with a new piece in it already using the methods defined in the
 * {@link EditorOperations} interface.
 */
public class EditorBuilder implements CompositionBuilder<EditorOperations> {
  EditorOperations model;

  public EditorBuilder() {
    this.model = new EditorModel();
    this.model.create();
  }

  @Override
  public EditorOperations build() {
    // No parametrization involved
    return this.model;
  }

  @Override
  public CompositionBuilder<EditorOperations> setTempo(int tempo) {
    this.model.setTempo(tempo);
    return this;
  }

  @Override
  public CompositionBuilder<EditorOperations> addNote(int start, int end, int instrument,
                                                      int pitch, int volume) {
    this.model.addNote(start, end, instrument, pitch, volume);
    return this;
  }
}
