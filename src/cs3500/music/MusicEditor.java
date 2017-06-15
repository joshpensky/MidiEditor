package cs3500.music;

import cs3500.music.model.MusicEditorOperations;
import cs3500.music.util.EditorBuilder;
import cs3500.music.view.MusicEditorView;
import cs3500.music.view.ViewFactory;
import cs3500.music.util.MusicReader;

import java.io.FileReader;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 *
 */
public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    String fileName = "mystery-2.txt";
    String viewName = "visual";
    MusicEditorOperations model = MusicReader.parseFile(new FileReader(fileName), new EditorBuilder());
    try {
      MusicEditorView view = ViewFactory.getView(viewName, model);
      view.initialize();
    } catch (MidiUnavailableException e) {
      System.err.println("midi unavailable.");
    }
  }
}
