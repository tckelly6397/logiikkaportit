package utils;

import java.awt.Color;

import ui.Button;
import utils.commands.SelectLedCommand;

public class Led extends Button {
	private boolean powered;
	private Color c;
	private boolean selected;
	
	public Led() {
		super(new Spot(-500, -500), 10, 10, new Color(25, 25, 25), "");
		this.powered = false;
		this.c = Color.RED;
		this.selected = false;
		
		setBorderC(Color.BLACK);
		setCommand(new SelectLedCommand(this));
	}

	public boolean isPowered() {
		return powered;
	}

	public void setPowered(boolean powered) {
		this.powered = powered;
	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public void switchSlected() {
		this.selected = !this.selected;
	}

	@Override
	public String toString() {
		return "Led [powered=" + powered + ", c=" + c + "]";
	}
}
