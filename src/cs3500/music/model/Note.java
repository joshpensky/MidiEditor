package cs3500.music.model;

import cs3500.music.util.MidiConversion;

/**
 * Represents a note in a piece of music.
 */
public final class Note {
  private int startPos;
  private int endPos;
  private int duration;
  private int instrument;
  private int volume;

  /**
   * Default constructor.
   * Constructs a new {@code Note} object with the given parameters.
   *
   * //@param position the starting position of the note in a piece
   * @param duration how long the note lasts (measured in beats)
   * @throws IllegalArgumentException if the duration or position are negative
   */
  protected Note(int startPos, int duration, int instrument, int volume)
    throws IllegalArgumentException {
    this.setStartPos(startPos);
    this.setDuration(duration);
    this.setInstrument(instrument);
    this.setVolume(volume);
  }

  /**
   * Copy constructor.
   * Constructs a duplicate copy of the given {@code Note} object with different references.
   *
   * @param other the note to be copied
   * @throws IllegalArgumentException if the given note is uninitialized
   */
  protected Note(Note other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("Cannot make duplicate from uninitialized note.");
    }
    this.setStartPos(other.startPos);
    this.setDuration(other.duration);
    this.setInstrument(other.instrument);
    this.setVolume(other.volume);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    else if (!(o instanceof Note)) {
      return false;
    }
    Note other = (Note) o;
    return this.startPos == other.startPos
      && this.endPos == other.endPos
      && this.instrument == other.instrument;
  }

  @Override
  public int hashCode() {
    return (this.startPos * 100000) + this.endPos;
  }

  /**
   * Sets the position of the note to the given variable.
   *
   * //@param position the new position of this note
   * @throws IllegalArgumentException if the given position is negative
   */
  protected void setStartPos(int startPos) throws IllegalArgumentException {
    if (startPos < 0) {
      throw new IllegalArgumentException("Cannot set as negative position.");
    }
    this.startPos = startPos;
    this.setEndPos();
  }

  /**
   * Sets the end point of the note, using the duration and position of this note.
   * This is used for checking where a note lies in between another note.
   */
  private void setEndPos() {
    this.endPos = (this.startPos + this.duration) - 1;
  }

  /**
   * Sets the duration of the note to the given variable.
   *
   * @throws IllegalArgumentException if the given duration is negative or zero
   */
  protected void setDuration(int duration) throws IllegalArgumentException {
    if (duration < 0 || duration == 0) {
      throw new IllegalArgumentException("Cannot set negative or zero duration.");
    }
    this.duration = duration;
    this.setEndPos();
  }

  /**
   * Sets the instrument of this note to the given one.
   *
   * @throws IllegalArgumentException if the given instrument is not in range [0, 127]
   */
  protected void setInstrument(int instrument) throws IllegalArgumentException {
    if (instrument < 0 || instrument > 127) {
      throw new IllegalArgumentException("Given instrument does not exist.");
    }
    this.instrument = instrument;
  }

  /**
   * Sets the volume of this note to the given one.
   *
   * @throws IllegalArgumentException if the given volume is not in range [0, 127]
   */
  protected void setVolume(int volume) throws IllegalArgumentException {
    if (volume < 0 || volume > 127) {
      throw new IllegalArgumentException("Volume must be between 0 and 127 (inclusive).");
    }
    this.volume = volume;
  }

  /**
   * Gets the starting position of this note.
   *
   * @return the starting position of this note
   */
  protected int getStartPos() {
    return this.startPos;
  }

  /**
   * Gets the ending position of this note.
   *
   * @return the ending position of this note
   */
  protected int getEndPos() {
    return this.endPos;
  }

  /**
   * Gets the instrument of this note.
   *
   * @return the instrument of this note
   */
  protected int getInstrument() {
    return this.instrument;
  }

  protected Integer[] getArray() {
    Integer[] arr = new Integer[5];
    arr[MidiConversion.NOTE_START] = this.startPos;
    arr[MidiConversion.NOTE_END] = this.endPos;
    arr[MidiConversion.NOTE_INSTRUMENT] = this.instrument;
    arr[MidiConversion.NOTE_VOLUME] = this.volume;
    return arr;
  }
}
