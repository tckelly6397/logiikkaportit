package ui;

import java.awt.Color;
import java.awt.Graphics;

import utils.Spot;

public class Rectangle {
	Spot location;
	int WIDTH;
	int HEIGHT;
	
	public Rectangle(Spot s, int W, int H) {
		this.location = s;
		this.WIDTH = W;
		this.HEIGHT = H;
	}
	
	public Rectangle(int x, int y, int W, int H) {
		this.location = new Spot(x, y);
		this.WIDTH = W;
		this.HEIGHT = H;
	}
	
	public void draw(Graphics g) {
		int x1 = location.getXAsInt();
		int y1 = location.getYAsInt();
		
		g.setColor(new Color(80, 80, 80));
		g.drawRect(x1, y1, WIDTH, HEIGHT);
	}

	public Spot getLocation() {
		return location;
	}

	public void setLocation(Spot location) {
		this.location = location;
	}
	
	public int getX() {
		return this.location.getXAsInt();
	}
	
	public int getY() {
		return this.location.getYAsInt();
	}
	
	public void setX(int x) {
		this.location.setX(x);
	}
	
	public void setY(int y) {
		this.location.setY(y);
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

	@Override
	public String toString() {
		return "Rectangle [location=" + location + ", WIDTH=" + WIDTH + ", HEIGHT=" + HEIGHT + "]";
	}
}
