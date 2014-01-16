package org.beh.jbc;

import java.util.HashMap;
import java.util.Map;

public class Player implements IController {
	private int id;
	private int score;
	private int tankLeft;
	private Tank tank;
	
	private Map<Integer, Integer> destroyCounter;
	
	public Player(int id){
		this.id = id;
		if (id==BattleCity.PLAYER_ID_P1){
			tank = new Tank(Tank.TANK_1P);
		}
		else if (id==BattleCity.PLAYER_ID_P2){
			tank = new Tank(Tank.TANK_2P);
		}
		destroyCounter = new HashMap<Integer, Integer>();
	}
	
	public void addScore(int addition){
		score += addition;
	}
	
	public void setTank(Tank tank){
		this.tank = tank;
	}
	public Tank getTank(){
		return tank;
	}
	
//	public void setController(IController controller){
//		this.controller = controller;
//	}
	
}
