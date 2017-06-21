package cs3500.music.view;

import cs3500.music.model.MusicEditorOperations;

import javax.sound.midi.MidiUnavailableException;

/**
 * Factory class for creating {@link MusicEditorView}s based on given strings.
 */
public class MusicEditorViewFactory {
  /**
   * Returns a new view object based on the given view name. Also passes the given model to the
   * new view object.
   * <table>
   *   <tr>
   *     <th>STRING</th>
   *     <th>VIEW</th>
   *   </tr>
   *   <tr>
   *     <th>"console"</th>
   *     <th>{@link TextView}</th>
   *   </tr>
   *   <tr>
   *     <th>"visual"</th>
   *     <th>{@link GuiView}</th>
   *   </tr>
   *   <tr>
   *     <th>"midi"</th>
   *     <th>{@link MidiView}</th>
   *   </tr>
   * </table>
   *
   * @param viewName   the name of the desired view (case-insensitive, see above table for details)
   * @param model      the model to be sent into the view
   * @return a new view object based on the desired view
   * @throws IllegalArgumentException the given view name is uninitialized, or the given model is
   *                                  uninitialized
   * @throws MidiUnavailableException if midi is currently unavailable when choosing the MIDI view
   */
  public static MusicEditorView getView(String viewName, MusicEditorOperations model)
      throws IllegalArgumentException {
    if (viewName == null) {
      throw new IllegalArgumentException();
    }
    viewName = viewName.toLowerCase();
    switch (viewName) {
      case "console":
        return new TextView.Builder(model).build();
      case "visual":
        return new GuiView(model);
      case "midi":
        try {
          return new MidiView.Builder(model).build();
        } catch (MidiUnavailableException e) {
          System.err.println(e.getMessage());
        }
        throw new IllegalArgumentException("Midi player error");
      case "composite":
        try {
          return new CompositeView(model);
        } catch (MidiUnavailableException e) {
          System.err.println(e.getMessage());
        }
        throw new IllegalArgumentException("Midi player error");
      default:
        throw new IllegalArgumentException("Given view, " + viewName + ", does not exist.");
    }
  }
}
