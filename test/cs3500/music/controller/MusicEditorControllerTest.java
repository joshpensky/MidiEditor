package cs3500.music.controller;

import cs3500.music.MusicEditor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Will on 6/23/2017.
 */
public class MusicEditorControllerTest {
    MusicEditorController controller;


    @Test
    public void testSingletonPatternForController() {
        controller = MusicEditorController.initialize();
        assertEquals(MusicEditorController.initialize(), controller);
    }

    //TEST OTHER PARTS OF CONTROLLER
}
