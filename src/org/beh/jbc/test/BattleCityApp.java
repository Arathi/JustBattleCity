package org.beh.jbc.test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import org.beh.jbc.CityMap;
import org.beh.jbc.Stage;
import org.beh.jbc.Tank;

import java.awt.BorderLayout;

public class BattleCityApp implements Runnable {
	public static final int FPS = 60;
	public static final int MsPF = 1000/FPS;

	private JFrame frmJustBattleCity;
	private BattleCityPanel panel;
	private Stage stage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BattleCityApp window = new BattleCityApp();
					window.frmJustBattleCity.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BattleCityApp() {
		stage = new Stage();
		stage.loadFromCityMap(new CityMap());
		Tank tank1P=new Tank(Tank.TANK_1P);
		Tank tank2P=new Tank(Tank.TANK_2P);
		Tank tankE1=new Tank(Tank.TANK_E1);
		Tank tankE2=new Tank(Tank.TANK_E2);
		Tank tankE4=new Tank(Tank.TANK_E4);
		stage.addTank(tank1P,3);
		stage.addTank(tank2P,4);
		stage.addTank(tankE1,0);
		stage.addTank(tankE2,1);
		stage.addTank(tankE4,2);
		initialize();
		
		Thread thread = new Thread(this);
		thread.start();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmJustBattleCity = new JFrame();
		frmJustBattleCity.setTitle("Just Battle City");
		frmJustBattleCity.setBounds(100, 100, 285, 300);
		frmJustBattleCity.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmJustBattleCity.setJMenuBar(menuBar);
		
		panel = new BattleCityPanel(stage);
		frmJustBattleCity.getContentPane().add(panel, BorderLayout.CENTER);
	}

	@Override
	public void run() {
		int frameCounter=0;
		while (true){
			try {
				Thread.sleep(MsPF);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(frameCounter++);
			stage.handle();
			panel.repaint();
			if (frameCounter>500) break;
		}
	}

}
