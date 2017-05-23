package motors;

import java.util.concurrent.atomic.AtomicBoolean;
import lejos.hardware.motor.Motor;

public class Move implements Runnable {

	private AtomicBoolean moverMoveStop;
	Boolean move;	// indicates whether the robot is moving or not
	
	public Move(AtomicBoolean moverMoveStop) {
		this.moverMoveStop = moverMoveStop;
		move = false;

		// set the speed of the motors
	    Motor.B.setSpeed(360);
	    Motor.C.setSpeed(360);
	}

	public void run() {
		while(true) {
			listen();
		}
	}
	
	private void listen() {
		synchronized(moverMoveStop) {
			try {
				// wait for the notification from the manager
				while(moverMoveStop.get() == false) {
					moverMoveStop.wait();					
				}
				moverMoveStop.compareAndSet(true, false);
				moverMoveStop.notifyAll();
				
				if(move) {	// if the robot is moving
					move = false;
					// stop
			    	Motor.B.stop();
			    	Motor.C.stop();
				} else {	// if the robot is stopped
					move = true;
					// start moving forward
			    	Motor.B.forward();
			    	Motor.C.forward();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}
}
