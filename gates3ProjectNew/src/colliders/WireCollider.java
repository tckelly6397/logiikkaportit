package colliders;

import java.util.ArrayList;

import components.Component;
import gates3Project.Initialize;
import utils.Spot;

public class WireCollider extends Collider {
	private ArrayList<Spot> spots;
	
	public WireCollider(Component obj, ArrayList<Spot> spots) {
		super(obj);
		this.spots = spots;
	}

	@Override
	public boolean getCollision(int x, int y) {
		y -= 32;
		x -= 8;
		
		for(int i = 0; i < spots.size() - 1; i++) {
			int x1 = spots.get(i).getXAsInt() + (Initialize.e.getOffsetX());
			int y1 = spots.get(i ).getYAsInt() + (Initialize.e.getOffsetY());
			int x2 = spots.get(i + 1).getXAsInt() + (Initialize.e.getOffsetX());
			int y2 = spots.get(i + 1).getYAsInt() + (Initialize.e.getOffsetY());
			
			//The x1 or y1 has to be smaller than there counter parts
			if(x2 < x1) {
				int temp = x2;
				x2 = x1;
				x1 = temp;
			}
			
			if(y2 < y1) {
				int temp = y2;
				y2 = y1;
				y1 = temp;
			}
			
			if(x1 == x2) {
				x1 -= 7;
				x2 += 7;
			}
			
			if(y1 == y2) {
				y1 -= 7;
				y2 += 7;
			}
			
			//Allows to not detect collision if on node
			if(i == 0)
				x1 += 12;
			
			if(i + 1 == spots.size() - 1)
				x2 -= 9;
			
			if(x > x1 && x < x2 && y > y1 && y < y2) {
				return true;
			}
		}
		
		return false;
	}
}
