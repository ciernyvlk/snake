package components;

import java.util.concurrent.atomic.AtomicBoolean;
import lejos.hardware.motor.Motor;

public class Arm implements Runnable {
	private final AtomicBoolean armOpenClose;
	private final AtomicBoolean end;
	Boolean open;
	
	public Arm(AtomicBoolean action, AtomicBoolean end) {
		this.armOpenClose = action;
		this.end = end;
		open = true;
	}

	public void run() {
		while(true) {
			listen();
		}
	}
	
	private void listen() {
		synchronized(armOpenClose) {
			try {
				while(armOpenClose.get() == false) {
					armOpenClose.wait();					
				}
				armOpenClose.compareAndSet(true, false);
				armOpenClose.notifyAll();
				if(open) {
					open = false;
			    	Motor.D.rotate(-135);
				} else {
					open = true;
			    	Motor.D.rotate(135);	
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}
}
