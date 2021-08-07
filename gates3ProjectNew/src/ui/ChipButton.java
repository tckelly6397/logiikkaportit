package ui;

import components.chips.Chip;
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
			Chip newC = Initialize.e.createChip(c.getAllNodes(), c.getInputNodes(), c.getOutputNodes(), c.getLabel(), c.getColor());
			Initialize.e.addChip(newC);
			Initialize.e.getPowerThread().addNext(newC);
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
