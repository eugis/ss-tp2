package utils;

import model.Point;

public class RandomGenerator {

	public static Point getPoint(double x, double y){
		return new Point(Math.random()*x, Math.random()*y);
	}
	
	public static double getDouble(double min, double max){
		return Math.random()*(max-min) + min;
	}
}
