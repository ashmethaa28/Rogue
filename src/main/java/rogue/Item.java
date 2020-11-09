package rogue;

import java.awt.Point;

/**
 * A basic Item class; basic functionality for both consumables and equipment.
 */
public class Item {

  private int itemId;
  private String itemName;
  private String itemType;
  private Room room = new Room();
  private String description;
  private Character ch;
  private Point p;

/**
 * Default constructor.
 */
  public Item() {
  }

/**
 *@param id - tells us id of items
 *@param name - tells us the name of items
 *@param type - discription of items
 *@param xyLocation - tells us the location of item
 *Sets the values of itemId, itemName, itemType, and p
 */
  public Item(int id, String name, String type, Point xyLocation) {
    setId(id);
    setName(name);
    setType(type);
    setXyLocation(xyLocation);
  }

// Getters and setters
/**
 *@return (int) the id of the item
 */
  public int getId() {
    return itemId;
  }

/**
 *@param id
 *updates the item's id
 */
  public void setId(int id) {
    itemId = id;
  }

/**
 *@return (String) the name of the item
 */
  public String getName() {
    return itemName;
  }

/**
 *@param name
 *updates the item's name
 */
  public void setName(String name) {
    itemName = name;
  }

/**
 *@return (String) the item's type
 */
  public String getType() {
    return itemType;
  }

/**
 *@param type
 *updates the item's type
 */
  public void setType(String type) {
    itemType = type;
  }

/**
 *@return (Character) a Character value that is used to represent an item
 */
  public Character getDisplayCharacter() {
    return ch;
  }

/**
 *@param newDisplayCharacter
 *updates the Character value that is used to represent an item
 */
  public void setDisplayCharacter(Character newDisplayCharacter) {
    ch = newDisplayCharacter;
  }

/**
 *@return a description of the item
 */
  public String getDescription() {
    return description;
  }

/**
 *@param newDescription
 *updates the description of an item
 */
  public void setDescription(String newDescription) {
    description = newDescription;
  }

/**
 *@return (Point) the items's location
 */
  public Point getXyLocation() {
    return p;
  }

/**
 *@param newXyLocation
 *updates the new location of an item
 */
  public void setXyLocation(Point newXyLocation) {
    p = newXyLocation;
  }

/**
 *@return (Room) the current room in which the item is in
 */
  public Room getCurrentRoom() {
    return room;
  }

/**
 *@param newCurrentRoom
 *updates the current room the item is in
 */
  public void setCurrentRoom(Room newCurrentRoom) {
    room = newCurrentRoom;
  }

}
