package sensors;

import components.Manager;

import lejos.hardware.port.Port;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.lcd.LCD;

public class Camera implements Runnable {
	private Port cameraPort;
	private EV3ColorSensor colorSensor;
	private int colorId;
	private static final int blackId = -1;
	private static final int whiteId = 7;
	
	private CameraListener cameraListener;
	
	public Camera(Manager manager) {
		cameraListener = manager;
		cameraPort = LocalEV3.get().getPort("S1");
		colorSensor = new EV3ColorSensor(cameraPort);
		colorId = whiteId;
	}
	
	public void run() {
		while (true) {
			LCD.clear();
			colorId = colorSensor.getColorID();
			
			LCD.drawInt(colorId, 2, 2);
	    	
	    	try {
	    		if(colorId != whiteId) {
			    	if(colorId == blackId) {
						Sound.twoBeeps(); 
			    		cameraListener.black();
			    	} else {
				    	cameraListener.sock(colorId);
			    	}	    			
	    		}
		    	Thread.sleep(500);
		    	LCD.refresh();
	    	} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
		}	
	}
}
