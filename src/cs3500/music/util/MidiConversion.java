package cs3500.music.util;

import cs3500.music.model.josh.Pitch;

/**
 * Created by Will on 6/14/2017.
 */
public class MidiConversion {
  public static Pitch getPitch(int pitch) throws IllegalArgumentException {
    if (pitch < 0 || pitch > 127) {
      throw new IllegalArgumentException("Given pitch is not in range [0, 127].");
    }
    int pitches = Pitch.values().length;
    int middleC = 60;
    int octave = getOctave(pitch);
    if (octave >= 4) {
      middleC += (pitches * (octave - 4));

    } else {
      middleC -= (pitches * (4 - octave));
    }
    return Pitch.values()[Math.abs(middleC - pitch)];
  }

  public static int getOctave(int pitch) throws IllegalArgumentException {
    if (pitch < 0 || pitch > 127) {
      throw new IllegalArgumentException("Given pitch is not in range [0, 127].");
    }
    int pitches = Pitch.values().length;
    int middleCValue = 60;
    int middleCOctave = 4;
    if (pitch > middleCValue) {
      int up = (int) Math.floor((pitch - middleCValue) / (double) pitches);
      return middleCOctave + up;
    } else {
      int down = (int) Math.ceil((middleCValue - pitch) / (double) pitches);
      return middleCOctave - down;
    }
  }

  public static int getDuration(int start, int end) throws IllegalArgumentException {
    if (end < start) {
        throw new IllegalArgumentException();
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
    int middleC = 60;
    if (octave >= 4) {
      return (middleC + ((octave - 4) * Pitch.values().length)) + pitchIndex;
    } else {
      return (middleC - (octave * Pitch.values().length)) + pitchIndex;
    }
  }
}
