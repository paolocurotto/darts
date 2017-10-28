package main;
public class Fitness {
	
	public int value; 

	public Dart dart;

	public Fitness(Dart dart_) {
		dart = dart_;
	}
	
	public void calculateFitness() {
		value = dart.getTime();

	}
	
	

}
