package Entity;

public class CheckIndex {
	public EntityForm entityform;
	Entity e;
	double lasty;


	
	public CheckIndex(Entity entity) {
		this.e=entity;
		entityform=new EntityForm(entity);
	}
	
	public void checkindex() {
		if(entityform.getform()==FormType.STAY) {
			cleanIndex();
			checkStayIndex();
		}
		if(entityform.getform()==FormType.WALK) {
			cleanIndex();
			checkWalkIndex();
		}
		if(entityform.getform()==FormType.JUMP) {
			cleanIndex();
			checkJumpIndex();
		}
		if(entityform.getform()==FormType.ATTACK) {
			cleanIndex();
			checkAttackIndex();
		}
		if(entityform.getform()==FormType.STARTJUMP) {
			cleanIndex();
			checkStartjumpIndex();
		}
		if(entityform.getform()==FormType.BEHIT) {
			cleanIndex();
			checkBehitIndex();
		}
		if(entityform.getform()==FormType.SKILL1) {
			cleanIndex();
			checkSkill1Index();
		}
		if(entityform.getform()==FormType.SKILL2) {
			cleanIndex();
			checkSkill2Index();
		}
	}
	
	
	private void checkStayIndex() {
		if(e.stayIndex == e.stayImages.length-1){
		    e.stayIndex=-1;
	    }
	    e.stayIndex+=1;
	}
	private void checkWalkIndex() {
	    if(e.walkIndex == e.walkImages.length-1){
	    	e.walkIndex=-1;
	    }
	    e.walkIndex+=1;
	}
	private void checkJumpIndex() {
		if(e.position.y<lasty) {
			e.jumpIndex=1;
		}
		else {
			e.jumpIndex=2;
		}
		lasty=e.position.y;
	}
	private void checkAttackIndex() {
	    if(e.attackIndex == e.attackImages.length-1){
	    	e.attackIndex=-1;
	    }
	    e.attackIndex+=1;
	}
	private void checkStartjumpIndex() {
		e.startjumpIndex=0;
	}
	private void checkBehitIndex() {
	    if(e.behitIndex == e.behitImages.length-1){
	    	e.behitIndex=-1;
	    }
	    e.behitIndex+=1;
	}
	private void checkSkill1Index() {
	    if(e.skill1Index == e.skill1Images.length-1){
	    	e.skill1Index=-1;
	    }
	    e.skill1Index+=1;
	}
	private void checkSkill2Index() {
	    if(e.skill2Index == e.skill2Images.length-1){
	    	e.skill2Index=-1;
	    }
	    e.skill2Index+=1;
	}
	
	
	private void cleanIndex() {
		if(entityform.getform()==FormType.STAY) {
			e.walkIndex=0;
		    e.jumpIndex=0;
		    e.attackIndex=0;
		    e.startjumpIndex=0;
		    e.behitIndex=0;
		    e.skill1Index=0;
		    e.skill2Index=0;
		}
		if(entityform.getform()==FormType.WALK) {
			e.stayIndex=0;
		    e.jumpIndex=0;
		    e.attackIndex=0;
		    e.startjumpIndex=0;
		    e.behitIndex=0;
		    e.skill1Index=0;
		    e.skill2Index=0;
		}
		if(entityform.getform()==FormType.JUMP) {
			e.walkIndex=0;
		    e.stayIndex=0;
		    e.attackIndex=0;
		    e.startjumpIndex=0;
		    e.behitIndex=0;
		    e.skill1Index=0;
		    e.skill2Index=0;
		}
		if(entityform.getform()==FormType.ATTACK) {
			e.walkIndex=0;
		    e.stayIndex=0;
		    e.jumpIndex=0;
		    e.startjumpIndex=0;
		    e.behitIndex=0;
		    e.skill1Index=0;
		    e.skill2Index=0;
		}
		if(entityform.getform()==FormType.STARTJUMP) {
			e.walkIndex=0;
		    e.stayIndex=0;
		    e.jumpIndex=0;
		    e.attackIndex=0;
		    e.behitIndex=0;
		    e.skill1Index=0;
		    e.skill2Index=0;
		}
		if(entityform.getform()==FormType.BEHIT) {
			e.walkIndex=0;
		    e.stayIndex=0;
		    e.jumpIndex=0;
		    e.attackIndex=0;
		    e.startjumpIndex=0;
		    e.skill1Index=0;
		    e.skill2Index=0;
		}
		if(entityform.getform()==FormType.SKILL1) {
			e.walkIndex=0;
		    e.stayIndex=0;
		    e.jumpIndex=0;
		    e.attackIndex=0;
		    e.startjumpIndex=0;
		    e.behitIndex=0;
		    e.skill2Index=0;
		}
		if(entityform.getform()==FormType.SKILL2) {
			e.walkIndex=0;
		    e.stayIndex=0;
		    e.jumpIndex=0;
		    e.attackIndex=0;
		    e.startjumpIndex=0;
		    e.behitIndex=0;
		    e.skill1Index=0;
		}
		
	}	
	//?????????form?????????????????????????????????form???Index??????????????????????????????
	//??????????????????Index???0???????????????????????????form????????????0?????????

}
