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
	private int x;
	private int y;
	private Color color;
	
	public int WIDTH = 100;
	public int HEIGHT = 50;
	
	public Chip(int x, int y, Color c, Environment e) {
		this.x = x;
		this.y = y;
		this.color = c;
		this.table = new TruthTable();
		this.e = e;
		
		setNodeLocations();
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRoundRect(x, y, WIDTH, HEIGHT, 15, 30);
		
		for(Node n : inputNodes)
			n.draw(g);
		
		for(Node n : outputNodes)
			n.draw(g);
	}
	
	public void update() {
		updateOutput();
		for(Node n : outputNodes) {
			Initialize.pw.addNext(n);
			n.setUsed(true);
		}
		
		setUsed(false);
	}
	
	public void setNodeLocations() {
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
			outputNodes.get(i).update();
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
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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
}
