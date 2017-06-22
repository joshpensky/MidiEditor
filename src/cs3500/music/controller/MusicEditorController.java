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
 * Created by Will on 6/20/2017.
 */
public class MusicEditorController implements KeyListener {
  private final MusicEditorOperations model;
  private final MusicEditorView view;


  public MusicEditorController(String model, String view) throws IOException {
    this.model = MusicReader.parseFile(new FileReader(model), new MusicEditorBuilder());
    this.view = MusicEditorViewFactory.getView(view, new ViewOnlyModel(this.model));
    this.view.setListeners(this, this);
    this.view.initialize();
  }


  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    //System.out.println(e.toString());
    Runnable r = view.getKeyEvents().getOrDefault(e.getKeyCode(), null);
    //System.out.println(e.getKeyCode());
    if (r != null) {
      r.run();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }

  public void addNote(int start, int end, int instrument, int pitch, int volume) {
    this.model.addNote(start, end, instrument, pitch, volume);
  }
}
