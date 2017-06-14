package cs3500.music.view;

import cs3500.music.model.josh.Pitch;

/**
 * Created by Will on 6/14/2017.
 */
public class Utils {

    public static Pitch getPitch(int pitch) throws IllegalArgumentException {
        if (pitch < 0 || pitch > 127) {
            throw new IllegalArgumentException();
        }
        return Pitch.values()[pitch % Pitch.values().length];
    }

    public static int getOctave(int pitch) throws IllegalArgumentException {
        if (pitch < 0 || pitch > 127) {
            throw new IllegalArgumentException();
        }
        return pitch / Pitch.values().length;
    }

    public static int getDuration(int start, int end) throws IllegalArgumentException {
        if (end < start) {
            throw new IllegalArgumentException();
        }
        return (end - start) + 1;
    }
}
