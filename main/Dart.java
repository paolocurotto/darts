package main;
import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Dart {

	public Fitness fitness;
	
	public int x;
	public int y;
	public int starting_x;
	public int starting_y;
	public int starting_angle;
	
	public int[] xPoints;
	public int[] yPoints;
	
	public int vel;
	public int angle;
	public int turn = 0 ;
	public int turn_intensity = 2;
	
	public Color color;
	
	public int[][] dir_moves; //(-1 -> move left) (0 -> move straight), (1 -> move right)
							// = {{150, -1},
							// 	  {100, 0},
							// 	  {1000, 1}};
	
	public int i = 0;
	public int k = 0;
	
	public boolean collided;
	public boolean dead;

	public int time_alive;
		
	public Dart() {

	}
	
	public Dart(int x_pos, int y_pos, int angle_c, Color color_c) {
		x = x_pos;
		y = y_pos;
		angle = angle_c;
		starting_x = x_pos;
		starting_y = y_pos;
		starting_angle = angle;
		vel = 2;
		color = color_c;
		xPoints = new int[3];
		yPoints = new int[3];
		collided = false;
		fitness = new Fitness(this);
		dir_moves = new int[200][2];
		time_alive = 0;
		createRandomMoves();


		/**									_
		 * 					/\				|
		 * 				   /  \				|
		 * 				  /    \			|
		 * 				 /		\			|
		 * 				/		 \			30
		 * 			   /		  \			|
		 * 			  /			   \		|
		 * 			 /				\		|
		 * 			/________________\		-
		 * 	
		 * 			|-------20-------|
		 **/

	}
	
	public void draw(int h, int w, Graphics g) {
		Color r = g.getColor();
		g.setColor(color);
		if(collided == true) {
			dead = true;
			g.fillPolygon(xPoints, yPoints, 3);
			g.setColor(Color.black);
			g.drawLine(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
			g.drawLine(xPoints[1], yPoints[1], xPoints[2], yPoints[2]);
			g.drawLine(xPoints[2], yPoints[2], xPoints[0], yPoints[0]);
			g.setColor(r);
			return;
		}
		
		//movement();
		
		angle = angle + turn_intensity * turn;
		x = x + (int) Math.round(vel * Math.cos(Math.toRadians(angle)));
		y = y + (int) Math.round(vel * Math.sin(Math.toRadians(angle)));
		
		if (x > w + 10) {x = -10;}
		if (y > h + 10) {y = -10;}		
		if (x < -10) {x = w + 10;}		
		if (y < -10) {y = h + 10;}
		
		xPoints[0] = x + (int) Math.round(vel * Math.cos(Math.toRadians(angle))) + (int) Math.round(18 * Math.cos(Math.toRadians(146.3099324598 + angle)));
		yPoints[0] = y + (int) Math.round(vel * Math.sin(Math.toRadians(angle))) + (int) Math.round(18 * Math.sin(Math.toRadians(146.3099324598 + angle)));
		
		xPoints[1] = x + (int) Math.round(vel * Math.cos(Math.toRadians(angle))) + (int) Math.round(18 * Math.cos(Math.toRadians(213.6900675402 + angle)));
		yPoints[1] = y + (int) Math.round(vel * Math.sin(Math.toRadians(angle))) + (int) Math.round(18 * Math.sin(Math.toRadians(213.6900675402 + angle)));
		
		xPoints[2] = x + (int) Math.round(vel * Math.cos(Math.toRadians(angle))) + (int) Math.round(15 * Math.cos(Math.toRadians(angle)));
		yPoints[2] = y + (int) Math.round(vel * Math.sin(Math.toRadians(angle))) + (int) Math.round(15 * Math.sin(Math.toRadians(angle)));

		g.fillPolygon(xPoints, yPoints, 3);
		
		g.setColor(Color.black);
		g.drawLine(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
		g.drawLine(xPoints[1], yPoints[1], xPoints[2], yPoints[2]);
		g.drawLine(xPoints[2], yPoints[2], xPoints[0], yPoints[0]);
		
		newTurn();

		g.setColor(r);	
		
	}
	
	public void reset() {
		x = starting_x;
		y = starting_y;
		angle = starting_angle;
		collided = false;
		dead = false;
	}
	
	
	public void movement() {
		turn = dir_moves[k][1];
		if (i == dir_moves[k][0]) {
			turn = dir_moves[k][1];
			i = 0;
			k++;
		}
		i++;
	}

	public void newTurn() {
	    int randomNum = ThreadLocalRandom.current().nextInt(1, 100 + 1);
	    if (randomNum == 10) {turn = -1;}
	    else if (randomNum == 50) {turn = 0;}
	    else if (randomNum == 90) {turn = 1;}
	    
	    //int r = ThreadLocalRandom.current().nextInt(1, 5 + 1);
	    turn_intensity = 2;
	}
	
	public void createRandomMoves() {
		for (int i = 0; i < dir_moves.length; i++) {	
		    int r1 = ThreadLocalRandom.current().nextInt(1, 40 + 1);
		    int r2 = ThreadLocalRandom.current().nextInt(-1, 1 + 1);
			dir_moves[i][0] = r1;
			dir_moves[i][1] = r2 ;
		}
	}
	
	public void collided() {
		collided = true;
	}

	public void checkIfHitAny(Dart[] enemies) {
		if(dead == true) {return;}
		for (Dart enemy : enemies) {
			if (this.hit(enemy) == true) {
				enemy.iWasKilled();
			}
		}
	}

	public void iWasKilled() {
		collided = true;
	}

	public boolean hit(Dart enemy) {
		int p0x = enemy.xPoints[0]; int p0y = enemy.yPoints[0];
		int p1x = enemy.xPoints[2]; int p1y = enemy.yPoints[2];
		int p2x = enemy.xPoints[1]; int p2y = enemy.yPoints[1];
		int px = xPoints[2]; int py = yPoints[2];

		int x3 = p2x;
        int y3 = p2y;
        int y23 = p1y - p2y;
        int x32 = p2x - p1x;
        int y31 = y3 - p0y;
        int x13 = p0x - x3;
        int det = y23 * x13 - x32 * y31;
        int minD = Math.min(det, 0);
        int maxD = Math.max(det, 0);
        
        double dx = px - x3;
        double dy = py - y3;
        double a = y23 * dx + x32 * dy;
        if (a < minD || a > maxD)
            return false;
        double b = y31 * dx + x13 * dy;
        if (b < minD || b > maxD)
            return false;
        double c = det - a - b;
        if (c < minD || c > maxD)
            return false;
        return true;
	}

	public int getTime() {
		return time_alive;
	}	

}
