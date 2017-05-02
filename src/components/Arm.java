package components;

import java.util.concurrent.atomic.AtomicBoolean;

import lejos.hardware.motor.Motor;

//import communication.Chat;

public class Arm implements Runnable {
	//Chat m;
	private final AtomicBoolean action;
	private final AtomicBoolean end;
	Boolean open;
	
	/*
	public Arm(Chat m) {
		this.m = m;
		new Thread(this, "ArmListener").start();
	}
	*/
	
	public Arm(AtomicBoolean action, AtomicBoolean end) {
		this.action = action;
		this.end = end;
		open = true;
	}

	public void run() {
		//synchronized(end) {
			while(end.get() == false) {
				listen();
			}
		//}
	}
	
	private void listen() {
		synchronized(action) {
			try {
				while(action.get() == false) {
					action.wait();					
				}
				action.compareAndSet(true, false);
				//action = false;
				action.notifyAll();
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

	/*
	public void Move() {}

	public void TurnAround() {}

	public void Stop() {}

	public void Open() {
    	Motor.D.rotate(135);		
	}

	public void Close() {
    	Motor.D.rotate(-135);
	}
	*/

}
