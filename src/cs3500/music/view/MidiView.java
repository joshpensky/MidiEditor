package cs3500.music.view;

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

import java.util.List;

/**
 * Represents the MIDI view for a music editor, in which it stores and plays all of the notes of
 * a model through the system's MIDI.
 */
public class MidiView implements MusicEditorView {
  private final StringBuilder log;
  private final MusicEditorOperations model;
  private final Sequencer sequencer;

  /**
   * Represents the builder class for a MidiView. Sets the sequencer of the MidiView to the
   * MidiSystem's default sequencer, but allows for the sequencer to be changed.
   */
  public static final class Builder {
    private MusicEditorOperations model;
    private Sequencer sequencer;

    /**
     * Constructs a new {@code Builder} for a MidiView. The sequencer is defaulted to the
     * MidiSystem's sequencer.
     *
     * @param model   the model to be represented musically using MIDI
     * @throws IllegalArgumentException if the given model is uninitialized
     * @throws MidiUnavailableException if MIDI is currently unavailable for the system
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
     * Returns a new MidiView with the given specifications set in this builder.
     *
     * @return a new MidiView with this builder's instructions
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
  }

  @Override
  public void initialize() {
    try {
      this.playSequence(this.model.getTempo(), this.model.getNotes(),
          this.model.getLength());
    } catch (InvalidMidiDataException e) {
      this.log.append("Encountered fatal InvalidMidiDataException:\n" + e.getMessage() + "\n");
    } catch (MidiUnavailableException e) {
      this.log.append("Encountered fatal MidiUnavailableException:\n" + e.getMessage() + "\n");
    }
  }

  /**
   * Helper to the initialize method. Opens the sequencer, then plays the sequence of notes
   * currently in the model at the tempo specified in the model. Closes the sequencer after all
   * notes have been played.
   *
   * @param tempo    the tempo of the piece currently in the model
   * @param notes    the list of note data of the piece currently from the model
   * @param length   the length of the piece currently in the model
   * @throws InvalidMidiDataException if an invalid note or tempo in the model is passed to the MIDI
   * @throws MidiUnavailableException if MIDI is currently unavailable for the system
   */
  private void playSequence(int tempo, List<Integer[]> notes, int length)
      throws InvalidMidiDataException, MidiUnavailableException {
    this.sequencer.open();
    this.sequencer.setSequence(createSequence(notes));
    this.sequencer.setTempoInMPQ(tempo);
    this.sequencer.start();
    this.sequencer.setTempoInMPQ(tempo);
    while (this.sequencer.isRunning()) {
      if (this.sequencer.getTickPosition() == length) {
        try {
          Thread.sleep(1000);
          sequencer.close();
        } catch (InterruptedException e) {
          this.log.append("Encountered InterruptedException:\n" + e.getMessage() + "\n");
        }
      }
    }
  }

  /**
   * Helper to the playSequence method. Creates a sequence from the list of note data currently in
   * the model, to be sent to the sequencer.
   *
   * @param notes    the list of note data of the piece currently from the model
   * @throws InvalidMidiDataException if an invalid note or tempo in the model is passed to the MIDI
   */
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
  public String getLog() {
    return this.log.toString();
  }
}
