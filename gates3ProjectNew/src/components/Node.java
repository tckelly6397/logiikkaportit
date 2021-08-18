package components;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import colliders.CircleCollider;
import colliders.Collider;
import components.chips.Chip;
import components.chips.LedSwitch;
import components.chips.NotGateChip;
import gates3Project.Environment;
import gates3Project.Initialize;
import utils.Spot;
import utils.Tools;

public class Node extends Component {
	private Spot spot;
	private int size;
	private Environment e;
	private boolean powered;
	private ArrayList<Wire> wires = new ArrayList<>();
	private ArrayList<Wire> inputWires = new ArrayList<>();
	private boolean clickable;
	private boolean clock;
	private Chip c;
	
	public Node(int x, int y, int size, boolean clickable, Environment e) {
		this.spot = new Spot(x, y);
		this.powered = false;
		this.size = size;
		this.clickable = clickable;
		this.e = e;
		setCollider(new CircleCollider(this, spot, size));
		Collider.registerCollider(getCollider());
	}
	
	public Node(int x, int y, int size, boolean clickable, Chip c, Environment e) {
		this.spot = new Spot(x, y);
		this.powered = false;
		this.size = size;
		this.clickable = clickable;
		this.c = c;
		this.e = e;
		setCollider(new CircleCollider(this, spot, size));
		Collider.registerCollider(getCollider());
	}
	
	public Node(int x, int y, int size, boolean powered, boolean clickable, Environment e) {
		this.spot = new Spot(x, y);
		this.powered = powered;
		this.size = size;
		this.clickable = clickable;
		this.e = e;
		setCollider(new CircleCollider(this, spot, size));
		Collider.registerCollider(getCollider());
	}
	
	public Node(Node n) {
		this.spot = n.getSpot();
		this.size = n.getSize();
		this.clickable = n.isClickable();
		this.e = n.getEnvironment();	
		this.powered = false;
		this.clock = n.isClock();
		setCollider(new CircleCollider(this, spot, size));
		Collider.registerCollider(getCollider());
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		
		if(powered)
			g.setColor(new Color(255, 0, 0));
		
		if(getHovered())
			g.setColor(new Color(Tools.clamp(g.getColor().getRed() + 20, 255), Tools.clamp(g.getColor().getGreen() + 20, 255), Tools.clamp(g.getColor().getBlue() + 20, 255)));
			
		g.fillOval(spot.getXAsInt() - (size / 2), spot.getYAsInt() - (size / 2), size, size);
	}
	
	public void update() {
		if(c != null && c instanceof NotGateChip) {
			e.getPowerThread().addNext(c);
		}
		
		if(c != null && c instanceof LedSwitch) {
			e.getPowerThread().addNext(c);
		}
		
		for(Wire w : wires) {
			w.setPowered(powered);
			e.getPowerThread().addNext(w);
		}
		
		if(c != null && inputWires.isEmpty())
			powered = false;
	}
	
	public static ArrayList<Node> getAllNodes() {
		ArrayList<Node> nodes = new ArrayList<>(Initialize.e.getInputNodes());
		nodes.addAll(Initialize.e.getOutputNodes());
		for(Chip c : Initialize.e.getChips()) {
			nodes.addAll(c.getAllNodes());
		}
		
		return nodes;
	}
	
	@Override
	public void leftClick(int x, int y) {
		if(clickable) {
			switchPowered();
			e.getPowerThread().addNext(this);
		}
		
		//Connect node
		for(Wire w : Initialize.e.getWires()) {
			if(w.isSelected()) {
				if(w.getSpots().size() == 1) {
					int dist = spot.getXAsInt() - w.getSpots().get(0).getXAsInt();
					Spot s1 = new Spot(spot.getXAsInt() - (dist / 2), w.getSpots().get(0).getYAsInt());
					Spot s2 = new Spot(spot.getXAsInt() - (dist / 2), spot.getYAsInt());
							
					w.addSpot(s1);
					w.addSpot(s2);
				}
				else {
					Spot last = w.getSpots().get(w.getSpots().size() - 1);
					last = new Spot(last.getX(), spot.getYAsInt());
					
					if(!w.isxAxis())
						w.getSpots().add(new Spot(last.getX(), last.getY()));
					
					w.getSpots().get(w.getSpots().size() - 1).setSpot(last);
				}
				
				w.getSpots().add(spot);
				w.setTempSpot(null);
				w.setSelected(false);
				addInputWire(w);
				w.setOutputNode(this);
				w.update();
			}
		}	
	}

	@Override
	public void rightClick(int x, int y) {		
		Boolean goOn = true;
		
		for(Wire w : Initialize.e.getWires()) {
			if(w.isSelected()) {
				goOn = false;
			}
		}
		
		if(goOn) {
			Wire w = new Wire(this, e);
			w.setSelected(true);
			wires.add(w);
			Initialize.e.getWires().add(w);
		}
		
	}

	@Override
	public void middleClick(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drag(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(int x, int y) {
		// TODO Auto-generated method stub
	}
	
	public void destroy(Boolean isInput, Boolean isOutput) {
		int wiresSize = wires.size();
		for(int i = wiresSize - 1; i >= 0; i--) 
			wires.get(i).destroy();
		
		wiresSize = inputWires.size();
		for(int i = wiresSize - 1; i >= 0; i--) 
			inputWires.get(i).destroy();
		
		if(isInput)
			Initialize.e.getInputNodes().remove(this);
		
		if(isOutput)
			Initialize.e.getOutputNodes().remove(this);
		
		Collider.unRegisterCollider(getCollider());
	}

	public Spot getSpot() {
		return spot;
	}

	public void setSpot(Spot spot) {
		this.spot = spot;
		((CircleCollider)(getCollider())).setLocation(spot);;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
		((CircleCollider)(getCollider())).setSize(size);
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

	public boolean isClock() {
		return clock;
	}

	public void setClock(boolean clock) {
		this.clock = clock;
	}

	@Override
	public String toString() {
		String label = "not available";
		if(this.c != null)
			label = c.getLabel();
		
		return "Node [spot=" + spot + ", size=" + size + ", powered=" + powered + ", wires="
				+ wires + ", Environment=" + e + ", Chip=" + label + "]";
	}
}
