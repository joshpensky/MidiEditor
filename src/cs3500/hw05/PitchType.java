package cs3500.hw05;

/**
 * Represents the different types of pitches of a note.
 */
public enum PitchType {
  C(0), CSHARP(1), D(2), DSHARP(3), E(4), F(5), FSHARP(6), G(7), GSHARP(8), A(9), ASHARP(10), B(11);

  private final int id;

  /**
   * Constructs a new PitchType.
   *
   * @param id      a unique identification number
   */
  PitchType(int id) {
    this.id = id;
  }

  /**
   * Gets the unique identification number for the pitch type.
   *
   * @return the pitch id
   */
  public int getId() {
    return this.id;
  }
}
