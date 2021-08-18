package ui.frames;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import ui.Button;
import ui.InputLabel;
import utils.Led;
import utils.LedArray;
import utils.Spot;
import utils.commands.AddLedsCommand;
import utils.commands.MakeLedCommand;
import utils.commands.SubtractLedsCommand;

public class LedMakerUI {
	private Spot location;
	private int WIDTH;
	private int HEIGHT;
	private LedArray ledArray;
	private Button minusYBtn;
	private Button minusXBtn;
	private Button plusYBtn;
	private Button plusXBtn;
	private InputLabel ledNameLabel;
	private Button createLed;
	
	public LedMakerUI() {
		this.location = new Spot(0, 0);
		this.WIDTH = 280;
		this.HEIGHT = 320;
		
		this.ledArray = new LedArray();
		setLedArrayLocation();
		
		minusYBtn = new Button(new Spot(50, 50), 20, 30, new Color(45, 45, 45), "-");
		minusYBtn.setCommand(new SubtractLedsCommand(false));
		minusXBtn = new Button(new Spot(50, 50), 20, 30, new Color(45, 45, 45), "-");
		minusXBtn.setCommand(new SubtractLedsCommand(true));
		plusYBtn = new Button(new Spot(50, 50), 20, 30, new Color(45, 45, 45), "+");
		plusYBtn.setCommand(new AddLedsCommand(false));
		plusXBtn = new Button(new Spot(50, 50), 20, 30, new Color(45, 45, 45), "+");
		plusXBtn.setCommand(new AddLedsCommand(true));
		
		ledNameLabel = new InputLabel(location.getXAsInt() + 40, location.getYAsInt() + 40, "", 100, 30, Color.WHITE, -1, 5, true, true);
		createLed = new Button(new Spot(50, 50), 90, 30, new Color(20, 180, 20), "MAKE");
		createLed.setBorderRadius(8);
		createLed.setCommand(new MakeLedCommand());
		
		setLedNameLabelLocation();
		setPlusMinusLocations();
	}
	
	public void draw(Graphics g) {
		int x = location.getXAsInt();
		int y = location.getYAsInt();
		
		g.setColor(new Color(45, 45, 45));
		g.fillRect(x, y, WIDTH, HEIGHT);
		g.setColor(new Color(10, 10, 10));
		g.drawRect(x, y, WIDTH, HEIGHT);
		
		minusYBtn.draw(g);
		minusXBtn.draw(g);
		plusYBtn.draw(g);
		plusXBtn.draw(g);
		createLed.draw(g);
		ledNameLabel.draw(g);
		
		ledArray.drawButtons(g);
	}
	
	public void executeHovered(int x, int y) {
		minusYBtn.executeHovered(x, y);
		minusXBtn.executeHovered(x, y);
		plusYBtn.executeHovered(x, y);
		plusXBtn.executeHovered(x, y);
		
		ledArray.executeHovered(x, y);
		
		createLed.executeHovered(x, y);
		ledNameLabel.executeHovered(x, y);
	}
	
	public void leftClick(int x, int y) {
		minusYBtn.leftClick(x, y);
		minusXBtn.leftClick(x, y);
		plusYBtn.leftClick(x, y);
		plusXBtn.leftClick(x, y);
		
		ledArray.leftClick(x, y);
		
		createLed.leftClick(x, y);
		ledNameLabel.leftClick(x, y);
	}
	
	public void setLedArrayLocation() {
		int x = this.location.getXAsInt() + 40;
		int y = this.location.getYAsInt() + 80;
		this.ledArray.setLocation(new Spot(x, y));
	}
	
	public void setPlusMinusLocations() {
		int x = ledArray.getLocation().getXAsInt();
		int y = ledArray.getLocation().getYAsInt();
		int ledAWidth = ledArray.getWIDTH();
		minusYBtn.setLocation(new Spot(x + (ledAWidth / 2) - (minusYBtn.getWIDTH() / 2), y - minusYBtn.getHEIGHT()));
		minusXBtn.setLocation(new Spot(x - minusYBtn.getWIDTH(), y + (ledAWidth / 2) - (minusYBtn.getHEIGHT() / 2)));
		plusYBtn.setLocation(new Spot(x + (ledAWidth / 2) - (minusYBtn.getWIDTH() / 2), y + ledAWidth));
		plusXBtn.setLocation(new Spot(x + ledAWidth, y + (ledAWidth / 2) - (minusYBtn.getHEIGHT() / 2)));
		
		createLed.setLocation(new Spot(location.getXAsInt() + 70 + ledNameLabel.getWIDTH(), location.getYAsInt() + 15));
	}
	
	public void setLedNameLabelLocation() {
		ledNameLabel.setLocation(new Spot(location.getXAsInt() + 20, location.getYAsInt() + 40));
	}
	
	public ArrayList<Led> getSelectedLeds() {
		ArrayList<Led> selectedLeds = new ArrayList<>();
		for(ArrayList<Led> ledList : ledArray.getMap())
			for(Led led : ledList)
				if(led.isSelected())
					selectedLeds.add(led);
		
		return selectedLeds;
	}
	
	public void deSelectLeds() {
		for(ArrayList<Led> ledList : ledArray.getMap())
			for(Led led : ledList)
				led.setSelected(false);
	}

	public Spot getLocation() {
		return location;
	}

	public void setLocation(Spot location) {
		this.location = location;
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public void setWIDTH(int wIDTH) {
		WIDTH = wIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public void setHEIGHT(int hEIGHT) {
		HEIGHT = hEIGHT;
	}

	public LedArray getLedArray() {
		return ledArray;
	}

	public void setLedArray(LedArray ledArray) {
		this.ledArray = ledArray;
	}

	public InputLabel getLedNameLabel() {
		return ledNameLabel;
	}

	public void setLedNameLabel(InputLabel ledNameLabel) {
		this.ledNameLabel = ledNameLabel;
	}
}
