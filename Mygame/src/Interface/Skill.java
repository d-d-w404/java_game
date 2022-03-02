package Interface;

import java.awt.Point;
import java.util.ArrayList;

import Block.Block;
import Block.BlockType;
import Entity.Entity;
import Monster.Monstercage;
import java.awt.geom.*;

public class Skill {
	//Player player;
	Entity e;
	Sub_skill_move Sub_skill_move;
	Sub_skill_speed Sub_skill_speed;
	//Point position;
	//private ArrayList<Block> blocks =new ArrayList<Block>();
	//在闪现的时候需要判断撞墙问题，需要block参与
	//Skill类中有个Entity,直接调用Entity中的public的scene,利用其中的block。
	
	private int  groundSpeed;
	private int downSpeed;
	Monstercage monstercage;
	
	private int distance;
	private int time;
	
	public Skill(Entity entity) {
		this.e=entity;
		//this.monstercage=monstercage;
		//this.position=new Point(0,0);
		this.Sub_skill_move=new Sub_skill_move(this.e);
		this.Sub_skill_speed=new Sub_skill_speed(this.e);
	}
	
	public void skill1() {		
		this.time=1;
		switch(e.checkindex.entityform.getskill1_count()) {
		case 1:
		    Sub_skill_move.sub_skill_move(200,0);//这个time其实就是执行几次sub_skill_move,这种瞬移一般就是执行一次
		    break;
		}		
	}

	
	public void skill2() {
		this.time=4;
		e.setSpeed(0);
		switch(e.checkindex.entityform.getskill2_count()) {
		case 1:
			Sub_skill_speed.sub_skill_speed(4,10);//跳跃是10
			//sub_skill_move(50,0);
			break;
		case 2:
			Sub_skill_speed.sub_skill_speed(4,0);
			break;
		case 3:
			Sub_skill_move.sub_skill_move(50,50);
			break;
		case 4:
			
			break;
		}//通过switch语句，虽然time=5，需要执行5次skill2,但是随着switch中的值的变化，每次都是执行同一个函数中的不同语句		
		//不建议使用(100,100)，容易发生边角问题，直接从第二象限进入第四象限，跳过第一象限。
	}
	//当skill的time很多的时候，如果全部用sub_skill_move(),会导致人物动作僵硬，画面闪变。此时的sub_skill_speed()就能让动作更流畅
	

	
	public int gettime() {
		return time;
	}


}
