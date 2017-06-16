package cs3500.music.view;

import cs3500.music.model.MusicEditorOperations;
import cs3500.music.util.MidiConversion;

import javax.sound.midi.*;

import java.util.List;

/**
 * A skeleton for MIDI playback
 */
public class MidiView implements MusicEditorView {
  private StringBuilder log;
  private MusicEditorOperations model;
  private final Sequencer sequencer;

  protected MidiView(MusicEditorOperations model) throws MidiUnavailableException {
    this.model = model;
    this.log = new StringBuilder();
    this.sequencer = MidiSystem.getSequencer();
    this.sequencer.open();
  }

  protected void playNote(int tempo, List<Integer[]> notes, int length)
      throws InvalidMidiDataException {
    this.sequencer.setSequence(createSequence(notes));
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

  private Sequence createSequence(List<Integer[]> notes)
      throws InvalidMidiDataException {
    Sequence sequence = new Sequence(Sequence.PPQ, 1);
    Track tr = sequence.createTrack();
    for (Integer[] note : notes) {
      int start = note[MidiConversion.NOTE_START];
      int end = note[MidiConversion.NOTE_END];
      int instrum = note[MidiConversion.NOTE_INSTRUMENT];
      int pitch = note[MidiConversion.NOTE_PITCH];
      int volume = note[MidiConversion.NOTE_VOLUME];
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

  @Override
  public String getLog() {
    return this.log.toString();
  }
}
