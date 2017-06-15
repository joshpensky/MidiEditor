package cs3500.music.util;

import cs3500.music.model.Pitch;

/**
 * Created by Will on 6/14/2017.
 */
public class MidiConversion {
  private static final int MIDDLE_C = 60;

  public static Pitch getPitch(int pitch) throws IllegalArgumentException {
    if (pitch < 0 || pitch > 127) {
      throw new IllegalArgumentException("Given pitch is not in range [0, 127].");
    }
    int pitches = Pitch.values().length;
    int middle = MIDDLE_C;
    int octave = getOctave(pitch);
    if (octave >= 4) {
      middle += (pitches * (octave - 4));
    } else {
      middle -= (pitches * (4 - octave));
    }
    return Pitch.values()[Math.abs(middle - pitch)];
  }

  public static int getOctave(int pitch) throws IllegalArgumentException {
    if (pitch < 0 || pitch > 127) {
      throw new IllegalArgumentException("Given pitch is not in range [0, 127].");
    }
    int pitches = Pitch.values().length;
    int middle = MIDDLE_C;
    if (pitch > middle) {
      int up = (int) Math.floor((pitch - middle) / (double) pitches);
      return 4 + up;
    } else {
      int down = (int) Math.ceil((middle - pitch) / (double) pitches);
      return 4 - down;
    }
  }

  public static int getDuration(int start, int end) throws IllegalArgumentException {
    if (end < start) {
        throw new IllegalArgumentException("End cannot be before start.");
    }
    return (end - start) + 1;
  }

  public static int getMidiPitch(int octave, Pitch pitch) {
    if (pitch == null) {
      throw new IllegalArgumentException("Given pitch is uninitialized.");
    }
    int pitchIndex = -1;
    for (int i = 0; i < Pitch.values().length; i++) {
      if (Pitch.values()[i] == pitch) {
        pitchIndex = i;
        break;
      }
    }
    if (octave >= 4) {
      return (MIDDLE_C + ((octave - 4) * Pitch.values().length)) + pitchIndex;
    } else {
      return (MIDDLE_C - ((4 - octave) * Pitch.values().length)) + pitchIndex;
    }
  }
}
