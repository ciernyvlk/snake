import java.util.concurrent.atomic.AtomicBoolean;

import components.Arm;
import components.Manager;

public class Main
{
	/*
	public static boolean action = false;
	
	public boolean getAction() {
		return action;
	}
	*/
	  
  public static void main(String[] args) throws InterruptedException
  {
	  //Manager manager = new Manager();
	  
	  final AtomicBoolean action = new AtomicBoolean(false);
	  final AtomicBoolean end = new AtomicBoolean(false);

	  //Thread tArm = new Thread(new Arm(), "Arm");
	  //Thread tManager = new Thread(new Manager(), "Manager");
	  
	  Thread tArm = new Thread(new Arm(action, end), "Arm");
	  Thread tManager = new Thread(new Manager(action, end), "Manager");

	  tArm.start();
	  tManager.start();	  
	  
	  Thread.sleep(10000);
	  	  
	  
  	/*
    Motor.B.setSpeed(360);
    Motor.C.setSpeed(360);
    for (int i = 0; i < 4; i++)
    {
    	// Move
    	Motor.B.forward();
    	Motor.C.forward();
    	Delay.msDelay(5000L);
    	Motor.B.stop();
    	Motor.C.stop();
    	
    	// Turn around
    	Motor.B.forward();
    	Motor.C.backward();
    	Delay.msDelay(1000L);
    	Motor.B.stop();
    	Motor.C.stop();
    	
    	// Arm
    	Motor.D.rotate(135);
    	Motor.D.rotate(-135);
    	Delay.msDelay(1000L);
    	
    	// Camera
    	Port cameraPort = LocalEV3.get().getPort("S1"); 
    	EV3ColorSensor colorSensor = new EV3ColorSensor(cameraPort);
    	int colorId = colorSensor.getColorID();
    	
    	LCD.drawInt(colorId, 0, 0);
    	for (int j = 0; j < colorId; j++) {
        	Sound.beep();    		
    	}
    	
    	colorSensor.close();
    	Delay.msDelay(2000L);
    }
    */
  }
}