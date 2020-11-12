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
  private HashMap<String, Door> doorList = new HashMap();
  private Player player;
//  private Character ch;
  private boolean isPlayer;
  private Map<String, Character> displayInfo = new HashMap();
  private Rogue game;

/**
 *Default constructor.
 */
  public Room() {

  }

// Required getter and setters below
/**
 * Gets the width of the room.
 * @return (int) the width
 */
  public int getWidth() {
    return roomWidth;
  }

/**
 * Sets the Rogue object.
 * @param theGame - Rogue object used in the game
 */
  public void setGame(Rogue theGame) {
    game = theGame;
  }

/**
 * Sets the width of the room.
 * @param newWidth of the room
 */
  public void setWidth(int newWidth) {
    roomWidth = newWidth;
  }

/**
 * Get the height of the room.
 * @return (int) the height
 */
  public int getHeight() {
    return roomHeight;
  }

/**
 * Sets the height of the room.
 * @param newHeight of the room
 */
  public void setHeight(int newHeight) {
    roomHeight = newHeight;
  }

/**
 * Gets the id of the room.
 * @return (int) the room id
 */
  public int getId() {
    return roomId;
  }

/**
 * Sets the id of the room.
 * @param newId of the room
 */
  public void setId(int newId) {
    roomId = newId;
  }

/**
 * Gets a list of items that are in the room.
 * @return (ArrayList) list of items
 */
  public ArrayList<Item> getRoomItems() {
    return itemList;
  }

/**
 * Sets a list of items that are in the room.
 * @param newRoomItems - list of items in this room
 */
  public void setRoomItems(ArrayList<Item> newRoomItems) {
    itemList = newRoomItems;
  }


/**
 * Player in this room.
 * @return (Player) in the room
 */
  public Player getPlayer() {
    return player;
  }


/**
 * Updates player in the room.
 * @param newPlayer that's in the room
 */
  public void setPlayer(Player newPlayer) {
    player = newPlayer;
  }


  private int getDoor(String direction) {
    for (String d : doorList.keySet()) {
      if (d.equals(direction)) {
        return doorList.get(d).getLocation();
      }
    }

    return -1;
  }

/**
 * Tells whether or not the player in this room.
 * @return (boolean) if player is in the room
 */
  public boolean isPlayerInRoom() {
    return isPlayer;
  }

/**
 *Produces a string that can be printed to produce an ascii rendering of the room and all of its contents.
 *@return (String) String representation of how the room looks
 */
  public String displayRoom() {
    String display = "";

    display = displayRoomWallFloor(display);

    display = displayRoomDoor(display);

    display = displayRoomItem(display);

    if (isPlayer) {
      display = displayPlayer(display);
    }

    return display;
  }

/**
 * Set if player is in the room.
 * @param areThey - tells whether or not player is in the room
 */
  public void setPlayerInRoom(boolean areThey) {
    isPlayer = areThey;
  }


/**
 * Gets display character for symbols.
 * @param symbol - symbol name
 * @return (Character) the Character value used to represent the symbol
 */
  public Character getDisplayCharacter(String symbol) {
    return displayInfo.get(symbol);
  }

