package rogue;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.awt.Point;

public class Rogue {

  private Player roguePlayer;
  private ArrayList<Room> roomList = new ArrayList();
  private ArrayList<Item> itemList = new ArrayList();
  private RogueParser game;
  public static final char DOWN = 's';
  public static final char UP = 'w';
  public static final char LEFT = 'a';
  public static final char RIGHT = 'd';
  private static final int MAXDOOR = 4;

/**
 * Constructor.
 */
  public Rogue() {
  }

/**
 * Constructor.
 * @param theGame - RoguePaser object used to extract information from file
 */
  public Rogue(RogueParser theGame) {
    game = theGame;
    createRooms();
  }

/**
 * Sets player and sets player in Room.
 * @param thePlayer - user playing the game
 */
  public void setPlayer(Player thePlayer) {
    roguePlayer.setName(thePlayer.getName());
    roguePlayer.setXyLocation(thePlayer.getXyLocation());
    for (int i = 0; i < roomList.size(); i++) {
      roomList.get(i).setPlayer(roguePlayer);
    }
  }

  private void setSymbols() {
    try {

      for (int i = 0; i < itemList.size(); i++) {
        (itemList.get(i)).setDisplayCharacter(game.getSymbol((itemList.get(i)).getType().toUpperCase()));
      }

      roguePlayer.setDisplayCharacter(game.getSymbol("PLAYER"));

      for (int i = 0; i < roomList.size(); i++) {
        (roomList.get(i)).setDisplayCharacter(game.getSymbol("DOOR"), "DOOR");
        (roomList.get(i)).setDisplayCharacter(game.getSymbol("FLOOR"), "FLOOR");
        (roomList.get(i)).setDisplayCharacter(game.getSymbol("NS_WALL"), "NS_WALL");
        (roomList.get(i)).setDisplayCharacter(game.getSymbol("EW_WALL"), "EW_WALL");
      }
    } catch (NullPointerException e) {
    }
  }

/**
 * Gets all the rooms in the game.
 * @return (ArrayList) list of all the rooms in the game
 */
  public ArrayList<Room> getRooms() {
    return roomList;
  }

/**
 * Gets all the Items in the game.
 * @return (ArrayList) list of all the items in the game
 */
  public ArrayList<Item> getItems() {
    return itemList;
  }

/**
 *@return (Player) the player playing the game
 */
  public Player getPlayer() {
    return roguePlayer;
  }

