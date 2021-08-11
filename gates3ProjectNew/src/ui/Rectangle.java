package ui;

import java.awt.Color;
import java.awt.Graphics;

import gates3Project.Initialize;
import utils.Spot;

public class Rectangle {
	Spot location;
	int WIDTH;
	int HEIGHT;
	boolean pressed;
	int ogX = 0;
	int ogY = 0;
	
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
	
	public void fillDraw(Graphics g) {
		int x1 = location.getXAsInt();
		int y1 = location.getYAsInt();
		
		g.setColor(new Color(35, 35, 35));
		g.fillRect(x1, y1, WIDTH, HEIGHT);
	}
	
	public void invertedDraw(Graphics g) {
		g.setColor(new Color(50, 50, 50));
		g.fillRect(0, 0, Initialize.e.getFrame().getWidth(), location.getYAsInt());
		g.fillRect(0, location.getYAsInt(), location.getXAsInt(), HEIGHT);
		g.fillRect(0, location.getYAsInt() + HEIGHT, Initialize.e.getFrame().getWidth(), Initialize.e.getFrame().getHeight() - (location.getYAsInt() + HEIGHT));
		g.fillRect(location.getXAsInt() + WIDTH, location.getYAsInt(), Initialize.e.getFrame().getWidth() - (location.getXAsInt() + WIDTH), HEIGHT);
	}
	
	public void middleClickDrag(int x, int y) {
		if(!getCollision(x, y) || !pressed)
			return;
		
		Initialize.e.translateOffsetX(x - ogX);
		Initialize.e.translateOffsetY(y - ogY);
		
		ogX = x;
		ogY = y;
	}	
	
	public void middlePressed(int x, int y) {
		pressed = true;
		ogX = x;
		ogY = y;
	}
	
	public void middleReleased(int x, int y) {
		pressed = false;
	}
	
	public boolean getCollision(int x, int y) {
		x -= 10;
		y -= 30;
		
		int x1 = location.getXAsInt();
		int y1 = location.getYAsInt();
		int x2 = location.getXAsInt() + WIDTH;
		int y2 = location.getYAsInt() + HEIGHT;
		
		if(x > x1 && x < x2 && y > y1 && y < y2)
			return true;
		
		return false;
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
