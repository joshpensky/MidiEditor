package cs3500.music.view;

import cs3500.music.controller.MusicEditorController;
import cs3500.music.model.MusicEditorOperations;
import cs3500.music.util.MidiConversion;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import java.awt.event.KeyListener;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents the MIDI view for a music editor, in which it stores and plays all of the notes of
 * a model through the system's MIDI.
 */
public class MidiView implements MusicEditorView {
  private final StringBuilder log;
  private final MusicEditorOperations model;
  private final Sequencer sequencer;
  private Sequence sequence;
  private int tickPosition;

  /**
   * Represents the builder class for a MidiView. Sets the sequencer of the MidiView to the
   * MidiSystem's default sequencer, but allows for the sequencer to be changed.
   */
  public static final class Builder {
    private MusicEditorOperations model;
    private Sequencer sequencer;

    /**
     * Constructs a new {@code Builder} for a MidiView.
     *
     * @param model   the model to be represented musically using MIDI
     * @throws IllegalArgumentException if the given model is uninitialized
     */
    protected Builder(MusicEditorOperations model) throws IllegalArgumentException {
      if (model == null) {
        throw new IllegalArgumentException("Given model is uninitialized.");
      }
      this.model = model;
      this.sequencer = null;
      
    }

    /**
     * Sets the sequencer for a new MidiView to the given one.
     *
     * @param sequencer   the sequencer to be set for the MidiView
     * @return this builder
     * @throws IllegalArgumentException if the given sequencer is uninitialized
     */
    protected Builder setSequencer(Sequencer sequencer) throws IllegalArgumentException {
      if (sequencer == null) {
        throw new IllegalArgumentException("Sequencer is uninitialized.");
      }
      this.sequencer = sequencer;
      return this;
    }

    /**
     * Returns a new MidiView with the given specifications set in this builder. If no sequencer
     * has been set, it is defaulted to the MidiSystem's sequencer.
     *
     * @return a new MidiView with this builder's instructions
     * @throws MidiUnavailableException if MIDI is currently unavailable for the system
     */
    protected MidiView build() throws MidiUnavailableException {
      if (this.sequencer == null) {
        this.sequencer = MidiSystem.getSequencer();
      }
      return new MidiView(this);
    }
  }

  /**
   * Constructs a new {@code MidiView} using an instance of the nested builder class.
   *
   * @param builder   the builder for this MidiView
   */
  private MidiView(Builder builder) {
    this.log = new StringBuilder();
    this.model = builder.model;
    this.sequencer = builder.sequencer;
    this.sequence = null;
    this.tickPosition = 0;
  }

  @Override
  public void initialize() {
    this.update();
    this.play();
  }

  /**
   * Starts or resumes playback of the set sequence in the view at the set tick position in the
   * set tempo.
   */
  protected void play() {
    this.sequencer.setTempoInMPQ(this.model.getTempo());
    this.sequencer.setTickPosition(this.tickPosition);
    this.sequencer.start();
    this.sequencer.setTempoInMPQ(this.model.getTempo());
  }

  /**
   * Pauses playback of the view.
   */
  protected void pause() {
    this.sequencer.stop();
  }

  /**
   * Sets the tick position of the midi view safely (in that it will push it to position 0 if
   * given negative, or pull it back to the length of the sequence if longer than).
   *
   * @param tickPosition   the new position of the MIDI's tick
   */
  protected void setTickPosition(int tickPosition) {
    this.tickPosition = Math.min(Math.max(0, tickPosition), this.model.getLength());
  }

  /**
   * Gets the current tick position of the midi view.
   *
   * @return the current tick position
   */
  protected int getTickPosition() {
    this.tickPosition = (int) this.sequencer.getTickPosition();
    return this.tickPosition;
  }

  /**
   * Checks whether or not the sequencer in the midi is actively playing something.
   *
   * @return true if the midi is playing the sequence, false otherwise
   */
  protected boolean isPlaying() {
    return this.sequencer.isRunning();
  }

  /**
   * Checks whether or not the sequencer has finished playing the sequence.
   *
   * @return true if the midi has finished playing, false otherwise
   */
  protected boolean isOver() {
    return this.sequencer.getSequence() == null;
  }

  /**
   * Helper to the playSequence method. Creates a sequence from the list of note data currently in
   * the model, to be sent to the sequencer.
   *
   * @param notes    the list of note data of the piece currently from the model
   * @throws InvalidMidiDataException if an invalid note or tempo in the model is passed to the MIDI
   */
  private Sequence createSequence(List<Integer[]> notes) throws InvalidMidiDataException {
    Sequence sequence = new Sequence(Sequence.PPQ, 1);
    Track tr = sequence.createTrack();
    for (Integer[] note : notes) {
      int start = note[MidiConversion.NOTE_START];
      int end = note[MidiConversion.NOTE_END];
      int instrum = note[MidiConversion.NOTE_INSTRUMENT];
      int pitch = Math.max(0, Math.min(127, note[MidiConversion.NOTE_PITCH]));
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
  public String getLog() {
    return this.log.toString();
  }

  @Override
  public Map<Integer, Runnable> getKeyEventRunnables() {
    // no key events
    return new TreeMap<>();
  }

  @Override
  public void addListeners(MusicEditorController controller, KeyListener keyListener) {
    // no listeners to set
    return;
  }

  @Override
  public void update() {
    try {
      this.sequence = createSequence(this.model.getNotes());
      if (this.sequencer.isOpen()) {
        this.sequencer.close();
      }
      this.sequencer.open();
      this.sequencer.setSequence(this.sequence);
    } catch (InvalidMidiDataException e) {
      this.log.append("Encountered fatal InvalidMidiDataException: " + e.getMessage() + "\n");
    } catch (MidiUnavailableException e) {
      this.log.append("Encountered fatal MidiUnavailableException: " + e.getMessage() + "\n");
    }
  }
}
