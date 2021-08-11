package colliders;

import components.Component;
import gates3Project.Initialize;
import utils.Spot;

public class RectangleCollider extends Collider {
	private Spot location;
	private int WIDTH;
	private int HEIGHT;
	
	public RectangleCollider(Component obj, Spot location, int WIDTH, int HEIGHT) {
		super(obj);
		this.location = location;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
	}

	@Override
	public boolean getCollision(int x, int y) {
		x -= 10;
		y -= 30;
		
		int x1 = location.getXAsInt() + (Initialize.e.getOffsetX());
		int y1 = location.getYAsInt() + (Initialize.e.getOffsetY());
		int x2 = location.getXAsInt() + WIDTH + (Initialize.e.getOffsetX());
		int y2 = location.getYAsInt() + HEIGHT + (Initialize.e.getOffsetY());
		
		if(x > x1 && x < x2 && y > y1 && y < y2)
			return true;
		
		return false;
	}

	public Spot getLocation() {
		return location;
	}

	public void setLocation(Spot location) {
		this.location = location;
	}
	
	public void setHeight(int HEIGHT) {
		this.HEIGHT = HEIGHT;
	}
}
