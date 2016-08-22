package model;

public class Particle {
	
	private int id;
	private Point position;
	private double radius;
	
	public Particle(int id, double x, double y, double r) {
		this.id = id;
		this.position = new Point(x, y);
		this.radius = r;
	}

	public int getId() {
		return id;
	}

	public Point getPosition() {
		return position;
	}
	
	public double getRadius() {
		return radius;
	}
	
	

}
