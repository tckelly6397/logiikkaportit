package utils.commands;

import gates3Project.Initialize;
import utils.Command;

public class SubtractLedsCommand implements Command {
private boolean isX = true;
	
	public SubtractLedsCommand(boolean isX) {
		this.isX = isX;
	}
	
	@Override
	public void Execute() {
		if(isX)
			Initialize.e.getCreateChipUI().getDropLedUI().getLedMakerUI().getLedArray().subtractX();
		else
			Initialize.e.getCreateChipUI().getDropLedUI().getLedMakerUI().getLedArray().subtractY();
	}
}
