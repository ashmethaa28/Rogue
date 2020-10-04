package rogue;

import java.util.ArrayList;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.awt.Point;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Rogue{

  private Player roguePlayer;
  private ArrayList<Room> roomList = new ArrayList();
  private static ArrayList<Item> itemList = new ArrayList();
  private static Room gameRoom;
  private Item gameItem;

/**
 *@param thePlayer
 *updates the player that is being used file
 */
  public void setPlayer(Player thePlayer){
    roguePlayer = thePlayer;
  }


/**
 *@param filename
 *opens up JSON file to extract information from
 */
  public void setSymbols(String filename){
    JSONParser parser = new JSONParser();

    try {
      Object symbolObj = parser.parse(new FileReader(filename));
      JSONObject symbolsJSON = (JSONObject) symbolObj;

      for (Object objSymbols : (JSONArray) symbolsJSON.get("symbols")){
        JSONObject jsonSymbol = (JSONObject)objSymbols;
          
        String name = jsonSymbol.get("name").toString();
        String symbols = jsonSymbol.get("symbol").toString();

        extractSymbols(name, symbols);
      }
    } catch(FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
       e.printStackTrace();
    }
  }


/**
 *@return (ArrayList<Room>) an ArrayList of Room objects that were created from the JSON file
 */
  public ArrayList<Room> getRooms(){
    return roomList;
  }


/**
 *@return (ArrayList<Item>)an ArrayList of all the Item objects that were created from the JSON file
 */
  public ArrayList<Item> getItems(){
    return itemList;
  }


/**
 *@return (Player) the Player object that is being used
 */
  public Player getPlayer(){
    return roguePlayer;
  }


/**
 *@param filename
 *opens JSON file to extract information from
 */
  public void createRooms(String filename){
    int once = 0;

    JSONParser parser = new JSONParser();

    try {
      Object objRoom = parser.parse(new FileReader(filename));
      JSONObject roomJSON = (JSONObject) objRoom;

      for(Object object : (JSONArray) roomJSON.get("room")){
        JSONObject room = (JSONObject)object;

        extractRoom(room);

        if(once == 0){
          extractRoomItems(room, roomJSON);
          once++;
          gameRoom.setRoomItems(itemList);
        }

        extractRoomDoor(room);

        roomList.add(gameRoom);
      }
    } catch(FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }

  }


/**
 *@return (String) a String with all the rooms stored in it
 */
  public String displayAll(){
    //creates a string that displays all the rooms in the dungeon
    String display = "";

    for(int i = 0 ; i < roomList.size() ; i++ ){
      display = display + "\nROOM " + (i + 1) + "\n";
      display = display + (roomList.get(i)).displayRoom();
    }

    return display;
  }


/**
 *@param name
 *@param symbols
 *used information from JSON files and updates the display character value in Item.java, Room.java, and Player.java
 */
  public void extractSymbols(String name, String symbols){
    if(name.equals("ITEM")){
      for(int i = 0 ; i < itemList.size() ; i++){
        (itemList.get(i)).setDisplayCharacter(symbols.charAt(0));
       }
    } else if (name.equals("PLAYER")){
      roguePlayer.setDisplayCharacter(symbols.charAt(0));
    } else if(name.equals("DOOR") || name.equals("FLOOR") || name.equals("NS_WALL") || name.equals("EW_WALL")){
      for(int i = 0 ; i < roomList.size() ; i++){
        (roomList.get(i)).setDisplayCharacter(symbols.charAt(0), name);
      }
    }
  }


/**
 *@param room
 *@param roomJSON
 *used information from JSON files and creates Item objects for each item in a room and updates the ArrayList of items
 */
  public void extractRoomItems(JSONObject room, JSONObject roomJSON){
    for(Object lootObj : (JSONArray) room.get("loot")){
      JSONObject loot = (JSONObject)lootObj;

      gameItem = new Item();

      gameItem.setCurrentRoom(gameRoom);

      int i = -1, x = -1, y = -1;

      if(loot != null){
        i = Integer.decode(loot.get("id").toString());
        x = Integer.decode(loot.get("x").toString());
        y = Integer.decode(loot.get("y").toString());
      }

      Point p = new Point(x, y);

      for(Object itemObj : (JSONArray) roomJSON.get("items")){
        JSONObject item = (JSONObject)itemObj;

        if (i == Integer.decode(item.get("id").toString())){
          String name = item.get("name").toString();
          String type = item.get("type").toString();
          gameItem = new Item(i, name, type, p);
        }
      }

      itemList.add(gameItem);
    }
  }


/**
 *@param room
 *used information from JSON files and updates the roomDoors in Room.java
 */
  public void extractRoomDoor(JSONObject room){
    for(Object doorObj : (JSONArray) room.get("doors")){
      JSONObject door = (JSONObject)doorObj;

      if(door != null){
        gameRoom.setDoor(door.get("dir").toString(), Integer.decode(door.get("id").toString()));
      }
    }
  }

/**
 *@param room
 *used information from JSON files and the features of the room
 */
  public void extractRoom(JSONObject room){
    gameRoom = new Room();

    gameRoom.setId(Integer.decode(room.get("id").toString()));
    gameRoom.setHeight(Integer.decode(room.get("height").toString()));
    gameRoom.setWidth(Integer.decode(room.get("width").toString()));

    if(room.get("start").toString() == "true"){
      gameRoom.setPlayerInRoom(true);
      roguePlayer.setCurrentRoom(gameRoom);
    } else {
      gameRoom.setPlayerInRoom(false);
    }
  }

}