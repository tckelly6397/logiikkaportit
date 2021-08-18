package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import utils.Command;
import utils.Spot;
import utils.Tools;

public class Button {
	private Spot location;
	private int WIDTH;
	private int HEIGHT;
	private Boolean hovered;
	private Color c;
	private Color borderC;
	private String label;
	private Command command;
	private int borderRadius;
	
	public Button(Spot location, int WIDTH, int HEIGHT, Color c, String label) {
		super();
		this.c = c;
		this.location = location;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.hovered = false;
		this.label = label;
		this.borderRadius = 0;
		this.borderC = c;
	}
	
	public void draw(Graphics g) {
		int x = location.getXAsInt();
		int y = location.getYAsInt();
		
		g.setColor(c);
		
		if(getHovered()) {
			g.setColor(new Color(Tools.clamp(g.getColor().getRed() + 50, 255), Tools.clamp(g.getColor().getGreen() + 50, 255), Tools.clamp(g.getColor().getBlue() + 50, 255)));
		}
		
		g.fillRoundRect(x, y, WIDTH, HEIGHT, borderRadius, borderRadius);
		
		//draw rectangle around
		java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
		g2d.setStroke(new java.awt.BasicStroke(1));
		g2d.setColor(borderC);
		g2d.drawRoundRect(x, y, WIDTH, HEIGHT, borderRadius, borderRadius);
		//
		
		g.setColor(Color.BLACK);
		if(getHovered()) {
			g.setColor(new Color(Tools.clamp(g.getColor().getRed() + 50, 255), Tools.clamp(g.getColor().getGreen() + 50, 255), Tools.clamp(g.getColor().getBlue() + 50, 255)));
		}
		g.setFont(new Font("Verdana", Font.BOLD, HEIGHT - 5));
		g.drawString(label, x + 5, y + 5 + (HEIGHT - (5 * 2) - (HEIGHT / 20)));
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

	public int getBorderRadius() {
		return borderRadius;
	}

	public Color getColor() {
		return c;
	}

	public void setColor(Color c) {
		this.c = c;
	}

	public Color getBorderC() {
		return borderC;
	}

	public void setBorderC(Color borderC) {
		this.borderC = borderC;
	}

	public void setBorderRadius(int borderRadius) {
		this.borderRadius = borderRadius;
	}
}
