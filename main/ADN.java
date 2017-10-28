package main;
import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

import Neural.Brain;

public class ADN {

	GreenDart[] darts_parents;
	GreenDart[] darts_sons;
	int n_darts;
		
	GreenDart dad;
	GreenDart mom;
	GreenDart bestDart;

	int fit_sum = 0;
	int averg;
	
	public ADN(GreenDart[] parents_darts) {
		darts_parents = parents_darts;
		n_darts = parents_darts.length;
		darts_sons = new GreenDart[darts_parents.length];
	}
	
	public GreenDart[] new_generation_darts(){

		calculateGenes();
		int su = 0;
		for (int i = 0; i < darts_parents.length; i++) {
				su += darts_parents[i].fitness.value;
		}
		averg = su /n_darts;
		System.out.print("Fitness AVG = " + averg + " --- ");		
		return darts_sons;
	}
	
	public void calculateGenes() {
						
		int bestFit = 0; 
		for (GreenDart parent : darts_parents) {
			parent.fitness.calculateFitness();
			int currFit = parent.fitness.value;

			if(currFit > bestFit) {
				bestFit = currFit;
				bestDart = parent;
			}
			fit_sum = fit_sum + currFit;
		}
			
		for (int i = 0; i < darts_sons.length; i++) {
			dad = darts_parents[i % n_darts];
			mom = darts_parents[(i + 1) % n_darts];
			
			GreenDart son = new GreenDart(dad.starting_x, dad.starting_y, dad.starting_angle);
			//Brain newBrain = bestDart.brain;
			//newBrain.dart = son;
			son.setEnemies(dad.enemies);
			son.id = i;
			son.setBrain(new Brain(son));
			//System.out.println("sin.id = " + son.id + "brainssons.id = " + son.brain.dart.id);

			crossing_over1(son, bestDart);
			
			darts_sons[i] = son;
		}	
	}
	
	public void crossing_over1(GreenDart son, GreenDart best) {
		
		double r = ThreadLocalRandom.current().nextDouble(-1, 1);
		
		
		for(int i = 0; i < son.brain.axons1.length; i++) {
		
			son.brain.axons1[i] = best.brain.axons1[i] + r;
		}
		
		for(int i = 0; i < son.brain.axons2.length; i++) {
			
			son.brain.axons2[i] = best.brain.axons2[i] + r;
		}
						
	}
}
