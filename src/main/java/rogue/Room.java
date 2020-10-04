package rogue;
import java.util.ArrayList; 
import java.util.Map;
import java.util.HashMap;
import java.awt.Point;


/**
 * A room within the dungeon - contains monsters, treasure,
 * doors out, etc.
 */
public class Room  {

  private int roomWidth;
  private int roomHeight;
  private int roomId;
  private ArrayList<Item> itemList = new ArrayList();
  private Map<String, Integer> roomDoors = new HashMap();
  private Player player; 
  private Character ch;
  private boolean isPlayer;
  private Map<String, Character> displayInfo = new HashMap();

    // Default constructor
  public Room() {

  }

// Required getter and setters below 
  public int getWidth() {
   	return roomWidth;
  }

 
  public void setWidth(int newWidth) {
    roomWidth = newWidth;
  }  

 
  public int getHeight() {
    return roomHeight;
  }


  public void setHeight(int newHeight) {
    roomHeight = newHeight;
  }

  public int getId() {
    return roomId;
  }


  public void setId(int newId) {
    roomId = newId;
  }


  public ArrayList<Item> getRoomItems() {
   	return itemList;
  }


  public void setRoomItems(ArrayList<Item> newRoomItems) {
    itemList = newRoomItems;
  }


  public Player getPlayer() {
    return player;
  }


  public void setPlayer(Player newPlayer) { //set in main
    player = newPlayer;
  }

  public int getDoor(String direction){
    try{
      return roomDoors.get(direction);
    } catch(NullPointerException e){
      return -1;
    }
  }

/*
direction is one of NSEW
location is a number between 0 and the length of the wall
*/

  public void setDoor(String direction, int location){
    roomDoors.put(direction, location);
  }


  public boolean isPlayerInRoom() {
    return isPlayer; 
  }


   /**
    * Produces a string that can be printed to produce an ascii rendering of the room and all of its contents
    * @return (String) String representation of how the room looks
    */
  public String displayRoom() {
    String display = "";

    display = displayRoomWallFloor(display);
  	
    display = displayRoomDoor(display);

    display = displayRoomItem(display);
  	
    if(isPlayerInRoom() == true){
      display = displayPlayer(display);
    }

    return display;
  }

  public void setPlayerInRoom(boolean player){
    isPlayer = player;
  }

//i added these method
  public Character getDisplayCharacter(String symbol) {
    return displayInfo.get(symbol);
  }


  public void setDisplayCharacter(Character newDisplayCharacter, String symbol) {
    displayInfo.put(symbol, newDisplayCharacter);
  }


  public String displayRoomWallFloor(String display){
    for(int y = 0 ; y < getHeight() ; y++){
      for(int x = 0 ; x < getWidth() ; x++){
        if(y == 0 || y == (getHeight() - 1)){
          display = display + getDisplayCharacter("NS_WALL");
        } else if(x == 0 || x == (getWidth() - 1)){
          display = display + getDisplayCharacter("EW_WALL");
        } else {
          display = display + getDisplayCharacter("FLOOR"); 
        }
      }

      display = display + "\n";
    }

    return display;
  }


  public String displayRoomDoor(String display){
    if(getDoor("S") != -1){
      display = display.substring(0, (getWidth() + 1)*(getHeight() - 1) + getDoor("S")) + getDisplayCharacter("DOOR") + display.substring((getWidth() + 1)*(getHeight() - 1) + getDoor("S") + 1);
    }
    if(getDoor("N") != -1){
      display = display.substring(0, getDoor("N")) + getDisplayCharacter("DOOR") + display.substring(getDoor("N") + 1);
    }
    if(getDoor("W") != -1){
      display = display.substring(0, getWidth() * getDoor("W") + getDoor("W")) + getDisplayCharacter("DOOR") + display.substring(getWidth() * getDoor("W") + getDoor("W") + 1);
    }
    if(getDoor("E") != -1){
      display = display.substring(0, getWidth() * (getDoor("E") + 1) + (getDoor("E") - 1)) + getDisplayCharacter("DOOR") + display.substring(getWidth() * (getDoor("E") + 1) + (getDoor("E") - 1)+ 1);
    }

    return display;
  }


  public String displayRoomItem(String display){
    int index;
    Point p;

    for(int i = 0 ; i < itemList.size() ; i++){
      Item itemDisplay = itemList.get(i);

      p = itemDisplay.getXyLocation();
      index = (getWidth() + 1) * (int)(p.getY()) + (int)(p.getX());
      display = display.substring(0, index) + itemDisplay.getDisplayCharacter() + display.substring(index + 1);

      if(isPlayerInRoom() == true && p.getY() == player.getXyLocation().getY() && p.getX() == player.getXyLocation().getX()){
        if(p.getX() == (getWidth() - 2)){
          p = new Point((int)p.getX() - 1, (int)p.getY());
        } else {
          p = new Point((int)p.getX() + 1, (int)p.getY());
        }
        if(p.getY() == (getHeight() - 2)){
          p = new Point((int)p.getX(), (int)p.getY() - 1);
        } else {
          p = new Point((int)p.getX(), (int)p.getY() + 1);
        }
        player.setXyLocation(p);
        i = -1;
        }
    }
    return display;
  }


  public String displayPlayer(String display){
    int index;
    Point p; 

    p = player.getXyLocation();
    index = (getWidth() + 1) * (int)(p.getY()) + (int)(p.getX());
    
    display = display.substring(0, index) + player.getDisplayCharacter() + display.substring(index + 1);

    return display;
  }
}