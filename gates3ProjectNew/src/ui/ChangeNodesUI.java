package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;

import gates3Project.Initialize;
import utils.Spot;

public class ChangeNodesUI {
	ArrayList<Button> buttons = new ArrayList<>();
	
	public ChangeNodesUI() {
		PlusButton addInput = new PlusButton(new Spot(5, 130), 40, 40, Color.BLUE, "Test", Initialize.e.getInputNodes(), true);
		PlusButton addOutput = new PlusButton(new Spot(300, 130), 40, 40, Color.BLUE, "Test", Initialize.e.getOutputNodes(), false);
		MinusButton removeInput = new MinusButton(new Spot(5, 300), 40, 40, Color.BLUE, "Test", Initialize.e.getInputNodes(), true);
		MinusButton removeOutput = new MinusButton(new Spot(300, 300), 40, 40, Color.BLUE, "Test", Initialize.e.getOutputNodes(), false);
		
		buttons.add(addInput);
		buttons.add(addOutput);
		buttons.add(removeInput);
		buttons.add(removeOutput);
	}
	
	public void draw(Graphics g) {
		for(Button b : buttons)
			b.draw(g);
	}
	
	public void updateLocations() {
		JFrame frame = Initialize.e.getFrame();
		//RemoveInput
		buttons.get(2).setLocation(new Spot(5, frame.getHeight() - 150));
		
		//RemoveOutput
		buttons.get(3).setLocation(new Spot(frame.getWidth() - 80 + 20, frame.getHeight() - 150));
		
		//AddOutput
		buttons.get(1).setLocation(new Spot(frame.getWidth() - 80 + 20, 130));
	}
	
	public void executeHovered(int x, int y) {
		for(Button b : buttons)
			b.executeHovered(x, y);
	}
	
	public void leftClick(int x, int y) {
		for(Button b : buttons)
			b.leftClick(x, y);
	}
}
