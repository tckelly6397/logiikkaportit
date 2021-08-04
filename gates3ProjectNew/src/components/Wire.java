package components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import gates3Project.Environment;
import gates3Project.Initialize;
import utils.Spot;

public class Wire extends Component {
	private ArrayList<Spot> spots = new ArrayList<>();
	private boolean powered;
	private Environment e;
	private Node inputNode = null;
	private Node outputNode = null;
	private boolean selected = true;
	private Spot tempSpot = null;
	private boolean xAxis = true;
	
	//Used to check if there is a loop
	private boolean used;
	
	public Wire(Node inputNode, Environment e) {
		this.used = false;
		this.inputNode = inputNode;
		this.powered = inputNode.isPowered();
		this.spots.add(inputNode.getSpot());
		this.selected = true;
		this.e = e;
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int w = 8;
		g.setColor(Color.BLACK);
		
		if(powered)
			g.setColor(new Color(255, 0, 0));
		
		if(getHovered())
			g.setColor(new Color(clamp(g.getColor().getRed() + 20, 255), clamp(g.getColor().getGreen() + 20, 255), clamp(g.getColor().getBlue() + 20, 255)));
		
		g2d.setStroke(new BasicStroke(w, 1, 1));
		
		ArrayList<Spot> loopSpots = new ArrayList<>(spots);
		
		if(tempSpot != null)
			loopSpots.add(tempSpot);
		
		for(int i = 0; i < loopSpots.size() - 1; i++) {
			Spot s1 = loopSpots.get(i);
			Spot s2 = loopSpots.get(i + 1);
			
			if(i + 1 == loopSpots.size() - 1 && outputNode != null)
				s2 = outputNode.getSpot();
			
			if(i == 0)
				s1 = inputNode.getSpot();
			
			//Just draw a straight line if there's two spots
			if(loopSpots.size() < 3) {
				g2d.drawLine(s1.getXAsInt(), s1.getYAsInt(), s2.getXAsInt(), s2.getYAsInt());
				break;
			}
				
			//Draw a line along the x axis
			if(s1.getXAsInt() != s2.getXAsInt()) {
				g2d.drawLine(s1.getXAsInt(), s1.getYAsInt(), s2.getXAsInt(), s2.getYAsInt());
				continue;	 
			}
			
			//Draw a line along the y axis
			if(s1.getYAsInt() != s2.getYAsInt()) {
				g2d.drawLine(s1.getXAsInt(), s1.getYAsInt(), s2.getXAsInt(), s2.getYAsInt());
				continue;
			}
		}
	}
	
	public void update() {
		if(outputNode != null) {
			Boolean keepPowered = false;
			for(Wire w : outputNode.getInputWires())
				if(w.isPowered())
					keepPowered = true;
			
			if(!keepPowered || powered) {
				outputNode.setPowered(powered);
				Initialize.pw.addNext(outputNode);
			}
		}
	}
	
	@Override
	public Boolean getCollision(int x, int y) {
		y -= 32;
		x -= 8;
		
		for(int i = 0; i < spots.size() - 1; i++) {
			int x1 = spots.get(i).getXAsInt();
			int y1 = spots.get(i ).getYAsInt();
			int x2 = spots.get(i + 1).getXAsInt();
			int y2 = spots.get(i + 1).getYAsInt();
			
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
	
	public static void updateTempSpot(int x, int y) {
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
	}
	
	public static void leftClick(int x, int y) {
		ArrayList<Wire> wires = Initialize.e.getWires();
		
		Wire wire = null;
		for(Wire w : wires) {
			if(w.getCollision(x, y)) {
				wire = w;
				break;
			}
		}
		
		if(wire != null) {
			wire.destroy();
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
		
	}
	
	public void destroy() {
		//If the wire is being created
		if(this.outputNode == null)
			return;
		
		this.powered = false;
		
		update();
		outputNode.update();
		
		inputNode.removeWire(this);
		outputNode.removeInputWire(this);
		
		this.e.getWires().remove(this);
	}
	
	public ArrayList<Spot> getSpots() {
		return spots;
	}

	public void setSpots(ArrayList<Spot> spots) {
		this.spots = spots;
	}

	public boolean isPowered() {
		return powered;
	}

	public void setPowered(boolean powered) {
		this.powered = powered;
	}

	public Node getInputNode() {
		return inputNode;
	}

	public void setInputNode(Node inputNode) {
		this.inputNode = inputNode;
	}

	public Node getOutputNode() {
		return outputNode;
	}

	public void setOutputNode(Node outputNode) {
		this.outputNode = outputNode;
	}
	
	public void addSpot(Spot s) {
		this.spots.add(s);
	}
	
	public void removeSpot(Spot s) {
		this.spots.remove(s);
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public Spot getTempSpot() {
		return tempSpot;
	}
	
	public void setTempSpot(Spot tempSpot) {
		this.tempSpot = tempSpot;
	}

	public boolean isxAxis() {
		return xAxis;
	}

	public void setxAxis(boolean xAxis) {
		this.xAxis = xAxis;
	}
	
	public void switchAxis() {
		this.xAxis = !this.xAxis;
	}
	
	public Environment getEnvironment() {
		return e;
	}

	public void setEnvironment(Environment e) {
		this.e = e;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	@Override
	public String toString() {
		return "Wire [spots=" + spots + ", powered=" + powered + "]";
	}
}
