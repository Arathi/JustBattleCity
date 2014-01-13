package org.beh.jbc;

public class CityMap {
	//TODO 这个类以后要变为抽象类，支持几种不同格式的外部map
	
	public static final byte LF_BRICK_R = 0x00;
	public static final byte LF_BRICK_B = 0x01;
	public static final byte LF_BRICK_L = 0x02;
	public static final byte LF_BRICK_T = 0x03;
	public static final byte LF_BRICK_FULL = 0x04;
	
	public static final byte LF_IRON_R = 0x05;
	public static final byte LF_IRON_B = 0x06;
	public static final byte LF_IRON_L = 0x07;
	public static final byte LF_IRON_T = 0x08;
	public static final byte LF_IRON_FULL = 0x09;
	
	public static final byte LF_WATER = 0x0A;
	public static final byte LF_GRESS = 0x0B;
	public static final byte LF_SNOW = 0x0C;
	
	public static final byte LF_NONE = 0x0D;
	
	public static final byte TILE_NONE = 0x00;
	public static final byte TILE_BRICK = 0x10;    //00010000
	public static final byte TILE_BRICK_R = 0x14;  //00010100
	public static final byte TILE_BRICK_B = 0x15;  //00010101
	public static final byte TILE_BRICK_L = 0x16;  //00010110
	public static final byte TILE_BRICK_T = 0x17;  //00010111
	public static final byte TILE_BRICK_TR = 0x18; //00011000
	public static final byte TILE_BRICK_BR = 0x19; //00011001
	public static final byte TILE_BRICK_BL = 0x1A; //00011010
	public static final byte TILE_BRICK_TL = 0x1B; //00011011
	public static final byte TILE_IRON = 0x20;
	public static final byte TILE_WATER0 = 0x30;
	public static final byte TILE_WATER1 = 0x31;
	public static final byte TILE_GRESS = 0x40;
	public static final byte TILE_SNOW = 0x50;
	
	protected int version; //地图版本
	protected byte[] mapdata;
	protected int xSize;
	protected int ySize;
	
	public CityMap(){
		init();
	}
	
	public void init(){
		xSize = 13;
		ySize = 13;
		mapdata = new byte[xSize*ySize];
		//载入默认数据
		loadFromFile(null);
	}
	
	public void loadFromFile(String path){
		if (path==null || path.trim().equals("")){
			/* 出自 Battle City  (J).nes
			   起始偏移: 0000308A, 尾部偏移: 000030E4, 长度: 0000005B */
			byte rawData[] = {
				(byte)0xDD, (byte)0xDD, (byte)0xDD, (byte)0xDD, (byte)0xDD, (byte)0xDD,
				(byte)0xDD, (byte)0xD4, (byte)0xD4, (byte)0xD4, (byte)0xD4, (byte)0xD4,
				(byte)0xD4, (byte)0xDD, (byte)0xD4, (byte)0xD4, (byte)0xD4, (byte)0xD4,
				(byte)0xD4, (byte)0xD4, (byte)0xDD, (byte)0xD4, (byte)0xD4, (byte)0xD4,
				(byte)0x94, (byte)0xD4, (byte)0xD4, (byte)0xDD, (byte)0xD4, (byte)0xD4,
				(byte)0xD3, (byte)0xD3, (byte)0xD4, (byte)0xD4, (byte)0xDD, (byte)0xD3,
				(byte)0xD3, (byte)0xD1, (byte)0xD1, (byte)0xD3, (byte)0xD3, (byte)0xDD,
				(byte)0x1D, (byte)0x11, (byte)0xD3, (byte)0xD3, (byte)0xD1, (byte)0x1D,
				(byte)0x1D, (byte)0x8D, (byte)0x33, (byte)0xD1, (byte)0xD1, (byte)0xD3,
				(byte)0x3D, (byte)0x8D, (byte)0xD1, (byte)0xD1, (byte)0xD4, (byte)0x44,
				(byte)0xD1, (byte)0xD1, (byte)0xDD, (byte)0xD4, (byte)0xD4, (byte)0xD4,
				(byte)0xD4, (byte)0xD4, (byte)0xD4, (byte)0xDD, (byte)0xD4, (byte)0xD4,
				(byte)0xD3, (byte)0xD3, (byte)0xD4, (byte)0xD4, (byte)0xDD, (byte)0xD4,
				(byte)0xD4, (byte)0xDD, (byte)0xDD, (byte)0xD4, (byte)0xD4, (byte)0xDD,
				(byte)0xDD, (byte)0xDD, (byte)0xDD, (byte)0xDD, (byte)0xDD, (byte)0xDD,
				(byte)0xDD
			};
			int x, y, hx, rawIndex, mapIndex;
			byte data;
			for (y=0;y<13;y++){
				for (hx=0; hx<7; hx++){
					rawIndex = hx+y*7;
					data = rawData[rawIndex];
					x = hx*2;
					mapIndex = x+y*13;
					mapdata[mapIndex] = (byte) ((data>>4) & 0x0F);
					if (hx!=6){
						x = hx*2+1;
						mapIndex = x+y*13;
						mapdata[mapIndex] = (byte) (data & 0x0F);
					}
				}
			}
			
		}
	}
	
