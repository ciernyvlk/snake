package components;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import lejos.hardware.Sound;

import sensors.CameraListener;
import sensors.InfraredListener;

public class Manager extends Thread implements Runnable, CameraListener, InfraredListener {
	private List<Integer> pairedSocks;
	private Integer holdSockId;
	
	private AtomicBoolean armOpenClose;
	private AtomicBoolean moverMoveStop;
	private AtomicBoolean moverTurnAround;
	
	public Manager(AtomicBoolean armOpenClose, AtomicBoolean moverMoveStop,	AtomicBoolean moverTurnAround) {
		pairedSocks = new ArrayList<Integer>();
		holdSockId = (Integer)null;
		
		this.armOpenClose = armOpenClose;
		this.moverMoveStop = moverMoveStop;
		this.moverTurnAround = moverTurnAround;
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

	public void obstacle() throws InterruptedException {
		moveStop();
		turnAround();
		moveStop();	
	}

	public void black() throws InterruptedException {
		Sound.twoBeeps();
		moveStop();
		turnAround();
		moveStop();		
	}

	public void sock(int colorId) throws InterruptedException {
		if(holdSockId == null) {
			// If not holding any sock and no such color in the list
			// pick the sock
			if(!pairedSocks.contains(colorId)) {
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
				turnAround();
				moveStop();
				pairedSocks.add(colorId);
				holdSockId = null;
			}
		}		
	}
	
	public void TestArm() throws InterruptedException {
		for(int i = 0; i < 4; i++) {
			openCloseArm();
			Thread.sleep(3000);			
		}
	}
	
	public void TestMover() throws InterruptedException {
		for(int i = 0; i < 4; i++) {
			moveStop();
			Thread.sleep(3000);			
		}
	}
	
	public void TestTurnAround() throws InterruptedException {
		for(int i = 0; i < 4; i++) {
			turnAround();
			Thread.sleep(3000);			
		}
	}
	
	public void TestMoveTurnAroundArm() throws InterruptedException {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 2; j++) {
				moveStop();
				Thread.sleep(2000);					
			}
			openCloseArm();
			turnAround();
		}
	}
	
	public void run() {
		try {
			moveStop();
			
			//TestMoveTurnAroundArm();
			//TestTurnAround();
			//TestMover();
			//TestArm();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
