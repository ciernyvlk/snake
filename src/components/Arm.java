package components;

import lejos.hardware.motor.Motor;
import interfaces.ManagerListener;

public class Arm implements ManagerListener, Runnable {

	public void run() {
		
	}

	public void Move() {}

	public void TurnAround() {}

	public void Stop() {}

	public void Open() {
    	Motor.D.rotate(135);		
	}

	public void Close() {
    	Motor.D.rotate(-135);
	}

}
