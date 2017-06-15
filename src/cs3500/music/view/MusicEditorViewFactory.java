package cs3500.music.view;

import cs3500.music.model.MusicEditorOperations;

import javax.sound.midi.MidiUnavailableException;

/**
 * Factory class for creating {@link MusicEditorView}s.
 */
public class MusicEditorViewFactory {

  public static MusicEditorView getView(String viewName, MusicEditorOperations model)
      throws IllegalArgumentException, MidiUnavailableException {
    if (viewName == null) {
      throw new IllegalArgumentException();
    }
    viewName = viewName.toLowerCase();
    switch (viewName) {
      case "text":
        return new TextView(model);
      case "visual":
        return new GuiViewFrame(model);
      case "audio":
        return new MidiView(model);
      default:
        throw new IllegalArgumentException("Given view, " + viewName + ", does not exist.");
    }
  }
}