  private void createRooms() {
    Map<String, String> roomMap = new HashMap();
    Map<String, String> itemMap = new HashMap();

    for (int i = 0; i <= game.getNumOfRooms(); i++) {
      roomMap = game.nextRoom();
      addRoom(roomMap);
    }

    createDoors();

    for (int i = 0; i <= game.getNumOfItems(); i++) {
      itemMap = game.nextItem();
      for (int n = 0; n <= game.getNumOfRooms(); n++) {
        try {
          if (Integer.decode(itemMap.get("room")) == (roomList.get(n)).getId()) {
            addItem(itemMap);
            Item item = itemList.get(i);
            item.setCurrentRoom(roomList.get(n));
            try {
              (roomList.get(n)).addItem(item);
            } catch (ImpossiblePositionException e) {
              int check = 1;
              while (check == 1) {
                check = invalidItemPosition(item, n, i);
              }
              try {
                (roomList.get(n)).addItem(item);
              } catch (ImpossiblePositionException ex) {
              } catch (NoSuchItemException ex) {
                (roomList.get(n)).removeItem(item);
              }
            } catch (NoSuchItemException e) {
              (roomList.get(n)).removeItem(item);
            }
          }
        } catch (NullPointerException ei) {
        }
      }
    }
    for (int i = 0; i < roomList.size(); i++) {
      try {
        boolean verify = roomList.get(i).verifyRoom();
      } catch (NotEnoughDoorsException e) {
        addingDoorToRoom(i);
      }
    }
    roguePlayer = new Player();
    setSymbols();
  }

/**
 * Checks if move is valid, and if not throws an Exception.
 * @param input - the key that the user pressed
 * @return (String) - tells what the user did when pressing the key
 * @throws InvalidMoveException if input isn't valid
 */
  public String makeMove(char input) throws InvalidMoveException {
    int x;
    int y;
    for (int i = 0; i < roomList.size(); i++) {
      if (roomList.get(i).isPlayerInRoom()) {
        Player player = roomList.get(i).getPlayer();
        if (input == 'w' || input == 's' || input == 'd' || input == 'a') {
          x = (int) player.getXyLocation().getX() + offsetForDoors(input, 'x');
          y = (int) player.getXyLocation().getY() + offsetForDoors(input, 'y');
          Point p = new Point(x, y);
          player.setXyLocation(p);
          roomList.get(i).setPlayer(player);
          try {
            if (!(roomList.get(i).verifyRoom())) {
              if (!(switchRooms(p, roomList.get(i).listDoor(), i))) {
                x = (int) player.getXyLocation().getX() - offsetForDoors(input, 'x');
                y = (int) player.getXyLocation().getY() - offsetForDoors(input, 'y');
                p = new Point(x, y);
                player.setXyLocation(p);
                roomList.get(i).setPlayer(player);
                throw new InvalidMoveException();
              } else {
                removeItemInPosition(p, roomList.get(i).getRoomItems(), i);
                return validMove(input);
              }
            } else {
              removeItemInPosition(p, roomList.get(i).getRoomItems(), i);
              return validMove(input);
            }
          } catch (NotEnoughDoorsException e) {
          }
        }
      }
    }
    throw new InvalidMoveException();
  }

/**
 * New Display moving player according to the user input.
 * @return (String) - the new display
 */
  public String getNextDisplay() {
    for (int i = 0; i < roomList.size(); i++) {
      if (roomList.get(i).isPlayerInRoom()) {
        return roomList.get(i).displayRoom();
      }
    }
    return null;
  }

/**
 * Extracts information from toAdd and creates rooms.
 * @param toAdd - contains information from JSON file about a room
 */
  public void addRoom(Map<String, String> toAdd) {
    Room room = new Room();
    room.setWidth(Integer.decode(toAdd.get("width")));
    room.setHeight(Integer.decode(toAdd.get("height")));
    room.setId(Integer.decode(toAdd.get("id")));
    if (toAdd.get("start") == "true") {
      room.setPlayerInRoom(true);
    } else {
      room.setPlayerInRoom(false);
    }
    if (Integer.decode(toAdd.get("N")) != -1) {
      Door door = new Door("N", Integer.decode(toAdd.get("N")), Integer.decode(toAdd.get("Ncon")), room);
      room.addDoor("N", door);
    }
    if (Integer.decode(toAdd.get("S")) != -1) {
      Door door = new Door("S", Integer.decode(toAdd.get("S")), Integer.decode(toAdd.get("Scon")), room);
      room.addDoor("S", door);
    }
    if (Integer.decode(toAdd.get("W")) != -1) {
      Door door = new Door("W", Integer.decode(toAdd.get("W")), Integer.decode(toAdd.get("Wcon")), room);
      room.addDoor("W", door);
    }
    if (Integer.decode(toAdd.get("E")) != -1) {
      Door door = new Door("E", Integer.decode(toAdd.get("E")), Integer.decode(toAdd.get("Econ")), room);
      room.addDoor("E", door);
    }

    room.setGame(this);
    roomList.add(room);
  }

/**
 * Extracts information from toAdd and creates Items.
 * @param toAdd - contains information from JSON file about an item
 */
  public void addItem(Map<String, String> toAdd) {
    Item item = new Item();
    item.setId(Integer.decode(toAdd.get("id")));
    item.setName(toAdd.get("name"));
    item.setType(toAdd.get("type"));
    item.setDescription(toAdd.get("description"));

    int x = Integer.decode(toAdd.get("x"));
    int y = Integer.decode(toAdd.get("y"));
    Point p = new Point(x, y);
    item.setXyLocation(p);

    itemList.add(item);
  }

  private void removeItemInPosition(Point p, ArrayList<Item> itemTempList, int i) {
    double x;
    double y;
    for (int n = 0; n < itemTempList.size(); n++) {
      x = itemTempList.get(n).getXyLocation().getX();
      y = itemTempList.get(n).getXyLocation().getY();
      if (p.getX() == x && p.getY() == y) {
        roomList.get(i).removeItem(itemTempList.get(n));
      }
    }
  }

  private boolean switchRooms(Point p, HashMap<String, Door> doors, int i) {
    for (String dir : doors.keySet()) {
      if (isDoorInLocation(doors.get(dir), p, roomList.get(i))) {
        roomList.get(i).setPlayerInRoom(false);
        Room room = doors.get(dir).getOtherRoom(roomList.get(i));
        for (int y = 0; y < roomList.size(); y++) {
          if (roomList.get(y).getId() == room.getId()) {
            i = y;
            roomList.get(i).setPlayerInRoom(true);
          }
        }
        HashMap<String, Door> doorList = room.listDoor();
        for (String direction : doorList.keySet()) {
          if (direction == oppositeDirection(dir)) {
            p = getNewPlayerLocation(roomList.get(i), roomList.get(i).listDoor().get(direction), dir);
            Player player = roomList.get(i).getPlayer();
            player.setXyLocation(p);
            roomList.get(i).setPlayer(player);
            return true;
          }
        }
      }
    }
    return false;
  }

  private int offsetForDoors(char direction, char xy) {
    if (direction == 'w' && xy == 'y') {
      return -1;
    } else if (direction == 'a' && xy == 'x') {
      return -1;
    } else if (direction == 's' && xy == 'y') {
      return 1;
    } else if (direction == 'd' && xy == 'x') {
      return 1;
    }

    return 0;
  }

  private boolean isDoorInLocation(Door door, Point p, Room room) {
    if (p.getY() == 0 && door.getDirection() == "N" && p.getX() == door.getLocation()) {
      return true;
    } else if (p.getY() == room.getHeight() - 1 && door.getDirection() == "S" && p.getX() == door.getLocation()) {
      return true;
    } else if (p.getY() == door.getLocation() && door.getDirection() == "E" && p.getX() == room.getWidth() - 1) {
      return true;
    } else if (p.getY() == door.getLocation() && door.getDirection() == "W" && p.getX() == 0) {
      return true;
    }

    return false;
  }

