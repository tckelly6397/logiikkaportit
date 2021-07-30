package ui;

import components.Chip;
import gates3Project.Initialize;
import utils.Spot;

public class ChipButton extends Button {
	private Chip c;
	
	public ChipButton(Spot location, Chip c) {
		super(location, 150, 50, c.getColor(), c.getLabel());
		this.c = c;
	}
	
	@Override
	public void leftClick(int x, int y) {
		if(getCollision(x, y)) {
			Chip newC = new Chip(c);
			Initialize.e.addChip(newC);
			newC.update();
		}
		
	}

	public Chip getC() {
		return c;
	}

	public void setC(Chip c) {
		this.c = c;
	}
}
