package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import utils.Spot;

public class DropList {
	private Spot location;
	private int WIDTH;
	private int HEIGHT;
	private Boolean opened;
	private int maxShown;
	private int scrollPosition;
	private Boolean hovered;
	ArrayList<Button> buttons = new ArrayList<>();
	
	public DropList(int x, int y) {
		this.location = new Spot(x, y);
		
		this.scrollPosition = 0;
		this.WIDTH = 150;
		this.HEIGHT = 30;
		this.maxShown = 5;
		this.hovered = false;
		this.opened = false;
	    
		for(int i = 0; i < 1; i++) {
			Color c = new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255));
			buttons.add(new Button(new Spot(x, y + (i * 50) + HEIGHT), 150, 50, c, ""));
		}
		
		setButtonLocations();
	}
	
	public void draw(Graphics g) {
		int x = location.getXAsInt();
		int y = location.getYAsInt();
		int border = 5;
		
		g.setColor(Color.BLACK);
		g.fillRoundRect(x, y, WIDTH, HEIGHT, 10, 10);
		
		g.setColor(new Color(180, 180, 180));
		if(hovered)
			g.setColor(new Color(clamp(g.getColor().getRed() + 20, 255), clamp(g.getColor().getGreen() + 20, 255), clamp(g.getColor().getBlue() + 20, 255)));
		g.fillRoundRect(x + border, y + border, WIDTH - (border * 2), HEIGHT - (border * 2), 5, 5);
		
		g.setColor(Color.BLACK);
		if(hovered)
			g.setColor(new Color(clamp(g.getColor().getRed() + 20, 255), clamp(g.getColor().getGreen() + 20, 255), clamp(g.getColor().getBlue() + 20, 255)));
		g.setFont(new Font("Verdana", Font.BOLD, HEIGHT - border - 2));
		g.drawString("CHIPS", x + border, y + border + (HEIGHT - (border * 2) - 1));
		
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
		
		int max = scrollPosition + maxShown;
		if(max > buttons.size()) //This can cause a problem, if not then remove this
			max = buttons.size();
		
		for(int i = scrollPosition; i < max; i++) {
			buttons.get(i).draw(g);
		}
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
		
		for(Button db : buttons) {
			db.executeHovered(x, y);
		}
	}
	
	public void open() {
		setButtonLocations();
	}
	
	public void close() {
		
	}
	
	public void setButtonLocations() {
		int max = scrollPosition + maxShown;
		if(max > buttons.size()) //This can cause a problem, if not then remove this
			max = buttons.size();
		
		for(int i = scrollPosition; i < max; i++) {
			buttons.get(i).setLocation(new Spot(location.getXAsInt(), location.getYAsInt() + ((i - scrollPosition) * 50) + HEIGHT));
		}
	}
	
	public void scrollUp() {
		if(scrollPosition > 0)
			scrollPosition--;
		
		setButtonLocations();
	}
	
	public void scrollDown() {
		if(scrollPosition < buttons.size() - maxShown)
			scrollPosition++;
		
		setButtonLocations();
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
			for(Button db : buttons)
				db.leftClick(x, y);
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

	public Boolean getOpened() {
		return opened;
	}

	public void setOpened(Boolean opened) {
		this.opened = opened;
	}
	
	public void switchOpened() {
		this.opened = !opened;
		
		if(this.opened)
			open();
		else if(!this.opened)
			close();
			
	}

	public int getMaxShown() {
		return maxShown;
	}

	public void setMaxShown(int maxShown) {
		this.maxShown = maxShown;
	}

	public int getScrollPosition() {
		return scrollPosition;
	}

	public void setScrollPosition(int scrollPosition) {
		this.scrollPosition = scrollPosition;
	}

	public ArrayList<Button> getButtons() {
		return buttons;
	}

	public void setButtons(ArrayList<Button> buttons) {
		this.buttons = buttons;
	}
	
	public void addButton(Button dp) {
		this.buttons.add(dp);
	}
	
	public void removeButton(Button dp) {
		this.buttons.remove(dp);
	}
}
