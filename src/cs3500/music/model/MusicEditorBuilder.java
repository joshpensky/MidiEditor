package cs3500.music.model;

import cs3500.music.util.CompositionBuilder;

/**
 * Represents a builder for an editor model. Uses the {@link MusicEditorOperations#setTempo(int)}
 * and {@link MusicEditorOperations#addNote(int, int, int, int, int)} methods to build the piece,
 * without needing to touch the model itself.
 */
public class MusicEditorBuilder implements CompositionBuilder<MusicEditorOperations> {
  private final MusicEditorOperations model;

  /**
   * Constructs a new {@code MusicEditorBuilder}. Initializes and creates a new piece in a
   * private model.
   */
  public MusicEditorBuilder() {
    this.model = new MusicEditorModel();
    this.model.create();
  }

  @Override
  public MusicEditorOperations build() {
    return this.model;
  }

  @Override
  public CompositionBuilder<MusicEditorOperations> setTempo(int tempo)
      throws IllegalArgumentException {
    this.model.setTempo(tempo);
    return this;
  }

  @Override
  public CompositionBuilder<MusicEditorOperations> addNote(int start, int end, int instrument,
                                                           int pitch, int volume)
      throws IllegalArgumentException {
    this.model.addNote(start, end, instrument, pitch, volume);
    return this;
  }
}
