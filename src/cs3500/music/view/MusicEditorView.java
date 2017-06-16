package cs3500.music.view;

/**
 * Represents a single view for the music editor.
 */
public interface MusicEditorView {
  /**
   * Initializes the view, according to the type of view it is (MIDI plays music, GUI displays
   * window, etc.).
   */
  void initialize();

  /**
   * Gets the log of all operations that the view has done at the point of calling this method.
   *
   * @return the log of operations as a String
   */
  String getLog();
}
