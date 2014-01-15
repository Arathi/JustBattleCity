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
import org.beh.jbc.Sprite;
import org.beh.jbc.Stage;
import org.beh.jbc.Tank;

public class BattleCityPanel extends JPanel implements IVisualStage {
	private static final long serialVersionUID = 122396563446556939L;
	
	public static final int STAGE_AREA_X = 2;
	public static final int STAGE_AREA_Y = 2;
	public static final int TILE_SIZE = 8;

	public static final int COLOR_YELLOW = 0;
	public static final int COLOR_GREEN = 1;
	public static final int COLOR_RED = 2;
	
	private Stage stage;
	private BufferedImage imageAll;
	private Color[] fgColors;
	private Color bgcNonStage;
	private Color bgcStage;
	private Image[][][][] tanks; //类型(type)，颜色(color)，方向(aspect)，帧(frame)
	private Image base; //老鹰基地
	private Image baseDestroyed; //老鹰基地损坏
	private Image[] landform;
	private Image[] imgItem;
	private Image[] animaBorn;
	private Image[] animaDie;
	private Image[] imgBoat;
	
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
			int type, aspect, frame, color, x, y;
			for (y=0; y<8; y++){
				frame = y%2;
				type = y/2;
				for (x=0; x<4*7; x++){
					aspect = x%4;
					color = x/4;
					tanks[type][color][aspect][frame] = imageAll.getSubimage(16*x,16*y,16,16);
				}
			}
			imgBoat = new Image[2];
			imgBoat[0] = imageAll.getSubimage(480, 16, 16, 16);
			imgBoat[1] = imageAll.getSubimage(496, 16, 16, 16);
			//载入物品Tile
			imgItem = new Image[8];
			for (x=0; x<8; x++){
				imgItem[x] = imageAll.getSubimage(128+x*16, 128, 16, 16);
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
			landform[CityMap.TILE_BRICK_T]=imageAll.getSubimage(480, 0, 8, 8);
			landform[CityMap.TILE_BRICK_R]=imageAll.getSubimage(488, 0, 8, 8);
			landform[CityMap.TILE_BRICK_L]=imageAll.getSubimage(480, 8, 8, 8);
			landform[CityMap.TILE_BRICK_B]=imageAll.getSubimage(488, 8, 8, 8);
			landform[CityMap.TILE_BRICK_TL]=imageAll.getSubimage(496, 0, 8, 8);
			landform[CityMap.TILE_BRICK_TR]=imageAll.getSubimage(504, 0, 8, 8);
			landform[CityMap.TILE_BRICK_BL]=imageAll.getSubimage(496, 8, 8, 8);
			landform[CityMap.TILE_BRICK_BR]=imageAll.getSubimage(504, 8, 8, 8);
			//分离出基地贴图
			base = imageAll.getSubimage(448, 16, 16, 16);
			baseDestroyed = imageAll.getSubimage(464, 16, 16, 16);
			//载入出生死亡动画
			animaBorn = new Image[4];
			for (y=0;y<4;y++){
				//TODO 临时位置
				animaBorn[y]=imageAll.getSubimage(192, 192+y*16, 16, 16);
			}
			animaDie = new Image[3];
			for (x=0;x<3;x++){
				//TODO 临时位置
				animaDie[x]=imageAll.getSubimage(192+x*16, 176, 16, 16);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setStage(Stage stage) {
		this.stage=stage;
		stage.registerUI(this);
	}
	
	public Image getTankImage(Tank tank){
		int status = tank.getStatus();
		if (status==Tank.STATUS_BORN){
			int frame=tank.getFrame();
			int bornFrameId=0;
			switch (frame){
			case 0:
			case 6:
				bornFrameId=0;
				break;
			case 1:
			case 5:
			case 7:
				bornFrameId=1;
				break;
			case 2:
			case 4:
			case 8:
				bornFrameId=2;
				break;
			case 3:
			case 9:
				bornFrameId=3;
				break;
			default:
				return tanks[tank.getType()][tank.getColor()][tank.getAspect()][0];
			}
			return animaBorn[bornFrameId];
		}
		else if (status==Tank.STATUS_READY){
			return tanks[tank.getType()][tank.getColor()][tank.getAspect()][0];
		}
		else if (status==Tank.STATUS_MOVING){
			int frameId=tank.getFrame();
			int frame = frameId%2;
			return tanks[tank.getType()][tank.getColor()][tank.getAspect()][frame];
		}
		else if (status==Tank.STATUS_DYING){
			int frame=tank.getFrame();
			return animaDie[frame];
		}
		else if (status==Tank.STATUS_DEAD) {
			//死掉了，就不显示了
			return null;
		}
		return tanks[tank.getType()][tank.getColor()][tank.getAspect()][0];
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
		//绘制
		for (y=0; y<Stage.STAGE_SIZE_Y; y++){
			for (x=0; x<Stage.STAGE_SIZE_X; x++){
				drawX=x*8;
				drawY=y*8;
				byte type = stage.getTile(x, y);
				if (type==CityMap.TILE_NONE || type==CityMap.TILE_GRESS) continue;
				if (landform!=null && landform[type]!=null) g.drawImage(landform[type], STAGE_AREA_X*TILE_SIZE+drawX, STAGE_AREA_Y*TILE_SIZE+drawY, this);
				else{
					g.setColor(fgColors[type]);
					g.fillRect(STAGE_AREA_X*TILE_SIZE+drawX, STAGE_AREA_Y*TILE_SIZE+drawY, 8, 8);
				}
			}
		}
		//绘制基地
		if (stage.isBaseAlive()){
			g.drawImage(base, STAGE_AREA_X*TILE_SIZE+12*TILE_SIZE, STAGE_AREA_Y*TILE_SIZE+24*TILE_SIZE, this);
		}
		else{
			g.drawImage(baseDestroyed, STAGE_AREA_X*TILE_SIZE+12*TILE_SIZE, STAGE_AREA_Y*TILE_SIZE+24*TILE_SIZE, this);
		}
		//绘制坦克
		List<Tank> aliveTanks = stage.getAliveTanks();
		for (Tank tank : aliveTanks){
			Image tankImage = getTankImage(tank);
			if (tankImage != null) {
				int pid = tank.getColor();
				int  distanceX = Math.abs(tank.getNextX()-tank.getTileX()) * TILE_SIZE,
					 distanceY = Math.abs(tank.getNextY()-tank.getTileY()) * TILE_SIZE;
				int offsetX=0, offsetY=0;
				if (tank.getStatus()==Tank.STATUS_MOVING && ( distanceX!=0 || distanceY!=0 ) ){
					switch (tank.getAspect()){
					case Sprite.ASPECT_UP:
						offsetY = -1*tank.getFrame();
						break;
					case Sprite.ASPECT_RIGHT:
						offsetX = +1*tank.getFrame();
						break;
					case Sprite.ASPECT_DOWN:
						offsetY = +1*tank.getFrame();
						break;
					case Sprite.ASPECT_LEFT:
						offsetX = -1*tank.getFrame();
						break;
					}
				}
				if (tank.hasBoat() && (pid==0 || pid==1) ){
					g.drawImage(
						imgBoat[pid], 
						STAGE_AREA_X*TILE_SIZE+tank.getTileX()*TILE_SIZE+offsetX, 
						STAGE_AREA_X*TILE_SIZE+tank.getTileY()*TILE_SIZE+offsetY, 
						this
					);
				}
				g.drawImage(
					tankImage, 
					STAGE_AREA_X*TILE_SIZE+tank.getTileX()*TILE_SIZE+offsetX, 
					STAGE_AREA_X*TILE_SIZE+tank.getTileY()*TILE_SIZE+offsetY, 
					this
				);
			}
		}
		//绘制草丛（覆盖坦克层）
		for (y=0; y<Stage.STAGE_SIZE_Y; y++){
			for (x=0; x<Stage.STAGE_SIZE_X; x++){
				drawX=x*8;
				drawY=y*8;
				byte type = stage.getTile(x, y);
				if (type!=CityMap.TILE_GRESS) continue;
				if (landform!=null && landform[type]!=null) g.drawImage(landform[type], STAGE_AREA_X*TILE_SIZE+drawX, STAGE_AREA_Y*TILE_SIZE+drawY, this);
				else{
					g.setColor(fgColors[type]);
					g.fillRect(STAGE_AREA_X*TILE_SIZE+drawX, STAGE_AREA_Y*TILE_SIZE+drawY, 8, 8);
				}
			}
		}
	}

}
