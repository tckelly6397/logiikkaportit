package utils;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class LedArray {
	private Spot location;
	private int WIDTH;
	private Color ledColor;
	
	private ArrayList<ArrayList<Led>> map;
	
	public LedArray() {
		this.location = new Spot(150, 150);
		this.WIDTH = 200;
		this.ledColor = Color.RED;
		
		this.map = new ArrayList<>();
		setSize(5, 9);
		
		setLedLocations();
	}
	
	public void draw(Graphics g) {
		for(ArrayList<Led> ledList : map) {
			for(Led led : ledList) {
				g.setColor(new Color(25, 25, 25));
				if(led.isPowered())
					g.setColor(led.getC());
				
				int x = led.getLocation().getXAsInt();
				int y = led.getLocation().getYAsInt();
				
				g.fillRect(x, y, led.getWIDTH(), led.getHEIGHT());
				
				//draw rectangle around
				java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
				g2d.setStroke(new java.awt.BasicStroke(1));
				g2d.setColor(Color.BLACK);
				g2d.drawRect(x, y, led.getWIDTH(), led.getHEIGHT());
				//
			}
		}
	}
	
	public void drawButtons(Graphics g) {
		for(ArrayList<Led> ledList : map)
			for(Led led : ledList) {
				if(led.isSelected()) 
					led.setColor(new Color(190, 190, 25));
				else if(led.isPowered())
					led.setColor(Color.RED);
				else
					led.setColor(new Color(25, 25, 25));
				led.draw(g);	
			}
	}
	
	public void executeHovered(int x, int y) {
		for(ArrayList<Led> ledList : map)
			for(Led led : ledList)
				led.executeHovered(x, y);
	}
	
	public void leftClick(int x, int y) {
		for(ArrayList<Led> ledList : map)
			for(Led led : ledList)
				led.leftClick(x, y);
	}
	
	public void setLedLocations() {
		int topSize = map.get(0).size();
		
		if(map.size() > topSize)
			topSize = map.size();
		
		for(int j = 0; j < map.size(); j++) {
			for(int i = 0; i < map.get(j).size(); i++) {
				Led led = map.get(j).get(i);
				
				led.setWIDTH(WIDTH / topSize);
				led.setHEIGHT(WIDTH / topSize);
				Spot spot = new Spot(led.getWIDTH() * i + location.getX(), led.getWIDTH() * j + location.getY());
				led.setLocation(spot);
			}
		}
	}
	
	//Creates new ledArray
	public void setSize(int xSize, int ySize) {
		map = new ArrayList<>();
		
		for(int j = 0; j < ySize; j++) {
			ArrayList<Led> ledList = new ArrayList<>();
			
			for(int i = 0; i < xSize; i++) {
				Led led = new Led();
				led.setC(ledColor);
				
				ledList.add(led);
			}
			
			map.add(ledList);
		}
	}
	
	public void addX() {
		for(int i = 0; i < map.size(); i++) {
			ArrayList<Led> ledList = map.get(i);
			Led led = new Led();
			led.setColor(ledColor);
			
			ledList.add(led);
			map.set(i, ledList);
		}
		
		setLedLocations();
	}
	
	public void addY() {
		ArrayList<Led> ledList = new ArrayList<>();
		
		for(int i = 0; i < map.get(0).size(); i++) {
			Led led = new Led();
			led.setColor(ledColor);
			
			ledList.add(led);
		}
		map.add(ledList);
		map.set(map.size() - 1, ledList);
		
		setLedLocations();
	}
	
	public void subtractX() {
		if(map.get(0).size() == 1)
			return;
		
		for(ArrayList<Led> ledList : map) {
			ledList.remove(ledList.size() - 1);
		}
		
		setLedLocations();
	}
	
	public void subtractY() {
		if(map.size() == 1)
			return;
		
		map.remove(map.size() - 1);
		
		setLedLocations();
	}

	public Spot getLocation() {
		return location;
	}

	public void setLocation(Spot location) {
		this.location = location;
		setLedLocations();
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public void setWIDTH(int wIDTH) {
		WIDTH = wIDTH;
	}

	public Color getLedColor() {
		return ledColor;
	}

	public void setLedColor(Color ledColor) {
		this.ledColor = ledColor;
	}

	public ArrayList<ArrayList<Led>> getMap() {
		return map;
	}

	public void setMap(ArrayList<ArrayList<Led>> map) {
		this.map = map;
	}
}
