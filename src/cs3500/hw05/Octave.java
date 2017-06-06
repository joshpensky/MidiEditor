package cs3500.hw05;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Represents an octave in a piece.
 */
public class Octave {
  private Map<Pitch, List<Note>> pitches;

  public Octave() {
    this.pitches = new HashMap<>();
    for (Pitch p : Pitch.values()) {
      this.pitches.put(p, new ArrayList<>());
    }
  }

  public Octave(Octave other) {
    // TODO
    // Add constructor for creating copies of other octaves
  }

  @Override
  public String toString() {
    return "";
  }

  public void add(Pitch pitch, int duration, int position) throws IllegalArgumentException {
    return;
  }

  public void remove(Pitch pitch, int position) throws IllegalArgumentException {
    return;
  }

  public void editPitch(Pitch pitch, int position, Pitch newPitch) throws IllegalArgumentException {
    return;
  }

  public void editDuration(Pitch pitch, int position, int newDuration)
                           throws IllegalArgumentException {
    return;
  }

  public void editPosition(Pitch pitch, int position, int newPosition)
                           throws IllegalArgumentException {
    return;
  }
}
