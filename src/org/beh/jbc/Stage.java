package org.beh.jbc;

import java.util.ArrayList;
import java.util.List;

public class Stage {
	public static final int STAGE_SIZE_X = 26;
	public static final int STAGE_SIZE_Y = 26;
	public static final int START_POINT_ENEMY_TL = 0;
	public static final int START_POINT_ENEMY_TM = 1;
	public static final int START_POINT_ENEMY_TR = 2;
	public static final int START_POINT_PLAYER_1 = 3;
	public static final int START_POINT_PLAYER_2 = 4;

	public static final int STATUS_STARTING = 0;
	public static final int STATUS_GAMING = 1;
	public static final int STATUS_SUSPEND = 2;
	public static final int STATUS_ENDING = 8;
	public static final int STATUS_ENDED = 9;
	
	public static final int DELAY_STARTING = 2;

	protected int status;
	protected byte[][] tiles;
	protected List<Tank> aliveTanks;
	protected List<Bullet> bullets;
	protected List<Tank> enemyLeft;
	protected boolean baseAlive;
	protected IVisualStage ui;
	protected BattleCity game;
	protected int curtainFrame;
	protected int subFrame;
	protected Player player1;
	protected Player player2;

	@Deprecated
	public Stage() {
		init();
	}

	public Stage(BattleCity game) {
		this.game = game;
		init();
		player1 = game.getPlayer(BattleCity.PLAYER_ID_P1);
		player2 = game.getPlayer(BattleCity.PLAYER_ID_P2);
	}

	/**
	 * ��ʼ��ս��
	 */
	public void init() {
		tiles = new byte[STAGE_SIZE_X][STAGE_SIZE_Y];
		aliveTanks = new ArrayList<Tank>();
		bullets = new ArrayList<Bullet>();
		enemyLeft = new ArrayList<Tank>();
		baseAlive = true;
	}

	/**
	 * �����ͼ
	 * 
	 * @param cmap
	 *            ��ͼ����
	 * @return �����Ƿ�ɹ�
	 */
	public boolean loadCityMap(CityMap cmap) {
		// byte[] cmap_tile = cmap.toArray();
		byte[][] cmap_tiles = cmap.toArray2D();
		// if (cmap_tile.length != STAGE_SIZE_X*STAGE_SIZE_Y)
		// return false;
		int x, y;
		for (y = 0; y < STAGE_SIZE_Y; y++) {
			for (x = 0; x < STAGE_SIZE_X; x++) {
				// index = x + y*STAGE_SIZE_X;
				tiles[x][y] = cmap_tiles[x][y];
			}
		}
		return true;
	}

