package org.beh.jbc;

public class Tank extends Sprite {
	public static final int TANK_1P=1;
	public static final int TANK_2P=2;
	public static final int TANK_E1=11;
	public static final int TANK_E2=12;
	public static final int TANK_E3=13;
	public static final int TANK_E4=14;
	
	public static final int STATUS_BORN = 0;
	public static final int STATUS_READY = 1;
	public static final int STATUS_MOVING = 2;
	public static final int STATUS_DYING = 8;
	public static final int STATUS_DEAD = 9;
	
	public static final int DELAY_BORN = 4;
	public static final int DELAY_DYING = 6;
	public static final int DELAY_MOVING = 1;
	
	protected int status;
	protected int type; //�з�4�֣��ҷ�����
	protected int life; //���ܱ����£���ɫ��ͬ��
	protected int power; //0: ��ͨ��1: ���٣�2:˫����3: ����ǽ��4: �ղ�
	protected boolean hasItem; //��ʾΪ��ɫ
	protected int invincibleLeft; //�޵�ʣ��ʱ��
	protected int suspendLeft; //��ͣʣ��ʱ�䣨�ҷ�̹�˱��Լ��˴��ˣ�ʱ��������㣩
	protected boolean hasBoat; //�Ƿ����ˮ���ƶ�
	protected Stage stage; //����ս��
	
	public Tank(){
		init(TANK_1P);
	}
	public Tank(int id){
		init(id);
	}
	
	public void init(int id){
		size=2;
		this.type=id;
		status=STATUS_BORN;
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
	
	public boolean hasBoat() {
		return this.hasBoat;
	}
	
	public int getStatus() {
		return status;
	}
	
	public int getFrame() {
		return frame;
	}
	
	public void doBorn(){
		subFrame++;
		if (subFrame>DELAY_BORN){
			frame++;
			subFrame=0;
		}
		if (frame>10){
			frame=0;
			status=STATUS_READY;
		}
	}
	
	public void doDying(){
		subFrame++;
		if (subFrame>DELAY_DYING){
			frame++;
			subFrame=0;
		}
		if (frame>3){
			frame=0;
			status=STATUS_DEAD;//STATUS_READY;
		}
	}
	
	public void doDead(){
		//������
	}
	
	public void doMoving(){
		subFrame++;
		if (subFrame>DELAY_MOVING){
			frame++;
			subFrame=0;
		}
		if (frame>8){
			frame=0;
			status=STATUS_READY;
			sPosX=sNextX;
			sPosY=sNextY;
		}
	}
	
	public boolean move(int aspect){
		if (status!=STATUS_READY) return false;
		//���ƶ�����ͬ�ڵ�ǰ�泯����ʱ
		if (this.aspect != aspect){
			this.aspect = aspect;
			return true;
		}
		//���ǰ����·���ߣ���ô����ʧ��
		int nextX=sPosX, nextY=sPosY;
		switch (aspect){
		case Sprite.ASPECT_UP:
			nextY=sPosY-1;
			break;
		case Sprite.ASPECT_RIGHT:
			nextX=sPosX+1;
			break;
		case Sprite.ASPECT_DOWN:
			nextY=sPosY+1;
			break;
		case Sprite.ASPECT_LEFT:
			nextX=sPosX-1;
			break;
		}
		if (stage.checkPoint(nextX,nextY,hasBoat)){
			sNextX = nextX;
			sNextY = nextY;
		}
		status=STATUS_MOVING;
		return true;
	}
	
	public void intoStage(Stage stage){
		this.stage = stage;
	}
	
}
