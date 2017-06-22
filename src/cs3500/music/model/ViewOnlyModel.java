package cs3500.music.model;

import java.util.List;

/**
 * View Only version of the MusicEditorOperations. Used by the views so they cannot modify the state of the model.
 */
public class ViewOnlyModel implements MusicEditorOperations {
    private MusicEditorOperations model;

    /**
     * Default constructor for this model. Should never be called.
     * @throws UnsupportedOperationException
     */
    public ViewOnlyModel() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot construct default view only mode");
    }

    /**
     * Constructor for view only model.
     * @param m model this model calls on for getter methods.
     */
    public ViewOnlyModel(MusicEditorOperations m) {
        if (m == null) {
            throw new IllegalArgumentException("Cannot construct a null with a null model");
        }
        this.model = m;
    }

    /**
     * Should never be called on this implementation. This is a view only model.
     */
    @Override
    public void create() {
        return;
    }


    /**
     * Should never be called on this implementation. This is a view only model.
     */
    @Override
    public String view() throws IllegalStateException, UnsupportedOperationException {
        return  "";
    }


    /**
     * Should never be called on this implementation. This is a view only model.
     * @param start        the starting position of the note (measured in beats)
     * @param end          the ending position of the note (measured in beats)
     * @param instrument   the instrument the note is played in [0, 127]
     * @param pitch        the pitch at which the note is played [0, 127]
     * @param volume       the volume at which the note is played [0, 127]
     * @throws IllegalStateException will not throw in view only
     * @throws IllegalArgumentException will not throw in view only model
     */
    @Override
    public void addNote(int start, int end, int instrument, int pitch, int volume) throws IllegalStateException, IllegalArgumentException {
        return;
    }

    /**
     * Should never be called. This is view only model.
     * @param start          the starting position of the note (measured in beats)
     * @param instrument     the instrument the note is played in [0, 127]
     * @param pitch          the pitch at which the note is played [0, 127]
     * @throws IllegalStateException  will not throw in view only model
     * @throws IllegalArgumentException will not throw in view only model
     */
    @Override
    public void removeNote(int start, int instrument, int pitch) throws IllegalStateException, IllegalArgumentException {
        return;
    }

    /**
     * Should never be called.
     * @param start          the starting position of the note (measured in beats)
     * @param instrument     the instrument the note is played in [0, 127]
     * @param pitch          the pitch at which the note is played [0, 127]
     * @param editedPitch    the new pitch of the note [0, 127]
     * @throws IllegalStateException will not throw in view only model
     * @throws IllegalArgumentException will not throw in view only model
     */
    @Override
    public void editNotePitch(int start, int instrument, int pitch, int editedPitch) throws IllegalStateException, IllegalArgumentException {
        throw new UnsupportedOperationException("Cannot edit pitch in view only mode");
    }

    /**
     * should never be called
     * @param start          the starting position of the note (measured in beats)
     * @param instrument     the instrument the note is played in [0, 127]
     * @param pitch          the pitch at which the note is played [0, 127]
     * @param editedStart    the new start position of the note (measured in beats)
     * @throws IllegalStateException throws only unsupported exception; view only
     * @throws IllegalArgumentException will not throw in view only model
     * @throws UnsupportedOperationException will not throw in view only model
     */
    @Override
    public void editNotePosition(int start, int instrument, int pitch, int editedStart) throws IllegalStateException, IllegalArgumentException {
        return;
    }

    /**
     * should never be called, this is view only
     * @param start        the starting position of the note (measured in beats)
     * @param instrument   the instrument the note is played in [0, 127]
     * @param pitch        the pitch at which the note is played [0, 127]
     * @param editedEnd    the new end position of the note (measured in beats)
     * @throws IllegalStateException will not throw in view only model
     * @throws IllegalArgumentException will not throw in view only model
     */
    @Override
    public void editNoteDuration(int start, int instrument, int pitch, int editedEnd) throws IllegalStateException, IllegalArgumentException {
        return;
    }

    /**
     * should never be called, view only method.
     * @param tempo   the tempo to set the piece
     * @throws IllegalStateException throws only unsupported exception; view only
     * @throws IllegalArgumentException will not throw in view only model
     * @throws UnsupportedOperationException will not throw in view only model
     */
    @Override
    public void setTempo(int tempo) throws IllegalStateException, IllegalArgumentException {
        return;
    }


    @Override
    public int getTempo() throws IllegalStateException {
        return this.model.getTempo();
    }

    @Override
    public List<Integer[]> getNotes() throws IllegalStateException {
        return this.model.getNotes();
    }

    @Override
    public List<Integer[]> getNotesAtBeat(int beat) throws IllegalStateException {
        return this.model.getNotesAtBeat(beat);
    }

    @Override
    public int getLength() throws IllegalStateException {
        return this.model.getLength();
    }
}
