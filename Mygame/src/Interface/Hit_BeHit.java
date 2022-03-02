package Interface;

import java.awt.Point;

import Entity.FormType;
import Monster.Monster;
import Monster.Monstercage;
import Player.Player;


public class Hit_BeHit {
	Player player;
	//Monster monster;
	Monstercage monstercage;
	double pc;
	double mc;
	private double distance=10;
	private double range;
	Range_confirm range_confirm;
	
	
	public Hit_BeHit(Player player,Monstercage monstercage) {
		this.player=player;
		//this.monster=monster;
		this.monstercage=monstercage;
		this.range_confirm=new Range_confirm();
	}
	
	public void refresh() {
		for(Monster monster:monstercage.getallmonster()) {
			player_hit_monster(monster);
			monster_hit_player(monster);
			player_hit_monster_skill(monster,FormType.SKILL1,4,30,0,60,100,50,0);
			player_hit_monster_skill(monster,FormType.SKILL2,4,30,2,60,100,50,0);//后退速度，伤害，伤害帧，技能范围x,技能范围y,技能中心偏移x,技能中心偏移y(向下为+)
		}	//Monster monster,FormType type,int repel_speed,int damage,int range_x,int range_y,Point skill_p
	}
	
	private void player_hit_monster(Monster monster) {//这个是平a的判断,我暂时保留，其实可以和技能那个何为一体
		if(player.checkindex.entityform.getform()==FormType.ATTACK) {
			pc=player.getPosition().x+player.getWidth()/2;	
			mc=monster.getPosition().x+monster.getWidth()/2;			
			range=(player.getWidth()+monster.getWidth())/2+distance;			
	        if(player.getPosition().y>=monster.getPosition().y && player.getPosition().y+player.getHeight()<=monster.getPosition().y+monster.getHeight() ) {
				//player的所有部分在monster的y的范围内。
	    		   if(player.getfacingRight() && pc<mc&& mc-pc<range) {
	    			   //人面向右，monster被向右击飞。a
	    			   //monster.setGroundSpeed(1);//平a我暂时不搞击退效果
	    			   if(monster.checkindex.entityform.getform()!=FormType.ATTACK) {
	    			       monster.checkindex.entityform.ifbehit();
	    			       monster.setlife(-1);
	    			   }//只有当monster不处于attack状态时才能攻击它，避免了player一直攻击导致无敌
	    		   }
	    		   if(player.getfacingRight() && pc>mc && pc<mc+monster.getWidth()/2) {
	    			   //monster.setGroundSpeed(-1);
	    			   if(monster.checkindex.entityform.getform()!=FormType.ATTACK) {
	    			       monster.checkindex.entityform.ifbehit();
	    			       monster.setlife(-1);
	    			   }
	    		   }
	    		   if(!player.getfacingRight() && pc>mc && pc-mc<range) {
	    			   //monster.setGroundSpeed(-1);
	    			   if(monster.checkindex.entityform.getform()!=FormType.ATTACK) {
	    			       monster.checkindex.entityform.ifbehit();
	    			       monster.setlife(-1);
	    			   }
	    		   }
	    		   if(!player.getfacingRight() && pc<mc && pc>mc-monster.getWidth()/2) {
	    			   //monster.setGroundSpeed(1);
	    			   if(monster.checkindex.entityform.getform()!=FormType.ATTACK) {
	    			       monster.checkindex.entityform.ifbehit();
	    			       monster.setlife(-1);
	    			   }
	    		   }                             	        		    
			}
		}	
	}
	
	//这个函数中我需要输入技能的类型，技能出现伤害的帧，技能的范围，技能的伤害...(如果有以后再加)
	private void player_hit_monster_skill(Monster monster,FormType type,int repel_speed,int damage,int damage_frame,int range_x,int range_y,int p_move_x,int p_move_y) {//这个是技能的判断
        //frame英文中又可以叫做帧
		//System.out.println(player.checkindex.entityform.getform());
		if(player.checkindex.entityform.getform()==type  && player.checkindex.entityform.getskill_count(type)==damage_frame ) {
			//不仅要为skill2，而且第三帧才有伤害
			//System.out.println(player.getPosition().y +" >= "+ monster.getPosition().y +"  and  "+ (player.getPosition().y+player.getHeight()) +" <= "+ (monster.getPosition().y+monster.getHeight() ));
	        //这个注释主要是显示人物和monster的位置关系。
			
			
			//当我的人物的朝向不同的时候，需要把p_move_x,p_move_y,repel_speed稍微做一点调整
			if(player.getfacingRight()) {
				//朝向右是正常的，不用变
			}else {
				p_move_x=-p_move_x;
				repel_speed=-repel_speed;
			}
			
			if(range_confirm.range_confirm(player, monster, range_x, range_y, p_move_x,p_move_y)) {
				//range_confrim函数判断为true说明技能命中了。然后开始实现伤害
				//还要通过player和monster的相对位置进行击退分析
 			   
 			   if(monster.checkindex.entityform.getform()!=FormType.ATTACK) {
 			       monster.checkindex.entityform.ifbehit();
 			       monster.setlife(-damage);
 			       monster.setGroundSpeed(0);//先让monster速度归0
 			       //System.out.println(monster.getGroundSpeed());
 			       //monster.setGroundSpeed(repel_speed);//技能击退效果处
 			      // System.out.println(repel_speed+"and"+monster.getGroundSpeed());
 			       //暂时取消击退效果，
			   }                	        		    
			}
			
		}	
	}
	
	private void monster_hit_player(Monster monster) {
		if(monster.checkindex.entityform.getform()==FormType.ATTACK) {
			pc=player.getPosition().x+player.getWidth()/2;	
			mc=monster.getPosition().x+monster.getWidth()/2;			
			range=(player.getWidth()+monster.getWidth())/2+distance;
			if(player.getPosition().y>=monster.getPosition().y && player.getPosition().y+player.getHeight()<=monster.getPosition().y+monster.getHeight() ) {
				   if(monster.getfacingRight() && pc>mc&& pc-mc<range) {
					   player.checkindex.entityform.ifbehit();
					   player.setlife(-1);
	    		   }
	    		   if(monster.getfacingRight() && pc<mc && pc>monster.getPosition().x) {
					   player.checkindex.entityform.ifbehit();
					   player.setlife(-1);
	    		   }
	    		   if(!monster.getfacingRight() && pc<mc && mc-pc<range) {
					   player.checkindex.entityform.ifbehit();
					   player.setlife(-1);
	    		   }
	    		   if(!monster.getfacingRight() && pc>mc && pc<monster.getPosition().x+monster.getWidth()) {
					   player.checkindex.entityform.ifbehit();
					   player.setlife(-1);
	    		   }
			}
		} 
	}
	
}
