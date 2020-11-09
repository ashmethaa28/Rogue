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
      Room room = new Room();
      roomMap = game.nextRoom();

      room.setWidth(Integer.decode(roomMap.get("width")));
      room.setHeight(Integer.decode(roomMap.get("height")));
      room.setId(Integer.decode(roomMap.get("id")));
      if (roomMap.get("start") == "true") {
        room.setPlayerInRoom(true);
      } else {
        room.setPlayerInRoom(false);
      }

      if (Integer.decode(roomMap.get("N")) != -1) {
        Door door = new Door("N", Integer.decode(roomMap.get("N")), Integer.decode(roomMap.get("Ncon")));
        room.addDoor(door);
      }
      if (Integer.decode(roomMap.get("S")) != -1) {
        Door door = new Door("S", Integer.decode(roomMap.get("S")), Integer.decode(roomMap.get("Scon")));
        room.addDoor(door);
      }
      if (Integer.decode(roomMap.get("W")) != -1) {
        Door door = new Door("W", Integer.decode(roomMap.get("W")), Integer.decode(roomMap.get("Wcon")));
        room.addDoor(door);
      }
      if (Integer.decode(roomMap.get("E")) != -1) {
        Door door = new Door("E", Integer.decode(roomMap.get("E")), Integer.decode(roomMap.get("Econ")));
        room.addDoor(door);
      }

      roomList.add(room);
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
      Item item = new Item();
      itemMap = game.nextItem();
      for (int n = 0; n <= game.getNumOfRooms(); n++) {
        try {
          if (Integer.decode(itemMap.get("room")) == (roomList.get(n)).getId()) {
            item.setId(Integer.decode(itemMap.get("id")));
            item.setName(itemMap.get("name"));
            item.setType(itemMap.get("type"));
            item.setDescription(itemMap.get("description"));
            item.setCurrentRoom(roomList.get(n));

            int x = Integer.decode(itemMap.get("x"));
            int y = Integer.decode(itemMap.get("y"));
            Point p = new Point(x, y);
            item.setXyLocation(p);

            (roomList.get(n)).addItem(item);
            itemList.add(item);
            //(roomList.get(n)).setRoomItems(itemList);
          }
        } catch (NullPointerException e) {

        }
      }
    }

  }

/**
 *@return (String) a String with all the rooms stored in it
 */
  public String displayAll() {
    //creates a string that displays all the rooms in the dungeon
    String display = "";

    for (int i = 0; i < roomList.size(); i++) {
      display = display + "\nROOM " + (i + 1) + "\n";
      display = display + (roomList.get(i)).displayRoom();
    }

    return display;
  }

}
