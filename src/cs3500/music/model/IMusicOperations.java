package cs3500.music.model;

/**
 * Created by Will on 6/13/2017.
 */
public interface IMusicOperations<T> {

    void addNoe(int startPos, int endPos, int instrument, int pitch, int volume);

    void removeNote(int startPos, int endPos, int instrument, int pitch, int volume);

    T getState();

    String stringState();

    void addTrack(int startPos, T track);

    void removeTrack(int startPOs, T track);

    void setTempo (int tempo);

}
