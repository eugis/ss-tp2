package run;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Bird;
import model.Point;
import utils.CellIndexMethod;
import utils.RandomGenerator;
import utils.XYZFilesGenerator;

public class FlockRun {

	public static void main(String[] args) {

		//a: new FlockRun(300, 7.0, 1.0, true, 0.03, 2.0, 1234, true); 
		//b: new FlockRun(300, 25.0, 1.0, true, 0.03, 0.1, 1234, true);
		//d: new FlockRun(300, 5.0, 1.0, true, 0.03, 0.1, 1234, true);
		//new FlockRun(300, 25.0, 1.0, true, 0.03, 2.0, 1234, true);
		//new FlockRun(300, 25.0, 1.0, true, 0.03, 1.0, 1234, false);
		//new FlockRun(100, 25.0, 2.0, true, 0.03, 0.01, 1234, true);
		//new FlockRun(300,25.0,1.0, true, 0.03, 2.0, 8, true);
		for(int i=0; i<50; i++){
			new FlockRun(18*9, 18.0, 1.0, true, 0.03, 0.1, i, 2000, i==0);
		}
	}
	
	private CellIndexMethod<Bird> cim;
	private List<Bird> birds;
	private double velocityOfBirds;
	private double L;
	private boolean printOutput;
	private int time;
	
	public FlockRun(int n, 
			double L,
			double rc,
			boolean periodicBounds,
			double velocityOfBirds,
			double pertubation,
			int randomSeed,
			int time,
			boolean printOutput) {
		this.L=L;
		this.printOutput = printOutput;
		this.velocityOfBirds = velocityOfBirds;
		this.time=time;
		RandomGenerator.setSeed(randomSeed);
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
		double va = 0.0;
		for(int t=0; t<time; t++){
			if(time-t<100){
				va += getVA();
			}
			if(printOutput){
				printOutput(t);	
			}
			Map<Bird, Set<Bird>> neighbours = cim.getNeighbours();
			for (Bird bird : birds) {
				bird.calculateFutureVariables(neighbours.get(bird));
			}
			for (Bird bird : birds) {
				bird.updateVariables();
				bird.modularize(L);
			}
		}
		System.out.println(String.valueOf(va/100).replace('.', ','));
	}
	
	
	private double getVA(){
		Point sum = new Point(0,0);
		for(Bird bird : birds){
			sum.add(bird.getVelocityX(), bird.getVelocityY());
		}
		return sum.abs() / (birds.size()*velocityOfBirds);
	}
	
	private void printOutput(int time){
		XYZFilesGenerator.showNeighbours("./flockAnimation/flock"+time, birds);
	}
	
	
	
}
