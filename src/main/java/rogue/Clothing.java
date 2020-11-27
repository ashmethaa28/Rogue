package rogue;

import java.awt.Point;

public class Clothing extends Item implements Wearable {
	public Clothing(){
	}

	public Clothing(int id, String name, String type, Point xyLocation, String description) {
		super(id, name, type, xyLocation, description);
	}

	@Override
	public String wear(){
		return getDescription();
	}
}