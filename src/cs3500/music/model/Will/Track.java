package cs3500.music.model.will;

import java.util.ArrayList;
import java.util.List;

/**
 * Track is used to represent a series of chords. Is mutable by adding notes, chord, or even tracks.
 */

public class Track {
  private List<Chord> chords;

  /**
   * Default constructor for a track.
   */
  public Track() {
    this.chords = new ArrayList<>();
  }

  /**
   * Alternative constructor for a track.
   * @param loc list of chords that you initialize this track to have.
   */
  public Track(List<Chord> loc) {
    this.chords = loc;
  }

  /**
   * Alternative constructor for a track.
   * @param t a track that this track will be initialized to be the same as.
   */
  public Track(Track t) {
    this.chords = new ArrayList<>(t.getFullTrack());
  }

  /**
   * Adds a note to this track at the specified measure.
   * @param measure the chord number this note will be added to.
   * @param n the note being added.
   */
  public void addNote(int measure, Note n) {
    if (n == null) {
      throw new IllegalArgumentException("Note cannot be null");
    }
    n.hasNull();

    if (measure < 0) {
      throw new IllegalArgumentException("Measure cannot be < 0");
    }

    if (chords.size() < measure + n.getDuration()) {
      this.extendTrack(measure + n.getDuration());
    }

    this.chords.get(measure).addNote(n);
    Note temp = new Note(n.getPitch(), n.getDuration(), n.getOctave(), false);
    for (int i = 1; i < n.getDuration(); i++) {
      this.chords.get(measure + i).addNote(temp);
    }

    int count = measure + n.getDuration();
    if (count < this.chords.size()) {
      while (this.chords.get(count).getChord().contains(temp)) {
        this.chords.get(count).removeNote(temp);
        count++;
      }
    }


  }

  /**
   * Adds a new chord at a spot and then adds the notes in the specified chord there. Makes the
   * track longer by at least 1.
   * @param measure the place you want to insert this chord.
   * @param c the chord you want to insert.
   */
  public void insertChord(int measure, Chord c) {
    if (this.chords.size() < measure) {
      this.extendTrack(measure);
    }

    if (measure < 0) {
      throw new IllegalArgumentException("Measure cannot be < 0");
    }

    if (c == null) {
      throw new IllegalArgumentException("Chord cannot be null");
    }

    chords.add(measure, new Chord());
    for (Note n : c.getChord()) {
      this.addNote(measure, n);
    }
  }

  /**
   * Allows you to insert multiple chords at once.
   * @param measure the start position for the chords to be inserted.
   * @param loc the list of chords you want to insert into the track.
   */
  public void insertChordSet(int measure, List<Chord> loc) {
    if (this.chords.size() < measure) {
      this.extendTrack(measure);
    }

    if (measure < 0) {
      throw new IllegalArgumentException("Measure cannot be < 0");
    }

    if (loc == null) {
      throw new IllegalArgumentException("Chord cannot be null");
    }


    int temp = 0;
    for (Chord c : loc) {
      this.insertChord(measure + temp, c);
      temp++;
    }

  }

  /**
   * Removes a specified note from the specified chord.
   * @param measure the chord you want to remove the note from
   * @param n the note you want to remove
   * @return the note you removed.
   */
  public Note removeNote(int measure, Note n) {

    if (n == null) {
      throw new IllegalArgumentException("Note cannot be null");
    }
    n.hasNull();

    if (this.chords.size() < measure || measure < 0) {
      throw new IllegalArgumentException("Cannot remove a note from a measure that doesn't exist");
    }


    Note temp = new Note();



    for (int i = 0; i < n.getDuration(); i++) {

      temp =  this.chords.get(measure + i).removeNote(n);

    }

    return temp;

  }

  /**
   * Removes an entire chord from the track.
   * @param measure the chord you want to remove's index
   * @return the chord you removed.
   */
  public Chord removeChord(int measure) {
    if (measure > this.chords.size() || measure < 0) {
      throw new IllegalArgumentException("Cannot remove chord from outside possible index");
    }

    Chord temp = new Chord();
    while (this.chords.get(measure).length() > 0) {
      temp.addNote(this.chords.get(measure).removeNote(this.chords.get(measure).getLowestNote()));
    }
    this.chords.remove(measure);

    return temp;
  }

