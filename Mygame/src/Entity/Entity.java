package Entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import Block.Block;
import Control.GameKeyController.direction;
import Player.Player;
import Scene.Scene;
import prefs.PRFS;

public class Entity {
	private String name;
	protected int width;
	protected int height;
	protected Point position;
	protected Point sceneposition;
	protected double speed=0;//这个speed是y方向上的速度
	protected double gravity=0.3;
	protected double groundSpeed;//这个是x方向的速度
	protected boolean canJump;
	protected boolean dropDown;
	protected boolean facingRight;
	protected boolean falling;
	protected double entitySpeed = 0.25;
	private double maxGroundSpeed;
	//上面的数据都是一些关于移动的参数
	
	public CheckIndex checkindex;
	protected int walkIndex=0;
	protected int stayIndex=0;
	protected int jumpIndex=0;
	protected int attackIndex=0;
	protected int startjumpIndex=0;
	protected int behitIndex=0;
	protected int skill1Index=0;
	protected int blinkIndex=0;
	protected int skill2Index=0;

	protected Image[] walkImages;
	protected Image[] stayImages;
	protected Image[] jumpImages;
	protected Image[] attackImages;
	protected Image[] startjumpImages;
	protected Image[] behitImages;
	protected Image[] skill1Images;
	protected Image[] blinkImages;
	protected Image[] skill2Images;

	protected int life;
	protected int alllife;

	
	public Scene scene;
	
	public Entity(String name,int x,int y,int life,int width,int height,Image[] walkImages,Image[] stayImages,Image[] jumpImages,Image[] attackImages,Image[] startjumpImages,Image[] behitImages,Image[] skill1Images,Image[] skill2Images,Scene scene) {

		this.checkindex=new CheckIndex(this);
		

		this.name=name;
		position=new Point(x,y);
		this.width=width;
		this.height=height;
		this.walkImages=walkImages;
		this.stayImages=stayImages;
		this.jumpImages=jumpImages;
		this.attackImages=attackImages;
		this.startjumpImages=startjumpImages;
		this.behitImages=behitImages;
		this.skill1Images=skill1Images;
		this.skill2Images=skill2Images;

		
		this.scene=scene;
		
		this.life=life;
		this.alllife=life;

	}
	public String getname() {
		return name;
	}
	
	public Point getPosition() {
		return position;
	}
	//得到Entity的位置
	public double getDownSpeed() {
		return speed;
	}
	//得到y方向的速度
	public double getGroundSpeed() {
		return groundSpeed;
	}
	public void addGroundSpeed(int x) {
		groundSpeed=groundSpeed+x;
	}
	//得到x方向的速度
	public void addSpeed(int x) {
		speed=speed+x;
	}
	
	public void setGroundSpeed(int x) {
		groundSpeed=x;
	}
	//得到x方向的速度
	public void setSpeed(int x) {
		speed=x;
	}
	
	//设置y方向的速度
	public void setPosition(Point position) {
		this.position=position;
	}
	//设定这个Entity的位置
	public int getWidth() {
		return width;
	}
	//得到Entity对象的宽度
	public int getHeight() {
		return height;
	}
	//得到Entity对象的高度
	public boolean getFalling() {
		return falling;
	}
	public boolean getfacingRight() {
		return facingRight;
	}
	public int getlife() {
		return life;
	}
	public void setlife(int change) {
		life+=change;
		System.out.println(life);
	}
	
	
	
	public void moveDown(int y) {
		position.move(position.x,y);
		//Point中move的作用就是让position的坐标移动到参数指定的
		//位置
	}
	//移动到y方向的指定位置上去
	
	public void moveLeft(int x) {
		position.move(x,position.y);
	}
	//移动到x方向的指定位置上去
	
	
	
	public void move(direction dir) {
		switch(dir) {
		case UP:
			jump(0);
			break;
		case DOWN:
			crouch();
			break;
		case LEFT:
			moveLeft();
			break;
		case RIGHT:
			moveRight();
			break;
		default:
		}
		//在GameKeyController中调用这个函数，让行动数组
		//中的每个元素对应一个行动
	}
	
	private void jump(int i) {
		if(canJump && checkindex.entityform.getform()!=FormType.BEHIT) {
			if(i==0) {
				speed=-10;
				canJump=false;
			}else {
				speed=-i;
				canJump=false;
			}
		}
	}
	//canJump为true代表没有跳，当我正在跳的时候
	//canJump就变为了false,后面应该有在落地后把
	//canJump恢复为true的地方
	
	private void crouch() {
		dropDown=true;
	}
	//dropDown变为true，表示能够向下跳，后面应该有
	//一个地方如果dropDown为true,直接让Entity向下移动
	//的地方，似乎用的是position.translate(x,y);x和y是
	//向x和y轴的平移量,和move是有区别的。
	
	private void moveLeft() {
		if(groundSpeed>((this instanceof Player)?-4:-1) && (checkindex.entityform.getform()!=FormType.ATTACK || falling)) {//在attack时不能移动，但是在空中attack时可以移动。
			if(checkindex.entityform.getform()!=FormType.BEHIT) {//behit时也不能移动
				this.groundSpeed-=entitySpeed;
			}			
		}
	}
	//当左移动的速度没有达到4的时候，我通过一个0.25的值，每一次
	//refresh把速度刷到4或者比4大一点点
	//后面有一个normalizeGroundSpeed()函数能够把比4大一点点的
	//的速度变成4

	private void moveRight() {
		if(groundSpeed < ((this instanceof Player)?4:1)&& (checkindex.entityform.getform()!=FormType.ATTACK || falling)){
			if(checkindex.entityform.getform()!=FormType.BEHIT) {//behit时也不能移动
				this.groundSpeed+=entitySpeed;
			}
		}
	}
	//上面的四个函数由GameKeyController中的key值驱动，他们四个
	//的作用是改变speed和groundSpeed，以及canJump和dropDown
	//再由后面的refresh()使用这些数据改变Entity的position
	
