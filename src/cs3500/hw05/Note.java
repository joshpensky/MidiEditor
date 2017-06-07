package cs3500.hw05;

/**
 * Represents a note in a piece of music.
 */
public final class Note {
  private int position;
  private int duration;
  private int endPoint;

  /**
   * Default constructor.
   * Constructs a new {@code Note} object with the given parameters.
   *
   * @param position    the starting position of the note in a piece
   * @param duration    how long the note lasts (measured in beats)
   * @throws IllegalArgumentException if the duration or position are negative
   */
  public Note(int position, int duration) throws IllegalArgumentException {
    this.setPosition(position);
    this.setDuration(duration);
    this.setEndPoint();
  }

  /**
   * Copy constructor.
   * Constructs a duplicate copy of the given {@code Note} object with different references.
   *
   * @param other       the note to be copied
   * @throws IllegalArgumentException if the given note is uninitialized
   */
  public Note(Note other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("Cannot make duplicate from uninitialized note.");
    }
    this.setPosition(other.position);
    this.setDuration(other.duration);
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
    return this.position == other.position
        && this.duration == other.duration;
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
   * Sets the position of the note to the given variable.
   *
   * @param position   the new position of this note
   * @throws IllegalArgumentException if the given position is negative
   */
  void setPosition(int position) throws IllegalArgumentException {
    if (position < 0) {
      throw new IllegalArgumentException("Cannot set as negative position.");
    }
    this.position = position;
    this.setEndPoint();
  }

  /**
   * Sets the duration of the note to the given variable.
   *
   * @param duration   the new duration of this note
   * @throws IllegalArgumentException if the given duration is negative or zero
   */
  void setDuration(int duration) throws IllegalArgumentException {
    if (duration <= 0) {
      throw new IllegalArgumentException("Cannot set as negative or zero duration.");
    }
    this.duration = duration;
    this.setEndPoint();
  }

  /**
   * Sets the end point of the note, using the duration and position of this note.
   * This is used for checking where a note lies in between another note.
   */
  private void setEndPoint() {
    this.endPoint = (this.position + this.duration) - 1;
  }

  /**
   * Checks if the given position is the same as this note's.
   *
   * @param position   the position to be checked against
   * @return true if it's the same position, false otherwise
   */
  boolean samePosition(int position) {
    return this.position == position;
  }

  /**
   * Checks if this note starts before the given note.
   *
   * @param other   the note being checked against
   * @return true if this note starts before the other, false otherwise
   * @throws IllegalArgumentException if the given note is uninitialized
   */
  boolean startsBefore(Note other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("Cannot use uninitialized note.");
    }
    return this.position < other.position;
  }

  /**
   * Overlays two (in-order) notes onto each other and separates them accordingly:
   * If the second note starts before or when the first ends, the first note is cut right before
   * the second starts.
   * Additionally, if the first note ends after the second note ends, the second note gains
   * the difference in duration from the second note's end to the first note's.
   *
   * @param other   the note being checked against
   * @throws IllegalArgumentException if the given note is uninitialized, or if the given note
   * starts before or at the same time as this note
   */
  void overlay(Note other) throws IllegalArgumentException {
    if (!this.startsBefore(other)) {
      throw new IllegalArgumentException("Given note should start after this one.");
    } else if (other.position <= this.endPoint) {
      if (other.endPoint < this.endPoint) {
        other.duration += this.endPoint - other.endPoint;
        other.setEndPoint();
      }
      this.duration -= (this.endPoint - other.position) + 1;
      this.setEndPoint();
    }
  }
}
