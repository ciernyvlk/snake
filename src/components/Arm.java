package components;

import lejos.hardware.motor.Motor;

//import communication.Chat;

public class Arm implements Runnable {
	//Chat m;
	Boolean open;
	
	/*
	public Arm(Chat m) {
		this.m = m;
		new Thread(this, "ArmListener").start();
	}
	*/
	
	public Arm(Boolean open) {
		this.open = open;
	}

	public void run() {
		while(true) {
			listen();
		}
	}
	
	private void listen() {
		synchronized(open) {
			try {
				open.wait();
				if(open) {
					//open = false;
			    	Motor.D.rotate(-135);
					Thread.sleep(1000);
				} else {
					//open = true;
			    	Motor.D.rotate(135);
					Thread.sleep(1000);		
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
