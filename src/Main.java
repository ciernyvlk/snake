import components.Arm;
import components.Manager;

public class Main
{
  public static void main(String[] args) throws InterruptedException
  {
	  //Manager manager = new Manager();
	  
	  Boolean open = new Boolean(true);

	  Thread tArm = new Thread(new Arm(open), "Arm");
	  Thread tManager = new Thread(new Manager(open), "Manager");

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