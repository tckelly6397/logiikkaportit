package gates3Project;

import java.awt.Color;
import java.util.ArrayList;

import components.Node;
import components.chips.NotGateChip;
import ui.ChipButton;
import ui.DropList;
import ui.frames.ChangeNodesUI;
import ui.frames.CreateChipUI;
import utils.Spot;
import utils.Tools;

public class Initialize {
	public static volatile Environment e;
	
	public static void main(String[] args) {
		e = new Environment(1400, 800);
		e.setDropList(new DropList(200, 80));
		e.setCreateChipUI(new CreateChipUI(60, 110, 600, 34));
		e.setChangeNodesUI(new ChangeNodesUI());
		
		NotGateChip ch = new NotGateChip(400, 200, Color.GREEN, Initialize.e, "NOT");
		ch.addInput(new Node(0, 0, 20, false, ch, Initialize.e));
	    ch.addOutput(new Node(0, 0, 20, false, Initialize.e));

		e.getDropList().getButtons().add(new ChipButton(new Spot(0, 0), ch));
	    
	    int defSize = 30;
	    //Setup input nodes
	    for(int i = 0; i < 5; i++) {
	    	e.addInputNode(new Node(0, 0, defSize, true, e));
	    }
		
	    //Setup output nodes
	    for(int i = 0; i < 1; i++) {
	    	e.addOutputNode(new Node(0, 0, defSize, false, e));
	    }

	    
		//Loading chips
		ArrayList<ArrayList<String>> chips = Tools.readChipFiles();
		
		for(ArrayList<String> c : chips) {
			Tools.createChip(c);
		}
		
	    e.beginPowerThread();
	    e.run();
	}
}
