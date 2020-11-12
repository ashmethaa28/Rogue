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
 * Constructor.
 * @param name of user
 */
  public Player(String name) {
    setName(name);
    Point newPoint = new Point(1, 1);
    setXyLocation(newPoint);
  }

/**
 * Gets name of player.
 * @return (String) the player's name
 */
  public String getName() {
    return playerName;
  }

/**
 * Sets name of player.
 * @param newName for player
 */
  public void setName(String newName) {
    playerName = newName;
  }

/**
 * Gets the location of player.
 * @return (Point) the player's location
 */
  public Point getXyLocation() {
    return p;
  }

/**
 * Sets the location of player.
 * @param newXyLocation of player
 */
  public void setXyLocation(Point newXyLocation) {
    p = newXyLocation;
  }

/**
 * Gets room which player is in.
 * @return (Room) that the player is currently in
 */
  public Room getCurrentRoom() {
    return room;
  }

/**
 * Sets the room in which the player is currently in.
 * @param newRoom player is in
 */
  public void setCurrentRoom(Room newRoom) {
    room = newRoom;
  }

/**
 * Gets the display character for player.
 * @return (Character) that is used to represent the player
 */
  public Character getDisplayCharacter() {
    return ch;
  }

/**
 * Sets display Character.
 * @param newDisplayCharacter for player
 */
  public void setDisplayCharacter(Character newDisplayCharacter) {
    ch = newDisplayCharacter;
  }

}
