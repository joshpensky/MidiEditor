package cs3500.hw05;

/**
 * Represents the different types of pitches of a note.
 */
public enum Pitch {
  C(0, "C"), CSHARP(1, "C#"), D(2, "D"), DSHARP(3, "D#"), E(4, "E"), F(5, "F"), FSHARP(6, "F#"),
  G(7, "G"), GSHARP(8, "G#"), A(9, "A"), ASHARP(10, "A#"), B(11, "B");

  private final int id;
  private final String toString;

  /**
   * Constructs a new Pitch.
   *
   * @param id      a unique identification number
   */
  Pitch(int id, String toString) {
    this.id = id;
    this.toString = toString;
  }

  /**
   * Gets the unique identification number for the pitch type.
   *
   * @return the pitch id
   */
  public int getId() {
    return this.id;
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
