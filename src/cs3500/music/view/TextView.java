package cs3500.music.view;

import cs3500.music.model.MusicEditorOperations;

/**
 * Represents the text view for a music editor model. Prints the String representation of the
 * currently opened piece to the console.
 */
public class TextView implements MusicEditorView {
  MusicEditorOperations model;

  protected TextView(MusicEditorOperations model) {
    this.model = model;
  }

  @Override
  public void initialize() {
    System.out.println(model.view());
  }
}
