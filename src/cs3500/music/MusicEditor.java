package cs3500.music;

import cs3500.music.model.MusicEditorOperations;
import cs3500.music.model.MusicEditorBuilder;
import cs3500.music.view.MusicEditorView;
import cs3500.music.view.MusicEditorViewFactory;
import cs3500.music.util.MusicReader;

import java.io.FileReader;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 *
 */
public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException,
      MidiUnavailableException {
    if (args.length != 2) {
      throw new IllegalArgumentException("Must give two arguments:\n"
              + "1) the file name (ex.: \"mary-little-lamb.txt\")\n"
              + "2) the desired view (either \"text\", \"audio\", or \"visual\")");
    }
    String fileName = args[0];
    String viewName = args[1];
    MusicEditorOperations model = MusicReader.parseFile(new FileReader(fileName),
            new MusicEditorBuilder());

      try {
        MusicEditorView view = MusicEditorViewFactory.getView(viewName, model);
        view.initialize();
      } catch (MidiUnavailableException e) {
        throw new MidiUnavailableException("Midi is currently unavailable. Try again later.");
      }

  }
}
