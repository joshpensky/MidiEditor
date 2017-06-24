package cs3500.music;

import cs3500.music.controller.MusicEditorController;
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
   * @throws IllegalArgumentException if the given file name does not point to a file, or the view
   *                                  name is not a valid view
   * @throws InvalidMidiDataException data given to midi player not correctly formatted
   * @throws MidiUnavailableException midi is unavailable for system at this time
   */
  public static void main(String[] args) throws IllegalArgumentException, InvalidMidiDataException,
      MidiUnavailableException {
    if (args.length != 2) {
      throw new IllegalArgumentException("Must give two arguments:\n"
          + "1) the file name (ex.: \"mary-little-lamb.txt\")\n"
          + "2) the desired view (either \"console\", \"visual\", or \"midi\")");
    }
    MusicEditorController mec = MusicEditorController.initialize();
    mec.setModelAndView(args[0], args[1]);

  }
}
