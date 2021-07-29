package components;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import gates3Project.Environment;
import gates3Project.Initialize;
import utils.Spot;

public class Node extends Component {
	private Spot spot;
	private int size;
	private Environment e;
	private boolean powered;
	private boolean hovered = false;
	private ArrayList<Wire> wires = new ArrayList<>();
	private ArrayList<Wire> inputWires = new ArrayList<>();
	private boolean clickable;
	private Chip c;
	
	public Node(int x, int y, int size, boolean clickable, Environment e) {
		this.spot = new Spot(x, y);
		this.powered = false;
		this.size = size;
		this.clickable = clickable;
		this.e = e;
	}
	
	public Node(int x, int y, int size, boolean clickable, Chip c, Environment e) {
		this.spot = new Spot(x, y);
		this.powered = false;
		this.size = size;
		this.clickable = clickable;
		this.c = c;
		this.e = e;
	}
	
	public Node(int x, int y, int size, boolean powered, boolean clickable, Environment e) {
		this.spot = new Spot(x, y);
		this.powered = powered;
		this.size = size;
		this.clickable = clickable;
		this.e = e;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		
		if(powered)
			g.setColor(new Color(255, 0, 0));
		
		if(hovered)
			g.setColor(new Color(clamp(g.getColor().getRed() + 20, 255), clamp(g.getColor().getGreen() + 20, 255), clamp(g.getColor().getBlue() + 20, 255)));
			
		g.fillOval(spot.getXAsInt() - (size / 2), spot.getYAsInt() - (size / 2), size, size);
	}
	
	public void update() {
		if(c != null) {
			Initialize.pw.addNext(c);
			c.setUsed(true);
		}
		
		for(Wire w : wires) {
			w.setPowered(powered);
			Initialize.pw.addNext(w);
			w.setUsed(true);
		}
		
		setUsed(false);
	}
	
	public Node getCollision(int x, int y) {
		int distN = (int)Math.abs(Math.sqrt(Math.pow(spot.getXAsInt() - x + 8, 2) + Math.pow(spot.getYAsInt() - y + 30, 2)));
		if(distN < size - 6)
			return this;
		
		hovered = false;
	    return null;
	}

	public Spot getSpot() {
		return spot;
	}

	public void setSpot(Spot spot) {
		this.spot = spot;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isPowered() {
		return powered;
	}

	public void setPowered(boolean powered) {
		this.powered = powered;
	}
	
	public void switchPowered() {
		this.powered = !this.powered;
	}

	public boolean isHovered() {
		return hovered;
	}

	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}

	public ArrayList<Wire> getWires() {
		return wires;
	}

	public void setWires(ArrayList<Wire> wires) {
		this.wires = wires;
	}
	
	public void addWire(Wire w) {
		this.wires.add(w);
	}
	
	public void removeWire(Wire w) {
		this.wires.remove(w);
	}
	
	public ArrayList<Wire> getInputWires() {
		return inputWires;
	}

	public void setInputWires(ArrayList<Wire> wires) {
		this.inputWires = wires;
	}
	
	public void addInputWire(Wire w) {
		this.inputWires.add(w);
	}
	
	public void removeInputWire(Wire w) {
		this.inputWires.remove(w);
	}

	public boolean isClickable() {
		return clickable;
	}

	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}

	public Chip getC() {
		return c;
	}

	public void setC(Chip c) {
		this.c = c;
	}
	
	public Environment getEnvironment() {
		return e;
	}

	public void setEnvironment(Environment e) {
		this.e = e;
	}

	@Override
	public String toString() {
		return "Node [spot=" + spot + ", size=" + size + ", powered=" + powered + ", hovered=" + hovered + ", wires="
				+ wires + "]";
	}
}
