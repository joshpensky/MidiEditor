package cs3500.music.view;


import javax.sound.midi.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a mock sequencer, that can take in a sequence and tempo, and log all events and
 * messages sent to it on start.
 */
public class MockSequencer implements Sequencer {
  private Sequence sequence;
  private final StringBuilder log;
  private int maxLength;
  private float tempo;

  /**
   * Constructs a new {@code Sequencer}.
   */
  public MockSequencer() {
    this.sequence = null;
    this.log = new StringBuilder();
    this.maxLength = 0;
    this.tempo = 0;
  }

  /**
   * Gets the log of the mock sequencer, created in the call to start().
   * @return log of sequencer events
   */
  public String getLog() {
    return this.log.toString();
  }

  @Override
  public void setSequence(Sequence sequence) throws InvalidMidiDataException {
    if (sequence == null) {
      throw new InvalidMidiDataException("Given sequence is uninitialized.");
    }
    this.sequence = sequence;
  }

  @Override
  public Sequence getSequence() {
    return this.sequence;
  }

  /**
   * Starts logging all midi events and messages stored in the mock sequencer's sequence.
   * Sets the tick position of the sequencer to the max length (or last beat) of the sequence.
   */
  @Override
  public void start() {
    if (this.sequence != null) {
      List<Integer[]> notes = new ArrayList<>();
      List<Integer[]> instruments = new ArrayList<>();
      this.log.append("tempo " + (int) this.tempo + "\n");
      for (Track track : this.sequence.getTracks()) {
        for (int i = 0; i < track.size(); i++) {
          MidiEvent event = track.get(i);
          MidiMessage msg = event.getMessage();
          if (msg instanceof ShortMessage) {
            ShortMessage m = (ShortMessage) msg;
            switch (m.getCommand()) {
              case ShortMessage.PROGRAM_CHANGE:
                instruments.add(new Integer[]{(int) event.getTick(), m.getData1()});
                break;
              case ShortMessage.NOTE_ON:
                notes.add(new Integer[]{(int) event.getTick(), m.getData1(),
                    m.getData2()});
                break;
              case ShortMessage.NOTE_OFF:
                for (Integer[] note : notes) {
                  if (note[1].equals(m.getData1()) && note[2].equals(m.getData2())) {
                    int tick = (int) event.getTick();
                    int instrum = 0;
                    for (Integer[] instrumArr : instruments) {
                      if (note[0].equals(instrumArr[0])) {
                        instrum = instrumArr[1];
                        instruments.remove(instrumArr);
                        break;
                      }
                    }
                    this.maxLength = Math.max(this.maxLength, tick);
                    this.log.append("note " + note[0] + " " + tick + " " + instrum
                        + " " + note[1] + " " + note[2] + "\n");
                    notes.remove(note);
                    break;
                  }
                }
                break;
              default:
                break;
            }
          }
        }
      }
    }
  }

  @Override
  public void setTempoInMPQ(float mpq) {
    this.tempo = mpq;
  }

  @Override
  public long getTickPosition() {
    return this.maxLength;
  }

  @Override
  public void setSequence(InputStream stream) throws IOException, InvalidMidiDataException {
    //does nothing for mock sequencer
  }

  @Override
  public void stop() {
    //does nothing for mock sequencer
  }

  @Override
  public boolean isRunning() {
    return false;
  }

  @Override
  public void startRecording() {
    //does nothing for mock sequencer
  }

  @Override
  public void stopRecording() {
    //does nothing for mock sequencer
  }

  @Override
  public boolean isRecording() {
    return false;
  }

  @Override
  public void recordEnable(Track track, int channel) {
    //does nothing for mock sequencer
  }

  @Override
  public void recordDisable(Track track) {
    //does nothing for mock sequencer
  }

  @Override
  public float getTempoInBPM() {
    return 0;
  }

  @Override
  public void setTempoInBPM(float bpm) {
    //does nothing for mock sequencer
  }

  @Override
  public float getTempoInMPQ() {
    return 0;
  }

  @Override
  public void setTempoFactor(float factor) {
    //does nothing for mock sequencer
  }

  @Override
  public float getTempoFactor() {
    return 0;
  }

  @Override
  public long getTickLength() {
    return 0;
  }

  @Override
  public void setTickPosition(long tick) {
    //does nothing for mock sequencer
  }

  @Override
  public long getMicrosecondLength() {
    return 0;
  }

  @Override
  public Info getDeviceInfo() {
    return null;
  }

  @Override
  public void open() throws MidiUnavailableException {
    //does nothing for mock sequencer
  }

  @Override
  public void close() {
    //does nothing for mock sequencer
  }

  @Override
  public boolean isOpen() {
    return false;
  }

  @Override
  public long getMicrosecondPosition() {
    return 0;
  }

  @Override
  public int getMaxReceivers() {
    return 0;
  }

  @Override
  public int getMaxTransmitters() {
    return 0;
  }

  @Override
  public Receiver getReceiver() throws MidiUnavailableException {
    return null;
  }

  @Override
  public List<Receiver> getReceivers() {
    return null;
  }

  @Override
  public Transmitter getTransmitter() throws MidiUnavailableException {
    return null;
  }

  @Override
  public List<Transmitter> getTransmitters() {
    return null;
  }

  @Override
  public void setMicrosecondPosition(long microseconds) {
    //does nothing for mock sequencer
  }

  @Override
  public void setMasterSyncMode(SyncMode sync) {
    //does nothing for mock sequencer
  }

  @Override
  public SyncMode getMasterSyncMode() {
    return null;
  }

  @Override
  public SyncMode[] getMasterSyncModes() {
    return new SyncMode[0];
  }

  @Override
  public void setSlaveSyncMode(SyncMode sync) {
    //does nothing for mock sequencer
  }

  @Override
  public SyncMode getSlaveSyncMode() {
    return null;
  }

  @Override
  public SyncMode[] getSlaveSyncModes() {
    return new SyncMode[0];
  }

  @Override
  public void setTrackMute(int track, boolean mute) {
    //does nothing for mock sequencer
  }

  @Override
  public boolean getTrackMute(int track) {
    return false;
  }

  @Override
  public void setTrackSolo(int track, boolean solo) {
    //does nothing for mock sequencer
  }

  @Override
  public boolean getTrackSolo(int track) {
    return false;
  }

  @Override
  public boolean addMetaEventListener(MetaEventListener listener) {
    return false;
  }

  @Override
  public void removeMetaEventListener(MetaEventListener listener) {
    //does nothing for mock sequencer
  }

  @Override
  public int[] addControllerEventListener(ControllerEventListener listener, int[] controllers) {
    return new int[0];
  }

  @Override
  public int[] removeControllerEventListener(ControllerEventListener listener, int[] controllers) {
    return new int[0];
  }

  @Override
  public void setLoopStartPoint(long tick) {
    //does nothing for mock sequencer
  }

  @Override
  public long getLoopStartPoint() {
    return 0;
  }

  @Override
  public void setLoopEndPoint(long tick) {
    //does nothing for mock sequencer
  }

  @Override
  public long getLoopEndPoint() {
    return 0;
  }

  @Override
  public void setLoopCount(int count) {
    //does nothing for mock sequencer
  }

  @Override
  public int getLoopCount() {
    return 0;
  }
}
