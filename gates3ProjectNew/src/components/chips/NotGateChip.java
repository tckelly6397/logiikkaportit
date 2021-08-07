package components.chips;

import java.awt.Color;

import components.Node;
import gates3Project.Environment;
import gates3Project.Initialize;

public class NotGateChip extends Chip{
	
	public NotGateChip(int x, int y, Color c, Environment e, String label) {
		super(x, y, c, e, label);
	}
	
	public NotGateChip(Chip c) {
		super(c);
	}
	
	@Override
	public Chip getNewChip() {
		NotGateChip ngc = new NotGateChip(this.getX(), this.getY(), Color.GREEN, Initialize.e, "NOT");
		Node input = new Node(0, 0, 20, false, ngc, Initialize.e);
		Node output = new Node(0, 0, 20, false, Initialize.e);
		ngc.addInput(input);
		ngc.addOutput(output);
		return ngc;
	}
	
	@Override
	public void update() {
		updateOutput();
	}
	
	public void updateOutput() {
		getOutputNodes().get(0).setPowered(!getInputNodes().get(0).isPowered());
		this.getEnvironment().getPowerThread().addNext(getOutputNodes().get(0));
	}
	
	@Override
	public void save() {
		System.out.println("Not necessary for not chips due to them being initially created.");
	}
}
