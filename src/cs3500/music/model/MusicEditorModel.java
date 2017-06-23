package cs3500.music.model;

import cs3500.music.util.MidiConversion;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents the model for the editor of a Music Editor. Allows for creating pieces,
 * adding/removing/editing notes, setting tempo, getting length of a piece, or getting note data.
 */
public final class MusicEditorModel implements MusicEditorOperations {
  private List<Piece> pieces;
  private Piece opened;

  /**
   * Creates a new {@code MusicEditorModel} with no pieces in memory.
   */
  protected MusicEditorModel() {
    this.pieces = new ArrayList<>();
    this.opened = null;
  }

  @Override
  public void create() {
    Piece next = new Piece();
    this.pieces.add(0, next);
    this.opened = next;
  }

  @Override
  public void addNote(int start, int end, int instrument, int pitch, int volume)
      throws IllegalStateException, IllegalArgumentException {
    this.openedPieceException();
    this.opened.addNote(MidiConversion.getOctave(pitch), MidiConversion.getPitch(pitch), start, 
        MidiConversion.getDuration(start, end), instrument, volume);
  }

  @Override
  public void removeNote(int start, int instrument, int pitch)
      throws IllegalStateException, IllegalArgumentException {
    this.openedPieceException();
    this.opened.removeNote(MidiConversion.getOctave(pitch), MidiConversion.getPitch(pitch),
        start, instrument);
  }

  @Override
  public void editNotePitch(int start, int instrument, int pitch, int editedPitch)
      throws IllegalStateException, IllegalArgumentException {
    this.openedPieceException();
    int octave = MidiConversion.getOctave(pitch);
    if (octave != MidiConversion.getOctave(editedPitch)) {
      throw new IllegalArgumentException("Cannot edit octave, only pitch.");
    }
    this.opened.editPitch(octave, MidiConversion.getPitch(pitch), start, instrument,
        MidiConversion.getPitch(editedPitch));
  }

  @Override
  public void editNotePosition(int start, int instrument, int pitch, int editedStart)
      throws IllegalStateException, IllegalArgumentException {
    this.openedPieceException();
    this.opened.editPosition(MidiConversion.getOctave(pitch), MidiConversion.getPitch(pitch),
        start, instrument, editedStart);
  }

  @Override
  public void editNoteDuration(int start, int instrument, int pitch, int editedEnd)
      throws IllegalStateException, IllegalArgumentException {
    this.openedPieceException();
    this.opened.editDuration(MidiConversion.getOctave(pitch), MidiConversion.getPitch(pitch),
        start, instrument, editedEnd);
  }

  @Override
  public void setTempo(int tempo) throws IllegalStateException, IllegalArgumentException {
    this.openedPieceException();
    this.opened.setTempo(tempo);
  }

  @Override
  public int getTempo() throws IllegalStateException {
    this.openedPieceException();
    return this.opened.getTempo();
  }

  @Override
  public List<Integer[]> getNotes() throws IllegalStateException {
    this.openedPieceException();
    return this.opened.getNotes();
  }

  @Override
  public List<Integer[]> getNotesAtBeat(int beat) throws IllegalStateException {
    this.openedPieceException();
    return this.opened.getNotesAtBeat(beat);
  }

  @Override
  public int getLength() throws IllegalStateException {
    this.openedPieceException();
    return this.opened.length();
  }

  /**
   * Helper to the print, close, addNote, removeNote, editNotePitch, editNotePosition,
   * editNoteDuration, setTempo, getTempo, getNotes, getNotesAtBeat, and getLength methods. Checks
   * if there is currently a piece opened, and if not throws an exception.
   *
   * @throws IllegalStateException if there is currently no piece opened
   */
  private void openedPieceException() throws IllegalStateException {
    if (this.opened == null) {
      throw new IllegalStateException("There is no piece currently open.");
    }
  }
}