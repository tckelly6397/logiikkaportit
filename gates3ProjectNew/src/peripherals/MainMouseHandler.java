package peripherals;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import colliders.Collider;
import components.Component;
import components.Node;
import components.Wire;
import gates3Project.Initialize;

public class MainMouseHandler extends MouseAdapter {
	int offsetX = 0;
	int offsetY = 0;
	
	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		//Hover Stuff
		Component.executeHovered(x, y, new ArrayList<Component>(Initialize.e.getWires()));
		Component.executeHovered(x, y, new ArrayList<Component>(Initialize.e.getChips()));
		Component.executeHovered(x, y, new ArrayList<Component>(Node.getAllNodes()));
		Initialize.e.getDropList().executeHovered(x, y);
		Initialize.e.getCreateChipUI().executeHovered(x, y);
		Initialize.e.getChangeNodesUI().executeHovered(x, y);
		Initialize.e.getPrompt().executeHovered(x, y);
		//End of Hover Stuff
		
		//Wire Stuff
		Wire.updateTempSpot(x, y);
		
		Initialize.e.update();	
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		//Left Click
		if(e.getButton() == MouseEvent.BUTTON1) {
			Initialize.e.getDropList().leftClick(x, y);
			Initialize.e.getCreateChipUI().leftClick(x, y);
			Initialize.e.getChangeNodesUI().leftClick(x, y);
			Initialize.e.getPrompt().leftClick(x, y);
			
			Collider.leftClick(x, y);
		}
		
		//Right Click
		if(e.getButton() == MouseEvent.BUTTON3) {
			Collider.rightClick(x, y);
		}
		
		//Middle Click
		if(e.getButton() == MouseEvent.BUTTON2) {
			Initialize.e.getChangeNodesUI().middleClick(x, y);
		}
		
		Initialize.e.update();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		//Left Click
		if(SwingUtilities.isLeftMouseButton(e))
			Collider.drag(x, y);
		
		//Middle Click
		if(SwingUtilities.isMiddleMouseButton(e)) {
			Initialize.e.getBox().middleClickDrag(x, y);
		}
		
		Initialize.e.update();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if(SwingUtilities.isLeftMouseButton(e))
			Collider.mousePressed(x, y);
		
		//Middle Click
		if(SwingUtilities.isMiddleMouseButton(e)) {
			Initialize.e.getBox().middlePressed(x, y);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		//Chip.mouseReleased();
		int x = e.getX();
		int y = e.getY();
		
		if(SwingUtilities.isLeftMouseButton(e))
			Collider.mouseReleased(x, y);
		
		//Middle Click
		if(SwingUtilities.isMiddleMouseButton(e)) {
			Initialize.e.getBox().middleReleased(x, y);
		}
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation() < 0) {
			Initialize.e.getDropList().scrollUp();
		} else {
			Initialize.e.getDropList().scrollDown();
		}
	}
}
