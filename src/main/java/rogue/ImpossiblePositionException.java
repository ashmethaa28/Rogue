package rogue;

import java.awt.Point;

public class ImpossiblePositionException extends Exception {

/**
 *Default Constructor.
 */
  public ImpossiblePositionException() {

  }

/**
 * @param item
 * @param w
 * @param h
 * Constructor.
 */
  public ImpossiblePositionException(Item item, int w, int h) {
    Point p;
    if ((item.getXyLocation()).getX() == (w - 2)) {
      p = new Point((int) (item.getXyLocation()).getX() - 1, (int) (item.getXyLocation()).getY());
    } else {
      p = new Point((int) (item.getXyLocation()).getX() + 1, (int) (item.getXyLocation()).getY());
    }
    if ((item.getXyLocation()).getY() == (h - 2)) {
      p = new Point((int) (item.getXyLocation()).getX(), (int) (item.getXyLocation()).getY() - 1);
    } else {
      p = new Point((int) (item.getXyLocation()).getX(), (int) (item.getXyLocation()).getY() + 1);
    }
    item.setXyLocation(p);
  }
}
