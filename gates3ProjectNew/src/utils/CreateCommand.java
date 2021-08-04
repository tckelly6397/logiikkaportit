package utils;

import gates3Project.Initialize;

public class CreateCommand implements Command {

	@Override
	public void Execute() {
		Initialize.e.createChip();
	}

}
