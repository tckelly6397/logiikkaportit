package ui;

import java.awt.Color;
import java.awt.Graphics;

import utils.CreateCommand;
import utils.Spot;

public class CreateChipUI {
	private Spot location;
	private Button btn;
	private int WIDTH;
	private int HEIGHT;
	
	public CreateChipUI(int x, int y, int WIDTH, int HEIGHT) {
		this.location = new Spot(x, y);
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		
		int btnWidth = 140;
		btn = new Button(new Spot(x + WIDTH - btnWidth, y), btnWidth, HEIGHT, new Color(20, 180, 20), "CREATE");
		btn.setCommand(new CreateCommand());
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		btn.draw(g);
	}
	
	public void setButtonLocation() {
		btn.setLocation(new Spot(location.getX() + WIDTH - btn.getWIDTH(), location.getY() - btn.getHEIGHT()));
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
}

