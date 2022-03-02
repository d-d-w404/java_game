package Scene;

public class SceneThread extends Thread{
	private Scene scene;
	
	public SceneThread(Scene scene) {
		this.scene=scene;
	}
	
	public void run() {
		while(true) {
			this.scene.refresh_all();
			//System.out.println("this is GameThread");
			try {
				sleep(8);
			}catch(Exception e) {
				System.out.println(e+"in thread");
			}
		}
	}

}
