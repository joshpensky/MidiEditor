package cs3500.music.view;

import cs3500.music.model.EditorOperations;

import javax.sound.midi.*;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl implements ViewInterface {
  private final Sequencer sequencer;

  public MidiViewImpl() throws MidiUnavailableException {
    this.sequencer = MidiSystem.getSequencer();
    this.sequencer.open();
  }

  public void playNote(int tempo, List<Integer[]> notes, int length) throws
    InvalidMidiDataException {
    this.sequencer.setSequence(createSequence(tempo, notes));
    this.sequencer.addMetaEventListener(new MetaEventListener() {
      @Override
      public void meta(MetaMessage meta) {
        if (meta.getType() == 47)
          sequencer.close();
      }
    });
    this.sequencer.start();
    this.sequencer.setTempoInMPQ(tempo);
  }

  private Sequence createSequence(int tempo, List<Integer[]> notes) throws
    InvalidMidiDataException {
    Sequence sequence = new Sequence(Sequence.PPQ, 1);
    Track tr = sequence.createTrack();
    System.out.println("tempo " + tempo);
    for (Integer[] note : notes) {
      System.out.println("note " + note[0] + " " + note[1] + " " + note[2] + " " + note[3]
          + " " + note[4]);
      int start = note[0];
      int end = note[1];
      int instrument = note[2];
      int pitch = note[3];
      int volume = note[4];
      int channel = instrument / 8;
      MidiMessage startMsg = new ShortMessage(ShortMessage.NOTE_ON, channel, pitch, volume);
      MidiMessage stopMsg = new ShortMessage(ShortMessage.NOTE_OFF, channel, pitch, volume);
      MidiMessage addInstum = new ShortMessage(ShortMessage.PROGRAM_CHANGE, channel, instrument, 0);
      if (tr.remove(new MidiEvent(addInstum, 0))) {
        tr.add(new MidiEvent(addInstum, 0));
      }
      tr.add(new MidiEvent(startMsg, start));
      tr.add(new MidiEvent(stopMsg, end));
    }
    return sequence;
  }

  @Override
  public void initialize(EditorOperations model) {
    try {
      playNote(model.getTempo(), model.getNotes(), model.totalPieceLength());
    } catch (InvalidMidiDataException e) {
      System.err.println("whhops, midi sucks: " + e.getMessage());
    }

  }
}
