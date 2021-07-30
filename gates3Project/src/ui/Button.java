package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import utils.Command;
import utils.Spot;

public class Button {
	private Spot location;
	private int WIDTH;
	private int HEIGHT;
	private Boolean hovered;
	private Color c;
	private String label;
	private Command command;
	
	public Button(Spot location, int WIDTH, int HEIGHT, Color c, String label) {
		super();
		this.c = c;
		this.location = location;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.hovered = false;
		this.label = label;
	}
	
	public void draw(Graphics g) {
		int x = location.getXAsInt();
		int y = location.getYAsInt();
		
		g.setColor(c);
		
		if(getHovered()) {
			g.setColor(new Color(clamp(g.getColor().getRed() + 50, 255), clamp(g.getColor().getGreen() + 50, 255), clamp(g.getColor().getBlue() + 50, 255)));
		}
		
		g.fillRect(x, y, WIDTH, HEIGHT);
		
		g.setColor(Color.BLACK);
		if(getHovered()) {
			g.setColor(new Color(clamp(g.getColor().getRed() + 50, 255), clamp(g.getColor().getGreen() + 50, 255), clamp(g.getColor().getBlue() + 50, 255)));
		}
		g.setFont(new Font("Verdana", Font.BOLD, HEIGHT - 5));
		g.drawString(label, x + 5, y + 5 + (HEIGHT - (5 * 2) - 3));
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
		if(getCollision(x, y)) {
			if(command != null)
				command.Execute();
		}
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}
}