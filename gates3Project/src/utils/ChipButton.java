package utils;

import java.awt.Color;
import java.awt.Graphics;

import components.Chip;

public class ChipButton {
	private Chip c;
	private Spot location;
	private int WIDTH;
	private int HEIGHT;
	private Boolean hovered;
	
	public ChipButton(Chip c) {
		this.c = c;
		this.WIDTH = 200;
		this.HEIGHT = 100;
		this.hovered = false;
	}
	
	public void draw(Graphics g) {
		g.setColor(c.getColor());
		
		if(hovered) {
			g.setColor(new Color(clamp(g.getColor().getRed() + 50, 255), clamp(g.getColor().getGreen() + 50, 255), clamp(g.getColor().getBlue() + 50, 255)));
		}
		
		g.drawRect(location.getXAsInt(), location.getYAsInt(), WIDTH, HEIGHT);
	}
	
	public int clamp(int num, int max) {
		if(num > max) return max;
		return num;
	}
	
	public Boolean getCollision(int x, int y) {
		int x1 = location.getXAsInt();
		int x2 = location.getXAsInt() + WIDTH;
		int y1 = location.getYAsInt();
		int y2 = location.getYAsInt() + HEIGHT;
		
		if(x > x1 && x < x2 && y > y1 && y < y2)
			return true;
		
		return false;
	}

	public Chip getC() {
		return c;
	}

	public void setC(Chip c) {
		this.c = c;
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

	public Boolean getHovered() {
		return hovered;
	}

	public void setHovered(Boolean hovered) {
		this.hovered = hovered;
	}
}
