package rogue;

import java.util.ArrayList;

import java.io.Serializable;

public class Door implements Serializable {

  private ArrayList<Room> connectedRooms = new ArrayList();
  private String roomDirection;
  private int roomLocation;
  private int connectedRoomId;
  private static final long serialVersionUID = 2184995992394982059L;

/**
 * Default Constructor.
 */
  public Door() {

  }

/**
 * Constructor.
 * @param direction of the door
 * @param location - position on the wall
 * @param r - room that this door is in
 */
  public Door(String direction, int location, Room r) {
    setDirection(direction);
    setLocation(location);
    connectRoom(r);
  }

/**
 * Constructor.
 * @param direction of the door
 * @param location - position on the wall
 * @param roomId - room Id of which room this door connects to
 * @param r - room that this door is in
 */
  public Door(String direction, int location, int roomId, Room r) {
    setDirection(direction);
    setLocation(location);
    setConnectedRoomId(roomId);
    connectRoom(r);
  }

/**
 * Sets the room id which the room the door is connected to.
 * @param roomId - id of the room
 */
  public void setConnectedRoomId(int roomId) {
    connectedRoomId = roomId;
  }

/**
 * Gets the room id which the room the door connects to.
 * @return (int) value of the room Id that the door connects to
 */
  public int getConnectedRoomId() {
    return connectedRoomId;
  }

/**
 * Sets the direction of the door.
 * @param direction where the door is.
 */
  public void setDirection(String direction) {
    roomDirection = direction;
  }

/**
 * Sets the location of the door along the wall.
 * @param location of where the door is.
 */
  public void setLocation(int location) {
    roomLocation = location;
  }

/**
 * Gets the direction of the door.
 * @return (String) value of which direction the door is.
 */
  public String getDirection() {
    return roomDirection;
  }

/**
 * Gets the location of the door.
 * @return (int) where the door is located along the wall
 */
  public int getLocation() {
    return roomLocation;
  }

/**
 * Current room that the door is connected to.
 * @param r - the room that the door is in or the door connects to.
 */
  public void connectRoom(Room r) {
    connectedRooms.add(r);
  }

/**
 * List of rooms that door is connects to.
 * @return (ArrayList) list of all the rooms that this door is connected to
 */
  public ArrayList<Room> getConnectedRooms() {
    return connectedRooms;
  }

/**
 * Gets the other room that door is connected to.
 * @param currentRoom - the room that player is currently in
 * @return (Room) the other room that the door connects to
 */
  public Room getOtherRoom(Room currentRoom) {
    for (int i = 0; i < connectedRooms.size(); i++) {
      if ((connectedRooms.get(i)).getId() != currentRoom.getId()) {
        return connectedRooms.get(i);
      }
    }
    return null;
  }
}
