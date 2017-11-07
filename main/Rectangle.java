package main;
import java.awt.Color;
import java.awt.Graphics;

public class Rectangle {
	
	Graphics gr;
	
	int x1, y1, x2, y2;
	
	int[] px = new int[4];
	int[] py = new int[4];
	

	public Rectangle(int x1, int y1, int x2, int y2) {
		
		this.x1 = x1; //   x1,y1__________x2,y1
		this.y1 = y1; //	|				|
		this.x2 = x2; //	|				|
		this.y2 = y2; //   x1,y2__________x2,y2
	
		
		px[0] = x1; py[0] = y1;
		px[1] = x2; py[1] = y1;
		px[2] = x2; py[2] = y2;
		px[3] = x1; py[3] = y2;
		
	
	}
	
	public void draw(int h, int w, Graphics g, Dart[] darts1, Dart[] darts2) {
		Color previousColor = g.getColor();
		g.setColor(Color.black);

		g.fillPolygon(px, py, 4);
		touch(darts1);
		touch(darts2);
		
		g.setColor(previousColor);

	}
	
	public void touch(Dart[] darts) {
		Dart dart;
		for (int i = 0; i < darts.length ; i++) {
			dart = darts[i];
			
			for(int k = 0; k < 3; k++) {
				
				if((x1 < dart.xPoints[k] && dart.xPoints[k] < x2) && (y1 < dart.yPoints[k] && dart.yPoints[k] < y2)) {
					dart.collided();
				}
			}
			
		}
	}
	

}
