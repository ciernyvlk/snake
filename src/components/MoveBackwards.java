package components;

import java.util.concurrent.atomic.AtomicBoolean;

import lejos.hardware.motor.Motor;
import lejos.utility.Delay;

public class MoveBackwards implements Runnable {
	private AtomicBoolean moveBackwards;
	
	public MoveBackwards(AtomicBoolean moveBackwards) {
		this.moveBackwards = moveBackwards;

	    Motor.B.setSpeed(360);
	    Motor.C.setSpeed(360);
	}

	public void run() {
		while(true) {
			listen();
		}
	}
	
	private void listen() {
		synchronized(moveBackwards) {
			try {
				while(moveBackwards.get() == false) {
					moveBackwards.wait();					
				}
				moveBackwards.compareAndSet(true, false);
				moveBackwards.notifyAll();
				
		    	Motor.B.backward();
		    	Motor.C.backward();
		    	Delay.msDelay(1500L);
		    	Motor.B.stop();
		    	Motor.C.stop();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}

}
