package components;

import java.util.concurrent.atomic.AtomicBoolean;
import lejos.hardware.motor.Motor;

public class Move implements Runnable {

	private AtomicBoolean moverMoveStop;
	Boolean move;
	
	public Move(AtomicBoolean moverMoveStop) {
		this.moverMoveStop = moverMoveStop;
		move = false;

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
				while(moverMoveStop.get() == false) {
					moverMoveStop.wait();					
				}
				moverMoveStop.compareAndSet(true, false);
				moverMoveStop.notifyAll();
				if(move) {
					move = false;
			    	Motor.B.stop();
			    	Motor.C.stop();
				} else {
					move = true;
			    	Motor.B.forward();
			    	Motor.C.forward();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}
}
