package gates3Project;

import java.awt.Color;

import components.Chip;
import components.Node;
import ui.ChipButton;
import ui.CreateChipUI;
import ui.DropList;
import utils.Spot;
import utils.TruthTable;

public class Initialize {
	public static volatile Environment e;
	public static volatile PowerThread pw;
	
	public static void main(String[] args) {
		pw = new PowerThread();
		Thread t1 = new Thread(pw);
		
		e = new Environment(1400, 800);
		e.setDropList(new DropList(200, 80));
		e.setCreateChipUI(new CreateChipUI(60, 110, 600, 30));
		
		//NOT GATE
		Chip ch = new Chip(400, 200, Color.GREEN, Initialize.e, "NOT");
	    TruthTable tb = new TruthTable();
	    tb.addTruth(new Boolean[]{true}, new Boolean[]{false});
	    tb.addTruth(new Boolean[]{false}, new Boolean[]{true});
	    ch.setTable(tb);
	    ch.addInput(new Node(0, 0, 20, false, ch, Initialize.e));
	    ch.addOutput(new Node(0, 0, 20, false, Initialize.e));
	    ch.setNodeLocations();
	    
	    e.getDropList().getButtons().add(new ChipButton(new Spot(0, 0), ch));
	    //END OF NOT GATE
	    
	    int defSize = 30;
	    //Setup input nodes
	    for(int i = 0; i < 5; i++) {
	    	e.addInputNode(new Node(0, 0, defSize, true, e));
	    }
		
	    //Setup output nodes
	    for(int i = 0; i < 1; i++) {
	    	e.addOutputNode(new Node(0, 0, defSize, false, e));
	    }
	    
	    t1.start();
	    
	    e.run();
	}
}
