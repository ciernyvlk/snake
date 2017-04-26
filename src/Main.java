import components.Manager;

public class Main
{
  public static void main(String[] args)
  {
	  Manager manager = new Manager();
	  
	  try {
		manager.TestMover();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
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