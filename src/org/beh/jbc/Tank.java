package org.beh.jbc;

public class Tank extends Sprite {
	public static final int TANK_1P=1;
	public static final int TANK_2P=2;
	public static final int TANK_E1=11;
	public static final int TANK_E2=12;
	public static final int TANK_E3=13;
	public static final int TANK_E4=14;
	
	protected int type; //敌方4种，我方两种
	protected int life; //还能被打几下（颜色不同）
	protected int power; //0: 普通；1: 加速；2:双弹；3: 拆铁墙；4: 烧草
	protected boolean hasItem; //显示为红色
	protected int bornFrame; //出生状态
	protected int invincibleLeft; //无敌剩余时间
	protected int suspendLeft; //暂停剩余时间（我方坦克被自己人打了，时间独立计算）
	protected boolean hasBoat; //是否可以水上移动
	
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
