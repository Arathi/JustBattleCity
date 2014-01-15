package org.beh.jbc;

import java.util.ArrayList;
import java.util.List;

public class Stage {
	public static final int STAGE_SIZE_X = 26;
	public static final int STAGE_SIZE_Y = 26;
	protected byte[][] tiles;
	protected List<Tank> aliveTanks;
	protected List<Bullet> bullets;
	protected List<Tank> enemyLeft;
	protected boolean baseAlive;
	protected IVisualStage ui;
	
	public Stage(){
		init();
	}
	
	/**
	 * ��ʼ��ս��
	 */
	public void init(){
		tiles = new byte[STAGE_SIZE_X][STAGE_SIZE_Y];
		aliveTanks = new ArrayList<Tank>();
		bullets = new ArrayList<Bullet>();
		enemyLeft = new ArrayList<Tank>();
		baseAlive = true;
	}
	
	/**
	 * �����ͼ
	 * @param cmap ��ͼ����
	 * @return �����Ƿ�ɹ�
	 */
	public boolean loadFromCityMap(CityMap cmap){
		//byte[] cmap_tile = cmap.toArray();
		byte[][] cmap_tiles = cmap.toArray2D();
//		if (cmap_tile.length != STAGE_SIZE_X*STAGE_SIZE_Y)
//			return false;
		int x,y;
		for (y=0; y<STAGE_SIZE_Y; y++){
			for (x=0; x<STAGE_SIZE_X; x++){
				//index = x + y*STAGE_SIZE_X;
				tiles[x][y]=cmap_tiles[x][y];
			}
		}
		return true;
	}
	
	public String toString(){
		String value="";
		int x, y;
		for (y=0; y<STAGE_SIZE_Y; y++){
			for (x=0; x<STAGE_SIZE_X; x++){
				if (tiles[x][y]<10) value += "0";
				value += tiles[x][y]+" ";
			}
			value += "\n";
		}
		return value;
	}

	public byte[][] getTiles() {
		return tiles;
	}
	
	/**
	 * ��ȡĳ���������ͼ���
	 * @param x x����
	 * @param y y����
	 * @return ��Ӧ��ͼ���
	 */
	public byte getTile(int x, int y){
		return tiles[x][y];
	}
	
	/**
	 * ��ȡ��ǰ�����ս���ϵ�����̹��
	 * @return
	 */
	public List<Tank> getAliveTanks(){
		return aliveTanks;
	}
	
	@Deprecated
	public void addTank(Tank tank){
		aliveTanks.add(tank);
	}
	
	public void addTank(Tank tank, int startPoint){
		switch (startPoint){
		case 0:
			tank.sPosX=0;
			tank.sPosY=0;
			tank.aspect=Sprite.ASPECT_DOWN;
			break;
		case 1:
			tank.sPosX=12;
			tank.sPosY=0;
			tank.aspect=Sprite.ASPECT_DOWN;
			break;
		case 2:
			tank.sPosX=24;
			tank.sPosY=0;
			tank.aspect=Sprite.ASPECT_DOWN;
			break;
		case 3:
			tank.sPosX=8;
			tank.sPosY=24;
			tank.aspect=Sprite.ASPECT_UP;
			break;
		case 4:
			tank.sPosX=16;
			tank.sPosY=24;
			tank.aspect=Sprite.ASPECT_UP;
			break;
		}
		aliveTanks.add(tank);
		tank.intoStage(this);
	}
	
	public boolean isBaseAlive(){
		return baseAlive;
	}
	
	/**
	 * ʹʱ��ǰ��һ֡(�ݶ�Ϊ1/60s)
	 */
	public void handle(){
		for (Tank tank:aliveTanks){
			tank.move(Sprite.ASPECT_DOWN); //ǿ���ƶ�
			switch (tank.getStatus()){
			case Tank.STATUS_BORN:
				tank.doBorn();
				break;
			case Tank.STATUS_DYING:
				tank.doDying();
				break;
			case Tank.STATUS_MOVING:
				tank.doMoving();
				break;
			case Tank.STATUS_READY:
			case Tank.STATUS_DEAD:
				//TODO �ͷ���Դ
				break;
			default:
			}
		}
	}
	
	public void registerUI(IVisualStage ivs) {
		ui = ivs;
	}
	
	/**
	 * ��Ԫ��������
	 * @param args
	 */
	public static void main(String[] args){
		Stage stage = new Stage();
		CityMap cmap = new CityMap();
		stage.loadFromCityMap(cmap);
		System.out.println(stage);
	}

	public int checkPoint(int sPosX, int sPosY, int aspect, boolean hasBoat) {
		int nextX=sPosX, nextY=sPosY;
		int originIndex = nextX+nextY*(STAGE_SIZE_X-1);
		int nextIndex = originIndex;
		
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

		//�߽���ײ���
		if (nextX<0 || nextX>STAGE_SIZE_X-2 || nextY<0 || nextY>STAGE_SIZE_Y-2)
			return -1;
		
		byte tl, tr, bl, br;
		byte nextTileL=CityMap.TILE_NONE, nextTileR=CityMap.TILE_NONE;
		byte ntlType, ntrType;
		boolean allowThrough=true;
		tl = getTile(nextX, nextY);
		tr = getTile(nextX+1, nextY);
		bl = getTile(nextX, nextY+1);
		br = getTile(nextX+1, nextY+1);
		switch (aspect){
		case Sprite.ASPECT_UP:
			nextTileL = tl;
			nextTileR = tr;
			break;
		case Sprite.ASPECT_RIGHT:
			nextTileL = tr;
			nextTileR = br;
			break;
		case Sprite.ASPECT_DOWN:
			nextTileL = br;
			nextTileR = bl;
			break;
		case Sprite.ASPECT_LEFT:
			nextTileL = bl;
			nextTileR = tl;
			break;
		}
		ntlType = (byte) (nextTileL & CityMap.TILE_MASK);
		ntrType = (byte) (nextTileR & CityMap.TILE_MASK);
		//ǽ����ײ���(��������ֹ)
		if ( ntlType==CityMap.TILE_BRICK_TYPE || ntlType==CityMap.TILE_IRON ||
			ntrType==CityMap.TILE_BRICK_TYPE || ntrType==CityMap.TILE_IRON ){
			allowThrough = false;
		}
		//ˮ����ײ���(�д���ͨ��)
		if ( (ntlType==CityMap.TILE_WATER_TYPE || ntrType==CityMap.TILE_WATER_TYPE) &&
			hasBoat==false ){
			allowThrough = false;
		}
		//�ݵء��յ�(������ͨ��)
		if (allowThrough){
			return nextX+nextY*(STAGE_SIZE_X-1);
		}
		else{
			return -1;
		}
	}

}
