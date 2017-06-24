package cs3500.music.view;

import cs3500.music.model.MusicEditorOperations;
import cs3500.music.model.MusicEditorBuilder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link ConsoleView} class.
 */
public class ConsoleViewTest {
  private final MusicEditorBuilder builder = new MusicEditorBuilder();
  private final Appendable app = new StringBuilder();
  private ConsoleView view;

  /**
   * Initializes the MidiView to the given model, using the mock sequencer.
   *
   * @param model   the model to set the view to
   */
  public void init(MusicEditorOperations model) {
    this.view = new ConsoleView.Builder(model).setAppendable(this.app).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void builderNullModel() {
    ConsoleView view = new ConsoleView.Builder(null).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void builderNullAppendable() {
    ConsoleView view = new ConsoleView.Builder(builder.build()).setAppendable(null).build();
  }

  @Test
  public void builderValid() {
    ConsoleView view = new ConsoleView.Builder(builder.addNote(0, 3, 4, 60, 64).build())
        .setAppendable(this.app).build();
    view.initialize();
    assertEquals(""
        + "     C4 \n"
        + "0    X  \n"
        + "1    |  \n"
        + "2    |  \n"
        + "3    |  \n", this.app.toString());
  }

  @Test
  public void initializeEmptyModel() {
    MusicEditorOperations model = builder.build();
    init(model);
    this.view.initialize();
    assertEquals("", this.app.toString());
  }

  @Test
  public void initializeAddNote() {
    MusicEditorOperations model = builder.addNote(0, 3, 4, 60, 64).build();
    init(model);
    this.view.initialize();
    assertEquals(""
        + "     C4 \n"
        + "0    X  \n"
        + "1    |  \n"
        + "2    |  \n"
        + "3    |  \n", this.app.toString());
  }

  @Test
  public void initializeMultipleNotes() {
    MusicEditorOperations model = builder.addNote(2, 4, 1, 47, 100).addNote(4, 8, 1, 47, 100)
        .addNote(4, 9, 1, 56, 100).addNote(2, 5, 1, 62, 100).build();
    init(model);
    String output = ""
        + "     B2   C3  C#3   D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4 \n"
        + "0                                                                                  \n"
        + "1                                                                                  \n"
        + "2    X                                                                          X  \n"
        + "3    |                                                                          |  \n"
        + "4    X                                            X                             |  \n"
        + "5    |                                            |                             |  \n"
        + "6    |                                            |                                \n"
        + "7    |                                            |                                \n"
        + "8    |                                            |                                \n"
        + "9                                                 |                                \n";
    this.view.initialize();
    assertEquals(output, this.app.toString());
  }

  @Test
  public void initializeMaryLittleLamb() {
    MusicEditorOperations model = builder.setTempo(200000)
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
        .addNote(56, 64, 1, 60, 73).build();
    init(model);
    this.view.initialize();
    String output = ""
        + "      E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4   F4  F#4   G4 \n"
        + " 0                   X                                            X                 \n"
        + " 1                   |                                            |                 \n"
        + " 2                   |                                  X         |                 \n"
        + " 3                   |                                  |                           \n"
        + " 4                   |                        X         |                           \n"
        + " 5                   |                        |                                     \n"
        + " 6                   |                        |         X                           \n"
        + " 7                   |                                  |                           \n"
        + " 8                   X                                  |         X                 \n"
        + " 9                   |                                            |                 \n"
        + "10                   |                                            X                 \n"
        + "11                   |                                            |                 \n"
        + "12                   |                                            X                 \n"
        + "13                   |                                            |                 \n"
        + "14                   |                                            |                 \n"
        + "15                   |                                            |                 \n"
        + "16                   X                                  X                           \n"
        + "17                   |                                  |                           \n"
        + "18                   |                                  X                           \n"
        + "19                   |                                  |                           \n"
        + "20                   |                                  X                           \n"
        + "21                   |                                  |                           \n"
        + "22                   |                                  |                           \n"
        + "23                   |                                  |                           \n"
        + "24                   X                                  |         X                 \n"
        + "25                   |                                            |                 \n"
        + "26                   |                                            |              X  \n"
        + "27                                                                               |  \n"
        + "28                                                                               X  \n"
        + "29                                                                               |  \n"
        + "30                                                                               |  \n"
        + "31                                                                               |  \n"
        + "32                   X                                            X              |  \n"
        + "33                   |                                            |                 \n"
        + "34                   |                                  X         |                 \n"
        + "35                   |                                  |                           \n"
        + "36                   |                        X         |                           \n"
        + "37                   |                        |                                     \n"
        + "38                   |                        |         X                           \n"
        + "39                   |                                  |                           \n"
        + "40                   X                                  |         X                 \n"
        + "41                   |                                            |                 \n"
        + "42                   |                                            X                 \n"
        + "43                   |                                            |                 \n"
        + "44                   |                                            X                 \n"
        + "45                   |                                            |                 \n"
        + "46                   |                                            X                 \n"
        + "47                   |                                            |                 \n"
        + "48                   X                                  X         |                 \n"
        + "49                   |                                  |                           \n"
        + "50                   |                                  X                           \n"
        + "51                   |                                  |                           \n"
        + "52                   |                                  |         X                 \n"
        + "53                   |                                            |                 \n"
        + "54                   |                                  X         |                 \n"
        + "55                   |                                  |                           \n"
        + "56    X              |                        X         |                           \n"
        + "57    |                                       |                                     \n"
        + "58    |                                       |                                     \n"
        + "59    |                                       |                                     \n"
        + "60    |                                       |                                     \n"
        + "61    |                                       |                                     \n"
        + "62    |                                       |                                     \n"
        + "63    |                                       |                                     \n"
        + "64    |                                       |                                     \n";
    assertEquals(output, this.app.toString());
  }
}
