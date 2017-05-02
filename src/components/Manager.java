package components;

import java.util.concurrent.atomic.AtomicBoolean;

public class Manager extends Thread implements Runnable {
	//private Integer[] pairedSocks;
	//private Integer holdSockId;
	//boolean end;
	//Mover mover;
	//Arm arm;
	
	private final AtomicBoolean armOpenClose;
	private final AtomicBoolean end;

	private final AtomicBoolean moverMoveStop;
	private final AtomicBoolean moverTurnAround;
	
	//private ManagerListener moverListener;
	//private ManagerListener armListener;
	
	public Manager(AtomicBoolean armOpenClose, AtomicBoolean moverMoveStop,	AtomicBoolean moverTurnAround, AtomicBoolean end) {
		//pairedSocks = new Integer[3];
		//holdSockId = (Integer)null;
		//end = false;
		
		//mover = new Mover();
		//moverListener = mover;
		//(new Thread(mover)).start();
		
		this.armOpenClose = armOpenClose;
		this.end = end;	
		this.moverMoveStop = moverMoveStop;
		this.moverTurnAround = moverTurnAround;
	}

	
	public void TestArm() throws InterruptedException {
		//synchronized(end) {
			synchronized(armOpenClose) {
				for(int i = 0; i < 6; i++) {
					armOpenClose.compareAndSet(false, true);
					armOpenClose.notifyAll();
					while (armOpenClose.get() == true) {
						armOpenClose.wait();					
					}
					Thread.sleep(3000);
				}
			}
			//end.compareAndSet(false, true);
			//end.notifyAll();
			//while (end.get() == true) {
				//end.wait();					
			//}
		//}
	}
	
	public void TestMover() throws InterruptedException {
		synchronized(moverMoveStop) {
			for(int i = 0; i < 6; i++) {
				moverMoveStop.compareAndSet(false, true);
				moverMoveStop.notifyAll();
				while (moverMoveStop.get() == true) {
					moverMoveStop.wait();
				}
				Thread.sleep(3000);
			}
		}
	}
	
	public void run() {
		//synchronized(end) {
			try {
				//TestArm();
				TestMover();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//end.compareAndSet(false, true);
			//end.notifyAll();
		//}
	}
}
