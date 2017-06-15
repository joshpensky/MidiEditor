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
  private final Synthesizer synth;
  private final Receiver receiver;

  public MidiViewImpl() throws MidiUnavailableException {
    this.synth = MidiSystem.getSynthesizer();
    this.receiver = synth.getReceiver();
    this.synth.open();
  }
  /**
   * Relevant classes and methods from the javax.sound.midi library:
   * <ul>
   *  <li>{@link MidiSystem#getSynthesizer()}</li>
   *  <li>{@link Synthesizer}
   *    <ul>
   *      <li>{@link Synthesizer#open()}</li>
   *      <li>{@link Synthesizer#getReceiver()}</li>
   *      <li>{@link Synthesizer#getChannels()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link Receiver}
   *    <ul>
   *      <li>{@link Receiver#send(MidiMessage, long)}</li>
   *      <li>{@link Receiver#close()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link MidiMessage}</li>
   *  <li>{@link ShortMessage}</li>
   *  <li>{@link MidiChannel}
   *    <ul>
   *      <li>{@link MidiChannel#getProgram()}</li>
   *      <li>{@link MidiChannel#programChange(int)}</li>
   *    </ul>
   *  </li>
   * </ul>
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI">
   *   https://en.wikipedia.org/wiki/General_MIDI
   *   </a>
   */

  public void playNote(int tempo, List<Integer[]> notes, int length) throws
    InvalidMidiDataException {
    try {
      Sequencer seq = MidiSystem.getSequencer();
      seq.setSequence(createSequence(tempo, notes));
      seq.setTempoInMPQ(tempo);
      seq.addMetaEventListener(new MetaEventListener() {
        @Override
        public void meta(MetaMessage meta) {
          if (meta.getType() == 47)
            System.exit(0);
        }
      });
      seq.open();
      seq.start();
    } catch (MidiUnavailableException e) {
      System.err.println("whoops");
    }
  }

  private Sequence createSequence(int tempo, List<Integer[]> notes) throws
    InvalidMidiDataException {
    Sequence sequence = new Sequence(Sequence.PPQ, 4);
    Map<Integer, Track> tracks = new TreeMap<>();
    for (Integer[] note : notes) {
      System.out.println("note " + note[0] + " " + note[1] + " " + note[2] + " " + note[3]
          + " " + note[4]);
      int start = note[0];
      int end = note[1];
      int instrument = note[2];
      int pitch = note[3];
      int volume = note[4];
      int channel = instrument;
      MidiMessage startMsg = new ShortMessage(ShortMessage.NOTE_ON, channel, pitch, volume);
      MidiMessage stopMsg = new ShortMessage(ShortMessage.NOTE_OFF, channel, pitch, volume);
      Track tr;
      if (tracks.containsKey(instrument)) {
        tr = tracks.get(instrument);
      } else {
        tr = sequence.createTrack();
        MidiMessage addInstum = new ShortMessage(ShortMessage.PROGRAM_CHANGE, channel, instrument, 0);
        tr.add(new MidiEvent(addInstum, 0));
        tracks.put(instrument, tr);
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
      System.err.println("whhops, midi sucks.");
    }

  }
}
