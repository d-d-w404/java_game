package Monster;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

import Control.GameKeyController.direction;
import Entity.Entity;
import Imagetool.ImageHandler;
import Player.Player;
import Scene.Scene;

public class Monster extends Entity{

	//private String name;
	private Queue<direction> directions = new LinkedList<direction>();
    Player player;
	Point playerPosition;
	private double offsetx;
	private double offsety;
	

	private int fps=10;
	private int frmNum=0;
	
	
	
	public Monster(String name,int x,int y,int life,Player player,Scene scene) {
		super(name,x,y,life,100,100,ImageHandler.con_Image("man1/walk"),ImageHandler.con_Image("man1/stay"),ImageHandler.con_Image("man1/jump_model"),ImageHandler.con_Image("man1/attack"),ImageHandler.con_Image("man1/startjump"),ImageHandler.con_Image("man1/behit"),ImageHandler.con_Image("man1/blink"),ImageHandler.con_Image("man1/blink"),scene);
		//super(x,y,60,60,ImageHandler.split(3,"walk.png"),false);
		//super(x,y,60,60,ImageHandler.split(4,"pk.png"),false);
		//插入不同类型的图只要改变这里就可以了
        this.player=player;


		entitySpeed=1;
	
	}
	public void refresh() {
		ai();
		//把direction队列中填入不同值，然后调用entity中的move
		//有了move，还需要entity中的refresh()函数
		//不过这个refresh只是更新了位置。
		//Monster的不同form的图片的切换是靠entity中的draw
		super.refresh();
		
		//上面两个主要是控制monster的移动
		
		hitplayer();
		//上面这个是控制monster的攻击
	}

	
	public void ai() {
		playerPosition = player.getPosition();//引入player，是因为他是可见框的中心，为了找到offset
//		double distance=Math.pow(position.x-playerPosition.x,2) + Math.pow(position.y-playerPosition.y,2) ;
//		if(distance>=85000) {
//			position.x=playerPosition.x;
//			position.y=playerPosition.y;
//			distance=0;
//		}
		
		//System.out.println(distance);
		//这个及时回到人物身边的函数先不写，可以作为未来跟随功能实现
		sceneposition=new Point(position.x-playerPosition.x+300,position.y-playerPosition.y+300);
		
		
		//3.1在不同的条件下，改变monster的situation以及添加direction
		if(directions.size() < 10){	
			//outoftrack.outtrack();//用于解除困境

		    if(position.x <= playerPosition.x-getWidth()/2 && ((int)Math.random()*100)<2) {
						    directions.add(direction.RIGHT);
		    }
		    if(position.x >= playerPosition.x+getWidth()/2 && ((int)Math.random()*100)<2) {
						    directions.add(direction.LEFT);
		    }
		    if(position.y+height > playerPosition.y +player.getHeight() && (position.x > playerPosition.x - 300 && position.x < playerPosition.x + 300)&& !falling &&((int)Math.random()*100)<2) {
				if(Math.pow(position.x+getWidth()/2-playerPosition.x-player.getWidth()/2,2.0)+Math.pow(position.y+getHeight()/2-playerPosition.y-player.getHeight()/2,2.0)<Math.pow(200,2.0)) {
			    	if(Math.random()>1) {
						directions.add(direction.UP);
					}
				}else {
			    	if(Math.random()>0.95) {
						directions.add(direction.UP);
					}//我感觉monster的jump有点频繁，也使用随机函数
					//在距离player近的地方不跳或少跳，在需要翻墙的地方稍微多跳一点
				}	
		    } 	
		    //上面对jump的判断，当monster超出屏幕时，不再跳跃，当离我比较近时，不再跳跃
		    //只有和player保持着某种距离时，才会跳跃
		    //似乎实现了player勾引monster的作用
		    
		//(position.x > playerPosition.x - 300 && position.x < playerPosition.x + 300)这个部分是让monster在人物附近一段范围内
		//引发UP和DOWN的动作，超过这段范围后，及时人物在monster的下面，它也不会DOWN，所以我打算设计一个函数，当monster有一段时间
		//position没动过后就会触发UP和DOWN,让它摆脱困境
		    if(position.y+height< playerPosition.y  && (position.x > playerPosition.x - 300 && position.x < playerPosition.x + 300)&& ((int)Math.random()*100)<2) {
						    directions.add(direction.DOWN);
		    }

		
		
//		
//		    if(position.x+width > playerPosition.x && position.x<playerPosition.x+player.getWidth() && position.y+height>playerPosition.y && position.y<playerPosition.y+player.getHeight()) {
//			//不能一接近玩家就开打，否则太难，用一个随机函数，达到位置条件后，还需要一定几率才能开始attack
//			    if(Math.random()>0.995) {//这个就相当于一个难度系数，100次里面只有一次攻击。
//			        situation="attack";	
//			    }
//
//            } else {
//        	    situation="";
//            }
		
		
//		    Attack_Hit logic=new Attack_Hit(player,this);
//		    if(logic.if_Player_hit_Monster(this)) {
//				    situation="behit";		
//		    }
	
		}else{
			directions.clear();
		    return;
		    }
		
		//3.2用move执行放入directions中的方向
		if(!directions.isEmpty()){
		    move(directions.poll());
		    //move函数就是Entity中的那个，可以通过四大改变speed
		}else{
			
		}

	}//怪物自己移动和攻击的函数,ai之后依旧需要使用refresh()
	
	public void hitplayer() {
	    if(position.x+width > playerPosition.x && position.x<playerPosition.x+player.getWidth() && position.y+height>playerPosition.y && position.y<playerPosition.y+player.getHeight()) {
		//不能一接近玩家就开打，否则太难，用一个随机函数，达到位置条件后，还需要一定几率才能开始attack
		    if(Math.random()>0.997) {
		        this.checkindex.entityform.ifattack_monster();	
		    }

        }
	}
	



}
