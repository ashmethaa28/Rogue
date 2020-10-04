package rogue;
import java.awt.Point;

/**
 * A basic Item class; basic functionality for both consumables and equipment
 */
public class Item  {

  private int itemId;
  private String itemName;
  private String itemType;
  private Room room = new Room();
  private String description;
  private Character ch;
  private Point p;

  //Constructors
  public Item() {
  }

  public Item(int id, String name, String type, Point xyLocation) {
    setId(id);
    setName(name);
    setType(type);
    setXyLocation(xyLocation);
  }
    
// Getters and setters


  public int getId() {
    return itemId; 
  }

  public void setId(int id) {
    itemId = id;
  }

  public String getName() {
    return itemName;
  }


  public void setName(String name) {
    itemName = name;
  }


  public String getType() {
    return itemType;
  }


  public void setType(String type) {
    itemType = type;
  }

    
  public Character getDisplayCharacter() {
    return ch;
  }


  public void setDisplayCharacter(Character newDisplayCharacter) {
    ch = newDisplayCharacter;
  }


  public String getDescription() {
    return description;
  }


  public void setDescription(String newDescription) {
    description = newDescription;
  }


  public Point getXyLocation() {
    return p;
     
  }

   
  public void setXyLocation(Point newXyLocation) {
    p = newXyLocation;
  }


  public Room getCurrentRoom() {
    return room;
  }


  public void setCurrentRoom(Room newCurrentRoom) {
    room = newCurrentRoom;
  }
}
