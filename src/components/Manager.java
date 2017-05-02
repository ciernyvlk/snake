package components;

import java.util.concurrent.atomic.AtomicBoolean;

import interfaces.ManagerListener;

public class Manager extends Thread implements Runnable {
	//private Integer[] pairedSocks;
	//private Integer holdSockId;
	//boolean end;
	Mover mover;
	//Arm arm;
	
	private final AtomicBoolean action;
	private final AtomicBoolean end;
	
	private ManagerListener moverListener;
	//private ManagerListener armListener;
	
	public Manager(AtomicBoolean action, AtomicBoolean end) {
		//pairedSocks = new Integer[3];
		//holdSockId = (Integer)null;
		//end = false;
		
		//mover = new Mover();
		//moverListener = mover;
		//(new Thread(mover)).start();
		
		this.action = action;
		this.end = end;
		
		//arm = new Arm();
		//armListener = arm;
		//(new Thread(arm)).start();
		
	}

	
	public void TestArm() throws InterruptedException {
		//synchronized(end) {
			synchronized(action) {
				for(int i = 0; i < 6; i++) {
					action.compareAndSet(false, true);
					//action = true;
					action.notifyAll();
					while (action.get() == true) {
						action.wait();					
					}
					Thread.sleep(3000);
				}
			}
			//end.compareAndSet(false, true);
			//end.notifyAll();
		//}
	}
	
	public void run() {
		//synchronized(end) {
			try {
				TestArm();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//end.compareAndSet(false, true);
			//end.notifyAll();
		//}
	}
	
	
	private void Move() {
		moverListener.Move();
	}

	private void TurnAround() {
		moverListener.TurnAround();
	}

	private void Stop() throws InterruptedException {
		moverListener.Stop();
		mover.wait();
	}
	
	public void TestMover() throws InterruptedException {
		
		Move();
		Thread.sleep(1000);
		TurnAround();
		
		Thread.sleep(1000);
		Stop();
	}

	/*
	private void Open() {
		armListener.Open();
	}

	private void Close() {
		armListener.Open();
	}
	*/
	
	/*
	public void TestArm() {
		Open();
		Close();
	}
	*/
	
	

}
