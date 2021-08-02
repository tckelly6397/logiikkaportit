package peripherals;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import gates3Project.Initialize;
import ui.InputLabel;

public class MainKeyHandler implements KeyListener {

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		ArrayList<InputLabel> labels = Initialize.e.getCreateChipUI().getLabels();
		
		for(InputLabel il : labels) {
			if(e.getKeyCode() == KeyEvent.VK_RIGHT && il.getSelected())
				il.moveCursor(1);
			
			if(e.getKeyCode() == KeyEvent.VK_LEFT && il.getSelected())
				il.moveCursor(-1);
			
			if(il.getSelected() && ((Character.isLetter(e.getKeyChar()) && il.getLetters()) || (Character.isDigit(e.getKeyChar()) && il.getNumbers())))
				il.addCharacter(e.getKeyChar());
			
			if(il.getSelected() && e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
				il.removeCharacter();
		}
		
		Initialize.e.update();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
