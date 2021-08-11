package colliders;

import components.Component;
import gates3Project.Initialize;
import utils.Spot;

public class CircleCollider extends Collider {
	private Spot location;
	private int radius;
	
	public CircleCollider(Component obj, Spot location, int radius) {
		super(obj);
		this.location = location;
		this.radius = radius;
	}

	@Override
	public boolean getCollision(int x, int y) {
		int distN = (int)Math.abs(Math.sqrt(Math.pow(location.getXAsInt() + (Initialize.e.getOffsetX()) - x + 8, 2) + Math.pow(location.getYAsInt() + (Initialize.e.getOffsetY()) - y + 30, 2)));
		if(distN < radius - 6)
			return true;
		
	    return false;
	}
	
	public void setSize(int radius) {
		this.radius = radius;
	}
	
	public void setLocation(Spot location) {
		this.location = location;
	}
}
