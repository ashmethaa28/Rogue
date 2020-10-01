package rogue;
import java.util.ArrayList;
import java.awt.Point;
/**
 * The player character
 */
public class Player {

    private String playerName;


    // Default constructor
    public Player() {
        setName(null);
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
        return null;

    }


    public void setXyLocation(Point newXyLocation) {

    }


    public Room getCurrentRoom() {
        return null;

    }


    public void setCurrentRoom(Room newRoom) {

    }
}