	public byte[][] toArray2D(){
		byte[][] result = new byte[Stage.STAGE_SIZE_X][Stage.STAGE_SIZE_Y];
		int x, y;
		if (mapdata.length==13*13){
			for (y=0; y<13; y++){
				for (x=0; x<13; x++){
					int mapIndex = x+y*13;
					int tlX, trX, blX, brX, tlY, trY, blY, brY;
					tlX = x*2;tlY = y*2; trX = x*2+1;trY = y*2;
					blX = x*2;blY = y*2+1; brX = x*2+1;brY = y*2+1;
					byte landform = mapdata[mapIndex];
					switch (landform){
					case LF_BRICK_T:
						result[tlX][tlY] = TILE_BRICK;
						result[trX][trY] = TILE_BRICK;
						result[blX][blY] = TILE_NONE;
						result[brX][brY] = TILE_NONE;
						break;
					case LF_BRICK_R:
						result[tlX][tlY] = TILE_NONE;
						result[trX][trY] = TILE_BRICK;
						result[blX][blY] = TILE_NONE;
						result[brX][brY] = TILE_BRICK;
						break;
					case LF_BRICK_B:
						result[tlX][tlY] = TILE_NONE;
						result[trX][trY] = TILE_NONE;
						result[blX][blY] = TILE_BRICK;
						result[brX][brY] = TILE_BRICK;
						break;
					case LF_BRICK_L:
						result[tlX][tlY] = TILE_BRICK;
						result[trX][trY] = TILE_NONE;
						result[blX][blY] = TILE_BRICK;
						result[brX][brY] = TILE_NONE;
						break;
					case LF_BRICK_FULL:
						result[tlX][tlY] = TILE_BRICK;
						result[trX][trY] = TILE_BRICK;
						result[blX][blY] = TILE_BRICK;
						result[brX][brY] = TILE_BRICK;
						break;
					case LF_IRON_T:
						result[tlX][tlY] = TILE_IRON;
						result[trX][trY] = TILE_IRON;
						result[blX][blY] = TILE_NONE;
						result[brX][brY] = TILE_NONE;
						break;
					case LF_IRON_R:
						result[tlX][tlY] = TILE_NONE;
						result[trX][trY] = TILE_IRON;
						result[blX][blY] = TILE_NONE;
						result[brX][brY] = TILE_IRON;
						break;
					case LF_IRON_B:
						result[tlX][tlY] = TILE_NONE;
						result[trX][trY] = TILE_NONE;
						result[blX][blY] = TILE_IRON;
						result[brX][brY] = TILE_IRON;
						break;
					case LF_IRON_L:
						result[tlX][tlY] = TILE_IRON;
						result[trX][trY] = TILE_NONE;
						result[blX][blY] = TILE_IRON;
						result[brX][brY] = TILE_NONE;
						break;
					case LF_IRON_FULL:
						result[tlX][tlY] = TILE_IRON;
						result[trX][trY] = TILE_IRON;
						result[blX][blY] = TILE_IRON;
						result[brX][brY] = TILE_IRON;
						break;
					case LF_WATER:
						result[tlX][tlY] = TILE_WATER0;
						result[trX][trY] = TILE_WATER1;
						result[blX][blY] = TILE_WATER1;
						result[brX][brY] = TILE_WATER0;
						break;
					case LF_GRESS:
						result[tlX][tlY] = TILE_GRESS;
						result[trX][trY] = TILE_GRESS;
						result[blX][blY] = TILE_GRESS;
						result[brX][brY] = TILE_GRESS;
						break;
					case LF_SNOW:
						result[tlX][tlY] = TILE_SNOW;
						result[trX][trY] = TILE_SNOW;
						result[blX][blY] = TILE_SNOW;
						result[brX][brY] = TILE_SNOW;
						break;
					case 0xD:
					default:
						result[tlX][tlY] = TILE_NONE;
						result[trX][trY] = TILE_NONE;
						result[blX][blY] = TILE_NONE;
						result[brX][brY] = TILE_NONE;
					}
				}
			}
			return result;
		}
		return null;
	}
	
	//转换为26*26个byte的形式
	@Deprecated
	public byte[] toArray() {
		// TODO Auto-generated method stub
		int x, y;
		byte[] result = new byte[26*26];
		if (mapdata.length==13*13){
			for (y=0; y<13; y++){
				for (x=0; x<13; x++){
					int mapIndex = x+y*13;
					int tlX, trX, blX, brX, tlY, trY, blY, brY;
					tlX = x*2;tlY = y*2; trX = x*2+1;trY = y*2;
					blX = x*2;blY = y*2+1; brX = x*2+1;brY = y*2+1; 
					result[tlX + tlY*26] = mapdata[mapIndex];
					result[trX + trY*26] = mapdata[mapIndex];
					result[blX + blY*26] = mapdata[mapIndex];
					result[brX + brY*26] = mapdata[mapIndex];
				}
			}
			return result;
		}
		else if (mapdata.length==26*26){
			for (x=0; x<26*26; x++){
				result[x] = mapdata[x];
			}
			return result;
		}
		return null;
	}
	
}
