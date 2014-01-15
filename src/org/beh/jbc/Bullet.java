package org.beh.jbc;

public class Bullet extends Sprite {
	protected Stage stage;
	protected Tank owner; //发射子弹的坦克
	protected int power; //子弹威力
	
	//protected int side;
	
	public Bullet(Tank tank){
		this.owner = tank;
//		this.sPosX = tank.sPosX;
//		this.sPosY = tank.sPosY;
		this.stage = tank.getStage();
		//int sIndex=getNext;
		
		sNextX = sPosX;
		sNextY = sPosY;
	}
}
