package cs3500.hw05;

/**
 * Represents a note in a piece of music.
 */
public class Note {
  private Pitch pitch;
  private Octave octave;
  private int duration;
  private int position;

  /**
   * Constructs a new {@code Note} object with the given parameters.
   *
   * @param pitch       the pitch of the note
   * @param octave      the octave of the pitch
   * @param duration    how long the note lasts (measured in beats)
   * @param position    the starting position of the note in a piece
   * @throws IllegalArgumentException if the given pitch is uninitialized, or if the duration or
   * position are negative
   */
  public Note(Pitch pitch, Octave octave, int duration, int position)
              throws IllegalArgumentException {
    this.setPitch(pitch, octave);
    this.setDuration(duration);
    this.setPosition(position);
  }

  /**
   * Constructs a duplicate copy of the given {@code Note} object with different references.
   *
   * @param other       the note to be copied
   * @throws IllegalArgumentException if the given note is uninitialized
   */
  public Note(Note other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("Cannot make duplicate from uninitialized note.");
    }
    this.setPitch(other.pitch, other.octave);
    this.setDuration(other.duration);
    this.setPosition(other.position);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (!(o instanceof Note)) {
      return false;
    }
    Note other = (Note) o;
    return this.pitch.equals(other.pitch)
        && this.octave.equals(other.octave)
        && this.duration == other.duration
        && this.position == other.position;
  }

  @Override
  public int hashCode() {
    return this.pitch.getId() * 10000000
        + this.duration * 10000
        + this.position;
  }

  @Override
  public String toString() {
    return "Pitch: " + this.pitch.toString() + this.octave.getValue()
        + "\nDuration: " + this.duration
        + "\nPosition: " + this.position;
  }

  /**
   * Sets the pitch of the note to the given variable.
   *
   * @param pitch      the new pitch of this note
   * @param octave     the new octave for the pitch
   * @throws IllegalArgumentException if the given pitch or octave is uninitialized
   */
  public void setPitch(Pitch pitch, Octave octave) throws IllegalArgumentException {
    if (pitch == null) {
      throw new IllegalArgumentException("Cannot set uninitialized pitch.");
    } else if (octave == null) {
      throw new IllegalArgumentException("Cannot set uninitialized octave.");
    }
    this.pitch = pitch;
    this.octave = octave;
  }

  /**
   * Sets the duration of the note to the given variable.
   *
   * @param duration   the new duration of this note
   * @throws IllegalArgumentException if the given duration is negative
   */
  public void setDuration(int duration) throws IllegalArgumentException {
    if (duration < 0) {
      throw new IllegalArgumentException("Cannot set negative duration.");
    }
    this.duration = duration;
  }

  /**
   * Sets the duration of the note to the given variable.
   *
   * @param position   the new position of this note
   * @throws IllegalArgumentException if the given position is negative
   */
  public void setPosition(int position) throws IllegalArgumentException {
    if (position < 0) {
      throw new IllegalArgumentException("Cannot set negative position.");
    }
    this.position = position;
  }
}
