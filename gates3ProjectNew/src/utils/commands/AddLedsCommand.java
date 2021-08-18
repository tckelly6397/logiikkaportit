package utils.commands;

import gates3Project.Initialize;
import utils.Command;

public class AddLedsCommand implements Command {
	private boolean isX = true;
	
	public AddLedsCommand(boolean isX) {
		this.isX = isX;
	}
	
	@Override
	public void Execute() {
		if(isX)
			Initialize.e.getCreateChipUI().getDropLedUI().getLedMakerUI().getLedArray().addX();
		else
			Initialize.e.getCreateChipUI().getDropLedUI().getLedMakerUI().getLedArray().addY();
	}
}
