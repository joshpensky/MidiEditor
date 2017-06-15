package cs3500.music;

import cs3500.music.model.EditorOperations;
import cs3500.music.util.EditorBuilder;
import cs3500.music.view.ViewInterface;
import cs3500.music.view.ViewFactory;
import cs3500.music.util.MusicReader;

import java.io.FileReader;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    //GuiViewFrame view = new GuiViewFrame();
    //MidiViewImpl midiView = new MidiViewImpl();
    // You probably need to connect these views to your model, too...

    String fileName = "mystery-3.txt";
    String viewName = "audio";
    EditorOperations model = MusicReader.parseFile(new FileReader(fileName), new EditorBuilder());
    try {
      ViewInterface view = ViewFactory.getView(viewName, model);
      view.initialize();
    } catch (MidiUnavailableException e) {
      System.err.println("midi unavailable.");
    }
  }
}
