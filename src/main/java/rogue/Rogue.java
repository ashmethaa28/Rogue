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

        try {

            Object objRoom = parser.parse(new FileReader(filename));
            JSONObject roomJSON = (JSONObject) objRoom;

            for(Object object : (JSONArray) roomJSON.get("room")){
            	JSONObject room = (JSONObject)object;

            	gameRoom = new Room();

            	gameRoom.setId(Integer.decode(room.get("id").toString()));
            	gameRoom.setHeight(Integer.decode(room.get("height").toString()));
            	gameRoom.setWidth(Integer.decode(room.get("width").toString()));

            	for(Object lootObj : (JSONArray) room.get("loot")){
            		JSONObject loot = (JSONObject)lootObj;

            		gameItem = new Item();

            		gameItem.setCurrentRoom(gameRoom);

            		if(loot != null){
            			gameItem.setId(Integer.decode(loot.get("id").toString()));
            		}
            	}

            	for(Object doorObj : (JSONArray) room.get("doors")){
            		JSONObject door = (JSONObject)doorObj;

            		if(door != null){
            			gameRoom.setDoor(door.get("dir").toString(), Integer.decode(door.get("id").toString()));
            		}
            	}

            	roomList.add(gameRoom);
            }

            for(Object itemObj : (JSONArray) roomJSON.get("items")){
            	JSONObject item = (JSONObject)itemObj;

            	gameItem = new Item();

            	gameItem.setId(Integer.decode(item.get("id").toString()));
            	gameItem.setName(item.get("name").toString());
            	gameItem.setType(item.get("type").toString());

            	itemList.add(gameItem);
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
        return null;
    }


}