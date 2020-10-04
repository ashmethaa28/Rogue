package rogue;

import java.util.Scanner;
import java.util.ArrayList;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.awt.Point;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class A1Solution{


  public static void main(String[] args) { 
    // Hardcoded configuration file location/name
    String configurationFileLocation = "fileLocations.json";  //please don't change this for this version of the assignment
        
 // reading the input file locations using the configuration file
    JSONParser parser = new JSONParser();
    Rogue game = new Rogue();

    Player player = new Player();
    game.setPlayer(player);

    try {
      Object obj = parser.parse(new FileReader(configurationFileLocation));
      JSONObject configurationJSON = (JSONObject) obj;
      String roomFile = configurationJSON.get("Rooms").toString();
      String symbolsFile = configurationJSON.get("Symbols").toString();

      // Extract the Rooms value from the file to get the file location for rooms
      game.createRooms(roomFile);

      // Extract the Symbols value from the file to get the file location for symbols-map
      game.setSymbols(symbolsFile);
            
      } catch(FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (ParseException e) {
        e.printStackTrace();
      }

// instantiate a new Rogue object and call methods to do the required things

      ArrayList<Room> roomList = new ArrayList();
      roomList = game.getRooms();

      for(int i = 0 ; i < roomList.size() ; i ++){
        roomList.get(i).setPlayer(player);
      }

      System.out.println(game.displayAll());
    }


}