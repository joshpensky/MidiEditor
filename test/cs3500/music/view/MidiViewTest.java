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
      .addNote(0, 2, 1, 64, 72).addNote(0, 7, 1, 55, 70).addNote(2, 4, 1, 62, 72)
      .addNote(4, 6, 1, 60, 71).addNote(6, 8, 1, 62, 79).addNote(8, 15, 1, 55, 79)
      .addNote(8, 10, 1, 64, 85).addNote(10, 12, 1, 64, 78).addNote(12, 15, 1, 64, 74)
      .addNote(16, 24, 1, 55, 77).addNote(16, 18, 1, 62, 75).addNote(18, 20, 1, 62, 77)
      .addNote(20, 24, 1, 62, 75).addNote(24, 26, 1, 55, 79).addNote(24, 26, 1, 64, 82)
      .addNote(26, 28, 1, 67, 84).addNote(28, 32, 1, 67, 75).addNote(32, 40, 1, 55, 78)
      .addNote(32, 34, 1, 64, 73).addNote(34, 36, 1, 62, 69).addNote(36, 38, 1, 60, 71)
      .addNote(38, 40, 1, 62, 80).addNote(40, 48, 1, 55, 79).addNote(40, 42, 1, 64, 84)
      .addNote(42, 44, 1, 64, 76).addNote(44, 46, 1, 64, 74).addNote(46, 48, 1, 64, 77)
      .addNote(48, 56, 1, 55, 78).addNote(48, 50, 1, 62, 75).addNote(50, 52, 1, 62, 74)
      .addNote(52, 54, 1, 64, 81).addNote(54, 56, 1, 62, 70).addNote(56, 64, 1, 52, 72)
      .addNote(56, 64, 1, 60, 73).build());
    this.view.initialize();
    assertEquals("tempo 200000\n"
        + "note 0 2 1 64 72\n"
        + "note 2 4 1 62 72\n"
        + "note 4 6 1 60 71\n"
        + "note 0 7 1 55 70\n"
        + "note 6 8 1 62 79\n"
        + "note 8 10 1 64 85\n"
        + "note 10 12 1 64 78\n"
        + "note 8 15 1 55 79\n"
        + "note 12 15 1 64 74\n"
        + "note 16 18 1 62 75\n"
        + "note 18 20 1 62 77\n"
        + "note 16 24 1 55 77\n"
        + "note 20 24 1 62 75\n"
        + "note 24 26 1 55 79\n"
        + "note 24 26 1 64 82\n"
        + "note 26 28 1 67 84\n"
        + "note 28 32 1 67 75\n"
        + "note 32 34 1 64 73\n"
        + "note 34 36 1 62 69\n"
        + "note 36 38 1 60 71\n"
        + "note 32 40 1 55 78\n"
        + "note 38 40 1 62 80\n"
        + "note 40 42 1 64 84\n"
        + "note 42 44 1 64 76\n"
        + "note 44 46 1 64 74\n"
        + "note 40 48 1 55 79\n"
        + "note 46 48 1 64 77\n"
        + "note 48 50 1 62 75\n"
        + "note 50 52 1 62 74\n"
        + "note 52 54 1 64 81\n"
        + "note 48 56 1 55 78\n"
        + "note 54 56 1 62 70\n"
        + "note 56 64 1 52 72\n"
        + "note 56 64 1 60 73\n", this.seq.getLog());
  }
}
