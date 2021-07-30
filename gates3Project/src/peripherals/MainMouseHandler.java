package peripherals;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import components.Chip;
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
		Initialize.e.getCreateChipUI().getBtn().executeHovered(x, y);
		Initialize.e.getAddInput().executeHovered(x, y);
		Initialize.e.getAddOutput().executeHovered(x, y);
		Initialize.e.getRemoveInput().executeHovered(x, y);
		Initialize.e.getRemoveOutput().executeHovered(x, y);
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
			Wire.leftClick(x, y);
			Node.leftClick(x, y, Node.getNodeClick(x, y));
			Initialize.e.getDropList().leftClick(x, y);
			Initialize.e.getCreateChipUI().getBtn().leftClick(x, y);
			Initialize.e.getAddInput().leftClick(x, y);
			Initialize.e.getAddOutput().leftClick(x, y);
			Initialize.e.getRemoveInput().leftClick(x, y);
			Initialize.e.getRemoveOutput().leftClick(x, y);
		}
		
		//Right Click
		if(e.getButton() == MouseEvent.BUTTON3) {
			Wire.rightClick(x, y);
			Node.rightClick(x, y, Node.getNodeClick(x, y));
		}
		
		Initialize.e.update();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		Chip.drag(x, y);
		
		Initialize.e.update();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		Chip.pressed(x, y);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		//int x = e.getX();
		//int y = e.getY();
		
		Chip.mouseReleased();
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
