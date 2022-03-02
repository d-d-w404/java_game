package Control;
//import gamelogic.entities.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import Entity.FormType;
import Player.Player;
import Scene.Scene;


public class GameKeyController implements KeyListener {
	public enum direction {LEFT,RIGHT,UP,DOWN};
    private Scene scene;
	private Player player;
	private ArrayList<String> keysDown = new ArrayList<String>();
	private String lastKeysDown ="";
	private boolean calculatingKeys;
	public long lastTime; 
	public GameKeyController(Player player,Scene scene){
		this.player = player;
		//this.game = game;
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		arg0.setKeyChar('p');//先把所有的按键统一为p，表示有输入，再到后面的released中细分
		keyReleased(arg0);
		//System.out.println("Key press");

	}
	
	//这里按键的设计pressed是没有用的，但是通过pressed可以调用released,真正发挥作用的是在released中。
	//这样做的好处应该就是可以长按。
	@Override
	public void keyReleased(KeyEvent arg0) {
		//System.out.println("Key release");
		while(calculatingKeys){//感觉calculatingKeys是true的时候，就是在处理keys，这里就用循环让keysDown接受不到新的输入key
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(arg0.getKeyCode() == KeyEvent.VK_A){
			//这个arg0可以是KeyPressed或者KeyReleased中的
			//通过后面的测试，我们可以看到，getKeyChar()不是
			//p的arg0就是松开按键时的arg0
			if(keysDown.contains("l")){
				keysDown.remove("l");
			}else if(arg0.getKeyChar() == 'p')
				keysDown.add("l");
			//if(arg0.getKeyChar()!='p') {System.out.print("a");}
			//
		}
		if(arg0.getKeyCode() == KeyEvent.VK_D){
			if(keysDown.contains("r")){
				keysDown.remove("r");
			}else if(arg0.getKeyChar() == 'p')
				keysDown.add("r");
		}
		if(arg0.getKeyCode() == KeyEvent.VK_W){
			if(keysDown.contains("up")){
				keysDown.remove("up");
			}else if(arg0.getKeyChar() == 'p')
				keysDown.add("up");
		}
		if(arg0.getKeyCode() == KeyEvent.VK_S){//用于下降
			if(keysDown.contains("down")){
				keysDown.remove("down");
			}else if(arg0.getKeyChar() == 'p')
				keysDown.add("down");
		}
		if(arg0.getKeyCode() == KeyEvent.VK_K){
			if(keysDown.contains("jump")){
				keysDown.remove("jump");
			}else if(arg0.getKeyChar() == 'p')
				keysDown.add("jump");
		}
		if(arg0.getKeyCode() == KeyEvent.VK_J){
			if(keysDown.contains("attack")){
				keysDown.remove("attack");
			}else if(arg0.getKeyChar() == 'p')
				keysDown.add("attack");
		}
		if(arg0.getKeyCode() == KeyEvent.VK_U){
			if(keysDown.contains("skill1")){
				keysDown.remove("skill1");
			}
			else if(arg0.getKeyChar() == 'p')
				keysDown.add("skill1");			
		}
		if(arg0.getKeyCode() == KeyEvent.VK_I){
				if(keysDown.contains("skill2")){
					keysDown.remove("skill2");
			}
			else if(arg0.getKeyChar() == 'p')
				keysDown.add("skill2");
			
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}
	public void calculateKeyPresses() {
	   lastTime = System.currentTimeMillis(); 
		try{
			if(calculatingKeys == true) return;
		
		calculatingKeys = true;
		for(String str: keysDown){
			if(str.equals("l")){
				player.move(direction.LEFT);
			}
			if(str.equals("r")){
				player.move(direction.RIGHT);
			}
			if(str.equals("jump")){
				player.move(direction.UP);
				if(!player.getFalling()) {
					player.checkindex.entityform.ifstartjump();
				}//只有在地上起跳时才能触发startjump状态，这个状态目前是为了那一帧的起跳动作	
			}
			if(str.equals("down")){
				player.move(direction.DOWN);
			}
			if(str.equals("attack") && player.checkindex.entityform.getform()!=FormType.BEHIT){
				player.checkindex.entityform.ifattack();
			}
			if(str.equals("skill1") && !player.checkindex.entityform.getifcd1()) {
				player.checkindex.entityform.ifskill1();
			}
			if(str.equals("skill2") && !player.checkindex.entityform.getifcd2()) {
				player.checkindex.entityform.ifskill2();
			}
		}
		if(!lastKeysDown.equals(keysDown.toString().trim())){
		}
		lastKeysDown = keysDown.toString().trim();//toString()让数字成为字符串，trim()删除字符串首尾的空白符
		calculatingKeys = false;
	}catch (ConcurrentModificationException e) {
		scene.resetKeyController();
		System.out.println(e + "in calculating key presses");
	}
	
	}
}
