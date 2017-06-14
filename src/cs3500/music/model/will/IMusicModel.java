package cs3500.music.model.will;

import java.util.List;

/**
 * This is the interface for the MusicModel.
 */

public interface IMusicModel {

  /*
  paramaterize interface over T (represents a track in this case)
  void addNoe(int, int, int, int, int)
  void removeNote(int, int,int, int,int)
  T getState()
  String stringState()
  void addT(int startPos, T t)
  void removeT(int startPOs, T t)
  void setTempo (int)

  /**
   * Return a valid track that contains the values of all the notes that have been created.
   * @return a Track that has all the notes in this track.
   */
  Track getTrack();

  /**
   * Start a new MusicModel and reset/intialize all the values of the model with their defaults.
   */
  void startTrack();

  /**
   * Starts a new MusicModel and intializes all the values. Uses the given track as input.
   * @param track the track to start from.
   */
  void startTrack(Track track);

  /**
   * Formatted representation of the track. Uses the following format.
   * A column of numbers representing the beats, printed right-justified and padded with leading
   *    spaces, that is exactly as wide as necessary. (So if your piece is 999 beats long, it uses
   *    three columns of characters; if it’s 1000 beats long, it uses four.)
   * A sequence of columns, each five characters wide, representing each pitch. The first line
   *    prints out the names of the pitches, more-or-less centered within the five-character
   *    column. I.e., "  F2 " and " G#3 " and " D#10". (Because we need to represent at least ten
   *    octaves, three-character columns won’t be wide enough.)
   * Use exactly as many columns as are needed for your piece, from its lowest to its highest note.
   * Each note-head is rendered as an "  X  ", and each note-sustain is rendered as "  |  ".
   *    When a note is not played, five spaces are rendered (as "     ").
   * As a consequence: every line should be exactly the same length, as shown above.
   * Every item, including the last one, ends in a newline.
   * @return the formatted string
   */
  String getMixerState();

  /**
   * Adds a note at the given measure.
   * @param measure the index of where you want to add the note in the track beatwise.
   * @param note the note to be added.
   */
  void addNote(int measure, Note note);

  /**
   * Inserts a chord into the track. This does not overwrite the notes that are there, it inserts
   * the new chord in the middle wherever it is placed.
   * @param measure index to be added at.
   * @param chord the chord to be inserted
   */
  void insertChord(int measure, Chord chord);

  /**
   * Removes a note from the specified index of beat.
   * @param measure the index from which the note should be removed
   * @param note the note to be removed
   * @return the note that was removed
   */
  Note removeNote(int measure, Note note);

  /**
   * Removes the entire chord at the given index.
   * @param measure the index of the chord in the track to be removed
   * @return the chord that has been taken out of the track.
   */
  Chord removeChord(int measure);

  /**
   * Removes a section of chords. Shortcut for removing multiple chords one by one.
   * @param start the index from which the chords should be removed.
   * @param numToBeCut the number of chords to be removed
   * @return a list of chords that have been removed from the track
   */
  List<Chord> cutCords(int start, int numToBeCut);

  /**
   * Adds a track to this one.
   * @param startPosition the index at which the track should be added from.
   * @param track the track to be added.
   */
  void addTrack(int startPosition, Track track);


}
