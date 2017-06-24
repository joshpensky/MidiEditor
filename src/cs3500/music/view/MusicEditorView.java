package cs3500.music.view;

import cs3500.music.controller.MusicEditorController;

import java.awt.event.KeyListener;
import java.util.Map;

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

  /**
   * Gets a map of key codes to Runnables to execute when the keys respective to those key codes
   * are pressed.
   *
   * @return a map of key codes to Runnables
   */
  Map<Integer, Runnable> getKeyEventRunnables();

  /**
   * Adds the key listener for handling key events, as well as the controller for updating the
   * model from changes within view.
   *
   * @param controller    the controller used to update the model for any changes within the view
   * @param keyListener   the listener that handles key events
   * @throws IllegalArgumentException if the given controller or keyListener are uninitialized
   */
  void addListeners(MusicEditorController controller, KeyListener keyListener)
      throws IllegalArgumentException;

  /**
   * Updates the view to reflect any changes in the model.
   */
  void update();
}
