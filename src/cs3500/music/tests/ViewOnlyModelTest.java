package cs3500.music.tests;

import cs3500.music.model.MusicEditorBuilder;
import cs3500.music.model.MusicEditorOperations;
import cs3500.music.model.ViewOnlyModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by Will on 6/23/2017.
 */
public class ViewOnlyModelTest {
  MusicEditorOperations model;
  ViewOnlyModel vom;

  void init() {
    model = new MusicEditorBuilder().build();


    model.addNote(0, 1, 1, 60, 10);
    model.addNote(2, 3, 1, 60, 10);
    model.setTempo(1000);

    vom = new ViewOnlyModel(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructNullModel() {
    ViewOnlyModel vom2 = new ViewOnlyModel(null);
  }

  @Test
  public void testCreate() {
    init();
    ViewOnlyModel vom2 = vom;
    vom2.create();
    assertEquals(vom2, vom);
  }

  @Test
  public void testAddNote() {
    init();
    vom.addNote(5, 6, 1, 60, 30);
    assertEquals(3, vom.getLength());
  }

  @Test
  public void testRemoveNote() {
    init();
    vom.removeNote(0, 1, 60);
    assertEquals(3, vom.getLength());
  }

  @Test
  public void testEditNotePitch() {
    init();
    vom.editNotePitch(0, 1, 60, 80);
    int curPitch = vom.getNotesAtBeat(0).get(0)[3];
    assertEquals(60, curPitch);
  }

  @Test
  public void testEditNotePosition() {
    init();
    vom.editNotePosition(0, 1, 60, 5);
    assertEquals(3, vom.getLength());
  }

  @Test
  public void testEditNoteDuration() {
    init();
    vom.editNoteDuration(2, 1, 60, 30);
    assertEquals(3, vom.getLength());
  }

  @Test
  public void testSetTempo() {
    init();
    vom.setTempo(2);
    assertEquals(1000, vom.getTempo());
  }

  @Test
  public void getTempoTest() {
    init();
    assertEquals(vom.getTempo(), 1000);
  }

  @Test
  public void getNotesTest() {
    init();
    assertEquals(vom.getNotes().size(), 2);
  }

  @Test
  public void getNotesAtBeatTest() {
    init();
    assertEquals(vom.getNotesAtBeat(0).size(), model.getNotesAtBeat(0).size());
  }

  @Test
  public void getLengthTest() {
    init();
    assertEquals(vom.getLength(), model.getLength());
  }

}
