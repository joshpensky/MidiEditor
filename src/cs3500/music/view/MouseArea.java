package cs3500.music.view;

/**
 * An area defined for a Swing view that can be recognized by a mouse for hover or click.
 */
public class MouseArea {
  private final Posn topLeft;
  private final Posn botRight;

  /**
   * Constructs a new rectangular mouse area using only the top-left and bottom-right positions.
   *
   * @param topLeft    the top-left position of the area
   * @param botRight   the bottom-right position of the area
   * @throws IllegalArgumentException if the given positions are uninitialized, if the
   *                                  bottom-right bottom's X is less than the top-left's,
   *                                  or if the bottom-right's Y is less than the top-left's.
   */
  protected MouseArea(Posn topLeft, Posn botRight)
      throws IllegalArgumentException {
    if (topLeft == null || botRight == null) {
      throw new IllegalArgumentException("Cannot construct area with null positions");
    }
    this.topLeft = topLeft;
    if (botRight.getX() < topLeft.getX()) {
      throw new IllegalArgumentException("Right edge is before left edge.");
    } else if (botRight.getY() < topLeft.getY()) {
      throw new IllegalArgumentException("Bottom edge is above top edge.");
    }
    this.botRight = botRight;
  }

  /**
   * Checks whether or not a mouse is within the given area.
   *
   * @param mouseX   the x-position of the mouse
   * @param mouseY   the y-position of the mouse
   * @return true if the mouse cursor is within the area, false otherwise
   */
  protected boolean mouseWithinArea(int mouseX, int mouseY) {
    if (mouseX < topLeft.getX() || mouseX > botRight.getX()
        || mouseY < topLeft.getY() || mouseY > botRight.getY()) {
      return false;
    }
    return true;
  }
}
