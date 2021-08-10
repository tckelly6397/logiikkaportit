package components.chips;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import components.Component;
import components.Node;
import components.Wire;
import components.chips.save.PowerSaveObj;
import gates3Project.Environment;

public class BasicChip extends Chip {
	
	public BasicChip(int x, int y, Color c, Environment e, String label) {
		super(x, y, c, e, label);
	}
	
	public BasicChip(Chip c) {
		super(c);
	}

	@Override
	public Chip getNewChip() {
		BasicChip bc = new BasicChip(this);
		bc.setAllNodes(new ArrayList<Node>(this.getAllNodes()));
		bc.setInputNodes(new ArrayList<Node>(this.getInputNodes()));
		bc.setOutputNodes(new ArrayList<Node>(this.getOutputNodes()));
		return bc;
	}
	
	@Override
	public void update() {	
		for(Node n : getInputNodes()) {
			this.getEnvironment().getPowerThread().addNext(n);
		}
	}
	
	@Override
	public void save() {
		//Gather all the components
		ArrayList<Component> components = new ArrayList<>();
		ArrayList<PowerSaveObj> saveObjects = new ArrayList<>();
		
		ArrayList<Wire> wires = new ArrayList<>();
		ArrayList<NotGateChip> notGates = new ArrayList<>();
		
		for(Node n : getAllNodes())
			wires.addAll(n.getWires());
		
		for(Node n : getAllNodes()) {
			if(n.getC() != null && n.getC().getClass() == NotGateChip.class)
				notGates.add((NotGateChip)n.getC());
		}
		
		components.addAll(getAllNodes());
		components.addAll(wires);
		components.addAll(notGates);
		
		//Setup labels
		for(int i = 0; i < components.size(); i++) {
			Component comp = components.get(i);
			PowerSaveObj pso = new PowerSaveObj(i);
			
			if(comp.getClass() == Node.class) {
				pso.setLabel("node");
				pso.setPowered(((Node)comp).isPowered());
				pso.setClock(((Node)comp).isClock());
			}else if(comp.getClass() == Wire.class) {
				pso.setLabel("wire");
				pso.setPowered(((Wire)comp).isPowered());
			}else if(comp.getClass() == NotGateChip.class) {
				pso.setLabel("noteGate");
			}
			
			saveObjects.add(pso);
		}
		
		//Set Nodes not gate Id's
		int ngId = components.size() - notGates.size();
		for(int i = 0; i < getAllNodes().size(); i++) {
			Node n = getAllNodes().get(i);
			if(n.getC() != null && n.getC().getClass() == NotGateChip.class) {
				saveObjects.get(i).setNgId(ngId);
				saveObjects.get(ngId).addInputId(i);
				ngId++;
			}
		}
		
		//Set not gate output node Id's
		for(int i = 0; i < getAllNodes().size(); i++) {
			Node n1 = getAllNodes().get(i);
			
			for(int j = 0; j < notGates.size(); j++) {
				NotGateChip notGate = notGates.get(j);
				
				if(n1 == notGate.getOutputNodes().get(0))
					saveObjects.get(components.size() - notGates.size() + j).addOutputId(i);
			}
		}
		
		//Set wires
		for(int i = 0; i < getAllNodes().size(); i++) {
			Node node = getAllNodes().get(i);

			//Set output wires
			for(int j = 0; j < node.getWires().size(); j++) {
				Wire w1 = node.getWires().get(j);
				
				for(int k = 0; k < wires.size(); k++) {
					Wire w2 = wires.get(k);
					
					if(w1 == w2) {
						saveObjects.get(i).addOutputId(k + getAllNodes().size());
						saveObjects.get(k + getAllNodes().size()).addInputId(i);
					}
					
				}	
			}
			
			//Set input wires
			for(int j = 0; j < node.getInputWires().size(); j++) {
				Wire w1 = node.getInputWires().get(j);
				
				for(int k = 0; k < wires.size(); k++) {
					Wire w2 = wires.get(k);
					
					if(w1 == w2) {
						saveObjects.get(i).addInputId(k + getAllNodes().size());
						saveObjects.get(k + getAllNodes().size()).addOutputId(i);
					}
					
				}	
			}
		}
		
		//Get input and output Id's
		String inputIds = "";
		String outputIds = "";
		for(int i = 0; i < getAllNodes().size(); i++) {
			Component c = getAllNodes().get(i);
			if(c.getClass() != Node.class)
				continue;
			
			for(Node n : getInputNodes()) {
				if(n == c)
					inputIds += i + ", ";
			}
			
			for(Node n : getOutputNodes()) {
				if(n == c)
					outputIds += i + ", ";
			}
		}
		
		inputIds = inputIds.substring(0, inputIds.length() - 2);
		outputIds = outputIds.substring(0, outputIds.length() - 2);
		
		//Write to a file
	    BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter("chips/" + getLabel(), true));
			String header = "";
			header += "{Label:" + getLabel() + ", Color:[" + getColor().getRed() + ", " + getColor().getGreen() + ", "  + getColor().getBlue() + "], InputIds:[" + inputIds + "], OutputIds:[" + outputIds + "]}";
			writer.append(header);
			writer.newLine();
			
			for(PowerSaveObj pso : saveObjects) {
				writer.append(pso.toString());
				writer.newLine();
			}
		    
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "BasicChip [getX()=" + getX() + ", getY()=" + getY() + ", getColor()=" + getColor()
				+ ", getEnvironment()=" + getEnvironment() + ", getLabel()=" + getLabel() + "]";
	}
}
