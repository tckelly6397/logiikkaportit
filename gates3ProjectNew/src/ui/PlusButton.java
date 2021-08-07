package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import components.Node;
import gates3Project.Initialize;
import utils.Spot;
import utils.Tools;

public class PlusButton extends Button {
	private ArrayList<Node> nodes;
	private Boolean isInput;
	
	public PlusButton(Spot location, int WIDTH, int HEIGHT, Color c, String label, ArrayList<Node> nodes, Boolean isInput) {
		super(location, WIDTH, HEIGHT, c, label);
		this.nodes = nodes;
		this.isInput = isInput;
	}
	
	@Override
	public void draw(Graphics g) {
		int x = getLocation().getXAsInt();
		int y = getLocation().getYAsInt();
		int thickness = 10;
		
		g.setColor(Color.BLACK);
		if(getHovered()) {
			g.setColor(new Color(Tools.clamp(g.getColor().getRed() + 20, 255), Tools.clamp(g.getColor().getGreen() + 20, 255), Tools.clamp(g.getColor().getBlue() + 20, 255)));
		}
		
		g.fillRect(x, y + (getHEIGHT() / 2) - (thickness / 2), getWIDTH(), thickness);
		g.fillRect(x + (getWIDTH() / 2) - (thickness / 2), y, thickness, getHEIGHT());
	}
	
	@Override
	public void leftClick(int x, int y) {
		if(getCollision(x, y)) {
			int defSize = 30;
			nodes.add(new Node(0, 0, defSize, isInput, Initialize.e));
		}
	}

}
