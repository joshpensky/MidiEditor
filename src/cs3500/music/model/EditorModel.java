package cs3500.music.model;

import cs3500.music.util.MidiConversion;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the model for a Midi Editor and an implementation of the MusicEditorOperations interface.
 */
public final class EditorModel implements MusicEditorOperations {
  private List<Piece> pieces;
  private Piece opened;

  /**
   * Creates a new {@code EditorModel} with no pieces in memory.
   */
  protected EditorModel() {
    this.pieces = new ArrayList<>();
    this.opened = null;
  }

  @Override
  public void create() throws IllegalArgumentException {
    Piece next = new Piece();
    this.pieces.add(0, next);
    this.opened = next;
  }

  @Override
  public String view() {
    this.openedPieceException();
    return this.opened.toString();
  }

  @Override
  public void close() throws IllegalStateException {
    this.openedPieceException();
    this.opened = null;
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
    this.opened.removeNote(MidiConversion.getOctave(pitch), MidiConversion.getPitch(pitch), start);
  }

  @Override
  public void editNotePitch(int start, int instrument, int pitch, int editedPitch)
    throws IllegalStateException, IllegalArgumentException {
    this.openedPieceException();
    int octave = MidiConversion.getOctave(pitch);
    if (octave != MidiConversion.getOctave(editedPitch)) {
      throw new IllegalArgumentException("Cannot edit octave, only pitch.");
    }
    this.opened.editPitch(octave, MidiConversion.getPitch(pitch), start,
        MidiConversion.getPitch(editedPitch));
  }

  @Override
  public void editNotePosition(int start, int instrument, int pitch, int editedStart)
    throws IllegalStateException, IllegalArgumentException {
    this.openedPieceException();
    this.opened.editPosition(MidiConversion.getOctave(pitch), MidiConversion.getPitch(pitch), start,
        editedStart);
  }

  @Override
  public void editNoteDuration(int start, int instrument, int pitch, int editedEnd)
    throws IllegalStateException, IllegalArgumentException {
    this.openedPieceException();
    this.opened.editDuration(MidiConversion.getOctave(pitch), MidiConversion.getPitch(pitch), start,
        editedEnd);
  }

  @Override
  public void setTempo(int tempo) {
    this.openedPieceException();
    this.opened.setTempo(tempo);
  }

  @Override
  public int getTempo() {
    this.openedPieceException();
    return this.opened.getTempo();
  }

//  @Override
//  public void overlay(String overlayTitle) throws IllegalStateException, IllegalArgumentException {
//    this.openedPieceException();
//    Piece toOverlay = this.getPieceFromMemory(overlayTitle);
//    if (toOverlay == null) {
//      throw new IllegalArgumentException("There is no piece with the given title, \""
//        + overlayTitle + "\".");
//    }
//    this.opened.overlay(new Piece(toOverlay));
//  }
//
//  @Override
//  public void addToEnd(String title) throws IllegalStateException, IllegalArgumentException {
//    this.openedPieceException();
//    Piece toAdd = this.getPieceFromMemory(title);
//    if (toAdd == null) {
//      throw new IllegalArgumentException("There is no piece with the given title, \""
//        + title + "\".");
//    }
//    toAdd = new Piece(toAdd);
//    toAdd.move(this.opened.length());
//    this.opened.overlay(toAdd);
//  }

  /**
   * Helper to the print, close, addNote, removeNote, editNotePitch, editNotePosition,
   * editNoteDuration, and overlay methods. Checks if there is currently a piece opened, and if
   * not throws an exception.
   *
   * @throws IllegalStateException if there is currently no piece opened
   */
  private void openedPieceException() throws IllegalStateException {
    if (this.opened == null) {
      throw new IllegalStateException("There is no piece currently open.");
    }
  }

  @Override
  public List<Integer[]> getNotes() {
    this.openedPieceException();
    return this.opened.getNotes();
  }

  @Override
  public List<Integer[]> getNotesAtBeat(int beat) {
    this.openedPieceException();
    return this.opened.getNotesAtBeat(beat);
  }

  @Override
  public int totalPieceLength() {
    this.openedPieceException();
    return this.opened.length();
  }
}