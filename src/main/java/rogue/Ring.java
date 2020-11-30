package rogue;

import java.awt.Point;

public class Ring extends Magic implements Wearable {

/**
 * Default Constructor.
 */
  public Ring() {
    setWear(true);
  }

/**
 * Constructor.
 * @param id
 * @param name
 * @param type
 * @param xyLocation
 * @param description
 */
  public Ring(int id, String name, String type, Point xyLocation, String description) {
    super(id, name, type, xyLocation, description);
    setWear(true);
  }

  @Override
  public String wear() {
    return getDescription();
  }
}