  private String oppositeDirection(String direction) {
    if (direction == "N") {
      return "S";
    } else if (direction == "S") {
      return "N";
    } else if (direction == "E") {
      return "W";
    } else {
      return "E";
    }
  }

  private Point getNewPlayerLocation(Room room, Door door, String direction) {
    Point p;
    if (direction == "N") {
      p = new Point(door.getLocation(), room.getHeight() - 2);
    } else if (direction == "S") {
      p = new Point(door.getLocation(), 1);
    } else if (direction == "E") {
      p = new Point(1, door.getLocation());
    } else {
      p = new Point(room.getWidth() - 2, door.getLocation());
    }
    return p;
  }

  private String validMove(char input) {
    if (input == 'a') {
      return "Moved to the right";
    } else if (input == 'd') {
      return "Moved to the left";
    } else if (input == 's') {
      return "Moved down";
    } else {
      return "Moved up";
    }
  }

  private int invalidItemPosition(Item item, int n, int i) {
    int check = 0;
    Point pTemp;
    Point p;
    for (int a = 0; a < itemList.size(); a++) {
      p = itemList.get(a).getXyLocation();
      if (a != i) {
        if (p.getX() == item.getXyLocation().getX() && p.getY() == item.getXyLocation().getY()) {
          pTemp = moveObjectToValidPosition(n, p);
          item.setXyLocation(pTemp);
          a = -1;
        }
      }
    }

    p = item.getXyLocation();
    double y = (roomList.get(n)).getPlayer().getXyLocation().getY();
    double x = (roomList.get(n)).getPlayer().getXyLocation().getX();
    if ((roomList.get(n)).isPlayerInRoom() && p.getY() == y && p.getX() == x) {
      p = moveObjectToValidPosition(n, p);
      (roomList.get(n)).getPlayer().setXyLocation(p);
      check = 1;
    }

    p = item.getXyLocation();
    if ((roomList.get(n)).getHeight() - 1 <= p.getY() || p.getY() <= 0) {
      p = new Point((int) p.getX(), 1);
      check = 1;
    }
    if ((roomList.get(n)).getWidth() - 1 <= p.getX() || p.getX() <= 0) {
      p = new Point(1, (int) p.getY());
      check = 1;
    }

    item.setXyLocation(p);
    if (check == 1) {
      return 1;
    } else {
      return 0;
    }
  }

  private String noDoorOnWall(HashMap<String, Door> door) {
    int n = 0;
    int w = 0;
    int s = 0;
    int e = 0;
    for (String direction : door.keySet()) {
      if (direction == "N") {
        n++;
      } else if (direction == "E") {
        e++;
      } else if (direction == "S") {
        s++;
      } else {
        w++;
      }
    }

    if (n == 0) {
      return "N";
    } else if (e == 0) {
      return "E";
    } else if (s == 0) {
      return "S";
    } else {
      return "W";
    }
  }

  private void createDoors() {
    for (int n = 0; n <= game.getNumOfRooms(); n++) {
      HashMap<String, Door> doorList = roomList.get(n).listDoor();
      for (String direction : doorList.keySet()) {
        for (int x = 0; x < roomList.size(); x++) {
          if ((roomList.get(x)).getId() == (doorList.get(direction)).getConnectedRoomId()) {
            (doorList.get(direction)).connectRoom(roomList.get(x));
          }
        }
      }
    }
  }

  private void addingDoorToRoom(int i) {
    for (int n = 0; n < roomList.size(); n++) {
      if (i != n) {
        if (roomList.get(n).listDoor().size() < MAXDOOR) {
          String  direction = noDoorOnWall(roomList.get(n).listDoor());
          Door door = new Door(direction, 1, roomList.get(n));
          door.connectRoom(roomList.get(i));
          roomList.get(n).addDoor(direction, door);
          door = new Door(oppositeDirection(direction), 1, roomList.get(i));
          door.connectRoom(roomList.get(n));
          roomList.get(i).addDoor(oppositeDirection(direction), door);
        }
      }
    }
  }

/**
 * Checks if item is in itemList.
 * @param item - item that needs to be checked
 * @return (boolean) tells if item is in list
 */
  public boolean containsItem(Item item) {
    for (int i = 0; i < itemList.size(); i++) {
      if (itemList.get(i).getId() == item.getId()) {
        return true;
      }
    }
    return false;
  }

  private Point moveObjectToValidPosition(int n, Point p) {
    if (p.getX() == ((roomList.get(n)).getWidth() - 2)) {
      p = new Point((int) p.getX() - 1, (int) p.getY());
    } else {
      p = new Point((int) p.getX() + 1, (int) p.getY());
    }
    if (p.getY() == ((roomList.get(n)).getHeight() - 2)) {
      p = new Point((int) p.getX(), (int) p.getY() - 1);
    } else {
      p = new Point((int) p.getX(), (int) p.getY() + 1);
    }

    return p;
  }
}
