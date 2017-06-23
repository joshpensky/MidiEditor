package cs3500.music.view;

import cs3500.music.controller.MusicEditorController;
import cs3500.music.model.MusicEditorOperations;

import javax.sound.midi.MidiUnavailableException;
import java.awt.event.*;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents the composite view for a MusicEditor. Allows for simultaneous viewing of the
 * editor and piano visuals, as well as playback from the MIDI view.
 */
public class CompositeView implements MusicEditorView {
  private final MusicEditorOperations model;
  private final MidiView midi;
  private final GuiView gui;
  private Map<Integer, Runnable> runs;
  private Runnable matchMidi;
  private StringBuilder log;

  /**
   * Constructs a new {@code CompositeView} using the given model to display notes in the gui
   * view, as well as playing notes through the MIDI.
   *
   * @param model   the model to be represented in the composite view
   * @throws MidiUnavailableException if MIDI is currently unavailable for the system
   */
  protected CompositeView(MusicEditorOperations model) throws MidiUnavailableException {
    this.model = model;
    this.midi = new MidiView.Builder(this.model).build();
    this.gui = new GuiView(this.model);
    this.setKeyEvents();
    this.gui.setFocusable(true);
    this.gui.requestFocus();
    this.log = new StringBuilder();
    this.matchMidi = () -> {
      while (!midi.isRunning()) {
        try {
          // has to update thread
          Thread.sleep(1);
        } catch (InterruptedException e) {
          this.log.append("Unexpected InterruptedException: " + e.getMessage() + "\n");
        }
      }
      int currPosition = midi.getTickPosition();
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
    this.gui.initialize();
    new Thread(this.matchMidi).start();
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      //
    }
    int currPosition = gui.getCursorPosition();
    midi.setTickPosition(currPosition);
    this.midi.initialize();
  }

  @Override
  public String getLog() {
    return this.log.append(this.gui.getLog()).append(this.midi.getLog()).toString();
  }

  @Override
  public Map<Integer, Runnable> getKeyEvents() {
    return this.runs;
  }

  @Override
  public void setListeners(MusicEditorController controls, KeyListener keys) {
    this.gui.setListeners(controls, keys);
    MouseListener guiMouse = this.gui.getMouseListeners()[0];
    MouseListener compositeMouse = new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        if (!midi.isRunning()) {
          guiMouse.mousePressed(e);
          midi.update();
        }
      }
    };
    this.gui.removeMouseListener(guiMouse);
    this.gui.addMouseListener(compositeMouse);
  }

  @Override
  public void update() {
    this.midi.update();
    this.gui.update();
  }

  /**
   * Sets the different key events for a KeyListener attached to this view. Provides
   * {@code Runnable}s per keyCode for the KeyListener to run when the respective key is pressed.
   * Uses the KeyEvents from the GUI view and modifies them so they only work when the midi is
   * not running.
   */
  private void setKeyEvents() {
    this.runs = new TreeMap<>();
    Map<Integer, Runnable> guiKeyEvents = this.gui.getKeyEvents();
    for (Integer keyCode : guiKeyEvents.keySet()) {
      this.runs.put(keyCode, () -> {
        if (!this.midi.isRunning()) {
          guiKeyEvents.get(keyCode).run();
        }
      });
    }
    this.runs.put(KeyEvent.VK_SPACE, () -> {
      if (this.midi.isRunning()) {
        this.midi.pause();
      } else {
        new Thread(matchMidi).start();
        int currPosition = gui.getCursorPosition();
        midi.setTickPosition(currPosition);
        if (midi.isOver()) {
          midi.initialize();
        } else {
          midi.play();
        }
      }
    });
  }
}
