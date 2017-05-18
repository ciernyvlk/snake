package sensors;

import components.Manager;

public class Touch implements Runnable {
	
	private TouchListener touchListener;
	
	public Touch(Manager manager) {
		touchListener = manager;
	}

	public void run() {
		while(true) {
			// ...
		}
	}
	
	private void obstacle() throws InterruptedException {
		touchListener.obstacle();
	}

}
