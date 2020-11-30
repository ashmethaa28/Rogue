package rogue;

import java.awt.Point;

public class Food extends Item implements Edible {
/**
 * Default Constructor.
 */
  public Food() {
    setEat(true);
  }

/**
 * Constructor.
 * @param id
 * @param name
 * @param type
 * @param xyLocation
 * @param description
 */
  public Food(int id, String name, String type, Point xyLocation, String description) {
    super(id, name, type, xyLocation, description);
    setEat(true);
  }

  @Override
  public String eat() {
    return getDescription();
  }
}
