package rogue;
import java.util.ArrayList;
import java.awt.Point;
/**
 * The player character
 */
public class Player {

    private String playerName;
    private Room room;
    private Character ch;
    private Point p;

    // Default constructor
    public Player() {
        setName("Unknown");
        Point p = new Point(1, 1);
    }


    public Player(String name) {
        setName(name);
    }


    public String getName() {
        return playerName;
    }


    public void setName(String newName) {
        playerName = newName;
    }

    public Point getXyLocation() {
        return p;
    }


    public void setXyLocation(Point newXyLocation) {
        p = newXyLocation;
    }


    public Room getCurrentRoom() {
        return room; //need to get boolean for this
    }


    public void setCurrentRoom(Room newRoom) {
        room = newRoom;
    }

    public Character getDisplayCharacter() {
        return ch; 
    }


    public void setDisplayCharacter(Character newDisplayCharacter) {
        ch = newDisplayCharacter;   
    }
}
