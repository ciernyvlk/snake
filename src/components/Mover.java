package components;

import java.util.concurrent.atomic.AtomicBoolean;
import lejos.hardware.motor.Motor;

public class Mover implements Runnable {

	private final AtomicBoolean moverMoveStop;
	private final AtomicBoolean moverTurnAround;
	private final AtomicBoolean end;
	Boolean move;
	
	public Mover(AtomicBoolean moverMoveStop, AtomicBoolean moverTurnAround, AtomicBoolean end) {
		this.moverMoveStop = moverMoveStop;
		this.moverTurnAround = moverTurnAround;
		this.end = end;
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
