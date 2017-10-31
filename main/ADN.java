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
			//System.out.println("id = " + parent.id);
			int currFit = parent.fitness.value;
			//System.out.println("dart "+parent.id+ " got fit " + currFit);

			if(currFit > bestFit) {
				bestFit = currFit;
				//System.out.println("id = " + parent.id);

				bestDart = parent;
				//System.out.println("best dart id = " + bestDart.id);

			}
			fit_sum = fit_sum + currFit;
		}
			
		for (int i = 0; i < n_darts; i++) {
			dad = darts_parents[i];
			
			GreenDart son = new GreenDart(dad.starting_x, dad.starting_y, dad.starting_angle);

			son.setEnemies(dad.enemies);
			son.id = i;
			son.setBrain(new Brain(son));
			
			for(int ix1 = 0; ix1 < son.brain.axons1.length; ix1++) {
				
				son.brain.axons1[ix1] = bestDart.brain.axons1[ix1] + ThreadLocalRandom.current().nextDouble(-1 * i, (1 * i) + 0.000001);
			}
			
			for(int ix2 = 0; ix2 < son.brain.axons2.length; ix2++) {
				
				son.brain.axons2[ix2] = bestDart.brain.axons2[ix2] + ThreadLocalRandom.current().nextDouble(-1 * i, (1 * i) + 0.000001);
			}
										
			darts_sons[i] = son;
		}	
	}
}
