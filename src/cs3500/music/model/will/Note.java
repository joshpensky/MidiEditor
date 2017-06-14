package cs3500.music.model.will;

/**
 * Class representing a Note. Can have any pitch that is represented in the Pitches. The octave must
 * be in the following bounds [1, 10]. Start is a variable that represents whether this note is the
 * begining of the note or if it is being sustained. Duration has a lower limit of 1.
 */
public class Note {
  private Pitch pitch;
  private int duration;
  private int octave;
  private boolean start;

  /**
   * Default constructor for a note. Sets the note to being middle C for one beat.
   */
  public Note() {
    this.pitch = Pitch.C;
    duration = 1;
    octave = 4;
    start = true;
  }

  /**
   * Alternate constructor for a note.
   * @param n the note that this is going to become.
   * @param start the start value to be changed/set/constructed.
   */
  public Note(Note n, boolean start) {
    if (n == null) {
      throw new IllegalArgumentException("Note cannot be null");

    }

    this.pitch = n.getPitch();
    this.duration = n.getDuration();
    this.octave = n.getOctave();
    this.start = start;
  }

  /**
   * Alternative Constructor for Note allowing all the fields to be set, the start value defaults T.
   * @param p the pitch this note will be
   * @param duration how long this note will last
   * @param octave the octave of this note
   */
  public Note(Pitch p, int duration, int octave) {
    if (duration < 1) {
      throw new IllegalArgumentException("Duration must be at least 1");
    }
    if (octave < 1 || octave > 10) {
      throw new IllegalArgumentException("Octave range is limited from 1 to 10");
    }

    if (p == null) {
      throw new IllegalArgumentException("Pitch cannot be null");
    }
    this.pitch = p;
    this.duration = duration;
    this.octave = octave;
    this.start = true;
  }

  /**
   * Alternative Constructor for Note allowing all the fields to be set.
   * @param p the pitch this note will be
   * @param duration how long this note will last
   * @param octave the octave of this note
   * @param start whether this is the start of this note.
   */
  public Note(Pitch p, int duration, int octave, boolean start) {
    if (duration < 1) {
      throw new IllegalArgumentException("Duration must be at least 1");
    }
    if (octave < 1 || octave > 10) {
      throw new IllegalArgumentException("Octave range is limited from 1 to 10");
    }

    if (p == null) {
      throw new IllegalArgumentException("Pitch cannot be null");
    }
    this.pitch = p;
    this.duration = duration;
    this.octave = octave;
    this.start = start;
  }

  /**
   * Getter for this's pitch.
   * @return this's pitch
   */
  public Pitch getPitch() {
    return this.pitch;
  }

  /**
   * Getter for this's octave.
   * @return this's octave
   */
  public int getOctave() {
    return this.octave;
  }

  /**
   * Getter for this's duration.
   * @return this's duration.
   */
  public int getDuration() {
    return this.duration;
  }

  /**
   * Getter for this's start value.
   * @return this's start value
   */
  public boolean getStart() {
    return this.start;
  }

  /**
   * Overides object's equals. Checks that pitch, duration, and octave are the same.
   * DOES NOT COMPARE WHETHER THE START VALUES ARE THE SAME!
   * @param o object to be checked.
   * @return true if O is equal to this
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Note)) {
      return false;
    }

    Note that = (Note)o;

    return this.getPitch() == that.getPitch()
        && this.getOctave() == that.getOctave() && this.getDuration() == that.getDuration();
  }


  /**
   * Checks to see if two objects are equal based on their toString.
   * @param o obnject to be checked.
   * @return true if both objects have an equivalent toString.
   */
  public boolean qualifiedEquality(Object o) {
    if (!(o instanceof Note)) {
      return false;
    }

    Note that = (Note)o;

    return this.toString().equals(that.toString());
  }

  /**
   * Overides the hashCode method for Notes.
   * @return int based on the hashCode for the pitch, duration, and octave.
   */
  @Override
  public int hashCode() {
    return this.duration * 1197 + this.octave * 9876 + this.pitch.hashCode();
  }

  /**
   * Overrides the toString method of Object.
   * @return the formatted note with padding so that it is length 5.
   */
  @Override
  public String toString() {
    String s = this.pitch.toString() + Integer.toString(this.octave);
    int length = s.length();

    switch (length) {
      case 2: s = "  " + s + " ";
        break;
      case 3: s = " " + s + " ";
        break;
      case 4: s = " " + s;
        break;
      case 5:
        break;
      default: throw new IllegalStateException("String for note impossible, " + s);
    }
    return s;
  }

  /**
   * Comparison for two notes.
   * @param n note to be compared with this one.
   * @return <0 that note is higher, >0 that note is lower, =0 the notes are the same
   */
  protected int compareTo(Note n) {
    if (n == null) {
      throw new IllegalArgumentException("Note cannot be null for comparison");
    }
    if (n.getOctave() > this.octave) {
      return -1;
    } else if (n.getOctave() < this.octave) {
      return 1;
    } else {
      return this.pitch.compareTo(n.getPitch());
    }
  }

  /**
   * returns the same exact note but with the opposite start value, used when checking notes.
   * @return An equal note ignoring start value.
   */
  protected Note toNeg() {
    return new Note(this.pitch, this.duration, this.octave, !this.getStart());
  }

  /**
   * Checks to see if the note is or has a null component.
   */
  protected void hasNull() {
    if (this == null || this.pitch == null) {
      throw new IllegalArgumentException("Note cannot be null");
    }
  }


}
