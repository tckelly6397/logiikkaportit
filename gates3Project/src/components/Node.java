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
		
		if(getHovered())
			g.setColor(new Color(clamp(g.getColor().getRed() + 20, 255), clamp(g.getColor().getGreen() + 20, 255), clamp(g.getColor().getBlue() + 20, 255)));
			
		g.fillOval(spot.getXAsInt() - (size / 2), spot.getYAsInt() - (size / 2), size, size);
	}
	
	public void update() {
		if(c != null) {
			Initialize.pw.addNext(c);
		}
		
		for(Wire w : wires) {
			w.setPowered(powered);
			Initialize.pw.addNext(w);
		}
		
		if(c != null && inputWires.isEmpty())
			powered = false;
	}
	
	public Boolean getCollision(int x, int y) {
		int distN = (int)Math.abs(Math.sqrt(Math.pow(spot.getXAsInt() - x + 8, 2) + Math.pow(spot.getYAsInt() - y + 30, 2)));
		if(distN < size - 6)
			return true;
		
	    return false;
	}
	
	public static ArrayList<Node> getAllNodes() {
		ArrayList<Node> nodes = new ArrayList<>(Initialize.e.getInputNodes());
		nodes.addAll(Initialize.e.getOutputNodes());
		for(Chip c : Initialize.e.getChips()) {
			nodes.addAll(c.getInputNodes());
			nodes.addAll(c.getOutputNodes());
		}
		
		return nodes;
	}
	
	public static Node getNodeClick(int x, int y) {
		Node node = null;
		
		for(Node n : getAllNodes()) { 
			if(n.getCollision(x, y)) {
				node = n;
				break;
			}
		}
		
		return node;
	}
	
	public static void leftClick(int x, int y, Node node) {
		if(node != null) {
				if(node.isClickable()) {
					node.switchPowered();
					//node.getEnvironment().getPowerThread().setNext(node);
					Initialize.pw.addNext(node);
				}
				
				//Connect node
				for(Wire w : Initialize.e.getWires()) {
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
	}
	
	public static void rightClick(int x, int y, Node node) {
		if(node == null)
			return;

		Boolean goOn = true;
		
		for(Wire w : Initialize.e.getWires()) {
			if(w.isSelected()) {
				goOn = false;
			}
		}
		
		if(goOn) {
			Wire w = new Wire(node, node.getEnvironment());
			w.setSelected(true);
			node.getWires().add(w);
			Initialize.e.getWires().add(w);
		}
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
		return "Node [spot=" + spot + ", size=" + size + ", powered=" + powered + ", wires="
				+ wires + "]";
	}
}
