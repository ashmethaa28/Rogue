package rogue;

import java.util.ArrayList;
import java.io.Serializable;
public class Inventory implements Serializable {
  private ArrayList<Item> itemList = new ArrayList();
  private static final long serialVersionUID = -6859226804699537081L;

/**
 * Default Constructor.
 */
  public Inventory() {
  }

/**
 * Constructor.
 * @param item
 */
  public Inventory(Item item) {
    addItem(item);
  }

/**
 * Gets list of item.
 * @return (ArrayList) - list of item
 */
  public ArrayList<Item> getList() {
   return itemList;
  }

/**
 * Adds item to list of item.
 * @param item that is being added
 */
  public void addItem(Item item) {
    itemList.add(item);
  }

/**
 * Removes item from list of item.
 * @param item that is being removed
 */
  public void removeItem(Item item) {
    itemList.remove(item);
  }

}
