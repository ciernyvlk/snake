package components;

import java.util.concurrent.atomic.AtomicBoolean;
import lejos.hardware.motor.Motor;
import lejos.utility.Delay;

public class TurnAround implements Runnable {

	private AtomicBoolean moverTurnAround;
	
	public TurnAround(AtomicBoolean moverTurnAround) {
		this.moverTurnAround = moverTurnAround;

	    Motor.B.setSpeed(360);
	    Motor.C.setSpeed(360);
	}

	public void run() {
		while(true) {
			listen();
		}
	}
	
	private void listen() {
		synchronized(moverTurnAround) {
			try {
				while(moverTurnAround.get() == false) {
					moverTurnAround.wait();					
				}
				moverTurnAround.compareAndSet(true, false);
				moverTurnAround.notifyAll();
				
		    	Motor.B.forward();
		    	Motor.C.backward();
		    	Delay.msDelay(1000L);
		    	Motor.B.stop();
		    	Motor.C.stop();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}
}
