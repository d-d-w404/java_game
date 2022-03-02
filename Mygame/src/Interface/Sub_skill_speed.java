package Interface;

import Entity.Entity;

public class Sub_skill_speed {
	Entity e;
	
	public Sub_skill_speed(Entity e) {
		this.e=e;
	}
	
	public void sub_skill_speed(int speed_x,int speed_y) {
		if(e.getfacingRight()) {
			e.setGroundSpeed(speed_x);//x方向
			e.setSpeed(-speed_y);//y方向
		}
		else {
			e.setGroundSpeed(-speed_x);//x方向
			e.setSpeed(-speed_y);//y方向
		}
	}	

}
//x向右为+，y向上为+