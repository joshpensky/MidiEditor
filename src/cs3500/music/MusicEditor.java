package cs3500.music;

import cs3500.music.model.EditorOperations;
import cs3500.music.model.josh.EditorModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.EditorBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.ViewFactory;
import cs3500.music.view.ViewInterface;

import java.io.FileReader;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    //GuiViewFrame view = new GuiViewFrame();
    //MidiViewImpl midiView = new MidiViewImpl();
    // You probably need to connect these views to your model, too...

    String fileName = "mystery-3.txt";
    EditorOperations model = MusicReader.parseFile(new FileReader(fileName), new EditorBuilder());
//    EditorOperations model = new EditorModel();
    ViewInterface view = ViewFactory.getView("visual", model);
    view.initialize();
  }
}
