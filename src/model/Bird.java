package model;

import java.util.List;
import java.util.stream.Stream;

public class Bird extends Particle{
	private double velocity;
	private double angle;
	private Point futurePosition;
	private double futureAngle;
	
	public Bird(int id, double x, double y, double velocity, double angle) {
		super(id, x, y, 0);
		this.velocity = velocity;
		this.angle = angle;
	}
	
	public double getFutureAngle() {
		return futureAngle;
	}
	
	public Point getFuturePosition() {
		return futurePosition;
	}
	
	public double getAngle() {
		return angle;
	}

	public void calculateFutureVariables(List<Bird> neighbourBirds) {
		calculateFuturePosition();
		calculateFutureAngle(neighbourBirds);
	}

	private void calculateFutureAngle(List<Bird> neighbourBirds) {
		Stream<Double> sin = neighbourBirds.stream().map((bird)->Math.sin(bird.getAngle()));
		Stream<Double> cos = neighbourBirds.stream().map((bird)->Math.cos(bird.getAngle()));
		
		
	}

	private void calculateFuturePosition() {
		futurePosition = Point.sum(getPosition(),
				new Point(velocity * Math.cos(angle), velocity * Math.sin(angle)));
	}
	
	
	
}
