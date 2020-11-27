package rogue;

import java.awt.Point;

public class Potion extends Magic implements Edible, Tossable {
	public Potion() {
	}

	public Potion(int id, String name, String type, Point xyLocation, String description) {
		super(id, name, type, xyLocation, description);
	}

    @Override
	public String eat() {
		int first = getDescription().indexOf(":", 0);
		String str = getDescription().substring(0, first);
		return str;
	}

    @Override
	public String toss() {
		int first = getDescription().indexOf(":", 0);
		String str = getDescription().substring(first + 2);
		return str;
	}
}