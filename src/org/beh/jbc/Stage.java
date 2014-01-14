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
	}
	
	/**
	 * ��Ԫ��������
	 * @param args
	 */
	public static void main(String[] args){
		Stage stage = new Stage();
		CityMap cmap = new CityMap();
		stage.loadFromCityMap(cmap);
		Tank tank=new Tank();
		stage.addTank(tank);
		System.out.println(stage);
	}
}