	public String toString() {
		String value = "";
		int x, y;
		for (y = 0; y < STAGE_SIZE_Y; y++) {
			for (x = 0; x < STAGE_SIZE_X; x++) {
				if (tiles[x][y] < 10)
					value += "0";
				value += tiles[x][y] + " ";
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
	 * 
	 * @param x
	 *            x����
	 * @param y
	 *            y����
	 * @return ��Ӧ��ͼ���
	 */
	public byte getTile(int x, int y) {
		return tiles[x][y];
	}

	/**
	 * ��ȡ��ǰ�����ս���ϵ�����̹��
	 * 
	 * @return
	 */
	public List<Tank> getAliveTanks() {
		return aliveTanks;
	}

	@Deprecated
	public void addTank(Tank tank) {
		aliveTanks.add(tank);
	}

	public void addTank(Tank tank, int startPoint) {
		int x = 0, y = 0, a = Sprite.ASPECT_UP;
		switch (startPoint) {
		case START_POINT_ENEMY_TL:
			x = 0;
			y = 0;
			a = Sprite.ASPECT_DOWN;
			break;
		case START_POINT_ENEMY_TM:
			x = 12;
			y = 0;
			a = Sprite.ASPECT_DOWN;
			break;
		case START_POINT_ENEMY_TR:
			x = 24;
			y = 0;
			a = Sprite.ASPECT_DOWN;
			break;
		case START_POINT_PLAYER_1:
			x = 8;
			y = 24;
			a = Sprite.ASPECT_UP;
			// player1=tank;
			break;
		case START_POINT_PLAYER_2:
			x = 16;
			y = 24;
			a = Sprite.ASPECT_UP;
			// player2=tank;
			break;
		}
		aliveTanks.add(tank);
		tank.intoStage(this, x, y, a);
	}

	public boolean isBaseAlive() {
		return baseAlive;
	}

	/**
	 * ʹʱ��ǰ��һ֡(�ݶ�Ϊ1/60s)
	 */
	public void handle() {
		switch (status) {
		case STATUS_STARTING:
			subFrame++;
			if (subFrame>DELAY_STARTING){
				curtainFrame++;
				subFrame=0;
			}
			if (curtainFrame>13){
				status = STATUS_GAMING;
				curtainFrame=0;
			}
			break;
		case STATUS_GAMING:
			for (Tank tank : aliveTanks) {
				// TODO ������ƣ��Ƴ�ǿ���ƶ��뿪��
				// tank.move(Sprite.ASPECT_UP); //ǿ���ƶ�
				// tank.fire(); //ǿ�п���
				switch (tank.getStatus()) {
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
					// TODO �ͷ���Դ
					break;
				default:
				}
			}
			break;
		}

	}

	public void registerUI(IVisualStage ivs) {
		ui = ivs;
	}

	/**
	 * ��Ԫ��������
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Stage stage = new Stage();
		CityMap cmap = new CityMap();
		stage.loadCityMap(cmap);
		System.out.println(stage);
	}

	public int convetXY2Index(int x, int y) {
		return x + y * (STAGE_SIZE_X + 1);
	}

	public int convetIndex2X(int index) {
		return index % (STAGE_SIZE_X + 1);
	}

	public int convetIndex2Y(int index) {
		return index / (STAGE_SIZE_X + 1);
	}

	public int getNextIndex(int sPosX, int sPosY, int aspect) {
		int nextX = sPosX, nextY = sPosY;
		switch (aspect) {
		case Sprite.ASPECT_UP:
			nextY = sPosY - 1;
			break;
		case Sprite.ASPECT_RIGHT:
			nextX = sPosX + 1;
			break;
		case Sprite.ASPECT_DOWN:
			nextY = sPosY + 1;
			break;
		case Sprite.ASPECT_LEFT:
			nextX = sPosX - 1;
			break;
		}
		// �Ƿ���������
		if (nextX < 0 || nextX > STAGE_SIZE_X - 2 || nextY < 0
				|| nextY > STAGE_SIZE_Y - 2)
			return -1;
		return convetXY2Index(nextX, nextY);
	}

	public int checkPoint(int sPosX, int sPosY, int aspect, boolean hasBoat) {
		int nextX = sPosX, nextY = sPosY;
		int nextIndex = getNextIndex(sPosX, sPosY, aspect);
		// �߽���ײ���
		if (nextIndex < 0)
			return -1;
		nextX = convetIndex2X(nextIndex);// nextIndex%(STAGE_SIZE_X-1);
		nextY = convetIndex2Y(nextIndex);// nextIndex/(STAGE_SIZE_X-1);

		byte tl, tr, bl, br;
		byte nextTileL = CityMap.TILE_NONE, nextTileR = CityMap.TILE_NONE;
		byte ntlType, ntrType;
		boolean allowThrough = true;
		tl = getTile(nextX, nextY);
		tr = getTile(nextX + 1, nextY);
		bl = getTile(nextX, nextY + 1);
		br = getTile(nextX + 1, nextY + 1);
		switch (aspect) {
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
		// ǽ����ײ���(��������ֹ)
		if (ntlType == CityMap.TILE_BRICK_TYPE || ntlType == CityMap.TILE_IRON
				|| ntrType == CityMap.TILE_BRICK_TYPE
				|| ntrType == CityMap.TILE_IRON) {
			allowThrough = false;
		}
		// ˮ����ײ���(�д���ͨ��)
		if ((ntlType == CityMap.TILE_WATER_TYPE || ntrType == CityMap.TILE_WATER_TYPE)
				&& hasBoat == false) {
			allowThrough = false;
		}
		// �ݵء��յ�(������ͨ��)
		if (allowThrough) {
			return nextIndex;// convetXY2Index(nextX, nextY);
								// //nextX+nextY*(STAGE_SIZE_X-1);
		} else {
			return -1;
		}
	}

	public List<Bullet> getBullets() {
		return bullets;
	}

	public Tank getPlayer1sTank() {
		// TODO ��ӻ�ȡ��ҵ�̹�˵Ĺ���
		return player1.getTank();
	}

	public Tank getPlayer2sTank() {
		// TODO ��ӻ�ȡ��ҵ�̹�˵Ĺ���
		return player2.getTank();
	}

	public boolean checkStageEnd() {
		// ʣ��л�Ϊ0(���ϴ��+���)

		// ʣ���Ի�Ϊ0(���ϴ��+���)

		// ���ر��ݻ�

		return false;
	}
	
	public int getStatus(){
		return status;
	}
	
	public int getCurtainFrame(){
		return curtainFrame;
	}

}
