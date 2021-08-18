package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import gates3Project.Initialize;
import utils.Spot;
import utils.commands.ClosePromptCommand;

public class Prompt {
	private boolean shown;
	private Spot location;
	private String label;
	private int WIDTH;
	private int HEIGHT;
	private Button okBtn;
	
	public Prompt() {
		this.shown = false;
		this.location = new Spot(0, 0);
		this.label = "Cannot create chip due to duplicite chip names";
		this.WIDTH = 500;
		this.HEIGHT = 170;
		this.okBtn = new Button(this.location, 80, 50, new Color(20, 150, 20), "Ok");
		okBtn.setCommand(new ClosePromptCommand());
		okBtn.setBorderRadius(15);
	}
	
	public void draw(Graphics g) {
		if(!shown)
			return;
		
		int x = location.getXAsInt();
		int y = location.getYAsInt();
		
		g.setColor(new Color(20, 20, 20));
		g.fillRoundRect(x, y, WIDTH, HEIGHT, 15, 15);
		
		g.setColor(new Color(255, 130, 10));
		g.drawRoundRect(x, y, WIDTH, HEIGHT, 15, 15);
		
		g.setFont(new Font("Verdana", Font.BOLD, 40));
		
		JTextPane ta = new JTextPane();
		ta.setText(label);
		ta.setLayout(null);
	    ta.setBounds(0, 0, WIDTH - 100, HEIGHT - 50);
	    ta.setForeground(Color.WHITE);
	    ta.setBackground(new Color(0, 0, 0, 0));
	    ta.setFont(g.getFont());
	    
	    //Center
	    StyledDocument doc = ta.getStyledDocument();
	    SimpleAttributeSet center = new SimpleAttributeSet();
	    StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
	    doc.setParagraphAttributes(0, doc.getLength(), center, false);
	    
	    Graphics g2 = g.create(x + 50, y, WIDTH, HEIGHT); // Use new graphics to leave original graphics state unchanged
	    ta.paint(g2);
	    
	    okBtn.draw(g);
	}
	
	public void updateLocation() {
		int x = (Initialize.e.getFrame().getWidth() / 2) - (WIDTH / 2);
		int y = (Initialize.e.getBox().getY() + (Initialize.e.getBox().getHEIGHT() / 2) - (HEIGHT / 2));
		
		this.location.setSpot(new Spot(x, y));
		
		this.okBtn.setLocation(new Spot(x + (WIDTH / 2) - (okBtn.getWIDTH() / 2), y + HEIGHT - okBtn.getHEIGHT() - 10));
	}
	
	public void leftClick(int x, int y) {
		this.okBtn.leftClick(x, y);
	}

	public void executeHovered(int x, int y) {
		okBtn.executeHovered(x, y);
	}
	
	public boolean isShown() {
		return shown;
	}

	public void setShown(boolean shown) {
		this.shown = shown;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
