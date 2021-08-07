package components;

import java.util.ArrayList;

public abstract class Component {
	public abstract void update();
	public abstract Boolean getCollision(int x, int y);
	
	private Boolean hovered = false;
	
	public static void executeHovered(int x, int y, ArrayList<Component> components) {
		for(Component c : components) {
			if(c.getCollision(x, y)) {
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
}
