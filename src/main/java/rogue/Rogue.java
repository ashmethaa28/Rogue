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
    private ArrayList<Item> itemList = new ArrayList();
    private Room gameRoom;
    private Item gameItem;

    public void setPlayer(Player thePlayer){
    	roguePlayer = thePlayer;
    }


    public void setSymbols(String filename){
        
        JSONParser parser = new JSONParser();

        try {
            Object symbolObj = parser.parse(new FileReader(filename));
            JSONObject symbolsJSON = (JSONObject) symbolObj;

            for (Object objSymbols : (JSONArray) symbolsJSON.get("symbols")){
    	       	JSONObject jsonSymbol = (JSONObject)objSymbols;
 	          
 	          	String name = jsonSymbol.get("name").toString();
           		String symbols = jsonSymbol.get("symbol").toString();

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
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Room> getRooms(){
        return roomList;
    }

    public ArrayList<Item> getItems(){
        return itemList;
    }

    public Player getPlayer(){
        return roguePlayer;
    }

    public void createRooms(String filename){
    	JSONParser parser = new JSONParser();
    	int once = 0;

        try {
            Object objRoom = parser.parse(new FileReader(filename));
            JSONObject roomJSON = (JSONObject) objRoom;

            for(Object object : (JSONArray) roomJSON.get("room")){
            	JSONObject room = (JSONObject)object;

            	gameRoom = new Room();

            	gameRoom.setId(Integer.decode(room.get("id").toString()));
            	gameRoom.setHeight(Integer.decode(room.get("height").toString()));
            	gameRoom.setWidth(Integer.decode(room.get("width").toString()));
            	if(room.get("start").toString() == "true"){
            		gameRoom.setPlayerInRoom(true);
            	} else {
            		gameRoom.setPlayerInRoom(false);
            	}


            	if(once == 0){
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
	            				// gameRoom.setRoomItems(gameItem);
        	    			}
		           		}

            			itemList.add(gameItem);
            		}
            		once++;
            		gameRoom.setRoomItems(itemList);
            	}

            	for(Object doorObj : (JSONArray) room.get("doors")){
            		JSONObject door = (JSONObject)doorObj;

            		if(door != null){
            			gameRoom.setDoor(door.get("dir").toString(), Integer.decode(door.get("id").toString()));
            		}
            	}

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

    public String displayAll(){
        //creates a string that displays all the rooms in the dungeon
        String display = "";

        for(int i = 0 ; i < roomList.size() ; i++ ){
        	display = display + "\nROOM " + (i + 1) + "\n";
        	display = display + (roomList.get(i)).displayRoom();
        }

        return display;
    }


}