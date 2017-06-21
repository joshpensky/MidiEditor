package cs3500.music.controller;

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
public class MusicEditorController {
  private final MusicEditorOperations model;
  private final MusicEditorView view;
  private final IMEKeyListener keyListener;

  public MusicEditorController(String model, String view) throws IOException {
    this.model = MusicReader.parseFile(new FileReader(model), new MusicEditorBuilder());
    this.view = MusicEditorViewFactory.getView(view, this.model);
    this.view.initialize();
    if (view.toLowerCase().equals("visual")) {
      keyListener = new GuiKeyListener(this.model, this.view);
    } else if (view.toLowerCase().equals("midi")) {

    } else if (view.toLowerCase().equals("combo")) {

    } else {

    }
    this.runProgram();
  }

  private void runProgram() {
    while (true) {

    }
  }

}
