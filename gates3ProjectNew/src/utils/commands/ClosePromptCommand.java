package utils.commands;

import gates3Project.Initialize;
import utils.Command;

public class ClosePromptCommand implements Command {

	@Override
	public void Execute() {
		Initialize.e.closePrompt();
	}
}
