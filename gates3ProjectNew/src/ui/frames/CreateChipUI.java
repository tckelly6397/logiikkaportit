package ui.frames;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import ui.Button;
import ui.DropLedUI;
import ui.InputLabel;
import utils.Spot;
import utils.commands.CreateCommand;

public class CreateChipUI {
	private Spot location;
	private Button btn;
	private InputLabel nameLabel;
	private InputLabel redLabel;
	private InputLabel greenLabel;
	private InputLabel blueLabel;
	private DropLedUI dropLedUI;
	private int WIDTH;
	private int HEIGHT;
	
	public CreateChipUI(int x, int y, int WIDTH, int HEIGHT) {
		this.location = new Spot(x, y);
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		
		int btnWidth = 140;
		btn = new Button(new Spot(x + WIDTH - btnWidth, y), btnWidth, HEIGHT, new Color(20, 180, 20), "CREATE");
		btn.setCommand(new CreateCommand());
		btn.setBorderRadius(15);
		
		nameLabel = new InputLabel(x + 8, y - 8, "", 200, HEIGHT, Color.WHITE, -1, 10, true, true);
		redLabel = new InputLabel(x + nameLabel.getWIDTH(), y - 8, "" + (int)(Math.random() * 255), 200, HEIGHT, Color.RED, -1, 3, false, true);
		greenLabel = new InputLabel(redLabel.getLocation().getXAsInt() + redLabel.getWIDTH(), y - 8, "" + (int)(Math.random() * 255), 200, HEIGHT, Color.GREEN, -1, 3, false, true);
		blueLabel = new InputLabel(greenLabel.getLocation().getXAsInt() + greenLabel.getWIDTH(), y - 8, "" + (int)(Math.random() * 255), 200, HEIGHT, Color.BLUE, -1, 3, false, true);
		dropLedUI = new DropLedUI(blueLabel.getLocation().getXAsInt() + greenLabel.getWIDTH(), y - 30);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		btn.draw(g);
		nameLabel.draw(g);
		redLabel.draw(g);
		greenLabel.draw(g);
		blueLabel.draw(g);
		dropLedUI.draw(g);
		
		g.setColor(getColor());
		g.fillRect(blueLabel.getLocation().getXAsInt() + blueLabel.getWIDTH() + 2, location.getYAsInt() - 6 - HEIGHT, 40, 40);
		g.setColor(Color.BLACK);
		g.drawRect(blueLabel.getLocation().getXAsInt() + blueLabel.getWIDTH() + 2, location.getYAsInt() - 6 - HEIGHT, 40, 40);
		
		setButtonLocation();
	}
	
	public void setButtonLocation() {
		btn.setLocation(new Spot(location.getX() + WIDTH - btn.getWIDTH() + 10, location.getY() - btn.getHEIGHT()));
		
		redLabel.setX(location.getXAsInt() + nameLabel.getWIDTH() + 20);
		greenLabel.setX(redLabel.getLocation().getXAsInt() + redLabel.getWIDTH() + 10);
		blueLabel.setX(greenLabel.getLocation().getXAsInt() + greenLabel.getWIDTH() + 10);
		dropLedUI.setX(blueLabel.getLocation().getXAsInt() + greenLabel.getWIDTH() + 50);
	}
	
	public void leftClick(int x, int y) {
		btn.leftClick(x, y);
		dropLedUI.leftClick(x, y);
		
		for(InputLabel il : getLabels()) {
			if(il.leftClick(x, y))
				continue;
			
			il.setSelected(false);
		}			
	}
	
	public void executeHovered(int x, int y) {
		btn.executeHovered(x, y);
		dropLedUI.executeHovered(x, y);
		
		for(InputLabel il : getLabels())
			il.executeHovered(x, y);
	}
	
	public Color getColor() {
		int r = clamp(Integer.parseInt("0" + redLabel.getLabel()), 255);
		int g = clamp(Integer.parseInt("0" + greenLabel.getLabel()), 255);
		int b = clamp(Integer.parseInt("0" + blueLabel.getLabel()), 255);
		
		return new Color(r, g, b);
	}
	
	public int clamp(int num, int max) {
		if(num > max) return max;
		return num;
	}

	public Spot getLocation() {
		return location;
	}

	public void setLocation(Spot location) {
		this.location = location;
		setButtonLocation();
	}

	public Button getBtn() {
		return btn;
	}

	public void setBtn(Button btn) {
		this.btn = btn;
	}

	public InputLabel getNameLabel() {
		return nameLabel;
	}

	public void setNameLabel(InputLabel nameLabel) {
		this.nameLabel = nameLabel;
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
	
	public ArrayList<InputLabel> getLabels() {
		ArrayList<InputLabel> labels = new ArrayList<>();
		labels.add(nameLabel);
		labels.add(redLabel);
		labels.add(greenLabel);
		labels.add(blueLabel);
		
		return labels;
	}

	public DropLedUI getDropLedUI() {
		return dropLedUI;
	}

	public void setDropLedUI(DropLedUI dropLedUI) {
		this.dropLedUI = dropLedUI;
	}
}

