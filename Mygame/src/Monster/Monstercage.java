package Monster;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import Player.Player;
import Scene.Scene;


public class Monstercage {
	private int monster_all=20;//地图中总共有多少个monster
	private int monster_count;//地图中某时刻最多有多少monster
	private int monster_show=5;
	private CopyOnWriteArrayList<Monster> monsters = new CopyOnWriteArrayList<Monster>();
	//CopyOnWriteArrayList替代了ArraryList，解决了java.util.ConcurrentModificationException报错问题
	private char[][] walls;//用于随机生成怪物时确定生成在0上。
	private int initmonsternumber=1;//当小于monster_count时就没用。
	Player player;
	Scene scene;
	
	public Monstercage(Player player,Scene scene,char[][] walls) {
		this.player=player;
		this.scene=scene;
		this.walls=walls;
	}
	
	private void monster_init() {

	}//暂时没用，以后应该会有作用
	
	private void addMonster_random() {
		int x_random=-1;
		int y_random=-1;
		while(x_random==-1 && y_random==-1) {
			for(int i=0;i<walls.length;i++) {//walls.length是有多少列。
				for(int j=0;j<walls[i].length;j++) {
					if(walls[i][j]=='0' && Math.random()>0.99) {
						x_random=j*100;//这里100是石块的宽高。
						y_random=i*100;
						break;
					}
				}
			}//j是x方向，i是y方向。	
		}
		monsters.add(new Monster("test",x_random,y_random,50,player,scene));

	}
		
	private void monsterdead(Monster monster) {
		if(monster.getlife()<=0) {
			monster_all--;
			
			monsters.remove(monster);
		    System.out.println(monster.getname()+"挂了");
		}
	}
	
	private void monsterdead_and_add() {	
		monster_count=monsters.size();		    
		if(monster_count<monster_show && monster_all>=monster_show) {
		    addMonster_random();
		}	    
	}
		
	public void refresh() {
		for(Monster monster:monsters) {
			monster.refresh();
			monsterdead(monster);			
		}
		monsterdead_and_add();
	}
	
	public void draw(Graphics g) {
		for(Monster monster:monsters) {
			monster.draw(g);
		}
	}
	
	public CopyOnWriteArrayList<Monster> getallmonster(){
		return monsters;
	}

}
