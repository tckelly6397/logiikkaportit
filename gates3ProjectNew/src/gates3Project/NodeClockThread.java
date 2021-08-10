package gates3Project;

import java.util.ArrayList;

import components.Node;

public class NodeClockThread implements Runnable {

	@Override
	public void run() {
		while(true) {
			ArrayList<Node> allNodes = Node.getAllNodes();
			for(Node node : allNodes) {
				if(node.isClock()) {
					node.switchPowered();
					node.update();
				}
			}
			
			try {
	    		Thread.sleep(1000);
	    	}catch(InterruptedException e) {
	    		
	    	}
		}
	}

}
