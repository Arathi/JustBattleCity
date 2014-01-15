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
	 * 初始化战场
	 */
	public void init(){
		tiles = new byte[STAGE_SIZE_X][STAGE_SIZE_Y];
		aliveTanks = new ArrayList<Tank>();
		bullets = new ArrayList<Bullet>();
		enemyLeft = new ArrayList<Tank>();
		baseAlive = true;
	}
	
	/**
	 * 载入地图
	 * @param cmap 地图对象
	 * @return 加载是否成功
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
	 * 获取某个坐标的贴图编号
	 * @param x x坐标
	 * @param y y坐标
	 * @return 对应贴图编号
	 */
	public byte getTile(int x, int y){
		return tiles[x][y];
	}
	
	/**
	 * 获取当前存活在战场上的所有坦克
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
	 * 使时间前进一帧(暂定为1/60s)
	 */
	public void handle(){
		for (Tank tank:aliveTanks){
			tank.move(Sprite.ASPECT_DOWN); //强行移动
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
				//TODO 释放资源
				break;
			default:
			}
		}
	}
	
	public void registerUI(IVisualStage ivs) {
		ui = ivs;
	}
	
	/**
	 * 单元测试驱动
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

		//边界碰撞检测
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
		//墙壁碰撞检测(无条件阻止)
		if ( ntlType==CityMap.TILE_BRICK_TYPE || ntlType==CityMap.TILE_IRON ||
			ntrType==CityMap.TILE_BRICK_TYPE || ntrType==CityMap.TILE_IRON ){
			allowThrough = false;
		}
		//水域碰撞检测(有船可通行)
		if ( (ntlType==CityMap.TILE_WATER_TYPE || ntrType==CityMap.TILE_WATER_TYPE) &&
			hasBoat==false ){
			allowThrough = false;
		}
		//草地、空地(无条件通行)
		if (allowThrough){
			return nextX+nextY*(STAGE_SIZE_X-1);
		}
		else{
			return -1;
		}
	}

}
