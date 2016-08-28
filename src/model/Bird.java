package model;

import java.util.Set;

import utils.RandomGenerator;

public class Bird extends Particle {

	private double velocity;
	private double angle;
	private Point futurePosition;
	private double futureAngle;
	private final double theta;

	public Bird(int id, double x, double y, double velocity, double angle,
			double theta) {
		super(id, x, y, 0);
		this.velocity = velocity;
		this.angle = angle;
		this.theta = theta;
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
	
	public double getVelocity() {
		return velocity;
	}

	public void calculateFutureVariables(Set<Bird> neighbourBirds) {
		calculateFutureAngle(neighbourBirds);
		calculateFuturePosition();
	}

	private void calculateFutureAngle(Set<Bird> neighbourBirds) {
		neighbourBirds.add(this);
		Double x = neighbourBirds.stream()
				.mapToDouble(bird -> Math.cos(bird.getAngle())).average()
				.getAsDouble();
		
		Double y = neighbourBirds.stream()
				.mapToDouble(bird -> Math.sin(bird.getAngle())).average()
				.getAsDouble();
		futureAngle = Math.atan2(y, x) + getRandomAngleModifier();
	}

	/**
	 * @return a value between -theta/2 and theta/2.
	 */
	private double getRandomAngleModifier() {
		return RandomGenerator.getDouble(-theta/2, theta/2);
	}

	/**
	 * Needs futureAngle to be calculated before.
	 */
	private void calculateFuturePosition() {
		futurePosition = Point.sum(
				getPosition(),
				new Point(velocity * Math.cos(futureAngle), velocity
						* Math.sin(angle)));
	}

	public void updateVariables() {
		position = futurePosition;
		angle = futureAngle;
	}
	
	public double getVelocityX(){
		return velocity*Math.cos(angle);
	}
	
	public double getVelocityY(){
		return velocity*Math.sin(angle);
	}

}
