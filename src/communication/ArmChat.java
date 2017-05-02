package communication;

import lejos.hardware.motor.Motor;

public class ArmChat {
	boolean open = true;
	
	public synchronized void Open() {
		open = true;
		notify();
	}
	
	public synchronized void Close() {
		open = false;
		notify();
	}
	
	public synchronized void ArmListener() {
		while(true) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(open) {
		    	Motor.D.rotate(-135);
			} else {
		    	Motor.D.rotate(135);			
			}
		}
	}
	
	public synchronized void TestRun() throws InterruptedException {
		for (int i = 0; i < 3; i++) {
			Open();
			Thread.sleep(2000);
			Close();
			Thread.sleep(2000);			
		}		
	}

}
