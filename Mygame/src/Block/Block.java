package Block;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;



import Entity.Entity;
import Imagetool.ImageHandler;
import prefs.PRFS;

public class Block {
	public static int blockDrawn;
	//已经画的block
	private int height;
	private int width;
	public final int firstX;
	//final让变量只能被赋值一次，后面不能变了
	public final int firstY;
	private int x;
	private int y;
	private boolean solid;
	//true即坚硬，不可穿；false即不坚硬，可以穿透
	private BlockType blockType;
	private Rectangle boundingBox;
	//Rectangle居然是一个自带的jar包的类,在图形中有很多用处
	//我可以判断一个点或一个矩形区域是否在某个矩形区域中
	public static Image platform = ImageHandler.loadImage("platform.png");
	public static Image block = ImageHandler.loadImage("block.png");
	public static Image caopi=ImageHandler.loadImage("地形/草皮.png");
	public static Image caoshi=ImageHandler.loadImage("地形/草石.png");
	public static Image shitou=ImageHandler.loadImage("地形/石头.png");
	public static Image youkuang=ImageHandler.loadImage("地形/右框.png");
	public static Image zuokuang=ImageHandler.loadImage("地形/左框.png");
	//用来导入图像的
	
	
	public Block(BlockType b,int x,int y,int width,int height,boolean solid) {
		firstX=x;
		firstY=y;
		this.x=x;
		this.y=y;
		this.blockType=b;
		this.width=width;
		this.height=height;
		this.boundingBox=new Rectangle(x,y,width,height);
		//在block区域生成一个对应的Rectangle对象。
		this.solid=solid;
		
	}
	
	public void draw(Graphics g,int offsetX,int offsetY) {
		if(isVisible(offsetX,offsetY)==true) {
			drawImage(g,offsetX,offsetY);
			blockDrawn++;//画了一个block后，blockDrawn+1
		}
	}
	//在draw中调用drawImage(其中包含了g.drawImage...),然后draw
	//又可以作为一个函数在World中的draw()中被引用
	//world对象又可以传入canvas中，在canvas中真正再次调用world
	//中的draw(),毕竟最后都只有在canvas中画才能真正起作用
	
	//感觉原来的代码调用了interface,不过implent这个interface的
	//类只有World,我感觉没必要，就弃用了
	
	public void drawImage(Graphics g,int offsetX,int offsetY) {
		switch(blockType) {
		case PLATFORM:
			g.drawImage(platform,x-offsetX,y-offsetY,width,height,null);
			//platform是一个Image对象
			break;
		case BLOCK:
			g.drawImage(block,x-offsetX,y-offsetY,width,height,null);
			break;
		case CAOPI:
			g.drawImage(caopi,x-offsetX,y-offsetY,width,height,null);
			break;
		case CAOSHI:
			g.drawImage(caoshi,x-offsetX,y-offsetY,width,height,null);
			break;
		case SHITOU:
			g.drawImage(shitou,x-offsetX,y-offsetY,width,height,null);
			break;
		case YOUKUANG:
			g.drawImage(youkuang,x-offsetX,y-offsetY,width,height,null);
			break;
		case ZUOKUANG:
			g.drawImage(zuokuang,x-offsetX,y-offsetY,width,height,null);
			break;

		}
	}
	
	public boolean isVisible(int offsetX,int offsetY) {
		do {
			if(this.x-offsetX>PRFS.GameWidth)
				break;
			if(this.x-offsetX+width<0)
				break;
			if(this.y-offsetY>PRFS.GameHeight)
				break;
			if(this.y-offsetY+height<0)
				break;
			return true;
		}
		while(false);
		return false;
	}//这个判断方法和上面的一样
	