  /**
   * Extends the track to allow for insertions out of bounds of what already exists.
   * Allows for a note to be added at measure 5 when the track is only 2 beats long.
   * @param length the required total length this track needs to be.
   */
  private void extendTrack(int length) {
    int extraBit = length - this.chords.size();

    for (int i = 0; i < extraBit; i++) {
      this.chords.add(new Chord());
    }
  }


  /**
   * Gets the size of this track.
   * @return the number of chords in this track.
   */
  public int length() {
    if (this == null) {
      return 0;
    } else {
      return this.chords.size();
    }
  }

  /**
   * Getter for a chord at the index.
   * @param index the index for which you want the chord.
   * @return the chord at the specified index.
   */
  public Chord getChord(int index) {
    Chord temp = new Chord();
    temp = this.chords.get(index);
    return temp;
  }

  /**
   * Adds a track from a given starting point to this track.
   * @param startPoint the starting point from which to start adding the other track to this one.
   * @param that the other track to be added.
   */
  public void addTrack(int startPoint, Track that) {
    if (that == null) {
      throw new IllegalArgumentException("Track cannot be null");
    }
    that.hasNull();

    if (that.length() + startPoint >= this.chords.size()) {
      this.extendTrack(that.length() + startPoint);
    }
    for (int i = 0; i < that.length(); i++) {
      this.chords.get(i + startPoint).mergeChord(that.getChord(i));
    }
  }

  /**
   * Returns a copy of this track in the form of a list of chords.
   * @return list of chords that represent this track.
   */
  public List<Chord> getFullTrack() {
    if (this == null) {
      return new ArrayList<>();
    }

    List<Chord> temp = this.chords;
    return temp;
  }

  /**
   * Gets the highest note (in value) in this track.
   * @return the note with the highest value in this track.
   */
  protected Note getHighestNote() {
    Note temp = new Note(Pitch.C, 1, 1, true);
    Note temp2 = new Note();
    for (Chord c : this.chords) {
      if (c.length() != 0) {
        temp2 = c.getHighestNote();

      } else {
        temp2 = new Note(Pitch.C, 1, 1, true);
      }
      if (temp.compareTo(temp2) < 0) {
        temp = temp2;
      }
    }
    return temp;
  }

  /**
   * Gets the lowers note (in value) in this track.
   * @return the note with the lowest value in this track.
   */
  protected Note getLowestNote() {
    Note temp = new Note(Pitch.B, 1, 10, true);
    Note temp2 = new Note();
    for (Chord c : this.chords) {
      if (c.length() != 0) {
        temp2 = c.getLowestNote();

      } else {
        temp2 = new Note(Pitch.B, 1, 10, true);
      }
      if (temp.compareTo(temp2) > 0) {
        temp = temp2;
      }
    }
    return temp;
  }

  /**
   * Checks to see if this is or has any null components.
   */
  protected void hasNull() {
    for (Chord c : this.chords) {
      if (c == null) {
        throw new IllegalArgumentException("Chord in track cannot be null");
      }
      c.hasNull();
    }
  }

  /**
   * Overrides the object's equals method.
   * @param o the object in question to be checked.
   * @return true if o is equal and all of o's subparts are equal and in the same sequence as this's
   */
  @Override
  public boolean equals(Object o) {
    if (! (o instanceof Track)) {
      return false;
    }
    Track that = (Track)o;

    return that.getFullTrack().equals(this.getFullTrack());
  }


  /**
   * Overrides the object's hashCode method for tracks.
   * @return sum of the hashCodes for the chords in the track each multiplied by its position.
   */
  @Override
  public int hashCode() {
    int temp = 0;
    int i = 0;
    for (Chord c : this.chords) {
      temp += c.hashCode() * i;
      i++;
    }

    return temp;
  }


}
