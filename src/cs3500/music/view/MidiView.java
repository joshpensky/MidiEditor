package cs3500.music.view;

import cs3500.music.model.MusicEditorOperations;
import cs3500.music.util.MidiConversion;

import javax.sound.midi.*;

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
   * Constructs a new {@code MidiView} using the given model to get notes to play.
   *
   * @param model   the model to be represented musically using MIDI
   * @throws MidiUnavailableException if MIDI is currently unavailable for the system
   */
  protected MidiView(MusicEditorOperations model) throws MidiUnavailableException {
    this.model = model;
    this.log = new StringBuilder();
    this.sequencer = MidiSystem.getSequencer();
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

  @Override
  public String getLog() {
    return this.log.toString();
  }

  /**
   * Opens the sequencer, then plays the sequence of notes currently in the model at the tempo
   * specified in the model. Closes the sequencer after all notes have been played.
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
   * Creates a sequence from the list of note data currently in the model, to be sent to the
   * sequencer.
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
}
