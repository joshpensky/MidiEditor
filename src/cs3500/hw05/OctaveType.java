package cs3500.hw05;

/**
 * Represents the different types of octaves of pitches.
 */
public enum OctaveType {
  ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10);

  private final int value;

  /**
   * Constructs a new {@code OctaveType}.
   *
   * @param value      the value of this octave
   */
  OctaveType(int value) {
    this.value = value;
  }

  /**
   * Gets the value of this octave.
   *
   * @return the value of the octave
   */
  public int getValue() {
    return this.value;
  }
}
