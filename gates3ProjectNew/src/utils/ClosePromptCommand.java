package utils;

import gates3Project.Initialize;

public class ClosePromptCommand implements Command {

	@Override
	public void Execute() {
		Initialize.e.closePrompt();
	}
}
