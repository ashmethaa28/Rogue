package rogue;

import java.util.ArrayList;

public class Inventory {
	public ArrayList<Item> itemList = new ArrayList();

	public Inventory(){

	}

	public Inventory(Item item) {
		addItem(item);
	}

	public void addItem(Item item) {
        itemList.add(item);
	}

    public void removeItem(Item item) throws InvalidMoveException {
//ADD CODE
    }
}