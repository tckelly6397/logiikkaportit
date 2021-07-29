package peripherals;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import components.Chip;
import components.Node;
import components.Wire;
import gates3Project.Initialize;
import utils.Spot;

public class MainMouseHandler extends MouseAdapter {
	int offsetX = 0;
	int offsetY = 0;
	
	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		//Chip stuff
		for(Chip c : Initialize.e.getChips()) {
			if(c.getCollision(x, y) != null) {
				c.setHovered(true);
				break;
			}
			else
				c.setHovered(false);
		}
		//End of Chip Stuff
		
		//Wire Stuff
		ArrayList<Wire> wires = Initialize.e.getWires();
		
		//Set position of temporary spot for wires
		for(Wire w : wires) {
			if(!w.isSelected())
				continue;
			Spot tempSpot = null;

			if(w.isxAxis())
				tempSpot = new Spot(x - 8, w.getSpots().get(w.getSpots().size() - 1).getYAsInt());
			else
				tempSpot = new Spot(w.getSpots().get(w.getSpots().size() - 1).getXAsInt(), y - 27);
			
			w.setTempSpot(tempSpot);
		}
		
		//Wire Collision
		for(Wire w : wires) {
			if(w.getCollision(x, y) != null)
				w.setHovered(true);
			else
				w.setHovered(false);
		}
		
		//End of Wire Stuff
		
		//Node Stuff
		ArrayList<Node> nodes = new ArrayList<>(Initialize.e.getInputNodes());
		nodes.addAll(Initialize.e.getOutputNodes());
		for(Chip c : Initialize.e.getChips()) {
			nodes.addAll(c.getInputNodes());
			nodes.addAll(c.getOutputNodes());
		}
		Node node = null;
		
		for(Node n : nodes) { 
			node = n.getCollision(x, y);
			if(node != null)
				break;
		}
		
		if(node != null)
			node.setHovered(true);
		//End of Node Stuff
		
		Initialize.e.update();	
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		//Wire Stuff
		ArrayList<Wire> wires = Initialize.e.getWires();
		
		if(e.getButton() == MouseEvent.BUTTON3) {
			for(Wire w : wires) {
				if(w.isSelected()) {
					w.addSpot(new Spot(w.getTempSpot().getXAsInt(), w.getTempSpot().getYAsInt()));
					w.switchAxis();
				}
			}
		}
		
		if(e.getButton() == MouseEvent.BUTTON1) {
			Wire wire = null;
			for(Wire w : wires) {
				wire = w.getCollision(x, y);
			}
			
			if(wire != null)
				wire.destroy();
		}
		//End of Wire Stuff
		
		//Node Stuff
		ArrayList<Node> nodes = new ArrayList<>(Initialize.e.getInputNodes());
		nodes.addAll(Initialize.e.getOutputNodes());
		for(Chip c : Initialize.e.getChips()) {
			nodes.addAll(c.getInputNodes());
			nodes.addAll(c.getOutputNodes());
		}
		
		Node node = null;
		
		for(Node n : nodes) { 
			node = n.getCollision(x, y);
			if(node != null)
				break;
		}
		
		if(node != null) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				if(node.isClickable()) {
					node.switchPowered();
					//node.getEnvironment().getPowerThread().setNext(node);
					Initialize.pw.addNext(node);
				}
				
				//Connect node
				for(Wire w : wires) {
					if(w.isSelected()) {
						if(w.getSpots().size() == 1) {
							int dist = node.getSpot().getXAsInt() - w.getSpots().get(0).getXAsInt();
							Spot s1 = new Spot(node.getSpot().getXAsInt() - (dist / 2), w.getSpots().get(0).getYAsInt());
							Spot s2 = new Spot(node.getSpot().getXAsInt() - (dist / 2), node.getSpot().getYAsInt());
									
							w.addSpot(s1);
							w.addSpot(s2);
						}
						else {
							Spot last = w.getSpots().get(w.getSpots().size() - 1);
							last = new Spot(last.getX(), node.getSpot().getYAsInt());
							
							if(!w.isxAxis())
								w.getSpots().add(new Spot(last.getX(), last.getY()));
							
							w.getSpots().get(w.getSpots().size() - 1).setSpot(last);
							//	w.addSpot(new Spot(last.getX(), last.getY()));
						}
						
						w.getSpots().add(node.getSpot());
						w.setTempSpot(null);
						w.setSelected(false);
						node.addInputWire(w);
						w.setOutputNode(node);
						w.update();
					}
				}
			}
			
			//Second wire add stuff
			Boolean goOn = true;
			
			for(Wire w : wires) {
				if(w.isSelected()) {
					goOn = false;
				}
			}
			
			if(e.getButton() == MouseEvent.BUTTON3 && goOn) {
				Wire w = new Wire(node, node.getEnvironment());
				//w.setSelected(true);
				node.getWires().add(w);
				Initialize.e.getWires().add(w);
			}
			//End of second wire and stuff
		}
		//End of Node Stuff
		
		Initialize.e.update();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		for(Chip c : Initialize.e.getChips()) {
			if(c.getSelected()) {
				c.translateX(x - c.getX() + offsetX);
				c.translateY(y - c.getY() + offsetY);
				
				break;
			}
		}
		
		Initialize.e.update();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		for(Chip c : Initialize.e.getChips()) {
			if(c.getCollision(x, y) != null) {
				c.setSelected(true);
				offsetX = c.getX() - x;
				offsetY = c.getY() - y;
				break;
			}		
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		//int x = e.getX();
		//int y = e.getY();
		
		for(Chip c : Initialize.e.getChips())
			c.setSelected(false);
		
		offsetX = 0;
		offsetY = 0;
	}
}
