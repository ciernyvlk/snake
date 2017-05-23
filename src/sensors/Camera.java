package sensors;


import lejos.hardware.port.Port;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.lcd.LCD;
import management.Manager;

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
		// periodically check the color
		while (true) {
			LCD.clear();
			colorId = colorSensor.getColorID(); // get the color id
			
			// print the color id on the LCD
			LCD.drawInt(colorId, 2, 2);
	    	
	    	try {
	    		// if a color other than white is detected
	    		if(colorId != whiteId) {
	    			// if black color is detected generate black() event
			    	if(colorId == blackId) {
			    		cameraListener.black();
			    	} else {
			    		// if a sock is detected send sock() event
				    	cameraListener.sock(colorId);
			    	}	    			
	    		}
		    	Thread.sleep(500);
		    	LCD.refresh();
	    	} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	
		}	
	}
}
