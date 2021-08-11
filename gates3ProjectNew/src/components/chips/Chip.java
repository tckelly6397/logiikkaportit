package components.chips;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import colliders.Collider;
import colliders.RectangleCollider;
import components.Component;
import components.Node;
import gates3Project.Environment;
import gates3Project.Initialize;
import ui.Rectangle;
import utils.Spot;
import utils.Tools;

public abstract class Chip extends Component {
	private ArrayList<Node> inputNodes = new ArrayList<>();
	private ArrayList<Node> outputNodes = new ArrayList<>();
	private ArrayList<Node> allNodes = new ArrayList<>();
	private Environment e;
	private Spot location;
	private Color color;
	private Boolean selected = false;
	private String label = "";
	
	private int offsetX = 0;
	private int offsetY = 0;
	public int WIDTH = 80;
	public int HEIGHT = 35;
	
	public abstract Chip getNewChip();
	public abstract void update();
	public abstract void save();
	
	public Chip(int x, int y, Color c, Environment e, String label) {
		this.location = new Spot(x, y);
		this.color = c;
		this.e = e;
		this.label = label;
		
		setHeight();
		setNodeLocations();
		setCollider(new RectangleCollider(this, location, WIDTH, HEIGHT));
		Collider.registerCollider(getCollider());
	}
	
	public Chip(Chip c) {
		this.location = new Spot(c.getX(), c.getY());
		this.color = c.getColor();
		this.e = c.getEnvironment();
		this.label = c.label;
		
		setHeight();
		setNodeLocations();
		setCollider(new RectangleCollider(this, location, WIDTH, HEIGHT));
		Collider.registerCollider(getCollider());
	}
	
	public void draw(Graphics g) {
		int x = location.getXAsInt();
		int y = location.getYAsInt();
		
		g.setColor(color);
		
		if(getHovered()) {
			g.setColor(new Color(Tools.clamp(g.getColor().getRed() + 50, 255), Tools.clamp(g.getColor().getGreen() + 50, 255), Tools.clamp(g.getColor().getBlue() + 50, 255)));
		}
			
		g.fillRoundRect(x, y, WIDTH, HEIGHT, 15, 15);
		
		for(Node n : inputNodes)
			n.draw(g);
		
		for(Node n : outputNodes)
			n.draw(g);
		
		setWidth(g);
		setHeight();
		setNodeLocations();
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Verdana", Font.BOLD, 25));
		g.drawString(label, x + 11, y + HEIGHT - ((HEIGHT - 20) / 2));
	}
	
	public void setWidth(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setFont(new Font("Verdana", Font.BOLD, 25));
		int stringWidth = (int) g2d.getFontMetrics().getStringBounds(label, g2d).getWidth();
		
		this.WIDTH = stringWidth + 28;
	}
	
	public void setHeight() {
		int nodeCount = inputNodes.size();
		if(outputNodes.size() > nodeCount)
			nodeCount = outputNodes.size();
		
		HEIGHT = nodeCount * (20 + 5);
		
		if(getCollider() != null)
			((RectangleCollider)(getCollider())).setHeight(HEIGHT);
	}
	
	public void setNodeLocations() {
		int x = location.getXAsInt();
		int y = location.getYAsInt();
		
		for(int i = 0; i < inputNodes.size(); i++) {
			int defSize = 20;
			int h = y + (HEIGHT / 2) - ((defSize + 5) * i) + ((defSize + 5) * inputNodes.size() / 2) - ((defSize + 5) / 2);
			inputNodes.get(i).setSpot(new Spot(x, h));
		}
		
		for(int i = 0; i < outputNodes.size(); i++) {
			int h = y + (HEIGHT / 2) - (HEIGHT / outputNodes.size() * i);
			if(outputNodes.size() > 1)
				h += (HEIGHT / 2) - (HEIGHT / outputNodes.size() / 2);
			
			outputNodes.get(i).setSpot(new Spot(x + WIDTH, h));
		}
	}
	
	@Override
	public void leftClick(int x, int y) {
		// TODO Auto-generated method stub
	}

