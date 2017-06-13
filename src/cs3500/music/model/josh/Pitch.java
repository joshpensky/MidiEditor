package cs3500.music.model.josh;

/**
 * Represents the different types of pitches of a note.
 */
public enum Pitch {
  C("C"), CSHARP("C#"), D("D"), DSHARP("D#"), E("E"), F("F"), FSHARP("F#"),
  G("G"), GSHARP("G#"), A("A"), ASHARP("A#"), B("B");

  private final String toString;

  /**
   * Constructs a new Pitch.
   *
   * @param toString   the String representation of the pitch
   */
  Pitch(String toString) {
    this.toString = toString;
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
}
