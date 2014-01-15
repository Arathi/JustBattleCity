package org.beh.jbc;

public class Sprite {
	public static final int ASPECT_UP = 0;
	public static final int ASPECT_RIGHT = 1;
	public static final int ASPECT_DOWN = 2;
	public static final int ASPECT_LEFT = 3;
	
	protected int sPosX; //��������x
	protected int sPosY; //��������y
	
	protected int sNextX;
	protected int sNextY; //�¸�λ�õ�����
	
	protected int size; //��ײ���
	
	protected int aspect; //�ƶ�����
	protected int speed; //�ƶ��ٶ�
	
	protected int frame; //����֡��
	protected int subFrame; //����֡��
	
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
