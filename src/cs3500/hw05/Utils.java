package cs3500.hw05;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josh_jpeg on 6/7/17.
 */
final class Utils {
  enum Alignment { LEFT, CENTER, RIGHT }

  static String padString(String str, int length, Alignment align)
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

  /**
   * Returns the last element in a {@code List}.
   *
   * @param list       a generic list of items
   * @return the last element
   * @throws IllegalArgumentException if the given {@code List} is or contains null
   */
  public static <T> List<T> reverse(List<T> list) throws IllegalArgumentException {
    if (list == null || list.contains(null)) {
      throw new IllegalArgumentException("Cannot give a list that is or contains null.");
    } else if (list.size() > 1) {
      List<T> copy = new ArrayList<>();
      for (T item : list) {
        copy.add(0, item);
      }
      return copy;
    }
    return list;
  }
}
