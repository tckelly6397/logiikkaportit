package colliders;

import java.util.ArrayList;

import components.Component;
import components.Wire;
import components.chips.Chip;
import gates3Project.Initialize;
import utils.Spot;

public abstract class Collider {
	//This will have all the basics for a collider and a collider register
	public static ArrayList<Collider> colliders = new ArrayList<>();
	public abstract boolean getCollision(int x, int y);
	
	private Component obj;
	
	public Collider(Component obj) {
		this.obj = obj;
	}
	
	public static void leftClick(int x, int y) {
		Collider c = collide(x, y);
		
		if(c != null) {
			c.getObject().leftClick(x, y);
		}
	}
	
	public static void rightClick(int x, int y) {
		ArrayList<Wire> wires = Initialize.e.getWires();
		
		for(Wire w : wires) {
			if(w.isSelected() && w.getTempSpot() != null) {
				w.addSpot(new Spot(w.getTempSpot().getXAsInt(), w.getTempSpot().getYAsInt()));
				w.switchAxis();
			}
		}
		
		Collider c = collide(x, y);
		
		if(c != null)
			c.getObject().rightClick(x, y);
	}
	
	public static void middleClick(int x, int y) {
		Collider c = collide(x, y);
		
		if(c != null)
			c.getObject().middleClick(x, y);
	}
	
	public static void drag(int x, int y) {
		for(Chip c : Initialize.e.getChips())
			if(c.getSelected()) {
				c.translateX(x - c.getX() + c.getOffsetX());
				c.translateY(y - c.getY() + c.getOffsetY());
				return;
			}
		
		Collider c = collide(x, y);
		
		if(c != null)
			c.getObject().drag(x, y);

	}
	
	public static void mouseReleased(int x, int y) {
		Collider c = collide(x, y);
		
		if(c != null)
			c.getObject().mouseReleased(x, y);
	}
	
	public static void mousePressed(int x, int y) {
		Collider c = collide(x, y);
		
		if(c != null)
			c.getObject().mousePressed(x, y);
	}
	
	public static Collider collide(int x, int y) {
		for(Collider c : colliders) {
			if(c.getCollision(x, y))
				return c;
		}
		
		return null;
	}
	
	public static void registerCollider(Collider c) {
		colliders.add(c);
	}
	
	public static void unRegisterCollider(Collider c) {
		colliders.remove(c);
	}
	
	public Component getObject() {
		return this.obj;
	}
}