	public void refresh() {
		
		speed+=gravity;
		normalizeGroundSpeed();
		falling=true;
		//初始时让falling为true，因为我一开始正悬空
		boolean stationairy=false;
		//stationariy 固定的
		
		for(Block b:scene.getBlocks()) {
			String status=b.checkBounds(this);
			
			if(status.equals("ontop")) {

					speed=0;
					falling=false;
					//此时站在方块上，falling自然为false
					canJump=true;

			if(b.isSolid() && dropDown) {
				//当我按了下坠的键，dropDown就变成了true
				//如果b.isSolid()为true，即我站在的方块
				//不可穿透，则把dropDown再次变为false

				dropDown=false;
			}
			}
			//上面是状态为ontop时的操作
			
			if(status.equals("beneath")) {
				//此时应该是因为什么原因从下面撞到了
				//方块
				falling=true;
				if(speed<0)
				//出现beneath,说明此时人物已经被吸到block的下端
				//没有缝隙，没有嵌入，如果speed>=0,说明要下坠了，
				//如果speed<0，说明如果没有block，将会继续向上
				//所以speed*=-1;做一个撞天花板后速度返向的效果
					//speed*=-1;
					speed=0;//感觉这里直接速度归0效果要好一点
			}
			//上面是beneath的操作
			
			if(status.equals("left")||status.equals("right")) {
					stationairy=true;
			}
			
		}
		//上面的四种状态在block类中出现的时候就，就确实已经是
		//这四种状态了，ontop就是人站在block上，没有一丝空隙
		//也没有一丝嵌入
		//感觉就像我的人物达到了了方块的某一个区域的时候就被
		//吸到了block的边上一样
		
		
		
		//通过Block中的checkBounds检查blokc和人的状态
		//再通过状态对speed,groundSpeed,falling,canJump,dropDown
		//stationairy做操作
		
		
		//for循环结束后，下面再通过上面的一些值speed,
		//groundSpeed,falling,canJump,dropDown,stationairy
		//修改position
		
		if(dropDown) {
			position.translate(0,1);
		}
		dropDown=false;
		if(!falling) {
			//这个if判断多余，其实我在上面falling为false的上一句
			//就把speed变为了0
			speed=0;
		}
		
		else {
			position.translate(0, (int) speed);
		}
		if(!stationairy) {
			position.translate((int) groundSpeed, 0);
		}
		if(groundSpeed>-0.2&& groundSpeed<0.2) {
			groundSpeed=0;
		}else {
			slowDownLeftRightMovement();
		}
		
		
	}		
	
	public void draw(Graphics g) {
		if(PRFS.frmNum == 0) {
			checkindex.entityform.checkform();//每30帧更新一次form
			checkindex.checkindex();//每30帧更新一次在某个form下的图片index
			//我把entityform放在checkindex中
		}
		if(checkindex.entityform.getform()==FormType.WALK)
		    subdraw(g,walkImages,walkIndex);
		if(checkindex.entityform.getform()==FormType.STAY)
		    subdraw(g,stayImages,stayIndex);
		if(checkindex.entityform.getform()==FormType.JUMP)
		    subdraw(g,jumpImages,jumpIndex);
		if(checkindex.entityform.getform()==FormType.ATTACK)
		    subdraw(g,attackImages,attackIndex);
		if(checkindex.entityform.getform()==FormType.STARTJUMP)
		    subdraw(g,startjumpImages,startjumpIndex);
		if(checkindex.entityform.getform()==FormType.BEHIT)
		    subdraw(g,behitImages,behitIndex);
		if(checkindex.entityform.getform()==FormType.SKILL1)
		    subdraw(g,skill1Images,skill1Index);
		if(checkindex.entityform.getform()==FormType.SKILL2)
		    subdraw(g,skill2Images,skill2Index);
	}
	
	public void subdraw(Graphics g,Image[] images,int imageIndex) {
		g.setColor(Color.BLACK);
		g.drawRect(sceneposition.x, sceneposition.y-15,width,10);//血条框
		g.setColor(Color.GREEN);
		g.fill3DRect(sceneposition.x, sceneposition.y-15, width*life/alllife, 10,true);
		
		g.setColor(Color.BLACK);
		g.drawRect(sceneposition.x, sceneposition.y-5,width,5);
		g.setColor(Color.RED);//血压槽，满了会进入一段霸体状态
		g.fill3DRect(sceneposition.x, sceneposition.y-5, width*checkindex.entityform.getreadyforinvincible_count()/20, 5,true);
		
		if(facingRight){
		    g.drawImage(images[imageIndex], sceneposition.x,sceneposition.y,width,height,null);
		}else{
			Image img = images[imageIndex];
			g.drawImage(img, sceneposition.x, sceneposition.y, sceneposition.x+width, sceneposition.y+height ,img.getWidth(null), 0,0,img.getHeight(null),null);	
		}
	}
	
	
	private void normalizeGroundSpeed() {
		if(groundSpeed>4) groundSpeed=4;
		if(groundSpeed<-4) groundSpeed=-4;
	}
	//moveLeft()和moveRight()都是在不断加一个0.25
	//有可能会超过4或-4，就需要normalizeGroundSpeed()
	//将速度统一到4
	
	public void slowDownLeftRightMovement() {
			if(groundSpeed >0){
				facingRight = true;
				groundSpeed -= 0.1;
			}
			else{
				facingRight = false;
				groundSpeed += 0.1;
			}
	}	
	//这个函数的作用有点像摩擦力，让我的人物能够在我停止按键时
	//逐渐停下来
	

}
