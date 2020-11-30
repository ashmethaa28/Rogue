package rogue;

import java.awt.Point;
import java.io.Serializable;
/**
 * A basic Item class; basic functionality for both consumables and equipment.
 */
public class Item implements Serializable {

  private int itemId;
  private String itemName;
  private String itemType;
  private Room room = new Room();
  private String description;
  private Character ch;
  private Point p;
  private boolean wearable = false;
  private boolean tossable = false;
  private boolean edible = false;
  private static final long serialVersionUID = 1377278047334935732L;

/**
 * Default constructor.
 */
  public Item() {
  }

/**
 * Constructor.
 * @param id of item
 * @param name of item
 * @param type of item
 * @param xyLocation of item
 * @param des description of item
 */
  public Item(int id, String name, String type, Point xyLocation, String des) {
    setId(id);
    setName(name);
    setType(type);
    setXyLocation(xyLocation);
    setDescription(des);
  }

// Getters and setters
/**
 * Gets the id of the item.
 * @return (int) the id of the item
 */
  public int getId() {
    return itemId;
  }

/**
 * Sets the id of the item.
 * @param id of item
 */
  public void setId(int id) {
    itemId = id;
  }

/**
 * Gets the name of the item.
 * @return (String) the name of the item
 */
  public String getName() {
    return itemName;
  }

/**
 * Sets name of item.
 * @param name of item
 */
  public void setName(String name) {
    itemName = name;
  }

/**
 * Gets type of item.
 * @return (String) the item's type
 */
  public String getType() {
    return itemType;
  }

/**
 * Sets the item's type.
 * @param type of item
 */
  public void setType(String type) {
    itemType = type;
  }

/**
 * Gets the display character of the item.
 * @return (Character) a Character value that is used to represent an item
 */
  public Character getDisplayCharacter() {
    return ch;
  }

/**
 * Sets the display character of the item.
 * @param newDisplayCharacter used to represent item
 */
  public void setDisplayCharacter(Character newDisplayCharacter) {
    ch = newDisplayCharacter;
  }

/**
 * Gets the description of the item.
 * @return (String) a description of the item
 */
  public String getDescription() {
    return description;
  }

/**
 * Sets the description of an item.
 * @param newDescription for the item
 */
  public void setDescription(String newDescription) {
    description = newDescription;
  }

/**
 * Gets the location of item.
 * @return (Point) the items's location
 */
  public Point getXyLocation() {
    return p;
  }

/**
 * Sets location of item.
 * @param newXyLocation of item
 */
  public void setXyLocation(Point newXyLocation) {
    p = newXyLocation;
  }

/**
 * Gets the room which the item is in.
 * @return (Room) in which the item is in
 */
  public Room getCurrentRoom() {
    return room;
  }

/**
 * Sets the room which item is in.
 * @param newCurrentRoom in which item is in
 */
  public void setCurrentRoom(Room newCurrentRoom) {
    room = newCurrentRoom;
  }

  protected void setWear(boolean verify) {
    wearable = verify;
  }

  protected boolean getWear() {
    return wearable;
  }

  protected void setToss(boolean verify) {
    tossable = verify;
  }

  protected boolean getToss() {
    return tossable;
  }

  protected void setEat(boolean verify) {
    edible = verify;
  }

  protected boolean getEat() {
    return edible;
  }

}
