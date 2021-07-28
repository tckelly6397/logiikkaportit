package gates3Project;

import java.util.ArrayList;

import components.Component;

public class PowerThread implements Runnable {
	ArrayList<Component> nextList = new ArrayList<>();
	
	@Override
	public void run() {
		while(true) {
			Environment e = Initialize.e;

			if(e == null || nextList.isEmpty()) 
				continue;

			ArrayList<Component> temp = new ArrayList<>(nextList);
			nextList = new ArrayList<>();
			
			for(Component c : temp)
				c.update();
			
			e.update();
			Initialize.e = e;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	public ArrayList<Component> getNext() {
		return nextList;
	}

	public void addNext(Component next) {
		this.nextList.add(next);
	}
}
