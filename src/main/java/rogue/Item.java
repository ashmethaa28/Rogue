package rogue;
import java.awt.Point;

/**
 * A basic Item class; basic functionality for both consumables and equipment
 */
public class Item  {

    private int itemId;
    private String itemName;
    private String itemType;
    private Room room;
    private String description;

    //Constructors
    public Item() {
        setId(null);
        setName(null);
        setType(null);
    }

    public Item(int id, String name, String type, Point xyLocation) {
        setId(id);
        setName(name);
        setType(type);
        //xyLocation
    }
    
    // Getters and setters


    public int getId() {
        return itemId;
       
    }


    public void setId(int id) {
        itemId = id;
    }

s
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
        return null;
        
    }


    public void setDisplayCharacter(Character newDisplayCharacter) {
           
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String newDescription) {
        description = newDescription;
    }


    public Point getXyLocation() {
        return null;
     
    }

    
    public void setXyLocation(Point newXyLocation) {
        
    }


    public Room getCurrentRoom() {
        return room;
        
    }


    public void setCurrentRoom(Room newCurrentRoom) {
        room = newCurrentRoom;
    }
}
