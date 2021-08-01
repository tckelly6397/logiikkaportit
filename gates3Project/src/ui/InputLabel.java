package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import utils.Spot;

public class InputLabel {
	private Spot location;
	private String label;
	private int WIDTH;
	private int HEIGHT;
	private Color textColor;
	private Boolean hovered;
	private int cursorPosition;
	private Boolean selected = true;
	private int max;
	
	public InputLabel(int x, int y, String label, int WIDTH, int HEIGHT, Color textColor, int cursorPosition, int max) {
		this.location = new Spot(x, y);
		this.label = label;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.textColor = textColor;
		this.hovered = false;
		this.cursorPosition = cursorPosition;
		this.max = max;
	}
	
	public void draw(Graphics g) {
		//If text.length is greater than width then hide hide characters till not, then take away three and put three periods
		
		g.setColor(textColor);
		Font f = new Font("Verdana", Font.BOLD, HEIGHT - 5);
		g.setFont(f);
		g.drawString(label, location.getXAsInt(), location.getYAsInt());
		
		//Draw rectangle around input
		String maxString = "";
		for(int i = 0; i < max; i++)
			maxString += "W";
		g.setColor(Color.BLACK);
		g.drawRect(location.getXAsInt() - 8, location.getYAsInt() - HEIGHT + 3, g.getFontMetrics(f).stringWidth(maxString) + 8, HEIGHT + 6);
		
		if(!selected) //Everything after this needs the input to be selected
			return;
		
		int x = 0;
		int w = 0;
		
		if(cursorPosition < label.length() && cursorPosition >= 0) {
			x = g.getFontMetrics(f).stringWidth(label.substring(0, cursorPosition));
			w = g.getFontMetrics(f).stringWidth(label.substring(cursorPosition, cursorPosition + 1));
		}
		
		g.setColor(Color.RED);
		g.drawRect(x + location.getXAsInt() + w, location.getYAsInt() - HEIGHT + 5, 2, HEIGHT - 5);
	}
	
	public Spot getLocation() {
		return location;
	}

	public void setLocation(Spot location) {
		this.location = location;
	}

	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public void addCharacter(Character c) {
		if(label.length() + 1 > max)
			return;
		
		String before = label.substring(0, cursorPosition + 1);
		String after = label.substring(cursorPosition + 1);
		label = before + Character.toUpperCase(c) + after;
		
		moveCursor(1);
	}
	
	public void removeCharacter() {
		if(cursorPosition < 0)
			return;
		
		StringBuilder sb = new StringBuilder(label);
		sb.deleteCharAt(cursorPosition);
		
		moveCursor(-1);
		
		label = sb.toString();
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
	
	public Color getTextColor() {
		return textColor;
	}
	
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}
	
	public Boolean getHovered() {
		return hovered;
	}
	
	public void setHovered(Boolean hovered) {
		this.hovered = hovered;
	}
	
	public int getCursorPosition() {
		return cursorPosition;
	}
	
	public void setCursorPosition(int cursorPosition) {
		this.cursorPosition = cursorPosition;
	}
	
	public void moveCursor(int direction) {
		if(cursorPosition + direction > label.length() - 1)
			cursorPosition = label.length() - 1;
		else if(cursorPosition + direction < -1)
			cursorPosition = -1;
		else
			cursorPosition += direction;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}	
}
