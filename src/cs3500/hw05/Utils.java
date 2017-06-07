package cs3500.hw05;

/**
 * Created by josh_jpeg on 6/7/17.
 */
public final class Utils {
  public enum Alignment { LEFT, CENTER, RIGHT }

  public static String padString(String str, int length, Alignment align)
      throws IllegalArgumentException {
    if (str == null) {
      throw new IllegalArgumentException("Cannot pad uninitialized string.");
    } else if (align == null) {
      throw new IllegalArgumentException("Alignment of string not defined.");
    }
    StringBuilder builder = new StringBuilder(str);
    boolean addToFront = true;
    if (align.equals(Alignment.LEFT)) {
      addToFront = false;
    }
    while (builder.length() < length) {
      if (addToFront) {
        builder.insert(0, " ");
      } else {
        builder.append(" ");
      }
      if (align.equals(Alignment.CENTER)) {
        addToFront = !addToFront;
      }
    }
    return builder.toString();
  }
}
