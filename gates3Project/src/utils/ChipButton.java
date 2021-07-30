package utils;

import components.Chip;
import gates3Project.Initialize;

public class ChipButton extends DropButton {
	private Chip c;
	
	public ChipButton(Spot location, Chip c) {
		super(location, 150, 50, c.getColor());
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
