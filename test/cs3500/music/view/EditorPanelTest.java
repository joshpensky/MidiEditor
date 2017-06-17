package cs3500.music.view;

import cs3500.music.model.MusicEditorBuilder;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.MusicEditorOperations;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link GuiContainer} class.
 */
public class EditorPanelTest {
    /*EditorPanel ep;
    MusicEditorBuilder m1;
    MusicEditorOperations model;

    void init() {
        m1 = new MusicEditorBuilder();
        model = m1.build();
        model.create();
        model.addNote(0, 30, 1, 60, 100);
        ep = new EditorPanel(model, 20, 20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadConstructionNullModel() {
        ep = new EditorPanel(null, 20, 20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadConstructionBadHeigth() {
        init();
        ep = new EditorPanel(model, 20, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadConstructionBadWidth() {
        init();
        ep = new EditorPanel(model, 0, 20);
    }


    @Test
    public void testCursorForwards() {
        init();
        assertEquals(ep.updateCursor(true), 1);
    }

    @Test
    public void testCursorBackwards() {
        init();
        ep.updateCursor(true);
        ep.updateCursor(true);
        ep.updateCursor(true);
        assertEquals(ep.updateCursor(false), 2);
    }

    @Test
    public void testCursorBackAndForwardsEquality() {
        init();
        MusicEditorBuilder temp = new MusicEditorBuilder();
        MusicEditorOperations m2 = temp.build();
        m2.create();
        m2.addNote(0, 30, 1, 60, 100);
        EditorPanel other = new EditorPanel(m2, 20, 20);
        ep.updateCursor(true);
        other.updateCursor(true);
        ep.updateCursor(false);
        other.updateCursor(true);
        assertEquals(ep.updateCursor(true), other.updateCursor(false));
    }*/

}
