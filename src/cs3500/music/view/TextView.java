package cs3500.music.view;

import cs3500.music.controller.MusicEditorController;
import cs3500.music.model.MusicEditorOperations;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

/**
 * Represents the text view for a music editor model. Prints the String representation of the
 * currently opened piece to the console.
 */
public class TextView implements MusicEditorView {
  private final MusicEditorOperations model;
  private final Appendable app;
  private final StringBuilder log;

  /**
   * Represents the builder class for a TextView. Defaults the appendable of the TextView to the
   * console (System.out), but allows for the appendable to be changed.
   */
  public static final class Builder {
    private MusicEditorOperations model;
    private Appendable app;

    /**
     * Constructs a new {@code Builder} for a TextView.
     *
     * @param model   the model to be represented musically using MIDI
     * @throws IllegalArgumentException if the given model is uninitialized
     */
    public Builder(MusicEditorOperations model) throws IllegalArgumentException {
      if (model == null) {
        throw new IllegalArgumentException("Given model is uninitialized.");
      }
      this.model = model;
      this.app = null;
    }

    /**
     * Sets the appendable for a new TextView to the given one.
     *
     * @param app   the appendable to be set for the TextView
     * @return this builder
     * @throws IllegalArgumentException if the given appendable is uninitialized
     */
    public Builder setAppendable(Appendable app) throws IllegalArgumentException {
      if (app == null) {
        throw new IllegalArgumentException("Given appendable object is uninitialized.");
      }
      this.app = app;
      return this;
    }

    /**
     * Returns a new TextView with the given specifications set in this builder. If no appendable
     * has been set, it is defaulted to the console (System.out).
     *
     * @return a new TextView with this builder's instructions
     */
    public TextView build() {
      if (this.app == null) {
        this.app = new OutputStreamWriter(System.out);
      }
      return new TextView(this);
    }
  }

  /**
   * Constructs a new {@code TextView} using an instance of the nested builder class.
   *
   * @param builder   the builder for this TextView
   */
  private TextView(Builder builder) {
    this.model = builder.model;
    this.app = builder.app;
    this.log = new StringBuilder();
  }

  @Override
  public void initialize() {
    try {
      this.app.append(model.view());
    } catch (IOException e) {
      this.log.append("Encountered fatal IOException: " + e.getMessage() + "\n");
    }
  }

  @Override
  public String getLog() {
    return this.log.toString();
  }

  @Override
  public Map<Integer, Runnable> getKeyEvents() {
    //do nothing for now
    return null;
  }

  @Override
  public void setListeners(MusicEditorController controls, KeyListener keys) {
    return;
  }

  @Override
  public void update() {
    return;
  }
}
