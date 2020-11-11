package rogue;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.awt.Point;

public class Rogue {

  private Player roguePlayer;
  private ArrayList<Room> roomList = new ArrayList();
  private ArrayList<Item> itemList = new ArrayList();
  // private Room gameRoom;
  // private Item gameItem;
  private RogueParser game;
  static char DOWN = 's'; //something like private should be here
  static char UP = 'w';
  static char LEFT = 'a';
  static char RIGHT = 'd';

/**
 *@param theGame
 *updates the RoguePaser
 */
  public Rogue(RogueParser theGame) {
    game = theGame;
  }
/**
 *@param thePlayer
 *updates the player that is being used file
 */
  public void setPlayer(Player thePlayer) {
    roguePlayer = thePlayer;
  }

/**
 *@param filename
 *opens up JSON file to extract information from
 */
  public void setSymbols(String filename) {
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
 *@return (ArrayList) an ArrayList of Room objects that were created from the JSON file
 */
  public ArrayList<Room> getRooms() {
    return roomList;
  }

/**
 *@return (ArrayList)an ArrayList of all the Item objects that were created from the JSON file
 */
  public ArrayList<Item> getItems() {
    return itemList;
  }

/**
 *@return (Player) the Player object that is being used
 */
  public Player getPlayer() {
    return roguePlayer;
  }

/**
 *@param filename
 *opens JSON file to extract information from
 */
  public void createRooms(String filename) {
    Map<String, String> roomMap = new HashMap();
    Map<String, String> itemMap = new HashMap();

    for (int i = 0; i <= game.getNumOfRooms(); i++) {
      roomMap = game.nextRoom();
      addRoom(roomMap);
    }

    for (int n = 0; n <= game.getNumOfRooms(); n++) {
      ArrayList<Door> doorList = roomList.get(n).listDoor();
      for (int i = 0; i < doorList.size(); i++) {
        for (int x = 0; x < roomList.size(); x++) {
          if ((roomList.get(x)).getId() == (doorList.get(i)).getConnectedRoomId()) {
            (doorList.get(i)).connectRoom(roomList.get(x));
          }
        }
      }
    }

    for (int i = 0; i <= game.getNumOfItems(); i++) {
      itemMap = game.nextItem();
      Item item = new Item();
      for (int n = 0; n <= game.getNumOfRooms(); n++) {
        try {
        if (Integer.decode(itemMap.get("room")) == (roomList.get(n)).getId()) {
          addItem(itemMap);
          item = itemList.get(i);
          item.setCurrentRoom(roomList.get(n));

        try {
          (roomList.get(n)).addItem(item);
        } catch (ImpossiblePositionException e) {
          int check = 1;
          Point pTemp;
          Point p;
          while (check == 1) {
            check = 0;
            for (int a = 0; a < itemList.size(); a++) {
              p = itemList.get(a).getXyLocation();
              if (a != i){
                if (p.getX() == item.getXyLocation().getX() && p.getY() == item.getXyLocation().getY()) {
                  if (item.getXyLocation().getX() == ((roomList.get(n)).getWidth() - 2)) {
                    pTemp = new Point((int)item.getXyLocation().getX() - 1, (int)item.getXyLocation().getY());
                  } else {
                    pTemp = new Point((int)item.getXyLocation().getX() + 1, (int)item.getXyLocation().getY());
                  }
                  if (item.getXyLocation().getY() == ((roomList.get(n)).getHeight() - 2)) {
                    pTemp = new Point((int)item.getXyLocation().getX(), (int)item.getXyLocation().getY() - 1);
                  } else {
                    pTemp = new Point((int)item.getXyLocation().getX(), (int)item.getXyLocation().getY() + 1);
                  }
                  item.setXyLocation(pTemp);
                  a = -1;
                }
              }
            }

            p = item.getXyLocation();
            if ((roomList.get(n)).isPlayerInRoom() == true && p.getY() == (roomList.get(n)).getPlayer().getXyLocation().getY() && p.getX() == (roomList.get(n)).getPlayer().getXyLocation().getX()) {
              if (p.getX() == ((roomList.get(n)).getWidth() - 2)) {
                p = new Point((int)p.getX() - 1, (int)p.getY());
              } else {
                p = new Point((int)p.getX() + 1, (int)p.getY());
              }
              if (p.getY() == ((roomList.get(n)).getHeight() - 2)) {
                p = new Point((int)p.getX(), (int)p.getY() - 1);
              } else {
              p = new Point((int)p.getX(), (int)p.getY() + 1);
              }
              (roomList.get(n)).getPlayer().setXyLocation(p);
              check = 1;
            }

            p = item.getXyLocation();
            if ((roomList.get(n)).getHeight() - 1 <= p.getY() || p.getY() <= 0) {
              p = new Point((int)p.getX(), 1);
              check = 1;
            }
            if ((roomList.get(n)).getWidth() - 1 <= p.getX() || p.getX() <= 0) {
              p = new Point(1, (int)p.getY());
              check = 1;
            }
            item.setXyLocation(p);
          }
          try {
            (roomList.get(n)).addItem(item);
          } catch (ImpossiblePositionException ex) {
          } catch (NoSuchItemException ex) {
          }
        } catch (NoSuchItemException e) {
          // (roomList.get(n)).removeItem(item);
        }
      } 
      } catch (NullPointerException ei) {
      } 
    }
    }
    for (int i = 0; i < roomList.size(); i++) {
      try{
        System.out.println(roomList.get(i).verifyRoom());
      } catch (NotEnoughDoorsException e) {
      	//ADD CODE MAYBE?
      }
    }
  }


// /**
//  *@return (String) a String with all the rooms stored in it
//  */
//   public String displayAll() {
//     //creates a string that displays all the rooms in the dungeon
//     String display = "";

//     for (int i = 0; i < roomList.size(); i++) {
//       display = display + "\nROOM " + (i + 1) + "\n";
//       display = display + (roomList.get(i)).displayRoom();
//     }

//     return display;
//   }

  public String makeMove(char input) throws InvalidMoveException {
    for (int i = 0; i < roomList.size(); i++){
      if (roomList.get(i).isPlayerInRoom()) {
        Player player = roomList.get(i).getPlayer();
        Point p;
        if (input == 'w') {
          p = new Point((int)player.getXyLocation().getX(), (int)player.getXyLocation().getY() - 1);
          player.setXyLocation(p);
          roomList.get(i).setPlayer(player);
          try {
            if (!(roomList.get(i).verifyRoom())) {
              if(!(switchRooms(p, roomList.get(i).listDoor(), i))){
                p = new Point((int)player.getXyLocation().getX(), (int)player.getXyLocation().getY() + 1);
                player.setXyLocation(p);
                roomList.get(i).setPlayer(player);
                throw new InvalidMoveException();
              } else {
                removeItemInPosition(p, roomList.get(i).getRoomItems(), i);
                return "Valid Move";
              }
            } else {
              removeItemInPosition(p, roomList.get(i).getRoomItems(), i);
              return "Valid Move";
            }
          } catch (NotEnoughDoorsException e) {
          }
        } else if (input == 's') {
          p = new Point((int)player.getXyLocation().getX(), (int)player.getXyLocation().getY() + 1);
          player.setXyLocation(p);
          roomList.get(i).setPlayer(player);
          try {
            if (!(roomList.get(i).verifyRoom())) {
              p = new Point((int)player.getXyLocation().getX(), (int)player.getXyLocation().getY() - 1);
              player.setXyLocation(p);
              roomList.get(i).setPlayer(player);
              throw new InvalidMoveException();
            } else {
              removeItemInPosition(p, roomList.get(i).getRoomItems(), i);
              return "Valid Move";
            }
          } catch (NotEnoughDoorsException e) {
          }
        } else if (input == 'd') {
          p = new Point((int)player.getXyLocation().getX() + 1, (int)player.getXyLocation().getY());
          player.setXyLocation(p);
          roomList.get(i).setPlayer(player);
          try {
            if (!(roomList.get(i).verifyRoom())) {
              p = new Point((int)player.getXyLocation().getX() - 1, (int)player.getXyLocation().getY());
              player.setXyLocation(p);
              roomList.get(i).setPlayer(player);
              throw new InvalidMoveException();
            } else {
              removeItemInPosition(p, roomList.get(i).getRoomItems(), i);
              return "Valid Move";
            }
          } catch (NotEnoughDoorsException e) {
          }
        } else if (input == 'a') {
          p = new Point((int)player.getXyLocation().getX() - 1, (int)player.getXyLocation().getY());
          player.setXyLocation(p);
          roomList.get(i).setPlayer(player);
          try {
            if (!(roomList.get(i).verifyRoom())) {
              p = new Point((int)player.getXyLocation().getX() + 1, (int)player.getXyLocation().getY());
              player.setXyLocation(p);
              roomList.get(i).setPlayer(player);
              throw new InvalidMoveException();
            } else {
              removeItemInPosition(p, roomList.get(i).getRoomItems(), i);
              return "Valid Move";
            }
          } catch (NotEnoughDoorsException e) {
          }
        }
      }
    }
    throw new InvalidMoveException();
  }

  public String getNextDisplay() {
    for (int i = 0; i < roomList.size(); i++) {
      if (roomList.get(i).isPlayerInRoom()) {
        return roomList.get(i).displayRoom();
      }
    }
    return null;
  }

  public void addRoom(Map<String, String> toAdd) {
    Room room = new Room();
    room.setWidth(Integer.decode(toAdd.get("width")));
    room.setHeight(Integer.decode(toAdd.get("height")));
    room.setId(Integer.decode(toAdd.get("id")));
    if (toAdd.get("start") == "true") {
      room.setPlayerInRoom(true);
      room.setPlayer(roguePlayer);
    } else {
      room.setPlayerInRoom(false);
    }
    if (Integer.decode(toAdd.get("N")) != -1) {
      Door door = new Door("N", Integer.decode(toAdd.get("N")), Integer.decode(toAdd.get("Ncon")), room);
      room.addDoor(door);
    }
    if (Integer.decode(toAdd.get("S")) != -1) {
      Door door = new Door("S", Integer.decode(toAdd.get("S")), Integer.decode(toAdd.get("Scon")), room);
      room.addDoor(door);
    }
    if (Integer.decode(toAdd.get("W")) != -1) {
      Door door = new Door("W", Integer.decode(toAdd.get("W")), Integer.decode(toAdd.get("Wcon")), room);
      room.addDoor(door);
    }
    if (Integer.decode(toAdd.get("E")) != -1) {
      Door door = new Door("E", Integer.decode(toAdd.get("E")), Integer.decode(toAdd.get("Econ")),room);
      room.addDoor(door);
    }

    // room.settingItemListId(Integer.decode(roomMap.get("loot")));
    // add MORE CODE FOR EXCEPTION
    roomList.add(room);
  }

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

  public void removeItemInPosition(Point p, ArrayList<Item> itemTempList, int i){
    for (int n = 0; n < itemTempList.size(); n++) {
      if (p.getX() == itemTempList.get(n).getXyLocation().getX() && p.getY() == itemTempList.get(n).getXyLocation().getY()){
        roomList.get(i).removeItem(itemTempList.get(n));
      }
    }
  }

  public boolean switchRooms(Point p, ArrayList<Door> doors, int i){
    for (int n = 0; n < doors.size(); n++) {
      if (p.getY() == 0 && doors.get(n).getDirection() == "N" && p.getX() == doors.get(n).getLocation()) {
        roomList.get(i).setPlayerInRoom(false);
        Room room = doors.get(n).getOtherRoom(roomList.get(i));
        for(int y = 0; y < roomList.size(); y++){
          if(roomList.get(y).getId() == room.getId()){
            i = y;
            roomList.get(i).setPlayerInRoom(true);
          }
        }
        for (int y = 0; y < room.listDoor().size(); y++) {
          if (roomList.get(i).listDoor().get(y).getDirection() == "S") {
            p = new Point(roomList.get(i).listDoor().get(y).getLocation(), roomList.get(i).getHeight() - 2);
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

}
