package cs3500.music;

import cs3500.music.model.MusicEditorOperations;
import cs3500.music.model.MusicEditorBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.MidiView;
import cs3500.music.view.MusicEditorView;
import cs3500.music.view.MusicEditorViewFactory;

import java.io.FileReader;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 * The main class for the music editor that (currently) handles model and view.
 */
public class MusicEditor {

  /**
   * Main for Music Editor. Runs and directs flow for program.
   *
   * @param args   arguments for program to be run, needs to be in format: textFileName
   *              desiredViewType
   * @throws IOException appendable/readable not initialized correctly
   * @throws InvalidMidiDataException data given to midi player not correctly formatted
   * @throws MidiUnavailableException midi is unavailable for system at this time
   */
  public static void main(String[] args) throws IOException, InvalidMidiDataException,
      MidiUnavailableException {
    if (args.length != 2) {
      throw new IllegalArgumentException("Must give two arguments:\n"
          + "1) the file name (ex.: \"mary-little-lamb.txt\")\n"
          + "2) the desired view (either \"console\", \"visual\", or \"midi\")");
    }
    String fileName = args[0];
    String viewName = args[1];
    MusicEditorOperations model = MusicReader.parseFile(new FileReader(fileName),
        new MusicEditorBuilder());
    MusicEditorView view = MusicEditorViewFactory.getView(viewName, model);
    view.initialize();
  }
}
