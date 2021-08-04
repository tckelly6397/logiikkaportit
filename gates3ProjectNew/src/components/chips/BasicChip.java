package components.chips;

import java.awt.Color;

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
		return new BasicChip(this);
	}
	
	@Override
	public void update() {	
		for(Node n : getOutputNodes()) {
			Initialize.pw.addNext(n);
		}
	}
}
