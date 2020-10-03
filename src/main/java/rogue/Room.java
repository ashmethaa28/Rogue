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
    return roomDoors.get(direction);
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
  	Point p;
  	int index;

  	for(int y = 0 ; y < getHeight() ; y++){
  		for(int x = 0 ; x < getWidth() ; x++){
  			if(y == 0 || y == (getHeight() - 1)){
  				display = display + getDisplayCharacter("NS_WALL");
  			} else if(x == 0 || x == (getWidth() - 1)){
  				display = display + getDisplayCharacter("EW_WALL");
  			}
  			else {
  				display = display + getDisplayCharacter("FLOOR"); 
  			}
  		}
  		display = display + "\n";
  	}
  	/*if(getDoor("S") != null ){
  	display = display.substring(0, (getWidth() + 1)*(getHeight() - 1) + getDoor("S")) + getDisplayCharacter("DOOR") + display.substring((getWidth() + 1)*(getHeight() - 1) + getDoor("S") + 1);
  	}
   	if(!(getDoor("N") == -1)){
   		System.out.println("hereN");
  	}
  	/*if(!(getDoor("E") == -1)){

  	}
  	if(!(getDoor("W") == -1)){

  	}*/

  	/*p = player.getXyLocation();
  	index = getHeight() * (int)(p.getY() - 1) + (int)(p.getX() - 1);
  	System.out.println(index);
	if(isPlayerInRoom() == true){
  		display = display.substring(0, index) + player.getDisplayCharacter() + display.substring(index + 1);
  	}

  	p = itemList.get(getId() - 1).getXyLocation();
  	index = getHeight() * (int)(p.getY() - 1) + (int)(p.getX() - 1);
  	System.out.println(index);
	display = display.substring(0, index) + itemList.get(getId() - 1).getDisplayCharacter() + display.substring(index + 1);*/

    return display;
  }

  public void setPlayerInRoom(boolean player){
    isPlayer = player;
  }

//i added this method
  public Character getDisplayCharacter(String symbol) {
    return displayInfo.get(symbol);
  }


  public void setDisplayCharacter(Character newDisplayCharacter, String symbol) {
    displayInfo.put(symbol, newDisplayCharacter);
  }

}