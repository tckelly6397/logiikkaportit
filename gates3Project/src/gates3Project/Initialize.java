package gates3Project;

import java.awt.Color;

import components.Chip;
import components.Node;
import utils.TruthTable;

public class Initialize {
	public static volatile Environment e;
	public static volatile PowerThread pw;
	
	public static void main(String[] args) {
		pw = new PowerThread();
		Thread t1 = new Thread(pw);
		
		e = new Environment(1400, 800);
		
		for(int i = 0; i < 4; i++) {
			Chip c = new Chip(200, 200, Color.GREEN, e);
		    TruthTable tb = new TruthTable();
		    tb.addTruth(new Boolean[]{true}, new Boolean[]{false});
		    tb.addTruth(new Boolean[]{false}, new Boolean[]{true});
		    c.setTable(tb);
		    c.addInput(new Node(0, 0, 20, false, c, e));
		    c.addOutput(new Node(0, 0, 20, false, e));
		    c.setNodeLocations();
		    c.update();
		    
		    e.addChip(c);
		}
	    
	    int defSize = 30;
	    //Setup input nodes
	    for(int i = 0; i < 5; i++) {
	    	e.addInputNode(new Node(0, 0, defSize, true, e));
	    }
		
	    //Setup output nodes
	    for(int i = 0; i < 6; i++) {
	    	e.addOutputNode(new Node(0, 0, defSize, false, e));
	    }
	    
	    t1.start();
	    
	    e.run();
	}
}
