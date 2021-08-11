package components;

import java.util.ArrayList;

import colliders.Collider;
import peripherals.mouseInterface;

public abstract class Component implements mouseInterface {
	public abstract void update();
	//public abstract Boolean getCollision(int x, int y);
	
	private Boolean hovered = false;
	private Collider collider;
	
	public static void executeHovered(int x, int y, ArrayList<Component> components) {
		for(Component c : components) {
			if(c.getCollider().getCollision(x, y)) {
				c.setHovered(true);
			}
			else
				c.setHovered(false);
		}
	}
	
	public Boolean getHovered() {
		return hovered;
	}
	
	public void setHovered(Boolean hovered) {
		this.hovered = hovered;
	}
	
	public Collider getCollider() {
		return this.collider;
	}
	
	public void setCollider(Collider collider) {
		this.collider = collider;
	}
}
