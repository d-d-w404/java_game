package Interface;

import java.awt.Point;
import java.awt.geom.Line2D;

import Block.Block;
import Block.BlockType;
import Entity.Entity;


public class Sub_skill_move {
	Entity e;
	
	public Sub_skill_move(Entity e) {
		this.e=e;
	}
	public void sub_skill_move(int distance_x,int distance_y) {//x右为+，y下为+	
		if(e.getfacingRight()) {
			
		}else {
			distance_x=-distance_x;
		}
		across_all_point(distance_x,distance_y);
	}
	
	private void across_all_point(int distance_x,int distance_y) {//这个函数是让所有我选定的点进行逐渐增长，判断是否撞block
		int distance_breakpoint=5;//我只能说尽可能的小，性能允许范围内
		//通过distance_x和distance_y的正负或0，确定每个断点需要向哪个方向加或减
		
		int provent_over_block=1;//预防过度阻挡的缓冲值，防止我站在地上被地皮阻挡了闪现
		
		double sub_distance_x;
		double sub_distance_y;
		
		if(distance_x>0) {
			sub_distance_x=distance_breakpoint/(Math.sqrt(1+Math.pow(distance_y,2)/Math.pow(distance_x,2)));
		}else if(distance_x<0) {
			sub_distance_x=-distance_breakpoint/(Math.sqrt(1+Math.pow(distance_y,2)/Math.pow(distance_x,2)));
		}else {
			sub_distance_x=0;
		}		
		if(distance_y>0) {
			sub_distance_y=distance_breakpoint/(Math.sqrt(1+Math.pow(distance_x,2)/Math.pow(distance_y,2)));
		}else if(distance_y<0){
			sub_distance_y=-distance_breakpoint/(Math.sqrt(1+Math.pow(distance_x,2)/Math.pow(distance_y,2)));
		}else {
			sub_distance_y=0;
		}
		
		Point p;
		Point p_start;
		p_start=new Point(0,0);
		p=new Point(0,0);
		
		boolean p1=false;
		boolean p2=false;
		boolean p3=false;
		boolean p4=false;
		
		for(int i=0;i<4;i++) {
			if(i==0) {//左上
				p_start.x=e.getPosition().x+provent_over_block;
				p_start.y=e.getPosition().y+provent_over_block;
				p.x=e.getPosition().x+provent_over_block;
				p.y=e.getPosition().y+provent_over_block;
				if(across(distance_x,distance_y,p,p_start,sub_distance_x,sub_distance_y)) {
					p1=true;
				}
				//System.out.println("this is 1"+p1);
			}else if(i==1 && !p1) {//左下
				p_start.x=e.getPosition().x+provent_over_block;//需要向内收缩一点，否则太容易碰撞了
				p_start.y=e.getPosition().y+e.getHeight()-provent_over_block;
				p.x=e.getPosition().x+provent_over_block;
				p.y=e.getPosition().y+e.getHeight()-provent_over_block;
				if(across(distance_x,distance_y,p,p_start,sub_distance_x,sub_distance_y)) {
					p2=true;
				}
				//System.out.println("this is 2"+p2);
			}else if(i==2 && !p1 && !p2) {//右下
				p_start.x=e.getPosition().x+e.getWidth()-provent_over_block;
				p_start.y=e.getPosition().y+e.getHeight()-provent_over_block;
				p.x=e.getPosition().x+e.getWidth()-provent_over_block;
				p.y=e.getPosition().y+e.getHeight()-provent_over_block;
				if(across(distance_x,distance_y,p,p_start,sub_distance_x,sub_distance_y)) {
					p3=true;
				}
				//System.out.println("this is 3"+p3);
			}else if(i==3 && !p1 && !p2 && !p3) {//右上
				p_start.x=e.getPosition().x+e.getWidth()-provent_over_block;
				p_start.y=e.getPosition().y+provent_over_block;
				p.x=e.getPosition().x+e.getWidth()-provent_over_block;
				p.y=e.getPosition().y+provent_over_block;
				if(across(distance_x,distance_y,p,p_start,sub_distance_x,sub_distance_y)) {
					p4=true;
				}
				//System.out.println("this is 4"+p4);
			}
		}
		if(!p1 && !p2 && !p3 && !p4) {//当上面的四个判断都最后返回false时，即四个角的across判断都没有撞到block，直接位移到目的地
			Point real_p=new Point(e.getPosition().x+distance_x,e.getPosition().y+distance_y);
			e.setPosition(real_p);
			//System.out.println("null");
		}
	}
	
