package org.beh.jbc.test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import org.beh.jbc.CityMap;
import org.beh.jbc.Stage;
import org.beh.jbc.Tank;

import java.awt.BorderLayout;

public class BattleCityApp {

	private JFrame frame;
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
					window.frame.setVisible(true);
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
		stage.addTank(tank1P);
		stage.addTank(tank2P);
		stage.addTank(tankE1,0);
		stage.addTank(tankE2,1);
		stage.addTank(tankE4,2);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		panel = new BattleCityPanel(stage);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
	}

}
