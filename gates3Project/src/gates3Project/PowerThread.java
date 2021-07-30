package gates3Project;

import java.util.concurrent.*;

import components.Component;

public class PowerThread implements Runnable {
	private CopyOnWriteArrayList<Component> nextList = new CopyOnWriteArrayList<>();
	volatile boolean inUse = false;
	private int wait = 17;
	
	@Override
	public void run() {
		while(true) {
			if(Initialize.e == null || nextList.isEmpty()) 
				continue;
				
			int size = nextList.size() - 1;
			for(Component c : nextList)
				if(c != null) {
					c.update();
				}
			
			for(int i = size; i >= 0; i--) 
				nextList.remove(i);
			
			//System.out.println(nextList);
		
			try {
				Thread.sleep(wait);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void addNext(Component next) {
		if(nextList.size() < 1000) {//Stops ArrayList heap error 
			this.nextList.add(next);
		}
	}
	
	public void setWait(int wait) {
		this.wait = wait;
	}
}
