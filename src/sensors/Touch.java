package sensors;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import components.Manager;

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
		while(true) {
			touch.fetchSample(sample, 0);
			if(sample[0] != 0) {
				try {
					obstacle();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void obstacle() throws InterruptedException {
		touchListener.obstacle();
	}

}
