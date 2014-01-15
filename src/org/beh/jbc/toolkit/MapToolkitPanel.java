package org.beh.jbc.toolkit;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

public class MapToolkitPanel extends JPanel {
	private static final long serialVersionUID = -1468360970630262501L;

	int x=0, y=0;
	public MapToolkitPanel() {
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int mx = e.getX(), my = e.getY();
				//int x, y;
				x = (mx - 16) / 8;
				y = (my - 16) / 8;
				if (x>=0 && x<27 && y>=0 && y<27 ){
					System.out.println("x="+x+", y="+y);
					repaint();
				}
			}
		});

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		int x=0, y = 0;
		int startX=2*8, startY=2*8;
		
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, 32*8, 30*8);
		g.setColor(Color.BLACK);
		g.fillRect(startX, startY, 26*8, 26*8);
		
		g.setColor(Color.RED);
		for (x=0; x<=26; x++){
			g.drawLine(startX+x*8, startY, startX+x*8, startY+26*8);
			g.drawLine(startX, startY+x*8, startX+26*8, startY+x*8);
		}
		g.setColor(Color.BLUE);
		g.fillRect(startX+this.x*8, startY+this.y*8, 8, 8);
		
		//String str="x:"+x+", y:"+y;
		//g.drawChars(str.toCharArray(), 0, 0, 16, 16);
		
	}
}
