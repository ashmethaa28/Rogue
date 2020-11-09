package rogue;

import java.util.ArrayList;

public class Door {

  private ArrayList<Room> connectedRooms = new ArrayList();
  private String roomDirection;
  private int roomLocation;
  private int connectedRoomId;

/**
 * Default Constructor.
 */
  public Door() {

  }

/**
 * @param direction
 * @param location
 * @param roomId
 * Constructor.
 */
  public Door(String direction, int location, int roomId) {
    setDirection(direction);
    setLocation(location);
    setConnectedRoomId(roomId);
  }

/**
 * @param roomId
 * sets the room id which the door is connected to
 */
  public void setConnectedRoomId(int roomId) {
    connectedRoomId = roomId;
  }

/**
 * @return (int)
 * gets the room id which the door connects to
 */
  public int getConnectedRoomId() {
    return connectedRoomId;
  }

/**
 * @param direction
 * sets the direction of the door
 */
  public void setDirection(String direction) {
    roomDirection = direction;
  }

/**
 * @param location
 * sets the location of the door along the wall
 */
  public void setLocation(int location) {
    roomLocation = location;
  }

/**
 * @return (String)
 * gets the direction of the door
 */
  public String getDirection() {
    return roomDirection;
  }

/**
 * @return (int)
 * gets the location of the door
 */
  public int getLocation() {
    return roomLocation;
  }

/**
 * @param r
 * current room that the door is connected to
 */
  public void connectRoom(Room r) {
    connectedRooms.add(r);
  }

/**
 * @return (ArrayList)
 * list of rooms that door is connected to
 */
  public ArrayList<Room> getConnectedRooms() {
    return connectedRooms;
  }

/**
 * @param currentRoom
 * @return (Room)
 * gets other Room that Door connects to
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