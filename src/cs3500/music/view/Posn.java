package cs3500.music.view;

/**
 * A position on a screen to be used in Swing views for defining mouse areas.
 */
public class Posn {
  private final int x;
  private final int y;

  /**
   * Constructs a new {@code Posn} with the given x and y positions.
   *
   * @param x   the x-position of the position
   * @param y   the y-position of the position
   */
  protected Posn(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the x-position of the position.
   *
   * @return the x-position
   */
  protected int getX() {
    return this.x;
  }

  /**
   * Gets the y-position of the position.
   *
   * @return the y-position
   */
  protected int getY() {
    return this.y;
  }
}
