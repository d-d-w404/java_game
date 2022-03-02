package gamelogic;

import java.awt.Point;

import Control.GameKeyController;
import Player.Player;
import Scene.Scene;
import Scene.SceneGetMapData;
import Scene_display.SceneCanvas;
import Scene_display.SceneFrame;
import prefs.PRFS;

public class Game {
	private Scene scene;

//	private GameFrame frame;
//	private GameCanvas canvas;
//	
//	private Player player;
//	
//	private GameKeyController gameKeyController;
//	
//	private GameThread theGame;
	//private boolean refreshing;

	

	
	public Game(){

		SceneGetMapData scene_map=new SceneGetMapData("tester.map");

		scene=new Scene(scene_map);
		


		
	}
	
//	private void initFrame() {
//		frame.add(canvas);
//		frame.setSize(PRFS.GameWidth,PRFS.GameHeight);
//		frame.setResizable(false);
//		frame.setVisible(true);
//		canvas.getworld(scene);
//		frame.addKeyListener(gameKeyController);
//		//当world对象生成后，我把world对象导入canvas中，让
//		//canvas使用world中的draw函数
//	}
	
//	public void refresh() {
//		canvas.repaint();
//		
//		player.refresh(scene);
//		
//		long currentTime = System.currentTimeMillis();
//		gameKeyController.calculateKeyPresses();
//
//	}
	
//	
//	public void resetKeyController() {
//		gameKeyController = new GameKeyController(player);
//	}
	
	
	public static void main(String[] args) {
		Game game=new Game();
	}

}
