package components.chips;

import java.awt.Color;
import java.util.ArrayList;

import components.Node;
import gates3Project.Environment;
import gates3Project.Initialize;

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
			Initialize.pw.addNext(n);
		}
	}
}
