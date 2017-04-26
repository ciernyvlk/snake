package components;

import lejos.hardware.motor.Motor;
import lejos.utility.Delay;
import interfaces.ManagerListener;

public class Mover implements ManagerListener, Runnable {
	public Mover() {
	    Motor.B.setSpeed(360);
	    Motor.C.setSpeed(360);
	}

	public void run() {
	    Move();		
	}
	
	public void Move() {
    	Motor.B.forward();
    	Motor.C.forward();
	}

	public void TurnAround() {
    	Motor.B.forward();
    	Motor.C.backward();	
    	Delay.msDelay(1000L);
    	Move();
	}

	public void Stop() throws InterruptedException {
    	Motor.B.stop();
    	Motor.C.stop();
		Thread.sleep(5000);
	}
	
	public void Open() {}
	
	public void Close() {}	
}
