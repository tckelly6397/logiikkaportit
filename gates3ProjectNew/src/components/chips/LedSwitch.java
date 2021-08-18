package components.chips;

import java.awt.Color;
import java.util.ArrayList;

import components.Node;
import gates3Project.Environment;
import gates3Project.Initialize;
import utils.Led;

public class LedSwitch extends BasicChip {
	private ArrayList<Led> leds;
	
	public LedSwitch(int x, int y, Color c, Environment e, String label, ArrayList<Led> leds) {
		super(x, y, c, e, label);
		this.leds = leds;
		
		Node node = new Node(0, 0, 20, false, Initialize.e);
		node.setC(this);
		this.getInputNodes().add(node);
		this.addAAllNode(node);
	}

	@Override
	public void update() {	
		for(Led led : leds) {
			led.setPowered(getInputNodes().get(0).isPowered());
		}
	}
}
