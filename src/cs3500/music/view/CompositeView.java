package cs3500.music.view;

import cs3500.music.model.MusicEditorOperations;

import javax.sound.midi.MidiUnavailableException;
import java.awt.event.KeyEvent;

/**
 * Created by josh_jpeg on 6/21/17.
 */
public class CompositeView implements MusicEditorView {
  private final MusicEditorOperations model;
  private final MidiView midi;
  private final GuiView gui;

  protected CompositeView(MusicEditorOperations model) throws MidiUnavailableException {
    this.model = model;
    this.midi = new MidiView.Builder(this.model).build();
    this.gui = new GuiView(this.model);
  }

  @Override
  public void initialize() {
    gui.initialize();
    Thread r = new Thread(new Runnable() {
      @Override
      public void run() {
        int currPosition = midi.getTickPosition();
        while (midi.getTickPosition() < model.getLength()) {
          if (currPosition != midi.getTickPosition()) {
            currPosition = midi.getTickPosition();
            gui.updateCursor(true);
          }
        }
      }
    });
    r.start();
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {

    }
    midi.initialize();
  }

  @Override
  public String getLog() {
    return null;
  }

  @Override
  public void doKeyEvent(KeyEvent e) {

  }
}
