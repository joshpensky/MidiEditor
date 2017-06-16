package cs3500.music.util;

import cs3500.music.model.Pitch;

/**
 * Utility functions for converting notes into pitches {@Link Pitch} and notes {@Link Note} as well a conversion
 * functionality for midi software to use.
 */
public class MidiConversion {
  public static final int NOTE_START = 0;
  public static final int NOTE_END = 1;
  public static final int NOTE_INSTRUMENT = 2;
  public static final int NOTE_PITCH = 3;
  public static final int NOTE_VOLUME = 4;

  private static final int MIDDLE_C = 60;

  /**
   * Calculates the pitch based off the given integer.
   * @param pitch numerical representation of the pitch/octave
   * @return {@Link Pitch} the corresponding pitch
   * @throws IllegalArgumentException
   */
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

  /**
   * Calculates the corresponding octave given the int value
   * @param pitch the numerical representation of the pitch/octave
   * @return the octave number of that given pitch
   * @throws IllegalArgumentException
   */
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

  /**
   * Gets the duration given a start and end position of a note.
   * @param start the place the note starts
   * @param end the place the note ends
   * @return the duration of the note, difference between the start and end.
   * @throws IllegalArgumentException
   */
  public static int getDuration(int start, int end) throws IllegalArgumentException {
    if (end < start) {
        throw new IllegalArgumentException("End cannot be before start.");
    }

    if (end < 0 || start < 0) {
      throw new IllegalArgumentException("Cannot have negative start or end points.");
    }

    return (end - start) + 1;
  }

  /**
   * Calculates the numerical equivalent of a pitch and octave combination.
   * @param octave octave of the intended note
   * @param pitch {@Link Pitch} pitch of the intended note
   * @return the numerical representation for Midi of a pitch/octave combination
   */
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

  /**
   * Gets the name of the a pitch [0, 127] as a String. For example, 60 (middle-c) would return
   * "C4".
   *
   * @param midiPitch   the pitch as given to the midi
   * @return the name of the pitch converted to a string
   * @throws IllegalArgumentException if the given midi pitch is out of range [0, 127]
   */
  public static String getPitchName(int midiPitch) throws IllegalArgumentException {
    return MidiConversion.getPitch(midiPitch).toString()
      + Integer.toString(MidiConversion.getOctave(midiPitch));
  }
}
