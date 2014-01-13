package org.beh.jbc;

public class Sprite {
	public static final int ASPECT_UP = 0;
	public static final int ASPECT_RIGHT = 1;
	public static final int ASPECT_DOWN = 2;
	public static final int ASPECT_LEFT = 3;
	
	protected int sPosX; //精灵坐标x
	protected int sPosY; //精灵坐标y
	protected int size; //碰撞体积
	
	protected int aspect; //移动方向
	
	public int getTileX(){
		return sPosX;
	}
	public int getTileY(){
		return sPosY-size/2;
	}
}
