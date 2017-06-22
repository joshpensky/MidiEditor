package cs3500.music.model;

import java.util.List;

/**
 * Created by Will on 6/22/2017.
 */
public class ViewOnlyModel implements MusicEditorOperations {
    private MusicEditorOperations model;

    public ViewOnlyModel() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot construct default view only mode");
    }

    public ViewOnlyModel(MusicEditorOperations m) {
        if (m == null) {
            throw new IllegalArgumentException("Cannot construct a null with a null model");
        }
        this.model = m;
    }
    @Override
    public void create() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot create in view only mode");
    }

    @Override
    public String view() throws IllegalStateException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot create in view only mode");
    }

    @Override
    public void addNote(int start, int end, int instrument, int pitch, int volume) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot add a note in view only mode");
    }

    @Override
    public void removeNote(int start, int instrument, int pitch) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot remove a note in view only mode");
    }

    @Override
    public void editNotePitch(int start, int instrument, int pitch, int editedPitch) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot edit pitch in view only mode");
    }

    @Override
    public void editNotePosition(int start, int instrument, int pitch, int editedStart) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot edit note position in view only mode");
    }

    @Override
    public void editNoteDuration(int start, int instrument, int pitch, int editedEnd) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot edit note duration in view only mode");
    }

    @Override
    public void setTempo(int tempo) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot set tempo in view only mode");
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
