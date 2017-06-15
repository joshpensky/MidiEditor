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

  public void playNote(int tempo, List<Integer[]> notes, int length)
      throws InvalidMidiDataException {

    Sequence sequence = new Sequence(Sequence.PPQ, 10);
    Map<Integer, Track> tracks = new TreeMap<>();

    for (Integer[] note : notes) {
      MidiMessage startMsg = new ShortMessage(ShortMessage.NOTE_ON, 0, note[3], note[4]);
      MidiMessage stopMsg = new ShortMessage(ShortMessage.NOTE_OFF, 0, note[3], note[4]);

      Track tr;
      int instrum = note[2];
      if (tracks.containsKey(instrum)) {
        tr = tracks.get(instrum);
      } else {
        tr = sequence.createTrack();
        tracks.put(instrum, tr);
        MidiMessage instrument = new ShortMessage(ShortMessage.PROGRAM_CHANGE, 0, instrum, 0);
        tr.add(new MidiEvent(instrument, 0));
      }
      tr.add(new MidiEvent(startMsg, note[0]));
      tr.add(new MidiEvent(stopMsg, note[1]));
    }

    try {
      Sequencer seq = MidiSystem.getSequencer();
      seq.setSequence(sequence);
      seq.setTempoInBPM(tempo);
      seq.open();
      seq.addMetaEventListener(new MetaEventListener() {
        @Override
        public void meta(MetaMessage meta) {
          if (meta.getType() == 47)
            System.exit(0);
        }
      });
      seq.start();
    } catch (MidiUnavailableException e) {
      System.err.println("whoops");
    }

    /*Instrument[] orchestra = this.synth.getAvailableInstruments();
    System.out.println(length);
    for (Integer[] note : notes) {
      System.out.println("{" + note[0] + ", " + note[1] + ", " + note[2] + ", " + note[3]
          + ", " + note[4] + "}");
      this.synth.loadInstrument(orchestra[note[2]]);
      MidiMessage startMsg = new ShortMessage(ShortMessage.NOTE_ON, note[2], note[3], note[4]);
      MidiMessage stopMsg = new ShortMessage(ShortMessage.NOTE_OFF, note[2], note[3], note[4]);
      //this.receiver.send(startMsg, -1);
      this.receiver.send(startMsg, note[0]);
      //this.receiver.send(stopMsg, this.synth.getMicrosecondPosition() + 200000);
      this.receiver.send(stopMsg, note[1]);
    }

    try {
      Thread.sleep(length);
    } catch (InterruptedException e) {
      System.err.println("interrupted");
    }*/

    /* 
    The receiver does not "block", i.e. this method
    immediately moves to the next line and closes the 
    receiver without waiting for the synthesizer to 
    finish playing. 
    
    You can make the program artificially "wait" using
    Thread.sleep. A better solution will be forthcoming
    in the subsequent assignments.
    */
    //this.receiver.close(); // Only call this once you're done playing *all* notes
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
