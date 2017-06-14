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
}
