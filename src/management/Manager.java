package management;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import sensors.CameraListener;
import sensors.TouchListener;

public class Manager extends Thread implements Runnable, CameraListener, TouchListener {
	private List<Integer> pairedSocks;
	private Integer holdSockId;
	
	private AtomicBoolean armOpenClose;
	private AtomicBoolean moverMoveStop;
	private AtomicBoolean moverTurnAround;
	private AtomicBoolean moveBackwards;
	
	public Manager(AtomicBoolean armOpenClose, AtomicBoolean moverMoveStop,	AtomicBoolean moverTurnAround, AtomicBoolean moveBackwards) {
		pairedSocks = new ArrayList<Integer>();		// list of colors of the socks that have already been sorted
		holdSockId = (Integer)null;		// color of the sock currently being held, null if none is being held
		
		this.armOpenClose = armOpenClose;
		this.moverMoveStop = moverMoveStop;
		this.moverTurnAround = moverTurnAround;
		this.moveBackwards = moveBackwards;
	}
	
	// open or close the arm
	public void openCloseArm() throws InterruptedException {
		synchronized(armOpenClose) {
			armOpenClose.compareAndSet(false, true);
			// notify Arm to open or close
			armOpenClose.notifyAll();
			while (armOpenClose.get() == true) {
				armOpenClose.wait();					
			}
		}
	}
	
	// move or stop the robot
	public void moveStop() throws InterruptedException {
		synchronized(moverMoveStop) {
			moverMoveStop.compareAndSet(false, true);
			// notify Move to move forward or stop
			moverMoveStop.notifyAll();
			while (moverMoveStop.get() == true) {
				moverMoveStop.wait();
			}
		}
	}
	
	// turn around
	public void turnAround() throws InterruptedException {
		synchronized(moverTurnAround) {
			moverTurnAround.compareAndSet(false, true);
			moverTurnAround.notifyAll();	// notify TurnAround
			while (moverTurnAround.get() == true) {
				moverTurnAround.wait();
			}
		}
	}
	
	// move backwards
	public void moveBackwards() throws InterruptedException {
		synchronized(moveBackwards) {
			moveBackwards.compareAndSet(false, true);
			moveBackwards.notifyAll();	// notify move backwards
			while (moveBackwards.get() == true) {
				moveBackwards.wait();
			}
		}
	}

	// If an obstacle is detected stop, move backwards, turn around and move again
	public void obstacle() throws InterruptedException {
		moveStop();
		moveBackwards();
		turnAround();
		moveStop();
	}

	// If the black line is detected stop, move backwards, turn around and move again
	public void black() throws InterruptedException {
		moveStop();
		moveBackwards();
		turnAround();
		moveStop();
	}

	// Process the detected sock
	public void sock(int colorId) throws InterruptedException {
		if(holdSockId == null) {
			// If not holding any sock and no such color in the list
			// pick the sock
			if(!pairedSocks.contains(colorId)) {
				Thread.sleep(500);		// sleep till you get closer to the sock
				moveStop();
				openCloseArm();
				moveStop();
				holdSockId = colorId;
			}
		} else {
			// If holding sock of the same color
			// leave the sock there and add the color to the list
			if(holdSockId == colorId) {
				moveStop();
				openCloseArm();
				moveBackwards();
				turnAround();
				moveStop();
				pairedSocks.add(colorId);
				holdSockId = null;
			}
		}
		// otherwise ignore the sock (socks of such color have already been sorted
		// or the sock is of different color than the cock currently being held)
	}
	
	public void run() {
		try {
			moveStop();	// start moving
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
