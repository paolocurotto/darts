package Neural;

import java.util.concurrent.ThreadLocalRandom;

import main.Dart;
import main.GreenDart;

public class Brain {
		
	public int n_midldle = 100;
	
	public int[] input;
	public double[] middle = new double[n_midldle];
	public double[] output = new double[1];
	
	public double[] axons1;
	public double[] axons2 = new double[n_midldle];
	
	public GreenDart dart;
	public Dart[] enemies;
	
	public Brain(GreenDart d) {
		
		dart = d;
		enemies = dart.enemies;
		
		input = new int[4 + enemies.length * 3];
		axons1 = new double[input.length * n_midldle];
		double rand = ThreadLocalRandom.current().nextDouble(-1, 1);
		
		//Initialize inputs
		input[0] = ThreadLocalRandom.current().nextInt(-100, 100);;
		updateInputs();
		
		//Initialize input axons
		for (int i = 0; i < axons1.length; i++) {
			axons1[i] = rand;
		}
		
		//Initialize middles
		for (int i = 0; i < middle.length; i++) {
			middle[i] = 0;
		}
		
		//Initialize output axons
		for (int i = 0; i < middle.length; i++) {
				axons2[i] = rand;
				
		}
		
	}
	
	public void updateInputs() {
		//input[0] = 1;
		
		//My position
		input[1] = normPosx(dart.x);
		input[2] = normPosy(dart.y);
		
		//My angle
		input[3] = normAng(dart.angle);
		
		int i = 4;
		for(Dart enemy : enemies) {
			//Enemy position
			input[i] = normPosx(enemy.x);
			input[i + 1] = normPosy(enemy.y);
			
			//Enemy angle
			input[i + 2] = normAng(enemy.angle);
			i += 3;
		}
		
	}
	
	public int newAngle() {
		//System.out.println("Dart n° "+ dart.id +" output = " + output[0]);
		if(output[0] <= 0) {
			return -1;
		} else {
			return 1;
		}		
	}
	
	public void calcMiddle() {
		int k = 0;
		int m_iter = 0;
		for (double m : middle) {
			double sum = 0;
			for (int i : input) {
				sum = sum + i * axons1[k];
				k++;
			}	
			middle[m_iter] = sum;
			m_iter++;
		} 
	}
	
	public void calcOutput() {
		updateInputs();
		calcMiddle();
		
		double sum = 0;
		for (int i = 0; i < middle.length; i++) {
			sum = sum + middle[i] * axons2[i];
		}
		output[0] = sum;
	}
	
	/** x -> [0 - 740]
	 *  y -> [0 - 500]
	 *  angle-> [0 - 360]
	 */
	
	public int normPosx(int x) {
		return (x - 370)/74;
	}
	public int normPosy(int y) {
		return (y - 250)/25;
	}
	public int normAng(int ang) {
		return (ang - 180)/18;
	}


}
