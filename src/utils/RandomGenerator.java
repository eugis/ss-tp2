package utils;

import java.util.Random;

import model.Point;

public class RandomGenerator {
	private static Random generator = new Random(0);
	
	public static void setSeed(int seed) {
		generator = new Random(seed);
	}

	public static Point getPoint(double x, double y){
		return new Point(generator.nextDouble()*x, generator.nextDouble()*y);
	}
	
	public static double getDouble(double min, double max){
		return generator.nextDouble()*(max-min) + min;
	}
}
