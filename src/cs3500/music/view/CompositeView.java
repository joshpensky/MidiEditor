package cs3500.music.view;

import cs3500.music.controller.MusicEditorController;
import cs3500.music.model.MusicEditorOperations;
import cs3500.music.util.MidiConversion;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
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
  private Runnable matchMidi;

  protected CompositeView(MusicEditorOperations model) throws MidiUnavailableException {
    this.model = model;
    this.midi = new MidiView.Builder(this.model).build();
    this.gui = new GuiView(this.model);
    this.setRunnable();
    this.gui.setFocusable(true);
    this.gui.requestFocus();
    this.matchMidi = () -> {
      boolean run = true;
      while (run) {
        try {
          // has to update thread
          Thread.sleep(1);
          run = !midi.isRunning();
        } catch (InterruptedException e) {
          // interrupted
        }
      }
      int currPosition = midi.getTickPosition();
      System.out.println(midi.getTickPosition() + " : " + gui.getCursorPosition());
      while (midi.getTickPosition() <= model.getLength()) {
        if (!midi.isRunning()) {
          break;
        }
        if (currPosition != midi.getTickPosition()) {
          currPosition = midi.getTickPosition();
          gui.updateCursor(true);
        }
      }
      gui.updateCursor(true);
    };
  }

  @Override
  public void initialize() {
    gui.initialize();
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      // allows gui to load first before starting midi
    }
    int currPosition = gui.getCursorPosition();
    midi.setTickPosition(currPosition);
    new Thread(matchMidi).start();
    midi.initialize();
  }

  @Override
  public String getLog() {
    return null;
  }

  @Override
  public Map<Integer, Runnable> getKeyEvents() {
    return this.runs;
  }

  @Override
  public void setListeners(MusicEditorController controls, KeyListener keys) {
    this.gui.setListeners(controls, keys);
    //GuiContainer container = this.gui.getContainer();
    MouseListener mouse = this.gui.getMouseListeners()[0];
    MouseListener m = new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {

      }

      @Override
      public void mousePressed(MouseEvent e) {
        if (!midi.isRunning()) {
          /*Integer[] note = container.getNote(e);
          if (note != null) {
            controls.addNote(note[MidiConversion.NOTE_START], note[MidiConversion.NOTE_END],
              note[MidiConversion.NOTE_INSTRUMENT], note[MidiConversion.NOTE_PITCH],
              note[MidiConversion.NOTE_VOLUME]);
            container.updatePosition(true);
            container.updatePanels();
          }*/
          mouse.mousePressed(e);
          midi.update(model.getNotes(), model.getTempo(), model.getLength());
        }
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
    };
    this.gui.removeMouseListener(mouse);
    this.gui.addMouseListener(m);

  }

  @Override
  public void update(List<Integer[]> notes, int tempo, int length) {

  }

  private void setRunnable() {
    this.runs = new TreeMap<>();
    GuiContainer c = this.gui.getContainer();
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
        //matchMidi.run();
        new Thread(matchMidi).start();
        // PLAY STOPS EVERYTHING IN THREAD, RELOCATE TO SEPARATE THREAD TO RUN THE MUSIC
        new Thread(new Runnable() {
          @Override
          public void run() {
            int currPosition = gui.getCursorPosition();
            midi.setTickPosition(currPosition);
            if (midi.isOver()) {
              midi.initialize();
            } else {
              midi.play();
            }
          }
        }).start();
      }
    });
  }
}
