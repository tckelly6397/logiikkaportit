package components;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import gates3Project.Environment;
import gates3Project.Initialize;
import utils.Spot;
import utils.TruthTable;

public class Chip extends Component {
	private ArrayList<Node> inputNodes = new ArrayList<>();
	private ArrayList<Node> outputNodes = new ArrayList<>();
	private TruthTable table;
	private Environment e;
	private Spot location;
	private Color color;
	private Boolean selected = false;
	private String label = "";
	
	private int offsetX = 0;
	private int offsetY = 0;
	
	public int WIDTH = 100;
	public int HEIGHT = 50;
	
	public Chip(int x, int y, Color c, Environment e) {
		this.location = new Spot(x, y);
		this.color = c;
		this.table = new TruthTable();
		this.e = e;
		
		setNodeLocations();
	}
	
	public Chip(Chip c) {
		this.location = new Spot(c.getX(), c.getY());
		this.color = c.getColor();
		this.table = c.getTable();
		this.e = c.getEnvironment();
		this.label = c.label;
		
		for(Node n : c.getInputNodes()) {
			n.setC(this);
			this.inputNodes.add(new Node(n));
		}
		
		for(Node n : c.getOutputNodes()) {
			n.setC(null);
			this.outputNodes.add(new Node(n));
		}
		
		setNodeLocations();
	}
	
	public void draw(Graphics g) {
		int x = location.getXAsInt();
		int y = location.getYAsInt();
		
		g.setColor(color);
		
		if(getHovered()) {
			g.setColor(new Color(clamp(g.getColor().getRed() + 50, 255), clamp(g.getColor().getGreen() + 50, 255), clamp(g.getColor().getBlue() + 50, 255)));
		}
			
		g.fillRoundRect(x, y, WIDTH, HEIGHT, 15, 15);
		
		for(Node n : inputNodes)
			n.draw(g);
		
		for(Node n : outputNodes)
			n.draw(g);
	}
	
	public void update() {
		updateOutput();
		for(Node n : outputNodes) {
			Initialize.pw.addNext(n);
		}
	}
	
	public void setNodeLocations() {
		int x = location.getXAsInt();
		int y = location.getYAsInt();
		
		for(int i = 0; i < inputNodes.size(); i++) {
			int defSize = 20;
			int h = y + (HEIGHT / 2) - ((defSize + 20) * i) + ((defSize + 20) * inputNodes.size() / 2) - ((defSize + 20) / 2);
			inputNodes.get(i).setSpot(new Spot(x, h));
		}
		
		for(int i = 0; i < outputNodes.size(); i++) {
			int defSize = 20;
			int h = y + (HEIGHT / 2) - ((defSize + 20) * i) + ((defSize + 20) * outputNodes.size() / 2) - ((defSize + 20) / 2);
			outputNodes.get(i).setSpot(new Spot(x + WIDTH, h));
		}
	}
	
	public void updateOutput() {
		ArrayList<Boolean> inputs = new ArrayList<>();
		for(Node n : inputNodes) 
			inputs.add(n.isPowered());
		
		Boolean[] truth = table.getOutputs(inputs);
		if(truth == null)
			return;
		
		for(int i = 0; i < truth.length; i++) {
			outputNodes.get(i).setPowered(truth[i]);
		}
	}
	
	public Boolean getCollision(int x, int y) {
		x -= 10;
		y -= 30;
		
		int x1 = location.getXAsInt();
		int y1 = location.getYAsInt();
		int x2 = location.getXAsInt() + WIDTH;
		int y2 = location.getYAsInt() + HEIGHT;
		
		if(x > x1 && x < x2 && y > y1 && y < y2)
			return true;
		
		return false;
	}
	
	public static void drag(int x, int y) {
		for(Chip c : Initialize.e.getChips()) {
			if(c.getSelected()) {
				c.translateX(x - c.getX() + c.getOffsetX());
				c.translateY(y - c.getY() + c.getOffsetY());
				
				break;
			}
		}
	}
	
	public static void pressed(int x, int y) {
		for(Chip c : Initialize.e.getChips()) {
			if(c.getCollision(x, y)) {
				c.setSelected(true);
				c.setOffsetX(c.getX() - x);
				c.setOffsetY(c.getY() - y);
				break;
			}		
		}
	}
	
	public static void mouseReleased() {
		for(Chip c : Initialize.e.getChips()) {
			c.setSelected(false);
			c.setOffsetX(0);
			c.setOffsetY(0);
		}
	}

	public ArrayList<Node> getInputNodes() {
		return inputNodes;
	}

	public void setInputNodes(ArrayList<Node> inputNodes) {
		this.inputNodes = inputNodes;
	}
	
	public void addInput(Node n) {
		this.inputNodes.add(n);
	}

	public ArrayList<Node> getOutputNodes() {
		updateOutput();
		return outputNodes;
	}

	public void setOutputNodes(ArrayList<Node> outputNodes) {
		this.outputNodes = outputNodes;
	}
	
	public void addOutput(Node n) {
		this.outputNodes.add(n);
	}

	public TruthTable getTable() {
		return table;
	}

	public void setTable(TruthTable table) {
		this.table = table;
	}

	public int getX() {
		return location.getXAsInt();
	}

	public void setX(int x) {
		this.location.setX(x);
		setNodeLocations();
	}
	
	public void translateX(int x) {
		this.location.setX(this.location.getX() + x);
		setNodeLocations();
	}

	public int getY() {
		return location.getYAsInt();
	}

	public void setY(int y) {
		this.location.setY(y);
		setNodeLocations();
	}
	
	public void translateY(int y) {
		this.location.setY(this.location.getY() + y);
		setNodeLocations();
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
}
