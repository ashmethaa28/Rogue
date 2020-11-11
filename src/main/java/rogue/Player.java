package rogue;

import java.awt.Point;
/**
 * The player character.
 */
public class Player {

  private String playerName;
  private Room room;
  private Character ch;
  private Point p;

/**
 * Default constructor.
 */
  public Player() {
    setName("Unknown");
    Point newPoint = new Point(1, 1);
    setXyLocation(newPoint);
  }


/**
 *@param name
 *Constructor that calls setName method
 */
  public Player(String name) {
    setName(name);
    Point newPoint = new Point(1, 1);
    setXyLocation(newPoint);
  }

/**
 * @return (String) the player's name
 */
  public String getName() {
    return playerName;
  }

/**
 * @param newName
 * updates player's name
 */
  public void setName(String newName) {
    playerName = newName;
  }

/**
 * @return (Point) the player's location
 */
  public Point getXyLocation() {
    return p;
  }

/**
 * @param newXyLocation
 * updates player's location
 */
  public void setXyLocation(Point newXyLocation) {
    p = newXyLocation;
  }

/**
 * @return (Room) a Room object that the player is currently in
 */
  public Room getCurrentRoom() {
    return room;
  }

/**
 * @param newRoom
 * updates the Room object that the player is currently in
 */
  public void setCurrentRoom(Room newRoom) {
    room = newRoom;
  }

/**
 * @return (Character) a Character value that is used to represent the player
 */
  public Character getDisplayCharacter() {
    return ch;
  }

/**
 * @param newDisplayCharacter
 * updates the Character value that is used to represent player
 */
  public void setDisplayCharacter(Character newDisplayCharacter) {
    ch = newDisplayCharacter;
  }

}
