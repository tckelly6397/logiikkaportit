package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import ui.frames.LedMakerUI;
import utils.Spot;
import utils.Tools;

public class DropLedUI {
	private Spot location;
	private int WIDTH;
	private int HEIGHT;
	private Boolean opened;
	private Boolean hovered;
	private LedMakerUI ledMakerUI;
	
	public DropLedUI(int x, int y) {
		this.location = new Spot(x, y);
		
		this.WIDTH = 150;
		this.HEIGHT = 30;
		this.hovered = false;
		this.opened = false;
		this.ledMakerUI = new LedMakerUI();
		
		setLedMakerUILocation();
	}
	
	public void draw(Graphics g) {
		int x = location.getXAsInt();
		int y = location.getYAsInt();
		int border = 5;
		
		g.setColor(Color.BLACK);
		g.fillRoundRect(x, y, WIDTH, HEIGHT, 10, 10);
		
		g.setColor(new Color(180, 180, 180));
		if(hovered)
			g.setColor(new Color(Tools.clamp(g.getColor().getRed() + 20, 255), Tools.clamp(g.getColor().getGreen() + 20, 255), Tools.clamp(g.getColor().getBlue() + 20, 255)));
		g.fillRoundRect(x + border, y + border, WIDTH - (border * 2), HEIGHT - (border * 2), 5, 5);
		
		g.setColor(Color.BLACK);
		if(hovered)
			g.setColor(new Color(Tools.clamp(g.getColor().getRed() + 20, 255), Tools.clamp(g.getColor().getGreen() + 20, 255), Tools.clamp(g.getColor().getBlue() + 20, 255)));
		g.setFont(new Font("Verdana", Font.BOLD, HEIGHT - border - 2));
		g.drawString("Led", x + border, y + border + (HEIGHT - (border * 2) - 1));
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(6, 1, 1));
		
		if(!opened) {
			g2d.drawLine(x + WIDTH - (border * 2), y + (HEIGHT / 7 * 2) + 2, x + WIDTH - (border * 4), y + (HEIGHT / 2));
			g2d.drawLine(x + WIDTH - (border * 2), y + (HEIGHT / 7 * 6) - 4, x + WIDTH - (border * 4), y + (HEIGHT / 2));
		} else {
			g2d.drawLine(x + WIDTH - (border * 4), y + (HEIGHT / 7 * 2) + 2, x + WIDTH - (border * 3), y + (HEIGHT / 7 * 6) - 4);
			g2d.drawLine(x + WIDTH - (border * 2), y + (HEIGHT / 7 * 2) + 2, x + WIDTH - (border * 3), y + (HEIGHT / 7 * 6) - 4);
		}
		
		if(!opened) return; //Everything after this is if the drop list is open
		
		ledMakerUI.draw(g);
	}
	
	public void executeHovered(int x, int y) {
		if(getCollision(x, y)) 
			hovered = true;
		else
			hovered = false;
		
		ledMakerUI.executeHovered(x, y);
	}
	
	public void setLedMakerUILocation() {
		ledMakerUI.setLocation(new Spot(location.getXAsInt(), location.getYAsInt() + HEIGHT));
		ledMakerUI.setPlusMinusLocations();
		ledMakerUI.setLedArrayLocation();
		ledMakerUI.setPlusMinusLocations();
		ledMakerUI.setLedNameLabelLocation();
	}
	
	public void open() {
		setLedMakerUILocation();
	}
	
	public void close() {
		
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
			switchOpened();
		}
		
		if(opened) {
			ledMakerUI.leftClick(x, y);
		}
	}

	public Spot getLocation() {
		return location;
	}

	public void setLocation(Spot location) {
		this.location = location;
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

	public Boolean getOpened() {
		return opened;
	}

	public void setOpened(Boolean opened) {
		this.opened = opened;
	}
	
	public LedMakerUI getLedMakerUI() {
		return ledMakerUI;
	}

	public void setLedMakerUI(LedMakerUI ledMakerUI) {
		this.ledMakerUI = ledMakerUI;
	}

	public void switchOpened() {
		this.opened = !opened;
		
		if(this.opened)
			open();
		else if(!this.opened)
			close();		
	}
}
