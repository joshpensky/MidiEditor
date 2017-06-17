package cs3500.music.view;

import cs3500.music.model.MusicEditorBuilder;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by josh_jpeg on 6/17/17.
 */
public class MusicEditorViewFactoryTest {
  private final MusicEditorBuilder builder = new MusicEditorBuilder();

  /*@Test(expected = IllegalArgumentException.class)
  public void getViewNullName() {
    MusicEditorViewFactory.getView(null, builder.build());
  }

  @Test(expected = IllegalArgumentException.class)
  public void getViewNullModel() {
    MusicEditorViewFactory.getView("visual", null);
  }

  @Test
  public void getViewConsole() {
    assertTrue(MusicEditorViewFactory.getView("console", builder.build()) instanceof TextView);
  }

  @Test
  public void getViewVisual() {
    assertTrue(MusicEditorViewFactory.getView("visual", builder.build()) instanceof GuiView);
  }

  @Test
  public void getViewMidi() {
    assertTrue(MusicEditorViewFactory.getView("midi", builder.build()) instanceof MidiView);
  }*/
}
