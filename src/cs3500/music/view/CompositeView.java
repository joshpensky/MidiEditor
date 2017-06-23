package cs3500.music.view;

import cs3500.music.controller.MusicEditorController;
import cs3500.music.model.MusicEditorOperations;

import javax.sound.midi.MidiUnavailableException;
import java.awt.event.*;
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
  private StringBuilder log;

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
    this.midi.initialize();
    this.midi.pause();
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
    this.gui.removeMouseListener(guiMouse);
    MouseListener compositeMouse = new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        return; // no need for detection of mouse clicking
      }

      @Override
      public void mousePressed(MouseEvent e) {
        if (!midi.isRunning()) {
          guiMouse.mousePressed(e);
          midi.update();
        }
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        return; // no need for detection of mouse releasing
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        return; // no need for detection of mouse entering
      }

      @Override
      public void mouseExited(MouseEvent e) {
        return; // no need for detection of mouse exiting
      }
    };
    this.gui.addMouseListener(compositeMouse);
  }

  @Override
  public void update() {
    this.midi.update();
    this.gui.update();
  }

  private void setKeyEvents() {
    this.runs = new TreeMap<>();
    GuiContainer container = this.gui.getContainer();
    this.runs.put(39, () -> {
      if (!this.midi.isRunning()) {
        container.updatePosition(true);
      }
    });
    this.runs.put(37, () -> {
      if (!this.midi.isRunning()) {
        container.updatePosition(false);
      }
    });
    this.runs.put(36, () -> {
      if (!this.midi.isRunning()) {
        container.jumpToBeginning();
      }
    });
    this.runs.put(35, () -> {
      if (!this.midi.isRunning()) {
        container.jumpToEnd();
      }
    });
    this.runs.put(32, () -> {
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