	private boolean across(int distance_x,int distance_y,Point p,Point p_start,double sub_distance_x,double sub_distance_y) {//这个函数让闪现和block联系起来，保证不会穿模到实心block中去。
		//定义好位移的初始点和每隔100m的断点，这些点用的都是entity的中心，可能会有穿模bug，以后改进可以用entity边上的几个点
		//我一开始使用的点是entity的中间的点，容易发生穿模，改为使用entity四个角上的点，感觉会基本规避闪现穿模问题，在across_all_point中实现4个角。
				
		while(Math.pow(p.x-p_start.x,2)+Math.pow(p.y-p_start.y,2)<=Math.pow(distance_x,2)+Math.pow(distance_y,2)) {
			p.setLocation(p.x+sub_distance_x, p.y+sub_distance_y);
			for(Block b : e.scene.getvisibleBlocks()) {
				if(b.ifinblock(p.x, p.y)) {
					if(b.gettype()!=BlockType.PLATFORM) {
						intersect_and_set_entity_pos(p, p_start, b.getposition(), b);						
						//当撞到实心block的时候，不能直接把它的位置传到这个实心block上，传到的位置应该离出发点再近一点距离
						//否则就会进入这个block中，导致穿模，于是使用了intersect_and_set_entity_pos函数
						return true;
					}
				}
			}
		}		
		return false;
	}
	
	//这个intersect_and_set_entity_pos()函数的功能是找到entity与实心block相撞的面，然后将entity的位置设置到相撞的面上
	private void intersect_and_set_entity_pos(Point p,Point p_start,Point p_block,Block b) {//p_block是石块的左上角的点。
		double block_center_x=p_block.x+b.getwidth()/2;
		double block_center_y=p_block.y+b.getheight()/2;
		double x_distance_in_block_entity=e.getWidth()/2+b.getwidth()/2;
		double y_distance_in_block_entity=e.getHeight()/2+b.getheight()/2;
		Point pos=new Point(0,0);	
		if(Line2D.linesIntersect(p.x,p.y, p_start.x,p_start.y,p_block.x,p_block.y, p_block.x, p_block.y+b.getheight())) {			
			pos.x=(int) (block_center_x-x_distance_in_block_entity);
			pos.y=(int) block_center_y;
		}//左
		else if(Line2D.linesIntersect(p.x,p.y, p_start.x,p_start.y,p_block.x,p_block.y+b.getheight(), p_block.x+b.getwidth(), p_block.y+b.getheight())) {
			pos.x=(int) block_center_x;
			pos.y=(int) (block_center_y+y_distance_in_block_entity);
		}//下
		else if(Line2D.linesIntersect(p.x,p.y, p_start.x,p_start.y,p_block.x+b.getwidth(),p_block.y+b.getheight(), p_block.x+b.getwidth(), p_block.y)) {
			pos.x=(int) (block_center_x+x_distance_in_block_entity);
			pos.y=(int) block_center_y;
		}//右
		else if(Line2D.linesIntersect(p.x,p.y, p_start.x,p_start.y,p_block.x+b.getwidth(),p_block.y, p_block.x, p_block.y)) {
			pos.x=(int) block_center_x;
			pos.y=(int) (block_center_y-y_distance_in_block_entity);
		}//上
		else {//这种情况是人物由于穿模问题进入了实心的block中，此时如果我再位移，且位移的距离不足以让我离开这个block，就会导致进入
			//这个函数，但是没有相交判断，上面四个if都不会进入，导致直接执行最后的语句，由于pos初始化是（0，0），加上最后的改到中心位置
			//基本就导致人物直接位移到(0,0)
			pos.x=(int) block_center_x;
			pos.y=(int) block_center_y;
		}
		//上面if中计算出来的pos都是entity的中心的位置，e.setPosition()中需要的参数是enety左上角的，所以我就提前修改一下，就不用每个if中都修改
		pos.x=pos.x-e.getWidth()/2;
		pos.y=pos.y-e.getHeight()/2;
		e.setPosition(pos);
	}//判断顺序，左下右上


}
//这个类就是为了解决技能中位移的效果，这么多函数就是为了在实现位移的情况下保证不穿模