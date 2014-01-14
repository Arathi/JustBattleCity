package org.beh.jbc;

public class Tank extends Sprite {
	public static final int TANK_1P=1;
	public static final int TANK_2P=2;
	public static final int TANK_E1=11;
	public static final int TANK_E2=12;
	public static final int TANK_E3=13;
	public static final int TANK_E4=14;
	
	protected int type; //�з�4�֣��ҷ�����
	protected int life; //���ܱ����£���ɫ��ͬ��
	protected int power; //0: ��ͨ��1: ���٣�2:˫����3: ����ǽ��4: �ղ�
	protected boolean hasItem; //��ʾΪ��ɫ
	protected int bornFrame; //����״̬
	protected int invincibleLeft; //�޵�ʣ��ʱ��
	protected int suspendLeft; //��ͣʣ��ʱ�䣨�ҷ�̹�˱��Լ��˴��ˣ�ʱ��������㣩
	protected boolean hasBoat; //�Ƿ����ˮ���ƶ�
	
	public Tank(){
		init(TANK_1P);
	}
	public Tank(int id){
		init(id);
	}
	
//	public int getType(int id){
//		switch (id){
//		case TANK_1P:
//			
//		}
//	}
	
	public void init(int id){
		size=2;
		this.type=id;
		switch (id){
		case TANK_1P:
			this.life=0;
			sPosX = 8;
			sPosY = 24;
			invincibleLeft=3;
			aspect=Sprite.ASPECT_UP;
			break;
		case TANK_2P:
			this.life=0;
			sPosX = 16;
			sPosY = 24;
			invincibleLeft=3;
			aspect=Sprite.ASPECT_UP;
			break;
		case TANK_E1:
			this.life=0;
			sPosX = 0;
			sPosY = 0;
			invincibleLeft=0;
			aspect=Sprite.ASPECT_DOWN;
			break;
		case TANK_E2:
			this.life=0;
			sPosX = 0;
			sPosY = 0;
			invincibleLeft=0;
			aspect=Sprite.ASPECT_DOWN;
			break;
		case TANK_E3:
			this.life=0;
			sPosX = 0;
			sPosY = 0;
			invincibleLeft=0;
			aspect=Sprite.ASPECT_DOWN;
			break;
		case TANK_E4:
			this.life=3;
			sPosX = 0;
			sPosY = 0;
			invincibleLeft=0;
			aspect=Sprite.ASPECT_DOWN;
			break;
		}
		suspendLeft=0;
	}

	public int getType() {
		switch (type){
		case TANK_1P:
		case TANK_2P:
			return power;
		case TANK_E1:
			return 0;
		case TANK_E2:
			return 1;
		case TANK_E3:
			return 2;
		case TANK_E4:
			return 3;
		}
		return 0;
	}

	public int getColor() {
		int color;
		switch (type){
		case TANK_1P:
			color=0;
			break;
		case TANK_2P:
			color=1;
			break;
		default:
			color = life+3;
		}
		return color;
	}

	public int getAspect() {
		return aspect;
	}
}
