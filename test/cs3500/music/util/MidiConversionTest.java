package cs3500.music.util;

import cs3500.music.model.Pitch;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the dopest group this side of the quadrant.
 */
public class MidiConversionTest {
    MidiConversion mc;

    @Test(expected = IllegalArgumentException.class)
    public void testGetPitchInvalidArgument1() {
     
        mc.getPitch(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPitchInvalidArgument2() {
  
        mc.getPitch(128);
    }

    @Test
    public void testGetPitch1() {
        assertEquals(mc.getPitch(60).toString(), "C");
    }

    @Test
    public void testGetPitch2() {
     
        assertEquals(mc.getPitch(61).toString(), "C#");
    }

    @Test
    public void testGetPitch3() {
       
        assertEquals(mc.getPitch(72).toString(), "C");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOctaveIllegalArgument1() {
     
        mc.getOctave(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOctaveIllegalArgument2() {

        mc.getOctave(128);
    }

    @Test
    public void testGetOctave1() {
         
        assertEquals(mc.getOctave(60), 4);
    }

    @Test
    public void testGetOctave2() {
         
        assertEquals(mc.getOctave(48), 3);
    }

    @Test
    public void testGetOctave3() {
         
        assertEquals(mc.getOctave(72), 5);
    }

    @Test
    public void testGetOctave4() {
         
        assertEquals(mc.getOctave(61), 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetDurationIllegalArgument1() {
        mc.getDuration(31, 15);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetDurationIllegalArgument2() {
         
        mc.getDuration(-2, 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetDurationIllegalArgument3() {
         
        mc.getDuration(-4, -2);
    }

    @Test
    public void testGetDuration1() {
         
        assertEquals(mc.getDuration(0, 3), 4);
    }


    @Test
    public void testGetDuration2() {
         
        assertEquals(mc.getDuration(15, 16), 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetMidiPitchIllegalArgument() {
         
        mc.getMidiPitch(3, null);
    }

    @Test
    public void testGetMidiPitch1() {
         
        assertEquals(mc.getMidiPitch(4, Pitch.C), 60);
    }

    @Test
    public void testGetMidiPitch2() {
         
        assertEquals(mc.getMidiPitch(3, Pitch.C), 48);
    }

    @Test
    public void testRingAroundTheRosie() {
         
        int pitch = -1;
        pitch = mc.getMidiPitch(4, Pitch.C);
        assertEquals(mc.getPitch(pitch).toString(), "C");
        assertEquals(mc.getOctave(pitch), 4);
        assertEquals(mc.getMidiPitch(mc.getOctave(pitch), mc.getPitch(pitch)), pitch);
    }

}
