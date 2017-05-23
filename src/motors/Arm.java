package motors;

import java.util.concurrent.atomic.AtomicBoolean;
import lejos.hardware.motor.Motor;

public class Arm implements Runnable {
	private final AtomicBoolean armOpenClose;
	Boolean open;	// indicates whether the arm is closed or open
	
	public Arm(AtomicBoolean armOpenClose) {
		this.armOpenClose = armOpenClose;
		open = true;	// the arm starts as open
	}

	public void run() {
		while(true) {
			listen();
		}
	}
	
	private void listen() {
		synchronized(armOpenClose) {
			try {
				// wait for the notification from the manager
				while(armOpenClose.get() == false) {
					armOpenClose.wait();					
				}
				armOpenClose.compareAndSet(true, false);
				armOpenClose.notifyAll();
				
				if(open) {	// if the arm is open
					open = false;
					// close the arm
			    	Motor.D.rotate(-135);
				} else {	// if the arm is closed
					open = true;
					// open the arm
			    	Motor.D.rotate(135);	
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}
}
