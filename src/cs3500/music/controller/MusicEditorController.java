package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
public class MusicEditorController implements KeyListener {
  private MusicEditorOperations model;
  private MusicEditorView view;
  private static MusicEditorController controller;

  /**
   * Constructor for controller, using the singleton pattern.
   */
  private MusicEditorController() {
    // Singleton pattern requires a private constructor
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
   * Setter for the model and controller
   * @param model the model to be used
   * @param view the view to be used
   * @throws IOException
   */
  public void setModelAndView(String model, String view) throws IOException {
    this.setModel(model);
    this.setView(view);
  }

  /**
   * Sets the view of this controller.
   * @param view the view this controller should use.
   */
  private void setView(String view) {
    this.view = MusicEditorViewFactory.getView(view, new ViewOnlyModel(this.model));
    this.view.setListeners(this, this);
    this.view.initialize();
  }

  /**
   * Sets the model of this controller.
   * @param model the model to be used.
   * @throws IOException when file reader is not initialized properly or when file does not exist.
   */
  private void setModel(String model) throws IOException{
    this.model = MusicReader.parseFile(new FileReader(model), new MusicEditorBuilder());
  }

  /**
   * Key event handler, does nothing in this implementation.
   * @param e the key event.
   */
  @Override
  public void keyTyped(KeyEvent e) {

  }

  /**
   * Key event handler for the pressing of the key. Calls the view's set of runnable commands.
   * @param e key that has been pressed.
   */
  @Override
  public void keyPressed(KeyEvent e) {
    Runnable r = view.getKeyEvents().getOrDefault(e.getKeyCode(), null);
    if (r != null) {
      r.run();
    }
  }

  /**
   * Key event handler. release does nothing in this implementation.
   * @param e
   */
  @Override
  public void keyReleased(KeyEvent e) {

  }

  /**
   * Adds a note to the model.
   * @param start the start position for the note.
   * @param end the end position for the note
   * @param instrument the instrument for that note.
   * @param pitch the pitch of that note.
   * @param volume the volume of that note.
   */
  public void addNote(int start, int end, int instrument, int pitch, int volume) {
    this.model.addNote(start, end, instrument, pitch, volume);
  }
}
