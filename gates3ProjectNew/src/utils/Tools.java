package utils;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import components.Node;
import components.Wire;
import components.chips.NotGateChip;
import components.chips.save.PowerSaveObj;
import gates3Project.Initialize;
import ui.ChipButton;

public class Tools {
	
	public static int clamp(int num, int max) {
		if(num > max) return max;
		return num;
	}
	
	//Return an ArrayList of string arrays
	//I hope for the rest of my life that I will never have to look at this again.
	//Up till 5 am and then waking up at 11, working on it till 2 just to get this working and
	//As of now I probably can't explain all of it.  I hate parsing strings.
	public static ArrayList<String[]> parseTSON(String input) {
		ArrayList<String> variables = new ArrayList<>();
		
		//Handle the annoying multiples
		String lastInput = new String(input);
		while(lastInput.indexOf("[") != -1) {
			int s1 = lastInput.indexOf("[");
			int s2 = lastInput.indexOf("]", s1);
			String fullL = lastInput.substring(0, lastInput.indexOf("[") + (s2 - s1) + 1);
			String[] ssssis = lastInput.substring(0, lastInput.indexOf("[")).split(",");
			ssssis[ssssis.length - 1] = lastInput.substring(0, lastInput.indexOf("[") + (s2 - s1) + 1).substring(fullL.length() - ssssis[ssssis.length - 1].length() - (s2 - s1));
			
			for(int i = 0; i < ssssis.length; i++)
				variables.add(ssssis[i]);
			
			lastInput = lastInput.substring(fullL.length());
		}
		
		//Last part
		String[] theRest = lastInput.split(",");
		for(int i = 0; i < theRest.length; i++)
			variables.add(theRest[i]);
		
		//Clean it all up
		for(int i = variables.size() - 1; i >= 0; i--) {
			String s = variables.get(i);
			
			s = s.replace("{", "");
			s = s.replace("}", "");
			s = s.trim();
			
			variables.set(i, s);
			variables.set(i, s);
			
			if(s == "")
				variables.remove(i);
			
		}
		
		//Split it up and store the values
		ArrayList<String[]> values = new ArrayList<>();
		for(String s : variables) {
			String[] value = new String[2];
			
			for(int i = 0; i < 2; i++)
				value[i] = s.split(":")[i];
			
			values.add(value);
		}
		
		return values;
	}
	
