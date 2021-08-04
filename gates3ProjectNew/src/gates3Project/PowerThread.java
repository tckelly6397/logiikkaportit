package gates3Project;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import components.Component;

public class PowerThread implements Runnable {
	private volatile CopyOnWriteArrayList<Component> nextList = new CopyOnWriteArrayList<>();
	private volatile AtomicBoolean inUse = new AtomicBoolean(false);
	private int wait = 17;
	
	@Override
	public void run() {
		while(true) {
			if(nextList.isEmpty()) {
				synchronized(Initialize.e) {
					Initialize.e.notify();
				}
			}
			
			if(Initialize.e == null || nextList.isEmpty()) {
				continue;
			}
			
			inUse.set(true);
			int size = nextList.size() - 1;
			for(Component c : nextList)
				if(c != null) {
					c.update();
				}
			
			for(int i = size; i >= 0; i--) 
				nextList.remove(i);
		
			try {
				Thread.sleep(wait);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	public AtomicBoolean getInUse() {
		return this.inUse;
	}
	
	public ArrayList<Component> getNextList() {
		return new ArrayList<Component>(this.nextList);
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
