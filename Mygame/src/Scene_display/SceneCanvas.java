package Scene_display;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

import Scene.Scene;
import gamelogic.Game;

public class SceneCanvas extends JComponent{

	Scene scene;
	
	public SceneCanvas() {
		super();
		setPreferredSize(new Dimension(600,600));
		//setPreferredSize是设定画布大小的函数，参数时一个Demension
		//对象


	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		scene.draw(g);
	}
	
	public void getworld(Scene scene) {
		this.scene=scene;
	}
	
	//我得想办法把world对象传到这个类中，才能调用它的draw函数

}
