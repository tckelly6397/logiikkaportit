package peripherals;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import components.Chip;
import components.Node;
import gates3Project.Initialize;
import ui.InputLabel;

public class MainKeyHandler implements KeyListener {

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(e.getKeyCode());
		if(e.getKeyCode() == KeyEvent.VK_A) {
			//System.out.println(e);
			for(Chip c : Initialize.e.getChips())
				for(Node n : c.getOutputNodes()) {
					System.out.println(n);
				}
		}
		
		InputLabel inputLabel = Initialize.e.getCreateChipUI().getNameLabel();
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			if(inputLabel.getSelected())
				inputLabel.moveCursor(1);
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			if(inputLabel.getSelected())
				inputLabel.moveCursor(-1);
		
		if(inputLabel.getSelected() && Character.isLetter(e.getKeyChar()))
			inputLabel.addCharacter(e.getKeyChar());
		
		if(inputLabel.getSelected() && e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
			inputLabel.removeCharacter();
		
		Initialize.e.update();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
