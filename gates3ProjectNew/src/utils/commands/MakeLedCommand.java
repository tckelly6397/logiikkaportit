package utils.commands;

import java.awt.Color;
import java.util.ArrayList;

import components.chips.LedSwitch;
import gates3Project.Initialize;
import utils.Command;
import utils.Led;

public class MakeLedCommand implements Command {

	@Override
	public void Execute() {
		ArrayList<Led> selectedLeds = Initialize.e.getCreateChipUI().getDropLedUI().getLedMakerUI().getSelectedLeds();
		String label = Initialize.e.getCreateChipUI().getDropLedUI().getLedMakerUI().getLedNameLabel().getLabel();
		
		LedSwitch ledS = new LedSwitch(150, 150, Color.RED, Initialize.e, label, selectedLeds);
		Initialize.e.addChip(ledS);
		
		Initialize.e.getCreateChipUI().getDropLedUI().getLedMakerUI().deSelectLeds();
	}
}
