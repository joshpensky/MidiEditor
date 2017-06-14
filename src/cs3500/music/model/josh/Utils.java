package cs3500.music.model.josh;

import java.util.ArrayList;
import java.util.List;

/**
 * A utilities class containing static methods for use throughout the project.
 */
public final class Utils {
  protected enum Alignment { LEFT, CENTER, RIGHT }

  /**
   * Pads the given String with spaces, until the length of the given String matches or is
   * greater than the given length. The alignment property directs where the padding goes.
   *
   * @param str      the string to be padded
   * @param length   the max length of the new String
   * @param align    LEFT to add padding to the right, RIGHT to add padding to the left, or
   *                 CENTER to add padding on both sides of the string
   * @return the new space-padded String
   * @throws IllegalArgumentException if the given string or Alignment are null
   */
  protected static String padString(String str, int length, Alignment align)
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
  protected static <T> List<T> reverse(List<T> list) throws IllegalArgumentException {
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

  public static Pitch getPitch(int pitch) throws IllegalArgumentException {
    if (pitch < 0 || pitch > 127) {
      throw new IllegalArgumentException();
    }
    //return Pitch.values()[pitch % Pitch.values().length];
    int pitches = Pitch.values().length;
    int middleC = 60;
    int octave = getOctave(pitch);
    if (octave >= 4) {
      middleC += (pitches * (octave - 4));

    } else {
      middleC -= (pitches * (4 - octave));
    }
    System.out.println(middleC + " " + pitch);
    return Pitch.values()[Math.abs(middleC - pitch)];
  }

  public static int getOctave(int pitch) throws IllegalArgumentException {
    if (pitch < 0 || pitch > 127) {
      throw new IllegalArgumentException();
    }
    int pitches = Pitch.values().length;
    int middleC = 60;
    if (pitch > middleC) {
      int up = (int) Math.floor((pitch - middleC) / (double) pitches);
      return 4 + up;
    } else {
      int down = (int) Math.ceil((middleC - pitch) / (double) pitches);
      return 4 - down;
    }

  }

  public static int getDuration(int start, int end) throws IllegalArgumentException {
    if (end < start) {
      throw new IllegalArgumentException();
    }
    return (end - start) + 1;
  }

  public static int getTone(int octave, Pitch pitch) {
    int pitchIndex = -1;
    for (int i = 0; i < Pitch.values().length; i++) {
      System.out.println(Pitch.values()[i].toString());
      if (Pitch.values()[i] == pitch) {
        pitchIndex = i;
        break;
      }
    }
    System.out.println(octave + " : " + pitchIndex);
    int middleC = 60;
    if (octave >= 4) {
      return (middleC + ((octave - 4) * Pitch.values().length)) + pitchIndex;
    } else {
      return (middleC - (octave * Pitch.values().length)) + pitchIndex;
    }
  }
}
