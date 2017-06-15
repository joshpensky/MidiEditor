package cs3500.music.view;

import cs3500.music.model.EditorOperations;

import javax.sound.midi.MidiUnavailableException;

/**
 * Created by josh_jpeg on 6/14/17.
 */
public class ViewFactory {

  public static ViewInterface getView(String viewName, EditorOperations model)
      throws IllegalArgumentException {
    if (viewName == null) {
      throw new IllegalArgumentException();
    }
    viewName = viewName.toLowerCase();
    switch (viewName) {
      case "visual":
        return new GuiViewFrame(model);
      case "audio":
        try {
          return new MidiViewImpl(model);
        } catch (MidiUnavailableException e) {
          System.err.println("Midi unavailable.");
        }
      default:
        throw new IllegalArgumentException("Given view, " + viewName + ", does not exist.");
    }
  }
}
