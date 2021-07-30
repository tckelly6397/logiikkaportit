package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import components.Node;
import utils.Spot;

public class MinusButton extends Button {
	ArrayList<Node> nodes;
	
	public MinusButton(Spot location, int WIDTH, int HEIGHT, Color c, String label, ArrayList<Node> nodes) {
		super(location, WIDTH, HEIGHT, c, label);
		this.nodes = nodes;
	}
	
	@Override
	public void draw(Graphics g) {
		int x = getLocation().getXAsInt();
		int y = getLocation().getYAsInt();
		int thickness = 10;
		
		g.setColor(Color.BLACK);
		if(getHovered()) {
			g.setColor(new Color(clamp(g.getColor().getRed() + 20, 255), clamp(g.getColor().getGreen() + 20, 255), clamp(g.getColor().getBlue() + 20, 255)));
		}
		
		g.fillRect(x, y + (getHEIGHT() / 2) - (thickness / 2), getWIDTH(), thickness);
	}
	
	@Override
	public void leftClick(int x, int y) {
		if(getCollision(x, y)) {
			nodes.remove(nodes.size() - 1);
		}
	}

}
