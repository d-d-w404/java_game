package Interface;

import java.awt.Point;
import java.awt.geom.Line2D;

import Entity.FormType;
import Monster.Monster;
import Player.Player;
import java.awt.geom.Line2D;

public class Range_confirm {
	//Player player;
	//Monster monster;
	
	public Range_confirm() {
		//this.player=player;
		//this.monster=monster;
	}
	
	
	public boolean range_confirm(Player player,Monster monster,int range_x,int range_y,int p_move_x,int p_move_y) {
		//range_x和range_y分别是技能范围的长度和宽度,skill_p是技能的中心
		//提供一个技能范围的判断，一般的技能或是平a，范围都是一个矩形，然后确定不同的中心。
        
        
        //描述技能范围的四个点
        Point skill_p_1=new Point(player.getPosition().x+p_move_x,player.getPosition().y+p_move_y);//左上
        Point skill_p_2=new Point(player.getPosition().x+p_move_x,player.getPosition().y+p_move_y+range_y);//左下
        Point skill_p_3=new Point(player.getPosition().x+p_move_x+range_x,player.getPosition().y+p_move_y+range_y);//右下
        Point skill_p_4=new Point(player.getPosition().x+p_move_x+range_x,player.getPosition().y+p_move_y);//右上
        
        //描述受技能影响的对象的四个点
        Point obj_p_1=new Point(monster.getPosition().x,monster.getPosition().y);//左上
        Point obj_p_2=new Point(monster.getPosition().x,monster.getPosition().y+monster.getHeight());//左下
        Point obj_p_3=new Point(monster.getPosition().x+monster.getWidth(),monster.getPosition().y+monster.getHeight());//右下
        Point obj_p_4=new Point(monster.getPosition().x+monster.getWidth(),monster.getPosition().y);//右上
        
        if(line_to_line(skill_p_1,skill_p_2,obj_p_1,obj_p_2)) {
        	return true;
        }
        if(line_to_line(skill_p_1,skill_p_2,obj_p_2,obj_p_3)) {
        	return true;
        }
        if(line_to_line(skill_p_1,skill_p_2,obj_p_3,obj_p_4)) {
        	return true;
        }
        if(line_to_line(skill_p_1,skill_p_2,obj_p_4,obj_p_1)) {
        	return true;
        }
        
        if(line_to_line(skill_p_2,skill_p_3,obj_p_1,obj_p_2)) {
        	return true;
        }
        if(line_to_line(skill_p_2,skill_p_3,obj_p_2,obj_p_3)) {
        	return true;
        }
        if(line_to_line(skill_p_2,skill_p_3,obj_p_3,obj_p_4)) {
        	return true;
        }
        if(line_to_line(skill_p_2,skill_p_3,obj_p_4,obj_p_1)) {
        	return true;
        }
        
        if(line_to_line(skill_p_3,skill_p_4,obj_p_1,obj_p_2)) {
        	return true;
        }
        if(line_to_line(skill_p_3,skill_p_4,obj_p_2,obj_p_3)) {
        	return true;
        }
        if(line_to_line(skill_p_3,skill_p_4,obj_p_3,obj_p_4)) {
        	return true;
        }
        if(line_to_line(skill_p_3,skill_p_4,obj_p_4,obj_p_1)) {
        	return true;
        }
        
        if(line_to_line(skill_p_4,skill_p_1,obj_p_1,obj_p_2)) {
        	return true;
        }
        if(line_to_line(skill_p_4,skill_p_1,obj_p_2,obj_p_3)) {
        	return true;
        }
        if(line_to_line(skill_p_4,skill_p_1,obj_p_3,obj_p_4)) {
        	return true;
        }
        if(line_to_line(skill_p_4,skill_p_1,obj_p_4,obj_p_1)) {
        	return true;
        }

        return false;
        
	}
	
	private boolean line_to_line(Point p1,Point p2,Point p3,Point p4) {//1,2两点形成一条线，3,4两点形成一条线
		if(Line2D.linesIntersect(p1.x,p1.y,p2.x,p2.y,p3.x,p3.y,p4.x,p4.y)) {
			//如果发生了碰撞
			return true;
		}else {
			return false;
		}
		
	}

}
//这个类的核心思想就是让我的技能的范围形成一个矩形，（传入矩形的长度和宽度以及矩形的中心，就能唯一确定一个矩形）
//然后拿矩形的四条边和被技能作用的对象（这个对象也是个矩形）的四条边分别做相交测试，如果测试成功，再确定player和monster的相对位置
//好做击退等效果
