package components;

import interfaces.ManagerListener;

public class Manager extends Thread implements Runnable {
	//private Integer[] pairedSocks;
	//private Integer holdSockId;
	boolean end;
	Mover mover;
	//Arm arm;
	
	Boolean open;
	
	private ManagerListener moverListener;
	//private ManagerListener armListener;
	
	public Manager(Boolean open) {
		//pairedSocks = new Integer[3];
		//holdSockId = (Integer)null;
		//end = false;
		
		//mover = new Mover();
		//moverListener = mover;
		//(new Thread(mover)).start();
		
		this.open = open;
		
		//arm = new Arm();
		//armListener = arm;
		//(new Thread(arm)).start();
		
	}

	
	public void TestArm() throws InterruptedException {
		synchronized(open) {
			for(int i = 0; i < 5; i++) {
				open.notify();
				open = !open;
				Thread.sleep(3000);
			}
		}
	}
	
	public void run() {
		try {
			TestArm();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
