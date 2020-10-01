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
    private ArrayList<Room> rooms;
    private ArrayList<Item> item;

    public void setPlayer(Player thePlayer){

    }


    public void setSymbols(String filename){
        
        JSONParser parser = new JSONParser();

        try {

            Object objSymbols = parser.parse(new FileReader(filename));
            JSONObject symbolsJSON = (JSONObject) objSymbols;
            JSONArray symbols = (JSONArray) symbolsJSON.get("symbols");
        
        } catch(FileNotFoundException e) {
        
            e.printStackTrace();
        
        } catch (IOException e) {
        
            e.printStackTrace();
        
        } catch (ParseException e) {
        
            e.printStackTrace();
        
        }
    }

    public ArrayList<Room> getRooms(){
        return rooms;
    }

    public ArrayList<Item> getItems(){
        return item;
    }

    public Player getPlayer(){
        return roguePlayer;
    }

    public void createRooms(String filename){

    	JSONParser parser = new JSONParser();

        try {

            Object objRoom = parser.parse(new FileReader(filename));
            JSONObject roomJSON = (JSONObject) objRoom;//there should be more to this??
        
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