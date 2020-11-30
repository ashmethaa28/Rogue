package rogue;

import java.awt.Point;

public class SmallFood extends Food implements Tossable {
/**
 * Default Constructor.
 */
  public SmallFood() {
    setToss(true);
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
  public SmallFood(int id, String name, String type, Point xyLocation, String description) {
    super(id, name, type, xyLocation, description);
    setToss(true);
    setEat(true);
  }

  @Override
  public String toss() {
    int first = getDescription().indexOf(":", 0);
    String str = getDescription().substring(first + 2);
    return str;
  }

  @Override
  public String eat() {
    int first = getDescription().indexOf(":", 0);
    String str = getDescription().substring(0, first);
    return str;
  }
}
