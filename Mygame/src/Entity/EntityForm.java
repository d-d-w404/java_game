package Entity;

import Interface.Skill;

public class EntityForm {
	private FormType form;
	private Entity entity;
	
	
	private double groundSpeed;
	private boolean falling;
	
	
	private boolean ifattack;
	private int monster_attack_count;
	private boolean ifattack_monster;
	private boolean ifstartjump;
	private boolean ifbehit;
	private int behit_count;
	
	private boolean ifinvincible;//无敌的
	private int readyforinvincible_count;
	private int ifinvincible_count;

	//skill
	Skill skill;
	private boolean ifskill1;
	private int skill1_count;
	private boolean ifcd1;
	private int cd1_clock;
	
	private boolean ifskill2;
	private int skill2_count;
	private boolean ifcd2;
	private int cd2_clock;
	
	public EntityForm(Entity entity) {
		this.entity=entity;
		skill=new Skill(this.entity);
	}
	
	public void checkform() {
		this.groundSpeed=this.entity.getGroundSpeed();
		this.falling=this.entity.getFalling();
		
		//cd 判断
		startcd1();
		startcd2();
		
		
		//受击判断
		if(ifbehit &&!ifinvincible) {	
			ifskill1=false;
			skill1_count=0;
			//当player在skill1时受击，进入ifbehit状态，需要取消skill1的状态
			//并让skill1Index变为0，否则就会在受击完后继续执行skill1
			readyforinvincible_count++;
			if(readyforinvincible_count>=20) {
				ifinvincible();//在behit状态20次后，进入无敌状态，如果中间进入其他状态，重新计时
				readyforinvincible_count=0;
				return;
			}
			form=FormType.BEHIT;
			behit_count++;
			if(behit_count>1) {
				behit_count=0;
				ifbehit=false;
			}
			return;
		}
		
		readyforinvincible_count=0;//没有进入behit状态，就重新计时
		
		//skill1判断
		if(ifskill1 && !ifcd1) {
			form=FormType.SKILL1;
			skill1_count++;
			skill.skill1();
			if(skill1_count>=skill.gettime()) {
				skill1_count=0;
				ifskill1=false;
				ifcd1=true;
			}		
			return;	
		}
		
		//skill2判断
		if(ifskill2 && !ifcd2) {
			form=FormType.SKILL2;
			skill2_count++;
			skill.skill2();//skill2_count虽然在后面的if中归0，但是上面有个skill2_count++;所以在skill中基本就是从1开始，一直到skill中的time
			if(skill2_count>=skill.gettime()) {
				skill2_count=0;
				ifskill2=false;
				ifcd2=true;
			}			
			return;	
		}
		
		//无敌判断
		if(ifinvincible) {
			form=FormType.INVINCIBLE;
			ifinvincible_count++;
			if(ifinvincible_count>10) {
				ifinvincible_count=0;
				ifinvincible=false;
			}
		}
		
		//player攻击判断
		if(ifattack) {
			form=FormType.ATTACK;
			ifattack=false;
			return;			
		}//给player用的
		
		//monster攻击判断
		if(ifattack_monster) {
			form=FormType.ATTACK;
			monster_attack_count++;
			if(monster_attack_count>5) {//这个数值和attack文件中图片的数量有关
				monster_attack_count=0;//0-5一共6个数，attack文件中有6张图片
				ifattack_monster=false;
			}
			return;			
		}//给monster用的，之所以不和player用同一个，是因为player的attack是我按
		//一下"J"，就放出一张图片，但是monster的攻击是由随机函数控制的，很难连续
		//让monster处于ATTACK状态中，所以基本ATTACK状态只持续1，2帧的样子，导致
		//monster的动作很抽搐。
		//我这里让monster一旦进入ATTACK状态就保持6个帧，执行完所有的图片，动作就
		//很连贯了
		
		//起跳判断
		if(ifstartjump) {
			form=FormType.STARTJUMP;
			ifstartjump=false;
			return;
		}
		
		//原地待判断
	    if((groundSpeed >= -0.5 && groundSpeed <= 0.5) && !falling ){
            form=FormType.STAY;
	    }
	    
	    //跳至空中的判断
	    if(falling) {
	    	form=FormType.JUMP;
	    }
	    
	    //行走判断
	    if((groundSpeed <=-0.5 || groundSpeed >= 0.5) && !falling){
	    	form=FormType.WALK;
	    }

	}
	
	public FormType getform() {
		return form;
	}
	
	
	//改变状态的boolean值
	public void ifattack() {
			ifattack=true;	
	}
	public void ifattack_monster() {
		ifattack_monster=true;
	}
	public void ifstartjump() {
		ifstartjump=true;
	}
	public void ifbehit() {
			ifbehit=true;	
	}
	
	public void ifinvincible() {
		ifinvincible=true;
	}
	public int getreadyforinvincible_count() {
		return readyforinvincible_count;
	}
	
	
	public int getskill_count(FormType form) {
		int skill_count = 0;
		switch(form) {
		case SKILL1 :
			skill_count=getskill1_count();
			break;
		case SKILL2 :
			skill_count=getskill2_count();
			break;
		}
		return skill_count;
	}
	
	
	
	//skill
	public void ifskill1() {
		ifskill1=true;
	}//当我还在cd时间时，可能也会使用skill1，虽然不会立马执行，但是我的ifskill1已经
	//变成了true，当cd结束时，会立马执行skill1，显然效果不行
	//我可以在这个类中每个除了skill1的form下都进行一个ifskill1=false的操作，让我的
	//skill1没有及时执行，之后及时cd到了也不会执行
	
	//上面的想法虽然都是在这个类中实现,但是有点麻烦，我直接从control入手，简单一些
	public int getskill1_count() {
		return skill1_count;
	}
	
	
	public void ifskill2() {
		ifskill2=true;
	}
	public int getskill2_count() {
		return skill2_count;
	}
	
	//cd1
	private void startcd1() {
		//System.out.println(cd1_clock);
		if(ifcd1) {
			cd1_clock++;
			if(cd1_clock>1) {//目前的skill1的cd是10*30帧的样子
				ifcd1=false;
				cd1_clock=0;
			}
		}		
	}
	
	public boolean getifcd1() {
		return ifcd1;
	}//用于control中，解决//skill下的想法
	
	//cd2
	private void startcd2() {
		//System.out.println(cd1_clock);
		if(ifcd2) {
			cd2_clock++;
			if(cd2_clock>1) {//目前的skill1的cd是10*30帧的样子
				ifcd2=false;
				cd2_clock=0;
			}
		}		
	}
	
	public boolean getifcd2() {
		return ifcd2;
	}


}
