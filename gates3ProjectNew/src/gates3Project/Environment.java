package gates3Project;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import components.Node;
import components.Wire;
import components.chips.BasicChip;
import components.chips.Chip;
import components.chips.NotGateChip;
import peripherals.MainKeyHandler;
import peripherals.MainMouseHandler;
import ui.ChipButton;
import ui.DropList;
import ui.Prompt;
import ui.Rectangle;
import ui.frames.ChangeNodesUI;
import ui.frames.CreateChipUI;
import utils.Spot;
import utils.Tools;

@SuppressWarnings("serial")
public class Environment extends JPanel {
	private JFrame frame = new JFrame("Gate");
	private volatile PowerThread pw;
	private MainMouseHandler mouseHandler = new MainMouseHandler();
	private MainKeyHandler keyHandler = new MainKeyHandler();
	
	private ArrayList<Node> inputNodes = new ArrayList<>();
	private ArrayList<Node> outputNodes = new ArrayList<>();
	private ArrayList<Wire> wires = new ArrayList<>();
	private ArrayList<Chip> chips = new ArrayList<>();
	
	private DropList dropList;
	private CreateChipUI createChipUI;
	private ChangeNodesUI changeNodesUI;
	private Rectangle box;
	private Prompt prompt;
	
	private int offsetX = 0;
	private int offsetY = 0;
	
	public void paintComponent(Graphics g) {
		box.fillDraw(g);
		
		//Draw dotted lines
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setColor(new Color(60, 60, 60));
		//These dash lines make me sick
		//Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{10}, 0);
		//g2d.setStroke(dashed);
		
		g2d.translate(offsetX, offsetY);
		g2d.drawLine(-offsetX, box.getLocation().getYAsInt() + (box.getHEIGHT() / 2), frame.getWidth() - offsetX, box.getLocation().getYAsInt() + (box.getHEIGHT() / 2));
		g2d.translate(-offsetX, -offsetY);
		
		g2d.translate(offsetX, offsetY);
		g2d.drawLine(frame.getWidth() / 2, -offsetY, frame.getWidth() / 2, frame.getHeight() - offsetY);
		g2d.translate(-offsetX, -offsetY);
		g2d.setStroke(new BasicStroke(8, 1, 1));
		//
		
		g.translate(offsetX, offsetY);
		for(Wire w : wires)
			w.draw(g);
		
		for(Wire w : wires) {
			if(w.isPowered())
				w.draw(g);
		}
		
		for(Chip c : chips)
			c.draw(g);
		g.translate(-offsetX, -offsetY);
		
		g.setColor(new Color(80, 80, 80));
		
		g.translate(offsetX, offsetY);
		for(Node n : outputNodes)
			n.draw(g);
		
		for(Node n : inputNodes)
			n.draw(g);
		g.translate(-offsetX, -offsetY);
		
		//g2d.scale(.8, .8);
		
		box.invertedDraw(g);
		box.draw(g);
		dropList.draw(g);
		createChipUI.draw(g);
		changeNodesUI.draw(g);
		prompt.draw(g);
	}
	
	public Environment(int WIDTH, int HEIGHT) {
		frame.setBackground(new Color(50, 50, 50));
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(WIDTH, HEIGHT);
	    frame.setVisible(true);  
	    frame.add(this);
	    
	    box = new Rectangle(60, 120, frame.getWidth() - 130, frame.getHeight() - 240);
	    prompt = new Prompt();
	    
	    pw = new PowerThread();
	    
	    NodeClockThread nct = new NodeClockThread();
	    Thread thread = new Thread(nct);
	    thread.start();
	}
	
	public void addUserInput() {
		frame.addMouseListener(mouseHandler);
	    frame.addMouseMotionListener(mouseHandler);
	    frame.addMouseWheelListener(mouseHandler);
	    frame.addKeyListener(keyHandler);
	}
	
	public void beginPowerThread() {
		Thread t1 = new Thread(pw);
		t1.start();
	}
	
	public void run() {
		while(true) {
	    	update();
	    	
	    	try {
	    		Thread.sleep(17);
	    	}catch(InterruptedException e) {
	    		
	    	}
	    }
	}
	
	public void update() {
		//Update input nodes
		for(int i = 0; i < inputNodes.size(); i++) {
			int defSize = 30;
			int h = (frame.getHeight() / 2) - ((defSize + 20) * i) + ((defSize + 20) * inputNodes.size() / 2) - ((defSize + 20) / 2);
			inputNodes.get(i).setSpot(new Spot(95 + (defSize / 2), h));
		}
			
		//Update output nodes
		for(int i = 0; i < outputNodes.size(); i++) {
			int defSize = 30;
			int h = (frame.getHeight() / 2) - ((defSize + 20) * i) + ((defSize + 20) * outputNodes.size() / 2) - ((defSize + 20) / 2);
			outputNodes.get(i).setSpot(new Spot(frame.getWidth() - 105 - (defSize / 2), h));
		}
		
		//Update dropList
		dropList.setLocation(new Spot(frame.getWidth() - 222, 80));
		createChipUI.setLocation(new Spot(60, 110));
		createChipUI.setWIDTH(frame.getWidth() - 130 - dropList.getWIDTH() - 20);
		
		box.setHEIGHT(frame.getHeight() - 240);
		box.setWIDTH(frame.getWidth() - 130);
		
		prompt.updateLocation();
		
		changeNodesUI.updateLocations();
		
		frame.repaint();
	}
	
