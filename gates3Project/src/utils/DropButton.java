package utils;

import java.awt.Color;
import java.awt.Graphics;

public class DropButton {
	private Spot location;
	private int WIDTH;
	private int HEIGHT;
	private Boolean hovered;
	private Color c;
	
	public DropButton(Spot location, int WIDTH, int HEIGHT, Color c) {
		super();
		this.c = c;
		this.location = location;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.hovered = false;
	}
	
	public void draw(Graphics g) {
		g.setColor(c);
		
		if(getHovered()) {
			g.setColor(new Color(clamp(g.getColor().getRed() + 50, 255), clamp(g.getColor().getGreen() + 50, 255), clamp(g.getColor().getBlue() + 50, 255)));
		}
		
		g.fillRect(location.getXAsInt(), location.getYAsInt(), WIDTH, HEIGHT);
	}
	
	public int clamp(int num, int max) {
		if(num > max) return max;
		return num;
	}
	
	public void executeHovered(int x, int y) {
		if(getCollision(x, y)) {
			hovered = true;
		}
		else
			hovered = false;
	}
	
	public Boolean getCollision(int x, int y) {
		x -= 10;
		y -= 30;
		
		int x1 = location.getXAsInt();
		int y1 = location.getYAsInt();
		int x2 = x1 + WIDTH;
		int y2 = y1 + HEIGHT;
		
		if(x > x1 && x < x2 && y > y1 && y < y2)
			return true;
		
		return false;
	}
	
	public void leftClick(int x, int y) {
		if(getCollision(x, y))
			System.out.println("NOT A CHIP");
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
