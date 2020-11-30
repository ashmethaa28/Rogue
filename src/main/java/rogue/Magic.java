package rogue;

import java.awt.Point;

public class Magic extends Item {
/**
 * Default Constructor.
 */
  public Magic() {
  }

/**
 * Constructor.
 * @param id
 * @param name
 * @param type
 * @param xyLocation
 * @param description
 */
  public Magic(int id, String name, String type, Point xyLocation, String description) {
    super(id, name, type, xyLocation, description);
  }
}
