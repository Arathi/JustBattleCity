package org.beh.jbc;

public class Sprite {
	public static final int ASPECT_UP = 0;
	public static final int ASPECT_RIGHT = 1;
	public static final int ASPECT_DOWN = 2;
	public static final int ASPECT_LEFT = 3;
	
	protected int sPosX; //��������x
	protected int sPosY; //��������y
	protected int size; //��ײ���
	
	protected int aspect; //�ƶ�����
	
	public int getTileX(){
		return sPosX;
	}
	public int getTileY(){
		return sPosY-size/2;
	}
}
