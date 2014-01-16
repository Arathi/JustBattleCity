package org.beh.jbc;

public class BattleCity {
	public static final int PLAYER_ID_P1 = 0;
	public static final int PLAYER_ID_P2 = 1;
	
	public static final byte PLAYER_MODE_NONE = 0;
	public static final byte PLAYER_MODE_P1_ONLY = 1;
	public static final byte PLAYER_MODE_P2_ONLY = 2;
	public static final byte PLAYER_MODE_BOTH = 3;
	
	private Player[] players;
	private Stage curStage;
	private int stageId;
	
	public BattleCity(byte player_mode){
		init(player_mode);
	}
	
	public void init(byte player_mode){
		players = new Player[2];
		boolean hasP1 = (player_mode & PLAYER_MODE_P1_ONLY)==PLAYER_MODE_P1_ONLY;
		boolean hasP2 = (player_mode & PLAYER_MODE_P2_ONLY)==PLAYER_MODE_P2_ONLY;
		if (hasP1) players[PLAYER_ID_P1] = new Player(PLAYER_ID_P1);
		if (hasP2) players[PLAYER_ID_P2] = new Player(PLAYER_ID_P2);
		stageId = 1;
		CityMap cmap = new CityMap(stageId);
		curStage = new Stage(this);
		curStage.loadCityMap(cmap);
		
		startStage();
	}
	
	public void startStage(){
		if (players[PLAYER_ID_P1]!=null && players[PLAYER_ID_P1].getTank()!=null){
			curStage.addTank(players[PLAYER_ID_P1].getTank(), Stage.START_POINT_PLAYER_1);
		}
		if (players[PLAYER_ID_P2]!=null && players[PLAYER_ID_P2].getTank()!=null){
			curStage.addTank(players[PLAYER_ID_P2].getTank(), Stage.START_POINT_PLAYER_2);
		}
	}
	
	public Stage getCurStage(){
		return curStage;
	}
	
	public Player getPlayer(int index){
		if (index!=PLAYER_ID_P1 && index!=PLAYER_ID_P2) return null;
		return players[index];
	}
	
	
}
