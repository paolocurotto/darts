package main;
import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.ThreadLocalRandom;

import Neural.Brain;

public class GreenDart extends Dart {
	
	public int time_alive;
	public int id=1;
	public Brain brain;
	
	public Dart[] enemies;
	
	public GreenDart() {
		
	}
	public GreenDart(int x_pos, int y_pos, int angle_c) {
		super(x_pos, y_pos, angle_c, Color.green.darker());
		color = Color.green.darker();
		time_alive = 0;
	}
	
	@Override
	public void draw(int h, int w, Graphics g) {
		super.draw(h, w, g);

		if(collided == false) {
			time_alive++;	
			brain.calcOutput();
		}
	}
	
	@Override
	public void reset() {
		super.reset();
		time_alive = 0;
	}

	@Override
	public void newTurn () {
		//super.newTurn();
		turn = brain.newAngle();
	}
	
	public int getTime() {
		return time_alive;
	}
	
	public void setEnemies(Dart[] darts) {
		enemies = darts;
		brain = new Brain(this);
	}
	
	public void setBrain(Brain brain_) {
		brain = brain_;
		brain.dart = this;
	}
	
}