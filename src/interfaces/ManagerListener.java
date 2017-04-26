package interfaces;

public interface ManagerListener {
	void Move();
	
	void TurnAround();
	
	void Stop() throws InterruptedException;
	
	void Open();
	
	void Close();
}
