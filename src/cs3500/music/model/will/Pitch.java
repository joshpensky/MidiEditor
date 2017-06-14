package cs3500.music.model.will;

/**
 * The types of pitches that this music model can represent.
 * <p>
 *   The pitches are as follows:
 *   C, C#, D, D#, E, F, F#, G, G#, A, A#, B
 * </p>
 */
public enum Pitch {
  C("C", 0), CS("C#", 1), D("D", 2), DS("D#", 3), E("E", 4), F("F", 5), FS("F#", 6), G("G", 7),
  GS("G#", 8), A("A", 9), AS("A#", 10), B("B", 11);

  /**
   * @Param pitch is used to represent the string value.
   * @Param val is used to represent the notes relative values in each octave in mutual relation.
   */
  private String pitch;
  private int val;


  Pitch(String b, int value) {
    pitch = b;
    this.val = value;
  }

  /**
   * Overides object's toString method. Returns this's pitch in its string form.
   * @return this's note representation in a string
   */
  public String toString() {
    return this.pitch;
  }

  /**
   * Getter method for this's value. Used in comparisons.
   * @return this's value for comparison.
   */
  public int getVal() {
    return this.val;
  }

  /**
   * compares two pitches.
   * @param p pitch to be compared to
   * @return >0 -> this pitch is higher, <0 that pitch is higher, =0 they are equal
   */
  public int compare(Pitch p) {
    return this.val - p.getVal();
  }


}
