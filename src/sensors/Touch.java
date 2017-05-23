package sensors;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import management.Manager;

public class Touch implements Runnable {

	EV3TouchSensor touchSensor;
	SensorMode touch;
	float[] sample;
	
	private TouchListener touchListener;
	
	public Touch(Manager manager) {
		touchSensor = new EV3TouchSensor(SensorPort.S2);
		touch = touchSensor.getTouchMode();
		sample = new float[touch.sampleSize()];
		touchListener = manager;
	}

	public void run() {
		// periodically check if the touch sensor is pressed
		while(true) {
			touch.fetchSample(sample, 0);
			// if an obstacle is detected send obstacle() event
			if(sample[0] != 0) {
				try {
					obstacle();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	// send obstacle() event to the touch listener (manager)
	private void obstacle() throws InterruptedException {
		touchListener.obstacle();
	}

}