	public String checkBounds(Entity entity) { 
		int top,bottom,left,right,boxTop,boxBottom,boxLeft,boxRight;
		top=entity.getPosition().y;
		bottom=entity.getPosition().y+entity.getHeight();
		left=entity.getPosition().x;
		right=entity.getPosition().x+entity.getWidth();
		//top,bottom,left,right是人物的位置参数
		
		boxTop=boundingBox.y;
		boxBottom=boundingBox.y+boundingBox.height;
		boxLeft=boundingBox.x;
		boxRight=boundingBox.x+boundingBox.width;
		//这四个是block的位置的参数
		
		double speed =entity.getDownSpeed();
		//人物的y方向的速度
		double groundSpeed=entity.getGroundSpeed();
		//人物x方向的速度
		
		do {
			if(right<=boxLeft) {
				break;
			}
			if(left>=boxRight) {
				break;
			}
			//上面的这两个判断确定了人物必定在block所在的
			//整个竖直的矩阵中
			if(bottom>boxTop) {
				break;
			}
			//人物的低端不能低于block的顶端，这种情况不能出现
			if(bottom+speed<boxTop) {
				break;
			}
			//人物的底端加上速度（感觉speed就是你在下一次刷新中能够
			//走的距离）后依然没有到达block顶端，说明人物下一次的移动
			//依旧不能到达block
			if(bottom+speed>=boxTop) {
				entity.moveDown(boundingBox.y-entity.getHeight());
				return "ontop";
			}
			//这里人物底端加上speed能够到达block的顶端，甚至嵌入block,
			//那么下一次刷新必然与block接触，那么我就直接让人物的的位置
			//确定，恰好站在了block上面，否则会发生人物嵌入block
		}while(false);
		
		//下面的三个do就是判断人物与block从其他三个方向接触。
		
		if(!solid) return "";
		//假如现在有一个平台，当我站在它上面的时候，就传一个ontop
		//由于return，后面也不再执行。
		//但当我没有站在它上面的时候，上面第一个do就不会传回任何值
		//并且还要执行下面的东西，
		//于是就执行if(!solid) return "";这句话
		//由于return,后面的三个do也不再执行，传回的值和执行完所有
		//do,但是和block没有任何接触（即ontop,beneath,left,right
		//都没有）时的值一样---""
		//在Entity中不做任何处理，速度，是否下落等变量不会有任何改变
		//就像空气一样。
		//所以我就可以从平台的左右以及下轻松穿过平台
		do{
			if(right<=boxLeft){
				//System.out.println("Break Left");
				break;
			}
			if(left>=boxRight){
				//System.out.println("Break Right");
				break;
			}
			if(top < boxBottom){
				//System.out.println("Break Too Low");
				break;
			}
			if(top+speed > boxBottom){
				//System.out.println("Break Too High");
				break;
			}
			if(top+speed <= boxBottom ){
				//System.out.println("not too high");
				entity.moveDown(boxBottom);
				return "beneath";
				}

		}while(false);
		do{
			if(bottom<=boxTop){
				//System.out.println("Break Left");
				break;
			}
			if(top>=boxBottom){
				//System.out.println("Break Right");
				break;
			}
			if(right > boxLeft){
				//System.out.println("Break Too Low");
				break;
			}
			if(right+groundSpeed < boxLeft){
				//System.out.println("Break Too High");
				break;
			}
			if(right+groundSpeed >= boxLeft ){
				//System.out.println("not too high");
				entity.moveLeft(boxLeft - entity.getWidth());
				return "left";
				}

		}while(false);
		do{
			if(bottom<=boxTop){
				//System.out.println("Break Left");
				break;
			}
			if(top>=boxBottom){
				//System.out.println("Break Right");
				break;
			}
			if(left < boxRight){
				//System.out.println("Break Too Low");
				break;
			}
			if(left+groundSpeed > boxRight){
				//System.out.println("Break Too High");
				break;
			}
			if(left+groundSpeed <= boxRight ){
				//System.out.println("not too high");
				entity.moveLeft(boxRight);
				return "right";
				}

		}while(false);
			return "";
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public boolean ifinblock(int judge_x,int judge_y) {
		if(x<=judge_x && x+width>=judge_x) {
			if(y<=judge_y && y+height>=judge_y)
				return true;
		}
		return false;
	}//这个函数用于判断某个点是否在这个block内,包括边缘
	
	
	public BlockType gettype() {
		return blockType;
	}
	
	public int getx() {
		return x;
	}
	public int gety() {
		return y;
	}//返回block左上角的坐标x和y
	
	public Point getposition() {
		Point position=new Point(x,y);
		return position;
	}
	public int getwidth() {
		return width;
	}
	public int getheight() {
		return height;
	}
}
