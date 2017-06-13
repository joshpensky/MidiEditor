package cs3500.music.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for the IMusicModel interface. Has a track that holds the chords that have been added to
 * this model.
 */
public class MusicModel implements IMusicModel {
  private Track track;

  private Note highestNote;
  private Note lowerstNote;

  /**
   * Constructor for this model.
   */
  public MusicModel() {
    track = new Track();
  }


  @Override
  public Track getTrack() {
    Track temp = new Track(this.track);
    return temp;
  }

  @Override
  public void startTrack() {
    this.track = new Track();
    highestNote = null;
    lowerstNote = null;
  }

  @Override
  public void startTrack(Track track) {
    checkNull(track, "Track cannot be null");
    this.track = track;
    this.getExtremeNotes();
  }

  @Override
  public String getMixerState() {

    String header = "";
    if (this.track.length() == 0) {
      return "";
    }
    List<Note> range = genNoteRange();


    for (Note n : range) {
      header += n.toString();
    }

    String output = "";
    int padding;
    int lineNum = 1;
    padding = this.getPadding(this.track.length());


    for (Chord c : this.track.getFullTrack()) {
      output += lineNumberPadded(padding, lineNum);
      for (Note n : range) {
        if (c.has(n) || c.has(n.toNeg())) {
          output += this.lineNotes(c.getNote(n));
        } else {
          output += "     ";
        }
      }
      output += "\n";
      lineNum++;
    }
    return header + "\n" + output;
  }

  /**
   * Generated the note range from the highest and lowest notes in this track.
   * @return List of notes that are [lowestNote, highestNote]
   */
  private List<Note> genNoteRange() {
    List<Note> notes = new ArrayList<>();
    Note temp;
    for (int i = 1; i <= 10; i++) {
      for (Pitch p : Pitch.values()) {
        temp = new Note(p, 1, i, false);
        if (lowerstNote.compareTo(temp) <= 0 && highestNote.compareTo(temp) >= 0) {
          notes.add(temp);
        }
      }
    }
    return notes;
  }

  /**
   * Helper method for padding for the beat number in toString.
   * @param length the length of this's track.
   * @return the amount of padding this length needs.
   */
  private int getPadding(int length) {
    int count = 1;
    int l = length;
    while (l > 9) {
      count++;
      l /= 10;
    }

    return count;
  }

  /**
   * Pads the number for each line which represents each chord.
   * @param padding the amount of padding that is needed.
   * @param lineNum the line number to be padded.
   * @return a padded Number
   */
  private String lineNumberPadded(int padding, int lineNum) {
    int leftPad = 0;

    int totalPad = padding - getPadding(lineNum);

    if (totalPad % 2 == 0) {
      leftPad = totalPad / 2;
    } else {
      leftPad = totalPad / 2 + 1;
    }

    String temp = "";
    for (int i = 0; i < leftPad; i++) {
      temp += " ";
    }
    temp += Integer.toString(lineNum);



    return temp;
  }

  /**
   * Helper method for toString. Prints the note as either X or | with padding.
   * @param n the note to be printed.
   * @return formatted note representation for model.
   */
  private String lineNotes(Note n) {
    if (n.getStart()) {
      return "  X  ";
    } else {
      return "  |  ";
    }
  }

  @Override
  public void addNote(int measure, Note note) {
    this.checkNull(note, "Note cannot be null");
    this.track.addNote(measure, note);
    this.getExtremeNotes();
  }

  @Override
  public void insertChord(int measure, Chord chord) {
    this.checkNull(chord, "Chord cannot be null");
    this.track.insertChord(measure, chord);
    this.getExtremeNotes();

  }


  @Override
  public Note removeNote(int measure, Note note) {
    this.checkNull(note, "Note cannot be null");
    if (measure >= this.track.length() || measure < 0) {
      throw new IllegalArgumentException("Measure for removal is out of bounds");
    }
    Note temp = this.track.removeNote(measure, note);
    this.getExtremeNotes();
    return temp;
  }

  @Override
  public Chord removeChord(int measure) {
    if (measure >= this.track.length()) {
      throw new IllegalArgumentException("Measure for removal is out of bounds");
    }

    Chord temp = this.track.removeChord(measure);
    this.getExtremeNotes();
    return temp;
  }

  @Override
  public List<Chord> cutCords(int start, int numToBeCut) {
    if (start < 0 || start >= this.track.length() || start + numToBeCut >= this.track.length()) {
      throw new IllegalArgumentException("Index for cutting out of bounds");
    }
    List<Chord> temp = new ArrayList<>();

    for (int i = 0; i < numToBeCut; i++) {
      temp.add(this.removeChord(start + i));
    }

    this.getExtremeNotes();

    return temp;
  }

  @Override
  public void addTrack(int startPosition, Track track) {
    if (startPosition < 0) {
      throw new IllegalArgumentException("Start position cannot be null");
    }
    this.checkNull(track, "Track cannot be or have null components");
    this.track.addTrack(startPosition, track);
    this.getExtremeNotes();
  }

  /**
   * Checker for a note for null values.
   * @param n the note to be checked
   * @param s the error message if there is null.
   */
  private void checkNull(Note n, String s) {
    if (n == null) {
      throw new IllegalArgumentException(s);
    }
  }

  /**
   * Checker for a chord for null values.
   * @param c the chord to be checked
   * @param s the error message if there is null.
   */
  private void checkNull(Chord c, String s) {
    if (c == null) {
      throw new IllegalArgumentException(s);
    }

    for (Note note : c.getChord()) {
      this.checkNull(note, s);
    }
  }


  /**
   * Checker for a track for null values.
   * @param t the track to be checked
   * @param s the error message if there is null.
   */
  private void checkNull(Track t, String s) {
    if (t == null) {
      throw new IllegalArgumentException(s);
    }

    for (Chord c : t.getFullTrack()) {
      this.checkNull(c, s);
    }
  }

  /**
   * Checker for a lits of chords for null values.
   * @param loc the note to be checked
   * @param s the error message if there is null.
   */
  private void checkNull(List<Chord> loc, String s) {
    Track t = new Track(loc);
    this.checkNull(t, s);
  }

  /**
   * sets the values for the highest and lowest notes in this track.
   */
  private void getExtremeNotes() {
    this.highestNote = this.track.getHighestNote();
    this.lowerstNote = this.track.getLowestNote();
  }
}

