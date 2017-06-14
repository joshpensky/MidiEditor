package cs3500.music.util;

import cs3500.music.model.EditorOperations;
import cs3500.music.model.josh.EditorModel;

/**
 * Created by josh_jpeg on 6/14/17.
 */
public class EditorBuilder implements CompositionBuilder<EditorOperations> {
  EditorOperations model;

  public EditorBuilder(String title) {
    this.model = new EditorModel();
    this.model.create(title);
  }

  @Override
  public EditorOperations build() {
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