	public Chip createChip(ArrayList<Node> allNodes, ArrayList<Node> firstInputNodes, ArrayList<Node> firstOutputNodes, String name, Color color) {
		BasicChip ch = new BasicChip(400, 200, color, Initialize.e, name);

		//Deep cloning
		ArrayList<Node> inputNodes = new ArrayList<>();
		ArrayList<Node> outputNodes = new ArrayList<>();
		ArrayList<Node> nodes = new ArrayList<>();
		
		//set input nodes and output wires
		for(int i = 0; i < allNodes.size(); i++) {
			Node newNode = new Node(allNodes.get(i));
			newNode.setSize(20);
			
			for(Node inputN : firstInputNodes) {
				if(allNodes.get(i) == inputN) {
					newNode.setClickable(false);
					inputNodes.add(newNode);
				}
			}
			
			for(Node inputN : firstOutputNodes) {
				if(allNodes.get(i) == inputN) {
					outputNodes.add(newNode);
				}
			}
			
			ArrayList<Wire> wires = allNodes.get(i).getWires(); 
			for(int l = 0; l < wires.size(); l++) {
				Wire newWire = new Wire(newNode, Initialize.e);
				newNode.addWire(newWire);				
			}

			nodes.add(newNode);
		}
		
		//set input wires
		for(int i = 0; i < allNodes.size(); i++) {
			Node n = allNodes.get(i);
			
			//Set the not gate
			if(n.getC() != null && n.getC().getClass() == NotGateChip.class) {
				for(int j = 0; j < allNodes.size(); j++) {
					Node n2 = allNodes.get(j);
					
					if(n2 == n.getC().getOutputNodes().get(0)) {
						Chip c = allNodes.get(i).getC();
						NotGateChip notGateChip = new NotGateChip(c);
						notGateChip.setInputNodes(new ArrayList<Node>());
						notGateChip.setOutputNodes(new ArrayList<Node>());
						
						nodes.get(i).setC(notGateChip);
						notGateChip.addInput(nodes.get(i));
						notGateChip.addOutput(nodes.get(j));
					}
				}
			}
			
			for(int j = 0; j < allNodes.size(); j++) {
				Node n2 = allNodes.get(j);
				
				for(int k = 0; k < n.getWires().size(); k++) {
					Wire w = n.getWires().get(k);
					
					if(w.getOutputNode() == n2) {
						Node newN = nodes.get(j);
						Wire newW = nodes.get(i).getWires().get(k);
						
						newN.addInputWire(newW);
						newW.setOutputNode(newN);
					}
				}
			}
		}
		//End of Deep cloning

		ch.setAllNodes(nodes);
		ch.setInputNodes(inputNodes);
		ch.setOutputNodes(outputNodes);
		return ch;
	}
	
	public void createChipButton() {
		if(chipNameTaken(createChipUI.getNameLabel().getLabel())) {
			prompt.setShown(true);
			prompt.setLabel("Duplicite Chip Names");
			return;
		}
		
		Chip chip = createChip(Node.getAllNodes(), Initialize.e.getInputNodes(), Initialize.e.getOutputNodes(), createChipUI.getNameLabel().getLabel(), createChipUI.getColor());
		Initialize.e.getDropList().getButtons().add(new ChipButton(new Spot(0, 0), chip));
		Initialize.e.getDropList().setButtonLocations();
		chip.save();
	}
	
	public boolean chipNameTaken(String name) {
		ArrayList<String> names = Tools.getChipFileNames();
		
		for(String n : names)
			if(name.equals(n))
				return true;
		
		return false;
	}
	
	public void setWait(int x) {
		synchronized(this) {
			try {
				synchronized(Initialize.e) {
					this.wait(x);
				}
            } catch (InterruptedException e)  {
                Thread.currentThread().interrupt(); 
            }
		}
	}
	
	public void closePrompt() {
		this.prompt.setShown(false);
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
	
	public void setInputsFalse() {
		for(Node n : inputNodes)
			n.setPowered(false);
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

	public DropList getDropList() {
		return dropList;
	}

	public void setDropList(DropList dropList) {
		this.dropList = dropList;
	}

	public CreateChipUI getCreateChipUI() {
		return createChipUI;
	}

	public void setCreateChipUI(CreateChipUI createChipUI) {
		this.createChipUI = createChipUI;
	}
	
	public ChangeNodesUI getChangeNodesUI() {
		return this.changeNodesUI;
	}
	
	public void setChangeNodesUI(ChangeNodesUI cnUI) {
		this.changeNodesUI = cnUI;
	}

	public Rectangle getBox() {
		return box;
	}

	public void setBox(Rectangle box) {
		this.box = box;
	}
	
	public Prompt getPrompt() {
		return prompt;
	}

	public void setPrompt(Prompt prompt) {
		this.prompt = prompt;
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
	
	public void translateOffsetX(int x) {
		this.offsetX += x;
	}
	
	public void translateOffsetY(int y) {
		this.offsetY += y;
	}

	@Override
	public String toString() {
		//return "Environment [frame=" + frame + ", mouseHandler=" + mouseHandler + ", keyHandler=" + keyHandler
			//	+ ", inputNodes=" + inputNodes + ", outputNodes=" + outputNodes + ", wires=" + wires + ", chips="
				//+ chips + "]";
		return "t";
	}
}
