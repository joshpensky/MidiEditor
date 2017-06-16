package cs3500.music.view;

import cs3500.music.model.MusicEditorOperations;

import javax.sound.midi.*;

import java.util.List;

/**
 * A skeleton for MIDI playback
 */
public class MidiView implements MusicEditorView {
  MusicEditorOperations model;
  private final Sequencer sequencer;

  public MidiView(MusicEditorOperations model) throws MidiUnavailableException {
    this.model = model;
    this.sequencer = MidiSystem.getSequencer();
    this.sequencer.open();
  }

  public void playNote(int tempo, List<Integer[]> notes, int length) throws
    InvalidMidiDataException {
    this.sequencer.setSequence(createSequence(tempo, notes));
    this.sequencer.addMetaEventListener(new MetaEventListener() {
      @Override
      public void meta(MetaMessage meta) {
        if (meta.getType() == 47) {
          try {
            Thread.sleep(1000);
            sequencer.close();
          } catch (InterruptedException e) {
            System.err.println("interrupted");
          }
        }
      }
    });
    this.sequencer.start();
    this.sequencer.setTempoInMPQ(tempo);
    /*while (this.sequencer.isRunning()) {
      if (this.sequencer.getTickPosition() == 1) {
        this.sequencer.close();
      }
    }*/
  }

  private Sequence createSequence(int tempo, List<Integer[]> notes) throws
    InvalidMidiDataException {
    Sequence sequence = new Sequence(Sequence.PPQ, 1);
    Track tr = sequence.createTrack();
    for (Integer[] note : notes) {
      int start = note[0];
      int end = note[1];
      int instrum = note[2];
      int pitch = note[3];
      int volume = note[4];
      MidiMessage startMsg = new ShortMessage(ShortMessage.NOTE_ON, 0, pitch, volume);
      MidiMessage stopMsg = new ShortMessage(ShortMessage.NOTE_OFF, 0, pitch, volume);
      MidiMessage addInstrum = new ShortMessage(ShortMessage.PROGRAM_CHANGE, 0, instrum, 0);
      tr.add(new MidiEvent(addInstrum, start));
      tr.add(new MidiEvent(startMsg, start));
      tr.add(new MidiEvent(stopMsg, end));
    }
    return sequence;
  }

  @Override
  public void initialize() {
    try {
      playNote(model.getTempo(), model.getNotes(), model.totalPieceLength());
    } catch (InvalidMidiDataException e) {
      System.err.println("whhops, midi sucks: " + e.getMessage());
    }

  }
}
