package components;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import lejos.hardware.Sound;

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
		pairedSocks = new ArrayList<Integer>();
		holdSockId = (Integer)null;
		
		this.armOpenClose = armOpenClose;
		this.moverMoveStop = moverMoveStop;
		this.moverTurnAround = moverTurnAround;
		this.moveBackwards = moveBackwards;
	}
	
	public void openCloseArm() throws InterruptedException {
		synchronized(armOpenClose) {
			armOpenClose.compareAndSet(false, true);
			armOpenClose.notifyAll();
			while (armOpenClose.get() == true) {
				armOpenClose.wait();					
			}
		}
	}
	
	public void moveStop() throws InterruptedException {
		synchronized(moverMoveStop) {
			moverMoveStop.compareAndSet(false, true);
			moverMoveStop.notifyAll();
			while (moverMoveStop.get() == true) {
				moverMoveStop.wait();
			}
		}
	}
	
	public void turnAround() throws InterruptedException {
		synchronized(moverTurnAround) {
			moverTurnAround.compareAndSet(false, true);
			moverTurnAround.notifyAll();
			while (moverTurnAround.get() == true) {
				moverTurnAround.wait();
			}
		}
	}
	
	public void moveBackwards() throws InterruptedException {
		synchronized(moveBackwards) {
			moveBackwards.compareAndSet(false, true);
			moveBackwards.notifyAll();
			while (moveBackwards.get() == true) {
				moveBackwards.wait();
			}
		}
	}

	public void obstacle() throws InterruptedException {
		moveStop();
		turnAround();
		moveStop();	
	}

	public void black() throws InterruptedException {
		Sound.twoBeeps();
		moveStop();
		moveBackwards();
		turnAround();
		moveStop();		
	}

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
	}
	
	public void run() {
		try {
			moveStop();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
