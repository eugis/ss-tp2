package run;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Bird;
import utils.CellIndexMethod;
import utils.RandomGenerator;
import utils.XYZFilesGenerator;

public class FlockRun {

	public static void main(String[] args) {
		//new FlockRun(300, 25.0, 1.0, true, 0.03, 0.1, 1234);
		//new FlockRun(300, 25.0, 1.0, true, 0.03, 2.0, 1234);
		new FlockRun(100, 25.0, 2.0, true, 0.03, 0.01, 1234);
	}
	
	private CellIndexMethod<Bird> cim;
	private List<Bird> birds;
	private double L;
	
	public FlockRun(int n, 
			double L,
			double rc,
			boolean periodicBounds,
			double velocityOfBirds,
			double pertubation,
			int randomSeed) {
		this.L=L;
		birds = new LinkedList<>();
		for (int i = 0; i < n; i++) {
			birds.add(new Bird(i, 
					RandomGenerator.getDouble(0, L),
					RandomGenerator.getDouble(0, L),
					velocityOfBirds,
					RandomGenerator.getDouble(0, 2*Math.PI),
					pertubation));
		}
		int m = (int) Math.floor(L / rc);
		cim = new CellIndexMethod<Bird>(birds, L, m, rc, periodicBounds);
		startSimulation();
	}
	
	private void startSimulation(){
		for(int t=0; t<1000; t++){
			printOutput(t);
			Map<Bird, Set<Bird>> neighbours = cim.getNeighbours();
			for (Bird bird : birds) {
				bird.calculateFutureVariables(neighbours.get(bird));
			}
			for (Bird bird : birds) {
				bird.updateVariables();
				bird.modularize(L);
			}			
		}
	}
	
	private void printOutput(int time){
		// TODO: VICKY HELP!
		XYZFilesGenerator.showNeighbours("./flockAnimation/flock"+time, birds);
	}
	
	
	
}
