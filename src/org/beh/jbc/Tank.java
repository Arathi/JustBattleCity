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
	
//	public int getType(int id){
//		switch (id){
//		case TANK_1P:
//			
//		}
//	}
	
	public void init(int id){
		size=2;
		switch (id){
		case TANK_1P:
			this.type=0;
			this.life=0;
			sPosX = 8;
			sPosY = 25;
			invincibleLeft=3;
			aspect=Sprite.ASPECT_UP;
			break;
		}
		suspendLeft=0;
	}

	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getColor() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getAspect() {
		// TODO Auto-generated method stub
		return aspect;
	}
}