/**
 * Updates the list of symbols and the Character value use to represent them.
 * @param symbol name
 * @param newDisplayCharacter - Character value used to represent symbol
 */
  public void setDisplayCharacter(Character newDisplayCharacter, String symbol) {
    displayInfo.put(symbol, newDisplayCharacter);
  }

  private String displayRoomWallFloor(String display) {
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

  private String displayRoomDoor(String display) {
    String sub;
    if (getDoor("S") != -1) {
      sub = getDisplayCharacter("DOOR") + display.substring((getWidth() + 1) * (getHeight() - 1) + getDoor("S") + 1);
      display = display.substring(0, (getWidth() + 1) * (getHeight() - 1) + getDoor("S")) + sub;
    }
    if (getDoor("N") != -1) {
      display = display.substring(0, getDoor("N")) + getDisplayCharacter("DOOR") + display.substring(getDoor("N") + 1);
    }
    if (getDoor("W") != -1) {
      sub = getDisplayCharacter("DOOR") + display.substring(getWidth() * getDoor("W") + getDoor("W") + 1);
      display = display.substring(0, getWidth() * getDoor("W") + getDoor("W")) + sub;
    }
    if (getDoor("E") != -1) {
      sub = getDisplayCharacter("DOOR") + display.substring(getWidth() * (getDoor("E") + 1) + (getDoor("E") - 1) + 1);
      display = display.substring(0, getWidth() * (getDoor("E") + 1) + (getDoor("E") - 1)) + sub;
    }

    return display;
  }

  private String displayRoomItem(String display) {
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

  private String displayPlayer(String display) {
    Point p = player.getXyLocation();
    int index = (getWidth() + 1) * (int) (p.getY()) + (int) (p.getX());
    display = display.substring(0, index) + player.getDisplayCharacter() + display.substring(index + 1);

    return display;
  }

/**
 * Adds item to the list of items in the room.
 * @param toAdd - item needed to be added into the list
 * @throws ImpossiblePositionException if item is in position that already occupied or if out the room boundary
 * @throws NoSuchItemException if item is present in room but doesn't exist in list of items
 */
  public void addItem(Item toAdd) throws ImpossiblePositionException, NoSuchItemException {
    double x;
    double y;
    int check = 0;

    for (int i = 0; i < itemList.size(); i++) {
      x = (itemList.get(i)).getXyLocation().getX();
      y = (itemList.get(i)).getXyLocation().getY();
      if (x == toAdd.getXyLocation().getX() && y == toAdd.getXyLocation().getY()) {
        check = 1;
      }
    }

    try {
      if (isPlayer) {
        x = player.getXyLocation().getX();
        y = player.getXyLocation().getY();
        if (x == toAdd.getXyLocation().getX() && y == toAdd.getXyLocation().getY()) {
          check = 1;
        }
      }
    } catch (NullPointerException e) {
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

    if (!(game.containsItem(toAdd))) {
      throw new NoSuchItemException();
    }
  }

/**
 * Adds door that is in this room.
 * @param direction of the door
 * @param door that needs to be added
 */
  public void addDoor(String direction, Door door) {
    doorList.put(direction, door);
  }

/**
 * Gets the list of doors in this room.
 * @return (HashMap) list of doors and the direction they are at
 */
  public HashMap<String, Door> listDoor() {
    return doorList;
  }

/**
 * Verifies that everything in the room is in a valid position.
 * @return (boolean) tells whether or not everything is in a valid position
 * @throws NotEnoughDoorsException if there are no doors in the room
 */
  public boolean verifyRoom() throws NotEnoughDoorsException {
    double x;
    double y;
    int valid;
    valid = 0;
    for (int i = 0; i < itemList.size(); i++) {
      x = (itemList.get(i)).getXyLocation().getX();
      y = (itemList.get(i)).getXyLocation().getY();
      if (roomWidth <= x || x <= 0) {
        valid++;
      }
      if (roomHeight <= y || y <= 0) {
        valid++;
      }
    }

    try {
      x = player.getXyLocation().getX();
      y = player.getXyLocation().getY();
      if (roomWidth - 1 <= x || x <= 0) {
        valid++;
      }
      if (roomHeight - 1 <= y || y <= 0) {
        valid++;
      }
    } catch (NullPointerException e) {
    }

    if (doorList.size() == 0) {
      throw new NotEnoughDoorsException();
    }

    for (String direction: doorList.keySet()) {
      int temp = (doorList.get(direction)).getConnectedRooms().size();
      if (temp != 2) {
        valid++;
      }
    }

    if (valid == 0) {
      return true;
    }
     return false;
  }

/**
 * Removes item in the room.
 * @param item that needs to be removed from room
 */
  public void removeItem(Item item) {
    for (int i = 0; i < itemList.size(); i++) {
      if (item.getId() == itemList.get(i).getId()) {
        itemList.remove(i);
      }
    }
  }

}
