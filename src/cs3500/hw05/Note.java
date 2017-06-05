package cs3500.hw05;

/**
 * Represents a note in a piece of music.
 */
public class Note {
  private PitchType pitch;
  private int duration;
  private int position;

  Note(PitchType pitch, int duration, int position) throws IllegalArgumentException {
    this.setPitch(pitch);
    this.setDuration(duration);
    this.setPosition(position);
  }

  Note(Note other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("Cannot make duplicate from uninitialized note.");
    }
    this.setPitch(other.pitch);
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
    return "Pitch: " + this.pitch.toString()
        + "\nDuration: " + this.duration
        + "\nPosition: " + this.position;
  }

  public void setPitch(PitchType pitch) throws IllegalArgumentException {
    if (pitch == null) {
      throw new IllegalArgumentException("Cannot set uninitialized pitch.");
    }
    this.pitch = pitch;
  }

  public void setDuration(int duration) throws IllegalArgumentException {
    if (duration < 0) {
      throw new IllegalArgumentException("Cannot set negative duration.");
    }
    this.duration = duration;
  }

  public void setPosition(int position) throws IllegalArgumentException {
    if (position < 0) {
      throw new IllegalArgumentException("Cannot set negative position.");
    }
    this.position = position;
  }
}
