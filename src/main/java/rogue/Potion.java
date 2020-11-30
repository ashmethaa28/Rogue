package rogue;

import java.awt.Point;

public class Potion extends Magic implements Edible, Tossable {

/**
 * Default Constructor.
 */
  public Potion() {
    setEat(true);
    setToss(true);
  }

/**
 * Constructor.
 * @param id
 * @param name
 * @param type
 * @param xyLocation
 * @param description
 */
  public Potion(int id, String name, String type, Point xyLocation, String description) {
    super(id, name, type, xyLocation, description);
    setEat(true);
    setToss(true);
  }

  @Override
  public String eat() {
    int first = getDescription().indexOf(":", 0);
    String str = getDescription().substring(0, first);
    return str;
  }

  @Override
  public String toss() {
    int first = getDescription().indexOf(":", 0);
    String str = getDescription().substring(first + 2);
    return str;
  }
}
