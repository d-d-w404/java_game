package Player;

import java.awt.Point;

import Entity.Entity;
import Entity.FormType;
import Imagetool.ImageHandler;
import Scene.Scene;

public class Player extends Entity{


	

	private int fps=10;
	private int frmNum=0;
	
	public Player(String name,int x,int y,int life,Scene scene) {
		super(name,x,y,life,60,60,ImageHandler.con_Image("man1/walk"),ImageHandler.con_Image("man1/stay"),ImageHandler.con_Image("man1/jump_model"),ImageHandler.con_Image("man1/attack"),ImageHandler.con_Image("man1/startjump"),ImageHandler.con_Image("man1/behit"),ImageHandler.con_Image("man1/blink"),ImageHandler.con_Image("man1/attack_black"),scene);
		//super(x,y,60,60,ImageHandler.split(3,"walk.png"),false);
		//super(x,y,60,60,ImageHandler.split(4,"pk.png"),false);
		//插入不同类型的图只要改变这里就可以了



        sceneposition=new Point(300,300);

		entitySpeed=1;
	
	}
		
	public void slowDownLeftRightMovement(){
		if(groundSpeed >0){
			facingRight = true;
			groundSpeed -= 0.2;
		}
		else{
			facingRight = false;
			groundSpeed += 0.2;
		}
	}

}
