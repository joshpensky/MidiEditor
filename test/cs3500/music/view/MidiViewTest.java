package cs3500.music.view;

import cs3500.music.model.MusicEditorBuilder;
import cs3500.music.model.MusicEditorOperations;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import javax.sound.midi.MidiUnavailableException;

/**
 * Created by josh_jpeg on 6/17/17.
 */
public class MidiViewTest {
  private final MusicEditorBuilder builder = new MusicEditorBuilder();
  private final MockSequencer seq = new MockSequencer();
  private MidiView view;

  public void init(MusicEditorOperations model) {
    try {
      view = new MidiView.Builder(model).setSequencer(this.seq).build();
    } catch (MidiUnavailableException e) {

    }
  }

  @Test
  public void initializeEmptyModel() {
    init(builder.build());
    this.view.initialize();
    assertEquals("tempo 0\n", this.seq.getLog());
  }

  @Test
  public void initializeSetTempoModel() {
    init(builder.setTempo(400).build());
    this.view.initialize();
    assertEquals("tempo 400\n", this.seq.getLog());
  }

  @Test
  public void initializeAddNotesModel() {
    init(builder.addNote(0, 3, 4, 60, 64).build());
    this.view.initialize();
    assertEquals("tempo 0\nnote 0 3 4 60 64\n", this.seq.getLog());
  }

  @Test
  public void initializeBothModel() {
    init(builder.addNote(0, 3, 4, 60, 64).setTempo(453).build());
    this.view.initialize();
    assertEquals("tempo 453\nnote 0 3 4 60 64\n", this.seq.getLog());
  }

  @Test
  public void initializeMultipleNotesPlaysInOrderByInstrument() {
    init(builder.setTempo(3567).addNote(1, 10, 1, 34, 64).addNote(5, 8, 4, 98, 110)
        .addNote(0, 3, 4, 60, 64).build());
    this.view.initialize();
    assertEquals("tempo 3567\n"
        + "note 0 3 4 60 64\n"
        + "note 5 8 4 98 110\n"
        + "note 1 10 1 34 64\n", this.seq.getLog());
  }

  @Test
  public void initializeMaryLittleLamb() {
    init(builder.setTempo(200000)
      .addNote(0, 2, 1, 64, 72)
      .addNote(0, 7, 1, 55, 70)
      .addNote(2, 4, 1, 62, 72)
      .addNote(4, 6, 1, 60, 71)
      .addNote(6, 8, 1, 62, 79)
      .addNote(8, 15, 1, 55, 79)
      .addNote(8, 10, 1, 64, 85)
      .build());
    this.view.initialize();
    assertEquals("tempo 3567\n"
      + "note 0 3 4 60 64\n"
      + "note 5 8 4 98 110\n"
      + "note 1 10 1 34 64\n", this.seq.getLog());
  }
}
