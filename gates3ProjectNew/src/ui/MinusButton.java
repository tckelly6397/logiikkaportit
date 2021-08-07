package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import components.Node;
import utils.Spot;
import utils.Tools;

public class MinusButton extends Button {
	ArrayList<Node> nodes;
	private Boolean isInput;
	
	public MinusButton(Spot location, int WIDTH, int HEIGHT, Color c, String label, ArrayList<Node> nodes, Boolean isInput) {
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
	}
	
	@Override
	public void leftClick(int x, int y) {
		if(getCollision(x, y)) {
			if(nodes.size() > 1) {
				Node n = nodes.get(nodes.size() - 1);
				n.destroy(isInput, !isInput);
			}
		}
	}

	public Boolean getIsInput() {
		return isInput;
	}

	public void setIsInput(Boolean isInput) {
		this.isInput = isInput;
	}
}
