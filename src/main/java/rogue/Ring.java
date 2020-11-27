package rogue;

import java.awt.Point;

public class Ring extends Magic implements Wearable {
	public Ring() {
	}

	public Ring(int id, String name, String type, Point xyLocation, String description) {
		super(id, name, type, xyLocation, description);
	}

	@Override
	public String wear() {
		return getDescription();
	}
}