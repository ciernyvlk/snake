package sensors;

import components.Manager;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;

public class Infrared implements Runnable {
	EV3IRSensor sensor;
	
	private InfraredListener infraredListener;
	
	public Infrared(Manager manager) {
		sensor = new EV3IRSensor(SensorPort.S2);
		infraredListener = manager;
	}

	public void run() {
		while(true) {
			// ...
			
			/*
			try {
				obstacle();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
		}
	}
	
	private void obstacle() throws InterruptedException {
		infraredListener.obstacle();
	}

}
