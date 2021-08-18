package utils.commands;

import utils.Command;
import utils.Led;

public class SelectLedCommand implements Command {
	Led led;
	
	public SelectLedCommand(Led led) {
		this.led = led;
	}
	
	@Override
	public void Execute() {
		led.switchSlected();
	}
}
