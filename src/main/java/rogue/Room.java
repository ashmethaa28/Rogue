package rogue;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.awt.Point;

/**
 * A room within the dungeon - contains monsters, treasure,
 * doors out, etc.
 */
public class Room {

  private int roomWidth;
  private int roomHeight;
  private int roomId;
  private ArrayList<Item> itemList = new ArrayList();
  private Map<String, Integer> roomDoors = new HashMap(); //might not be needed
  private ArrayList<Door> doorList = new ArrayList();
  private Player player;
  private Character ch;
  private boolean isPlayer;
  private Map<String, Character> displayInfo = new HashMap();
  // private ArrayList<Integer> itemListId = new ArrayList();
/**
 *Default constructor.
 */
  public Room() {

  }

// Required getter and setters below
/**
 *@return (int) the width of the room
 */
  public int getWidth() {
    return roomWidth;
  }

  // public void settingItemListId(int id){
  //   itemListId.add(id);
  // }

  // public ArrayList<Integer> getItemListId(){
  //   return itemListId;
  // }
/**
 *@param newWidth
 *updates the width of the room
 */
  public void setWidth(int newWidth) {
    roomWidth = newWidth;
  }

/**
 *@return (int) the height of the room
 */
  public int getHeight() {
    return roomHeight;
  }

/**
 *@param newHeight
 *updates the height of a room
 */
  public void setHeight(int newHeight) {
    roomHeight = newHeight;
  }

/**
 *@return (int) the room id
 */
  public int getId() {
    return roomId;
  }

/**
 *@param newId
 *updates the id of the room
 */
  public void setId(int newId) {
    roomId = newId;
  }

/**
 *@return (ArrayList) the ArrayList of Item objects in the room
 */
  public ArrayList<Item> getRoomItems() {
    return itemList;
  }

/**
 *@param newRoomItems
 *updates the ArrayList of Item objects in the room
 */
  public void setRoomItems(ArrayList<Item> newRoomItems) {
    itemList = newRoomItems;
  }


/**
 *@return (Player) a Player object
 */
  public Player getPlayer() {
    return player;
  }


/**
 *@param newPlayer
 *updates the player in the room
 */
  public void setPlayer(Player newPlayer) {
    player = newPlayer;
  }


/**
 *@param direction - direction of door
 *@return (int) location of door a certain direction
 */
  public int getDoor(String direction) {
    for (int i = 0; i < doorList.size(); i++) {
      if ((doorList.get(i)).getDirection().equals(direction)) {
        return (doorList.get(i)).getLocation();
      }
    }

    return -1;
  }

/*
direction is one of NSEW
location is a number between 0 and the length of the wall
*/

/**
 *@param direction - direction of door
 *@param location
 *updates the HashMap for the doors
 */
  public void setDoor(String direction, int location) {
    roomDoors.put(direction, location);
  }

/**
 *@return (boolean) if player is in the room
 */
  public boolean isPlayerInRoom() {
    return isPlayer;
  }

/**
 *@return (String) String representation of how the room looks
 *Produces a string that can be printed to produce an ascii rendering of the room and all of its contents
 */
  public String displayRoom() {
    String display = "";

    display = displayRoomWallFloor(display);

    display = displayRoomDoor(display);

    display = displayRoomItem(display);

    if (isPlayerInRoom() == true) {
      display = displayPlayer(display);
    }

    return display;
  }

/**
 * @param thePlayer
 * updates if player is in the room
 */
  public void setPlayerInRoom(boolean thePlayer) {
    isPlayer = thePlayer;
  }


/**
 *@param symbol - symbol for character
 *@return (Character) the Character value used to represent the symbol
 */
  public Character getDisplayCharacter(String symbol) {
    return displayInfo.get(symbol);
  }

/**
 *@param symbol - symbol that represents character
 *@param newDisplayCharacter
 *updates the list of symbols and the Character value use to represent them
 */
  public void setDisplayCharacter(Character newDisplayCharacter, String symbol) {
    displayInfo.put(symbol, newDisplayCharacter);
  }


/**
 *@param display - display of room
 *@return (String) string with the floors and walls in the room
 */
  public String displayRoomWallFloor(String display) {
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        if (y == 0 || y == (getHeight() - 1)) {
          display = display + getDisplayCharacter("NS_WALL");
        } else if (x == 0 || x == (getWidth() - 1)) {
          display = display + getDisplayCharacter("EW_WALL");
        } else {
          display = display + getDisplayCharacter("FLOOR");
        }
      }

      display = display + "\n";
    }

    return display;
  }

