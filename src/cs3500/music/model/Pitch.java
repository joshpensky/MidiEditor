package cs3500.music.model;
/**
 * Represents the different types of pitches of a note.
 */
public enum Pitch {
  C("C", false), CSHARP("C#", true), D("D", false), DSHARP("D#", true), E("E", false),
  F("F", false), FSHARP("F#", true), G("G", false), GSHARP("G#", true), A("A", false),
  ASHARP("A#", true), B("B", false);

  private final String toString;
  private final boolean isSharp;

  /**
   * Constructs a new Pitch.
   *
   * @param toString   the String representation of the pitch
   */
  Pitch(String toString, boolean isSharp) {
    this.toString = toString;
    this.isSharp = isSharp;
  }

  /**
   * Gets the String representation of the pitch.
   *
   * @return the string representation of the pitch
   */
  @Override
  public String toString() {
    return this.toString;
  }

  public boolean isSharp() {
    return this.isSharp;
  }
}
