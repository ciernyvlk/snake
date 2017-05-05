package sensors;

public interface CameraListener {
	void black() throws InterruptedException;
	
	void sock(int colorId) throws InterruptedException;
}
