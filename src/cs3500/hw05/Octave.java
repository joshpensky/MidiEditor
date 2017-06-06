package cs3500.hw05;

/**
 * Created by josh_jpeg on 6/5/17.
 */
public enum Octave {
  ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10);

  private final int value;

  Octave(int value) {
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
