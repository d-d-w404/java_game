package Scene;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import Block.Block;
import Control.GameKeyController;
import Imagetool.ImageHandler;
import Interface.Hit_BeHit;
import Monster.Monstercage;
import Player.Player;
import Scene_display.SceneCanvas;
import Scene_display.SceneFrame;
import prefs.PRFS;

public class Scene {
	private ArrayList<Block> blocks =new ArrayList<Block>();
	public static ArrayList<Block> visibleBlocks = new ArrayList<Block>();
	//private char[][] walls;
	private static int blockSize=100;
	public Point currentCenter;
	public Image background =ImageHandler.loadImage("Summit.jpg");
	
	
	private SceneFrame frame;
	private SceneCanvas canvas;	
	private Player player;
	private GameKeyController gameKeyController;
	private SceneThread theGame;

	private SceneAddBlocks sceneaddblocks;

	//private Monster monster;
	private Monstercage monstercage;
	
	private Hit_BeHit hit_behit;
	
	public Scene(SceneGetMapData scenegetmapdata) {
//		walls=constructer.getData();
//		//把Game中生成的worldmap中的数据传入walls,并在fillBlocks()
//		//中画出来。
//		fillBlocks();
		sceneaddblocks=new SceneAddBlocks(scenegetmapdata);
		blocks=sceneaddblocks.getBlocks();
		
		
		player=new Player("Pico",300,300,100,this);	//姓名，x,y坐标，life
		//monster=new Monster("Monster",400,300,50,player,this);//所有的怪物的行动都是和player有关的
		monstercage=new Monstercage(player,this,sceneaddblocks.getwalls());//真正的walls不在scene中
		hit_behit=new Hit_BeHit(player,monstercage);
		
		getVisibleBlocks(300, 300);
		
		frame=new SceneFrame();
		canvas=new SceneCanvas();	
		gameKeyController =new GameKeyController(player,this);
		initFrame();		
		theGame=new SceneThread(this);
		theGame.start();
	}
	
	private void initFrame() {
		frame.add(canvas);
		frame.setSize(PRFS.GameWidth,PRFS.GameHeight);
		frame.setResizable(false);
		frame.setVisible(true);
		canvas.getworld(this);
		frame.addKeyListener(gameKeyController);
		//当world对象生成后，我把world对象导入canvas中，让
		//canvas使用world中的draw函数
	}
	
	public void refresh_all() {
		player.refresh();
		if(PRFS.frmNum == 0) {
			hit_behit.refresh();
		}
		//monster.refresh();
		monstercage.refresh();

		canvas.repaint();
		
		gameKeyController.calculateKeyPresses();
	}
	
	public void resetKeyController() {
		gameKeyController = new GameKeyController(player,this);
	}
	
	
	public void draw(Graphics g) {
		g.drawImage(background,0,0,PRFS.GameWidth,PRFS.GameHeight,null);
		
		refreshOfgetVisibleBlocks();
		for(Block b:visibleBlocks) {
			b.draw(g, currentCenter.x-300, currentCenter.y-300);		
		}
		
		player.draw(g);
		
		//monster.draw(g);
		monstercage.draw(g);


	}
	
	private void getVisibleBlocks(int offsetX,int offsetY) {//偏移量offsetX offsetY
		visibleBlocks.clear();//应该是起一个清空list的作用
		for(Block b:blocks) {//从blocks里面选
			if(b.isVisible(offsetX, offsetY)) {
				visibleBlocks.add(b);
			}
		}//offsetX和offsetY得从主角那里来
		
	}//我有了所有的block的一个Block类的数列，我从中找出目前可以显示在
	//窗口中的block，并把他们在上面的draw中画出来
	//这里借用了Block类中的判断block是否在窗口内的函数
	
	
	
	private void refreshOfgetVisibleBlocks() {
		//当我的offsetX和offsetY有变化时，函数就要工作了
		try {
			currentCenter=player.getPosition();
			getVisibleBlocks(currentCenter.x-300, currentCenter.y-300);
			checkFPS();
			//System.out.println("this is World.refresh");
		}catch(Exception e) {
			System.err.println(e);
		}
	}
	//我感觉这个函数不应该叫做refresh,它只是用于draw函数中，
	//实现找到变化的visibleblocks 
	
	public ArrayList<Block> getBlocks(){
		return blocks;
	}
	
	public ArrayList<Block> getvisibleBlocks(){
		return visibleBlocks;
	}
	
	public Player getplayer() {
		return player;
	}
	
	public boolean checkFPS() {//fps是帧的意思
		if(PRFS.fps == PRFS.frmNum){
			PRFS.frmNum = 0;
			return true;
		}else{
			PRFS.frmNum++;
			return false;
		}
	}
	
	


}
