package rogue;

import java.awt.Point;

public class Food extends Item implements Edible{
  public Food(){
  }

  	public Food(int id, String name, String type, Point xyLocation, String description) {
		super(id, name, type, xyLocation, description);
	}

  @Override
  public String eat() {
  	return getDescription();
  }
}