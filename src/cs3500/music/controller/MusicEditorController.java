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
  private final MusicEditorOperations model;
  private final MusicEditorView view;

  /**
   * Constructs a music editor controller and sets up both the view to be used and the model to be used.
   * @param model the String argument for which type of model to use
   * @param view the String argument for which type of view to use
   * @throws IOException if input or output files are improperly initialized
   */
  public MusicEditorController(String model, String view) throws IOException {
    this.model = MusicReader.parseFile(new FileReader(model), new MusicEditorBuilder());
    this.view = MusicEditorViewFactory.getView(view, new ViewOnlyModel(this.model));
    this.view.setListeners(this, this);
    this.view.initialize();
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
