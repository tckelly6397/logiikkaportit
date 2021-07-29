package peripherals;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import components.Chip;
import components.Node;
import gates3Project.Initialize;

public class MainKeyHandler implements KeyListener {

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getKeyCode());
		if(e.getKeyCode() == KeyEvent.VK_A) {
			System.out.println(e);
			for(Chip c : Initialize.e.getChips())
				for(Node n : c.getInputNodes()) {
					System.out.println(n);
				}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
