package org.beh.jbc.test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import org.beh.jbc.BattleCity;
import org.beh.jbc.Stage;

import java.awt.BorderLayout;
import java.awt.Window.Type;

public class BattleCityApp implements Runnable {
	public static final int FPS = 60;
	public static final int MsPF = 1000/FPS;

	private JFrame frmJustBattleCity;
	private BattleCityPanel panel;
	private Stage stage;
	private BattleCity game;

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
		game = new BattleCity(BattleCity.PLAYER_MODE_BOTH);
		stage = game.getCurStage();
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
		frmJustBattleCity.setBounds(100, 100, 256+17, 240+42);
		frmJustBattleCity.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmJustBattleCity.setJMenuBar(menuBar);
		
		panel = new BattleCityPanel(stage);
		frmJustBattleCity.getContentPane().add(panel, BorderLayout.CENTER);
		
		frmJustBattleCity.addKeyListener(panel);
		frmJustBattleCity.enableInputMethods(false);
	}

	@Override
	public void run() {
		//int frameCounter=0;
		while (true){
			try {
				Thread.sleep(MsPF);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//System.out.println(frameCounter++);
			stage.handle();
			panel.repaint();
			//if (frameCounter>500) break;
		}
	}

}
