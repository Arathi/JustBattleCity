package org.beh.jbc;

public class Sprite {
	public static final int ASPECT_UP = 0;
	public static final int ASPECT_RIGHT = 1;
	public static final int ASPECT_DOWN = 2;
	public static final int ASPECT_LEFT = 3;
	
	protected int sPosX; //精灵坐标x
	protected int sPosY; //精灵坐标y
	
	protected int sNextX;
	protected int sNextY; //下个位置的坐标
	
	protected int size; //碰撞体积
	
	protected int aspect; //移动方向
	protected int speed; //移动速度
	
	protected int frame; //动画帧数
	protected int subFrame; //动画帧数
	
	public int getTileX(){
		return sPosX;
	}
	
	public int getTileY(){
		return sPosY;
	}
	
	public int getNextX(){
		return sNextX;
	}
	
	public int getNextY(){
		return sNextY;
	}
	
	public int getAspect() {
		return aspect;
	}
	
	public int getSpeed(){
		return speed;
	}
}