	@Override
	public void rightClick(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void middleClick(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drag(int x, int y) {
		//Handled inside of the Collider
	}

	@Override
	public void mouseReleased(int x, int y) {
		//ArrayList for concurrent modification
		ArrayList<Chip> destroyThese = new ArrayList<>();
		
		for(Chip c : Initialize.e.getChips()) {
			c.setSelected(false);
			c.setOffsetX(0);
			c.setOffsetY(0);
			
			if(!c.isInPlay())
				destroyThese.add(c);
		}
		
		for(int i = destroyThese.size() - 1; i >= 0; i--)
			destroyThese.get(i).destroy();
	}

	@Override
	public void mousePressed(int x, int y) {
		setSelected(true);
		setOffsetX(getX() - x);
		setOffsetY(getY() - y);
	}
	
	//This should be changed to use Rectangle(Box) getCollision function
	//I'm just too lazy right now because its late
	public boolean isInPlay() {
		int x1 = location.getXAsInt() + (Initialize.e.getOffsetX());;
		int y1 = location.getYAsInt() + (Initialize.e.getOffsetY());;
		int x2 = location.getXAsInt() + WIDTH + (Initialize.e.getOffsetX());;
		int y2 = location.getYAsInt() + HEIGHT + (Initialize.e.getOffsetY());;
		
		Rectangle box = Initialize.e.getBox();
		
		int bx1 = box.getX();
		int by1 = box.getY();
		int bx2 = box.getX() + box.getWIDTH();
		int by2 = box.getY() + box.getHEIGHT();
		
		if(x1 > bx1 && x2 < bx2 && y1 > by1 && y2 < by2)
			return true;
		
		return false;
	}
	
	public void destroy() {
		for(Node n : allNodes) {
			n.destroy(false, false);
		}
		
		e.getChips().remove(this);
		
		Collider.unRegisterCollider(getCollider());
	}
	
	public ArrayList<Node> getInputNodes() {
		return inputNodes;
	}

	public void setInputNodes(ArrayList<Node> inputNodes) {
		this.inputNodes = inputNodes;
	}
	
	public void addInput(Node n) {
		this.inputNodes.add(n);
		this.allNodes.add(n);
	}

	public ArrayList<Node> getOutputNodes() {
		return outputNodes;
	}

	public void setOutputNodes(ArrayList<Node> outputNodes) {
		this.outputNodes = outputNodes;
	}
	
	public void addOutput(Node n) {
		this.outputNodes.add(n);
		this.allNodes.add(n);
	}

	public int getX() {
		return location.getXAsInt();
	}

	public void setX(int x) {
		this.location.setX(x);
		setNodeLocations();
		((RectangleCollider)(getCollider())).setLocation(location);
	}
	
	public void translateX(int x) {
		this.location.setX(this.location.getX() + x);
		setNodeLocations();
		((RectangleCollider)(getCollider())).setLocation(location);
	}

	public int getY() {
		return location.getYAsInt();
	}

	public void setY(int y) {
		this.location.setY(y);
		setNodeLocations();
		((RectangleCollider)(getCollider())).setLocation(location);
	}
	
	public void translateY(int y) {
		this.location.setY(this.location.getY() + y);
		setNodeLocations();
		((RectangleCollider)(getCollider())).setLocation(location);
	}

	public Color getColor() {
		return color;
	}

	public Environment getEnvironment() {
		return e;
	}

	public void setEnvironment(Environment e) {
		this.e = e;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}
	
	public ArrayList<Node> getAllNodes() {
		return allNodes;
	}
	
	public void setAllNodes(ArrayList<Node> nodes) {
		this.allNodes = nodes;
	}
	
	public void addAAllNode(Node n) {
		this.allNodes.add(n);
	}
	
	@Override
	public String toString() {
		return "Chip [location=" + location + ", color=" + color + ", selected=" + selected + ", label=" + label
				+ ", offsetX=" + offsetX + ", offsetY=" + offsetY + ", WIDTH=" + WIDTH + ", HEIGHT=" + HEIGHT + "]";
	}
}
