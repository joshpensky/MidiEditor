package cs3500.hw05;

/**
 * Represents a note in a piece of music.
 */
public final class Note {
  private int duration;
  private int position;
  private int endPoint;

  /**
   * Constructs a new {@code Note} object with the given parameters.
   *
   * @param duration    how long the note lasts (measured in beats)
   * @param position    the starting position of the note in a piece
   * @throws IllegalArgumentException if the duration or position are negative
   */
  public Note(int duration, int position) throws IllegalArgumentException {
    this.setDuration(duration);
    this.setPosition(position);
    this.setEndPoint();
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
    this.setDuration(other.duration);
    this.setPosition(other.position);
    this.setEndPoint();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (!(o instanceof Note)) {
      return false;
    }
    Note other = (Note) o;
    return this.duration == other.duration
        && this.position == other.position;
  }

  @Override
  public int hashCode() {
    return (this.duration * 10000) + this.position;
  }

  @Override
  public String toString() {
    return "Duration: " + this.duration
        + "\nPosition: " + this.position;
  }

  /**
   * Sets the duration of the note to the given variable.
   *
   * @param duration   the new duration of this note
   * @throws IllegalArgumentException if the given duration is negative
   */
  void setDuration(int duration) throws IllegalArgumentException {
    if (duration < 0) {
      throw new IllegalArgumentException("Cannot set negative duration.");
    }
    this.duration = duration;
    this.setEndPoint();
  }

  /**
   * Sets the duration of the note to the given variable.
   *
   * @param position   the new position of this note
   * @throws IllegalArgumentException if the given position is negative
   */
  void setPosition(int position) throws IllegalArgumentException {
    if (position < 0) {
      throw new IllegalArgumentException("Cannot set negative position.");
    }
    this.position = position;
    this.setEndPoint();
  }

  private void setEndPoint() {
    this.endPoint = this.position + (this.duration - 1);
  }
}
