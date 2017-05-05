import java.util.concurrent.atomic.AtomicBoolean;

import sensors.Camera;
import sensors.Infrared;

import components.Arm;
import components.Manager;
import components.Move;
import components.TurnAround;

public class Main
{	  
  public static void main(String[] args) throws InterruptedException
  {	  	  
	  final AtomicBoolean armOpenClose = new AtomicBoolean(false);
	  final AtomicBoolean moverMoveStop = new AtomicBoolean(false);
	  final AtomicBoolean moverTurnAround = new AtomicBoolean(false);

	  Manager manager = new Manager(armOpenClose, moverMoveStop, moverTurnAround);	
	  Move move = new Move(moverMoveStop);
	  TurnAround turnAround = new TurnAround(moverTurnAround);  
	  Arm arm = new Arm(armOpenClose);
	  //Camera camera = new Camera(manager);
	  //Infrared infrared = new Infrared(manager);
	  
	  (new Thread(arm)).start();
	  (new Thread(move)).start();
	  (new Thread(turnAround)).start();
	  (new Thread(manager)).start();
	  //(new Thread(camera)).start();
	  //(new Thread(infrared)).start();
  }
}