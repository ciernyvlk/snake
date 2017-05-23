import java.util.concurrent.atomic.AtomicBoolean;

import management.Manager;
import motors.Arm;
import motors.Move;
import motors.MoveBackwards;
import motors.TurnAround;

import sensors.Camera;
import sensors.Touch;


public class Main
{	  
  public static void main(String[] args) throws InterruptedException
  {	  	  
	  // Initialize the atomic variables for arm and movers
	  final AtomicBoolean armOpenClose = new AtomicBoolean(false);
	  final AtomicBoolean moverMoveStop = new AtomicBoolean(false);
	  final AtomicBoolean moverTurnAround = new AtomicBoolean(false);
	  final AtomicBoolean moverBackwards = new AtomicBoolean(false);

	  // Initialize the components of the robot (manager, motors, sensors)
	  Manager manager = new Manager(armOpenClose, moverMoveStop, moverTurnAround, moverBackwards);	
	  Move move = new Move(moverMoveStop);
	  TurnAround turnAround = new TurnAround(moverTurnAround);  
	  MoveBackwards moveBackwards = new MoveBackwards(moverBackwards);  
	  Arm arm = new Arm(armOpenClose);
	  Camera camera = new Camera(manager);
	  Touch touch = new Touch(manager);
	  
	  // Start the manager, motors, and sensors
	  (new Thread(arm)).start();
	  (new Thread(move)).start();
	  (new Thread(turnAround)).start();
	  (new Thread(moveBackwards)).start();
	  (new Thread(manager)).start();
	  (new Thread(camera)).start();
	  (new Thread(touch)).start();
  }
}