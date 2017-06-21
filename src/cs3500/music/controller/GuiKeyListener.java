package cs3500.music.controller;

import java.awt.event.KeyEvent;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.MusicEditorOperations;
import cs3500.music.view.GuiView;
import cs3500.music.view.MusicEditorView;


import static javafx.scene.input.KeyCode.END;
import static javafx.scene.input.KeyCode.HOME;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;

/**
 * Created by Will on 6/20/2017.
 */
public class GuiKeyListener implements IMEKeyListener {
  private final MusicEditorOperations model;
  private final GuiView view;

  protected GuiKeyListener(MusicEditorOperations m, MusicEditorView v) {
    model = m;
    view = (GuiView) v;
  }


  @Override
  public void keyTyped(KeyEvent e) {
    switch (e) {
      if (e.equals(HOME)) {
        this.goToStart();
      }
      if (e.equals(END)) {
        this.goToEnd();
      }
      if (e.equals(LEFT)) {
        this.updateCursor(false);
      }
      if (e.equals(RIGHT)) {
        this.updateCursor(true);
      }
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    //do nothing when key is pressed
  }

  @Override
  public void keyReleased(KeyEvent e) {
    //do nothing when key is pressed
  }
}


private void goToStart() {
  int temp = view.
}

