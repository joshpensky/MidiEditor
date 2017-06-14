package cs3500.music.model.will;

import java.util.ArrayList;
import java.util.List;

/**
 * A chord is a representation of notes. It has a minimum size of 0. There cannot be duplicate notes
 * in a chord.
 */
public class Chord {
  private List<Note> notes;

  /**
   * Constructor for chord.
   */
  public Chord() {
    notes = new ArrayList<>();
  }

  /**
   * Adds a note to this's list of notes.
   * @param n the note to be added
   */
  public void addNote(Note n) {
    if (n == null) {
      throw new IllegalArgumentException("Note cannot be null");
    }

    n.hasNull();

    if (this.notes.contains(n)) {
      throw new IllegalArgumentException("Cannot add a note more than once to a chord");
    }
    notes.add(n);

  }

  /**
   * returns a list of notes in this chord.
   * @return list of notes that this chord has.
   */
  public List<Note> getChord() {
    List<Note> t = new ArrayList<>(this.notes);
    return t;
  }

  /**
   * removes the specified note from this chord.
   * @param n the note to be removed
   * @return the note that has been removed.
   */
  public Note removeNote(Note n) {
    if (!this.has(n)) {
      throw new IllegalArgumentException("Note is not in this chord");
    }
    Note temp = this.notes.get(this.notes.indexOf(n));
    this.notes.remove(n);
    return temp;
  }


  /**
   * takes another chord and puts the two together. Mutates this and allows for repeat notes in the
   * two chords. Any repeats will be ignored and the final merging will have one of every note that
   * appeared in either this or the other chord.
   * @param c the chord to be merged.
   */
  public void mergeChord(Chord c) {
    if (this == null || c == null) {
      throw new IllegalArgumentException("Cannot have null chords");
    }
    this.hasNull();
    c.hasNull();
    List<Note> temp = c.getChord();
    for (int i = 0; i < temp.size(); i++) {
      try {
        this.addNote(temp.get(i));
      } catch (IllegalArgumentException e) {
        //suppresses exception when merging chords that have the same notes
        int p = 0;
      }
    }
  }

  /**
   *Finds the highest note in this chord. Prioritizes octave over pitch.
   * @return the note with the highest value
   */
  protected Note getHighestNote() {
    if (this == null || this.notes.size() == 0) {
      throw new IllegalArgumentException("Must add notes to this chord");
    }

    Note temp = new Note(Pitch.C, 1, 1, true);
    for (Note n : this.notes) {
      if (temp.compareTo(n) < 0) {
        temp = n;
      }
    }

    return temp;
  }


  /**
   * Finds lowest note in this chord. Prioritizes octave over pitch.
   * @return note with the lowest value.
   */
  protected Note getLowestNote() {
    Note temp = new Note(Pitch.B, 1, 10, true);
    for (Note n : this.notes) {
      if (temp.compareTo(n) > 0) {
        temp = n;
      }
    }

    return temp;
  }

  /**
   * Gets the size of this chord, ie how many notes there are.
   * @return number of notes there are in this chord.
   */
  public int length() {
    return this.notes.size();
  }

  /**
   * uses the loose equality to check to see if there are notes that are the same, ignoring the
   * duration or start values.
   * @param n note to be checked
   * @return true if the note is in this chord.
   */
  protected boolean has(Note n) {
    if (n == null) {
      throw new IllegalArgumentException("Note cannot be null");
    }
    for (Note lon : this.notes) {
      if (n.getOctave() == lon.getOctave() && n.getPitch() == lon.getPitch()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Gets the specified note.
   * @param n the note you are querying.
   * @return the note if it is in this chord.
   */
  public Note getNote(Note n) {
    if (n == null) {
      throw new IllegalArgumentException("Note cannot be null");
    }
    for (Note these : this.notes) {
      if (these.qualifiedEquality(n))  {
        return these;
      }
    }
    throw new IllegalArgumentException("Not does not exist in chord");
  }

  /**
   * Checker to see if this chord is or has null parts.
   */
  protected void hasNull() {
    if (this == null || this.notes == null) {
      throw new IllegalArgumentException("Chord cannot be null");
    }
    for (Note n : this.notes) {
      n.hasNull();
    }
  }

  /**
   * Overides the object's equals method. checks all the notes in this chord.
   * @param o the object to be checked
   * @return true if the object is equal.
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Chord)) {
      return false;
    }

    Chord that = (Chord)o;

    if (this.notes.size() == that.length()) {
      for (int i = 0; i < this.length(); i++) {
        if (this.notes.get(i) != that.getChord().get(i)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Overrides the hashCode for a Chord.
   * @return sum of the notes
   */
  @Override
  public int hashCode() {
    int temp = 0;

    for (Note n : this.notes) {
      temp += n.hashCode();
    }

    return temp;
  }

  /**
   * The string representation of this chord.
   * @return the string of all the notes in this chord.
   */
  @Override
  public String toString() {
    if (this == null || this.notes.size() == 0) {
      return "";
    }

    String t = "";
    for (Note n : this.notes) {
      t += n.toString();
    }

    return t;
  }
}
