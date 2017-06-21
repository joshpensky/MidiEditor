package cs3500.music.view;

import cs3500.music.model.MusicEditorOperations;

import javax.sound.midi.MidiUnavailableException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by josh_jpeg on 6/21/17.
 */
public class CompositeView implements MusicEditorView {
  private final MusicEditorOperations model;
  private final MidiView midi;
  private final GuiView gui;
  private Map<Integer, Runnable> runs;

  protected CompositeView(MusicEditorOperations model) throws MidiUnavailableException {
    this.model = model;
    this.midi = new MidiView.Builder(this.model).build();
    this.gui = new GuiView(this.model);
    this.setRunnable();
    this.gui.setFocusable(true);
    this.gui.requestFocus();
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
  public Map<Integer, Runnable> getKeyEvents() {
    return null;
  }

  @Override
  public void setListeners(MouseListener clicks, KeyListener keys) {
    //do nothing for now
  }

  private void setRunnable() {
    this.runs = new TreeMap<>();
    GuiContainer c = this.gui.getContonainer();
    this.runs.put(39, () -> {
      if (!this.midi.isRunning()) {
        c.updatePosition(true);
      }
    });
    this.runs.put(37, () -> {
      if (!this.midi.isRunning()) {
        c.updatePosition(false);
      }
    });
    this.runs.put(36, () -> {
      if (!this.midi.isRunning()) {
        c.jumpToBeginning();
      }
    });
    this.runs.put(35, () -> {
      if (!this.midi.isRunning()) {
        c.jumpToEnd();
      }
    });
    this.runs.put(32, () -> {
      if (this.midi.isRunning()) {
        this.midi.pause();
      } else {
        //TO MAKE START GO, MAKE GOOD START :)
      }
    });
  }
}
