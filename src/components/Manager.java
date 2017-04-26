package components;

import interfaces.ManagerListener;

public class Manager extends Thread {
	//private Integer[] pairedSocks;
	//private Integer holdSockId;
	boolean end;
	Mover mover;
	Arm arm;
	
	private ManagerListener moverListener;
	private ManagerListener armListener;
	
	public Manager() {
		//pairedSocks = new Integer[3];
		//holdSockId = (Integer)null;
		end = false;
		
		mover = new Mover();
		moverListener = mover;
		(new Thread(mover)).start();
		
		arm = new Arm();
		armListener = arm;
		(new Thread(arm)).start();		
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

	private void Open() {
		armListener.Open();
	}

	private void Close() {
		armListener.Open();
	}
	
	public void TestMover() throws InterruptedException {
		
		Move();
		Thread.sleep(1000);
		TurnAround();
		
		Thread.sleep(1000);
		Stop();
	}
	
	public void TestArm() {
		Open();
		Close();
	}

}
