package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileReader;
import java.io.IOException;

import cs3500.music.model.MusicEditorBuilder;
import cs3500.music.model.MusicEditorOperations;
import cs3500.music.util.MusicReader;
import cs3500.music.view.MusicEditorView;
import cs3500.music.view.MusicEditorViewFactory;


/**
 * Created by Will on 6/20/2017.
 */
public class MusicEditorController implements KeyListener, MouseListener {
  private final MusicEditorOperations model;
  private final MusicEditorView view;


  public MusicEditorController(String model, String view)  throws IOException{
    this.model = MusicReader.parseFile(new FileReader(model), new MusicEditorBuilder());
    this.view = MusicEditorViewFactory.getView(view, this.model);
    this.view.initialize();
  }


  @Override
  public void keyTyped(KeyEvent e) {
    view.doKeyEvent(e);
  }

  @Override
  public void keyPressed(KeyEvent e) {

  }

  @Override
  public void keyReleased(KeyEvent e) {

  }

  @Override
  public void mouseClicked(MouseEvent e) {

  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }
}
