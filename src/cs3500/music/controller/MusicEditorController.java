package cs3500.music.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.io.IOException;

import cs3500.music.model.MusicEditorBuilder;
import cs3500.music.model.MusicEditorOperations;
import cs3500.music.model.ViewOnlyModel;
import cs3500.music.util.MusicReader;
import cs3500.music.view.MusicEditorView;
import cs3500.music.view.MusicEditorViewFactory;

/**
 * Controller in MVC design for music editor.
 */
public class MusicEditorController extends KeyAdapter {
  private MusicEditorOperations model;
  private MusicEditorView view;
  private static MusicEditorController controller;

  /**
   * Constructor for controller, using the singleton pattern.
   */
  private MusicEditorController() {
    this.model = null;
    this.view = null;
  }

  /**
   * initializer for singleton pattern for controller. Only one will ever be created.
   * @return this's controller
   */
  public static MusicEditorController initialize() {
    if (controller == null) {
      controller = new MusicEditorController();
    }
    return controller;
  }

  /**
   * Sets the model and view to be controlled, using the given file name and name of view.
   *
   * @param fileName   the name of the file to be represented by the model
   * @param viewName   the name of the view to be used to represent the model (either console,
   *                   visual, midi, or composite)
   * @throws IllegalArgumentException if there is no file with the given name, or new view exists
   *                                  with the given name
   */
  public void setModelAndView(String fileName, String viewName) throws IllegalArgumentException {
    this.setModel(fileName);
    this.setView(viewName);
  }

  /**
   * Sets the view of this controller, based on the given view name.
   *
   * @param viewName   the name of the view this controller should use
   * @throws IllegalArgumentException if no viewName exists with the given name
   */
  private void setView(String viewName) {
    this.view = MusicEditorViewFactory.getView(viewName, new ViewOnlyModel(this.model));
    this.view.addListeners(this, this);
    this.view.initialize();
  }

  /**
   * Sets the model of this controller using the notes from the given file.
   *
   * @param fileName   the name of the file to be represented by the model
   * @throws IllegalArgumentException if there is no file with the given name
   */
  private void setModel(String fileName) throws IllegalArgumentException {
    try {
      this.model = MusicReader.parseFile(new FileReader(fileName), new MusicEditorBuilder());
    } catch (IOException e) {
      throw new IllegalArgumentException("No file exists with the given name: \""
          + fileName + "\".");
    }
  }

  /**
   * Handles events when a key has been pressed. Uses the keyEvents set in the view to run. If no
   * runnables or key codes have been set, nothing runs.
   *
   * @param e   the event that occurs when a key has been pressed
   * @throws IllegalArgumentException if the view has not been set yet
   */
  @Override
  public void keyPressed(KeyEvent e) throws IllegalArgumentException {
    if (this.view == null) {
      throw new IllegalArgumentException("View has not been set.");
    }
    Runnable r = view.getKeyEventRunnables().getOrDefault(e.getKeyCode(), null);
    if (r != null) {
      r.run();
    }
  }

  /**
   * Adds a note to the model.
   *
   * @param start        the starting position for the note (measured in beats)
   * @param end          the end position for the note (measured in beats)
   * @param instrument   the instrument that plays the note [0, 127]
   * @param pitch        the pitch the note is played in
   * @param volume       the volume the note is played at
   * @throws IllegalArgumentException if the model has not been set yet
   */
  public void addNote(int start, int end, int instrument, int pitch, int volume) {
    if (this.model == null) {
      throw new IllegalArgumentException("Model has not been set.");
    }
    this.model.addNote(start, end, instrument, pitch, volume);
  }
}