/**
 *@param display - display of room
 *@return (String) added the doors to the string
 */
  public String displayRoomDoor(String display) {
    if (getDoor("S") != -1) {
      display = display.substring(0, (getWidth() + 1) * (getHeight() - 1) + getDoor("S")) + getDisplayCharacter("DOOR") + display.substring((getWidth() + 1) * (getHeight() - 1) + getDoor("S") + 1);
    }
    if (getDoor("N") != -1) {
      display = display.substring(0, getDoor("N")) + getDisplayCharacter("DOOR") + display.substring(getDoor("N") + 1);
    }
    if (getDoor("W") != -1) {
      display = display.substring(0, getWidth() * getDoor("W") + getDoor("W")) + getDisplayCharacter("DOOR") + display.substring(getWidth() * getDoor("W") + getDoor("W") + 1);
    }
    if (getDoor("E") != -1) {
      display = display.substring(0, getWidth() * (getDoor("E") + 1) + (getDoor("E") - 1)) + getDisplayCharacter("DOOR") + display.substring(getWidth() * (getDoor("E") + 1) + (getDoor("E") - 1) + 1);
    }

    return display;
  }


/**
 *@param display - display of room
 *@return (String) added the items to the string
 */
  public String displayRoomItem(String display) {
    int index;
    Point p;

    for (int i = 0; i < itemList.size(); i++) {
      Item itemDisplay = itemList.get(i);

      p = itemDisplay.getXyLocation();
      index = (getWidth() + 1) * (int) (p.getY()) + (int) (p.getX());
      display = display.substring(0, index) + itemDisplay.getDisplayCharacter() + display.substring(index + 1);
    }
    return display;
  }


/**
 *@param display - display of room
 *@return (String) added player to the String
 */
  public String displayPlayer(String display) {
    int index;
    Point p;

    System.out.println("why");
    p = player.getXyLocation();
    System.out.println(p);
    index = (getWidth() + 1) * (int)(p.getY()) + (int)(p.getX());
    System.out.println(getWidth());
    display = display.substring(0, index) + player.getDisplayCharacter() + display.substring(index + 1);
    System.out.println("what");

    return display;
  }

  public void addItem(Item toAdd) throws ImpossiblePositionException, NoSuchItemException{
    double x, y;
    int check = 0;

    for (int i = 0; i < itemList.size(); i++){
      x = (itemList.get(i)).getXyLocation().getX();
      y = (itemList.get(i)).getXyLocation().getY();
      if (x == toAdd.getXyLocation().getX() && y == toAdd.getXyLocation().getY()) {
        check = 1;
      }
    }

    if (isPlayerInRoom() == true) {
      x = player.getXyLocation().getX();
      y = player.getXyLocation().getY();
      if (x == toAdd.getXyLocation().getX() && y == toAdd.getXyLocation().getY()) {
        check = 1;
      }
    }

    if (roomWidth - 1 <= toAdd.getXyLocation().getX() || toAdd.getXyLocation().getX() <= 0) {
      check = 1;
    }
    if (roomHeight - 1 <= toAdd.getXyLocation().getY() || toAdd.getXyLocation().getY() <= 0) {
      check = 1;
    }

    if (check == 0) {
      itemList.add(toAdd);
    } else {
      throw new ImpossiblePositionException();
    }
    
  }

  public void addDoor(Door door) {
    doorList.add(door);
  }

  public ArrayList<Door> listDoor() {
    return doorList;
  }

  public boolean verifyRoom() throws NotEnoughDoorsException {
    double x, y, valid;
    valid = 0;
    for (int i = 0; i < itemList.size(); i++) {
      x = (itemList.get(i)).getXyLocation().getX();
      y = (itemList.get(i)).getXyLocation().getY();
      if (roomWidth <= x || x <= 0) {
        valid++;
      } if (roomHeight <= y || y <= 0) {
        valid++;
      }
    }

    try {
      x = player.getXyLocation().getX();
      y = player.getXyLocation().getY();
      if (roomWidth - 1 <= x || x <= 0) {
        valid++;
      } if (roomHeight - 1 <= y || y <= 0) {
        valid++;
      }
    } catch (NullPointerException e) {
    }

    if (doorList.size() == 0) {
      throw new NotEnoughDoorsException();
    }

    for (int i = 0; i < doorList.size(); i++) {
      int temp = (doorList.get(i)).getConnectedRooms().size();
      if(temp != 2) {
        valid++;
        System.out.println(temp);
      }
    }

    if (valid == 0) {
      return true;
    } else {
      return false;
    }
  }

  public void removeItem(Item item) {
    for (int i = 0; i < itemList.size(); i++) {
      if (item.getId() == itemList.get(i).getId()) {
        itemList.remove(i);
      }
    }
  }

}