	//Get all the chip files as ArrayList ArrayList Strings
	public static ArrayList<ArrayList<String>> readChipFiles() {
		ArrayList<ArrayList<String>> chips = new ArrayList<>();
		
		File folder = new File("chips");
		for (final File fileEntry : folder.listFiles()) {
			ArrayList<String> values = new ArrayList<>();
			
	        if (fileEntry.isDirectory()) {
	        	System.out.println("Directory: " + fileEntry.getName() + " is NOT going to be read.");
	        } else {
	        	try(BufferedReader br = new BufferedReader(new FileReader(fileEntry))) {
	        	    for(String line; (line = br.readLine()) != null; ) {
	        	        values.add(line);
	        	    }
	        	} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        
	        chips.add(values);
	    }
		
		return chips;
	}
	
	public static ArrayList<String> getChipFileNames() {
		ArrayList<String> chipNames = new ArrayList<>();
		
		File folder = new File("chips");
		for (final File fileEntry : folder.listFiles()) {
			chipNames.add(fileEntry.getName());
	    }
		
		return chipNames;
	}
	
	public static Color getColorFromString(String s) {
		Color color;
		s = s.replace("[", "");
		s = s.replace("]", "");
		String[] valueS = s.split(",");
		int[] rgb = new int[3];
		
		for(int i = 0; i < valueS.length; i++) {
			valueS[i] = valueS[i].trim();
			rgb[i] = Integer.parseInt(valueS[i]);
		}
		
		color = new Color(rgb[0], rgb[1], rgb[2]);
		
		return color;
	}
	
	public static ArrayList<Integer> getIntegerArrayFromString(String value) {
		ArrayList<Integer> ids = new ArrayList<>();
		value = value.replaceAll("[\\[\\]]", "");
		
		String[] stringIds = value.split(",");
		for(int i = 0; i < stringIds.length; i++) {
			stringIds[i] = stringIds[i].trim();
			if(!stringIds[i].equals(""))
				ids.add(Integer.parseInt(stringIds[i]));
		}
		
		return ids;
	}
	
	public static void createChip(ArrayList<String> chipString) {
		ArrayList<ArrayList<String[]>> comps = new ArrayList<>();
		for(String s : chipString) {
			comps.add(parseTSON(s));
		}
		
		ArrayList<String[]> info = comps.get(0);
		comps.remove(0);

		//Convert to saveObjects
		ArrayList<PowerSaveObj> psos = new ArrayList<>();
		for(int i = 0; i < comps.size(); i++) {
			PowerSaveObj pso = new PowerSaveObj(i);
			
			for(String[] compInfo : comps.get(i)) {
				String[] value = compInfo;
				String value0 = value[0];
				String value1 = value[1];
				
				if(value0.equals("Label")) 
					pso.setLabel(value1);
				
				if(value0.equals("id")) 
					pso.setId(Integer.parseInt(value1));
				
				if(value0.equals("powered"))
					if(value1.equals("true"))
						pso.setPowered(true);
					else if(value1.equals("false"))
						pso.setPowered(false);
				
				if(value0.equals("isClock"))
					if(value1.equals("true"))
						pso.setClock(true);
					else if(value1.equals("false"))
						pso.setClock(false);
				
				if(value0.equals("ngId")) 
					pso.setNgId(Integer.parseInt(value1));
				
				if(value0.equals("inputIds")) 
					pso.setInputIds(getIntegerArrayFromString(value1));
				
				if(value0.equals("outputIds")) 
					pso.setOutputIds(getIntegerArrayFromString(value1));
			}
			
			psos.add(pso);
		}
		
		//Time to create the components
		ArrayList<Node> inputNodes = new ArrayList<>();
		ArrayList<Node> outputNodes = new ArrayList<>();
		ArrayList<Node> allNodes = new ArrayList<>();
		
		//Create all nodes
		for(PowerSaveObj pso : psos) {
			if(!pso.getLabel().equals("node"))
				continue;
			
			Node newNode = new Node(0, 0, 20, false, Initialize.e);
			newNode.setClock(pso.isClock());
			
			for(Integer i1 : getIntegerArrayFromString(info.get(2)[1])) {
				if(i1 == pso.getId())
					inputNodes.add(newNode);
			}
			
			for(Integer i1 : getIntegerArrayFromString(info.get(3)[1])) {
				if(i1 == pso.getId())
					outputNodes.add(newNode);
			}
			
			allNodes.add(newNode);
		}
		
		//Set input wires, output wires and not gates
		for(int i = 0; i < allNodes.size(); i++) {
			Node n = allNodes.get(i);
			
			//Set the gate
			if(psos.get(i).getNgId() != -1) {
				NotGateChip ngc = new NotGateChip(0, 0, Color.GREEN, Initialize.e, "NOT");
				for(int j = 0; j < allNodes.size(); j++) {
					Node n2 = allNodes.get(j);
					
					if(psos.get(psos.get(i).getNgId()).getOutputIds().get(0) == j) {
						n.setC(ngc);
						ngc.setInputNodes(new ArrayList<>());
						ngc.setOutputNodes(new ArrayList<>());
						ngc.addInput(n);
						ngc.addOutput(n2);
					}
				}
			}
			
			//Set wires
			for(int j = 0; j < allNodes.size(); j++) {
				Node n2 = allNodes.get(j);
				
				for(Integer id : psos.get(i).getOutputIds()) {
					Wire w = new Wire(n, Initialize.e);
					
					if(psos.get(id).getOutputIds().get(0) == j) {
						w.setInputNode(n);
						w.setOutputNode(n2);
						
						n.addWire(w);
						n2.addInputWire(w);
					}
				}
			}
		}
		
		Initialize.e.getDropList().getButtons().add(new ChipButton(new Spot(0, 0), Initialize.e.createChip(allNodes, inputNodes, outputNodes, info.get(0)[1], getColorFromString(info.get(1)[1]))));
		Initialize.e.getDropList().setButtonLocations();
	}
}
