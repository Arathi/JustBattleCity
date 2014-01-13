package org.beh.jbc.test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.beh.jbc.CityMap;
import org.beh.jbc.IVisualStage;
import org.beh.jbc.Stage;
import org.beh.jbc.Tank;

public class BattleCityPanel extends JPanel implements IVisualStage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 122396563446556939L;
	
	public static final int STAGE_AREA_X = 2;
	public static final int STAGE_AREA_Y = 2;
	public static final int TILE_SIZE = 8;
	
	private Stage stage;
	private BufferedImage imageAll;
	private Color[] fgColors;
	private Color bgcNonStage;
	private Color bgcStage;
	private Image[][][][] tanks; //类型(type)，颜色(color)，方向(aspect)，帧(frame)
	private Image[] landform;
	private Image[] item[];
	private Image[] animaBorn;
	private Image[] animaDie;
	
	/**
	 * Create the panel.
	 */
	public BattleCityPanel() {
		init();
	}
	public BattleCityPanel(Stage stage){
		init();
		setStage(stage);
	}
	
	public void init(){
		fgColors = new Color[0x80];
		fgColors[CityMap.TILE_BRICK]=Color.RED;
		fgColors[CityMap.TILE_BRICK_R]=Color.RED;
		fgColors[CityMap.TILE_BRICK_B]=Color.RED;
		fgColors[CityMap.TILE_BRICK_L]=Color.RED;
		fgColors[CityMap.TILE_BRICK_T]=Color.RED;
		fgColors[CityMap.TILE_BRICK_TR]=Color.RED;
		fgColors[CityMap.TILE_BRICK_BR]=Color.RED;
		fgColors[CityMap.TILE_BRICK_BL]=Color.RED;
		fgColors[CityMap.TILE_BRICK_TL]=Color.RED;
		fgColors[CityMap.TILE_IRON]=Color.WHITE;
		fgColors[CityMap.TILE_WATER0]=Color.BLUE;
		fgColors[CityMap.TILE_WATER1]=Color.BLUE;
		fgColors[CityMap.TILE_GRESS]=Color.GREEN;
		fgColors[CityMap.TILE_SNOW]=Color.GRAY;
		
		bgcStage = Color.BLACK;
		bgcNonStage = new Color(133, 135, 132);
		
		try {
			imageAll=ImageIO.read( ClassLoader.getSystemResourceAsStream("battlecity.png") );
			//分离出坦克贴图
			tanks = new Image[4][7][4][2];
			int type, aspect, frame, power, x, y;
			for (y=0; y<8; y++){
				frame = y%2;
				power = y/2;
				for (x=0; x<4*7; x++){
					aspect = x%4;
					type = x/7;
					tanks[type][power][aspect][frame] = imageAll.getSubimage(16*x,16*y,16,16);
				}
			}
			//分离出字库
			
			//分离出地表贴图
			landform = new Image[0x80];
			landform[CityMap.TILE_BRICK]=imageAll.getSubimage(448, 0, 8, 8);
			landform[CityMap.TILE_IRON]=imageAll.getSubimage(448, 8, 8, 8);
			landform[CityMap.TILE_WATER0]=imageAll.getSubimage(456, 0, 8, 8);
			landform[CityMap.TILE_WATER1]=imageAll.getSubimage(456, 8, 8, 8);
			landform[CityMap.TILE_GRESS]=imageAll.getSubimage(464, 0, 8, 8);
			landform[CityMap.TILE_SNOW]=imageAll.getSubimage(464, 8, 8, 8);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setStage(Stage stage) {
		this.stage=stage;
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		if (stage==null) return;
		int x, y, drawX, drawY;
		g.setColor(bgcNonStage);
		g.fillRect(0, 0, 32*TILE_SIZE, 30*TILE_SIZE);
		g.setColor(bgcStage);
		g.fillRect(STAGE_AREA_X*TILE_SIZE, STAGE_AREA_Y*TILE_SIZE, 26*TILE_SIZE, 26*TILE_SIZE);
		for (y=0; y<Stage.STAGE_SIZE_Y; y++){
			for (x=0; x<Stage.STAGE_SIZE_X; x++){
				drawX=x*8;
				drawY=y*8;
				byte type = stage.getTile(x, y);
				if (type==0) continue;
				if (landform!=null && landform[type]!=null) g.drawImage(landform[type], STAGE_AREA_X*TILE_SIZE+drawX, STAGE_AREA_Y*TILE_SIZE+drawY, this);
				else{
					g.setColor(fgColors[type]);
					g.fillRect(STAGE_AREA_X*TILE_SIZE+drawX, STAGE_AREA_Y*TILE_SIZE+drawY, 8, 8);
				}
			}
		}
		List<Tank> aliveTanks = stage.getAliveTanks();
		for (Tank tank : aliveTanks){
			g.drawImage(tanks[tank.getType()][tank.getColor()][tank.getAspect()][0], STAGE_AREA_X*TILE_SIZE+tank.getTileX()*TILE_SIZE, STAGE_AREA_X*TILE_SIZE+tank.getTileY()*TILE_SIZE, this);
		}
	}

}
