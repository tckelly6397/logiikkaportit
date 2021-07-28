package gates3Project;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import components.Chip;
import components.Node;
import components.Wire;
import peripherals.MainKeyHandler;
import peripherals.MainMouseHandler;
import utils.Spot;

@SuppressWarnings("serial")
public class Environment extends JPanel {
	private JFrame frame = new JFrame("Gate");
	private PowerThread pw = Initialize.pw;
	private MainMouseHandler mouseHandler = new MainMouseHandler();
	private MainKeyHandler keyHandler = new MainKeyHandler();
	
	private ArrayList<Node> inputNodes = new ArrayList<>();
	private ArrayList<Node> outputNodes = new ArrayList<>();
	private ArrayList<Wire> wires = new ArrayList<>();
	private ArrayList<Chip> chips = new ArrayList<>();
	
	public void paintComponent(Graphics g) {
		for(Chip c : chips)
			c.draw(g);
		
		for(Wire w : wires)
			w.draw(g);
		
		g.setColor(Color.BLACK);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(8, 1, 1));
		
		g.drawRect(60, 120, frame.getWidth() - 130, frame.getHeight() - 240);
		
		for(Node n : outputNodes)
			n.draw(g);
		
		for(Node n : inputNodes)
			n.draw(g);
	}
	
	public Environment(int WIDTH, int HEIGHT) {
		frame.setBackground(new Color(50, 50, 50));
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.addMouseListener(mouseHandler);
	    frame.addMouseMotionListener(mouseHandler);
	    frame.addKeyListener(keyHandler);
	    frame.setSize(WIDTH, HEIGHT);
	    frame.setVisible(true);  
	    frame.add(this);
	    
	    update();
	}
	
	public void update() {
		//Update input nodes
		for(int i = 0; i < inputNodes.size(); i++) {
			int defSize = 30;
			int h = (frame.getHeight() / 2) - ((defSize + 20) * i) + ((defSize + 20) * inputNodes.size() / 2) - ((defSize + 20) / 2);
			inputNodes.get(i).setSpot(new Spot(60, h));
		}
			
		//Update output nodes
		for(int i = 0; i < outputNodes.size(); i++) {
			int defSize = 30;
			int h = (frame.getHeight() / 2) - ((defSize + 20) * i) + ((defSize + 20) * outputNodes.size() / 2) - ((defSize + 20) / 2);
			outputNodes.get(i).setSpot(new Spot(frame.getWidth() - 70, h));
		}
		
		frame.repaint();
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public ArrayList<Node> getInputNodes() {
		return inputNodes;
	}

	public void setInputNodes(ArrayList<Node> inputNodes) {
		this.inputNodes = inputNodes;
	}
	
	public void addInputNode(Node n) {
		inputNodes.add(n);
	}

	public ArrayList<Node> getOutputNodes() {
		return outputNodes;
	}

	public void setOutputNodes(ArrayList<Node> outputNodes) {
		this.outputNodes = outputNodes;
	}
	
	public void addOutputNode(Node n) {
		outputNodes.add(n);
	}

	public ArrayList<Wire> getWires() {
		return wires;
	}

	public void setWires(ArrayList<Wire> wires) {
		this.wires = wires;
	}

	public ArrayList<Chip> getChips() {
		return chips;
	}
	
	public void addChip(Chip chip) {
		chips.add(chip);
	}

	public void setChips(ArrayList<Chip> chips) {
		this.chips = chips;
	}

	public PowerThread getPowerThread() {
		return pw;
	}

	public void setPowerThread(PowerThread pw) {
		this.pw = pw;
	}

	@Override
	public String toString() {
		//return "Environment [frame=" + frame + ", mouseHandler=" + mouseHandler + ", keyHandler=" + keyHandler
			//	+ ", inputNodes=" + inputNodes + ", outputNodes=" + outputNodes + ", wires=" + wires + ", chips="
				//+ chips + "]";
		return "";
	}
}
