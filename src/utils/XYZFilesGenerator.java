package utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import model.Bird;
import model.Particle;

public class XYZFilesGenerator {

	private final static String RED = "1 0 0";
	private final static String GREEN = "0 1 0";
	private final static String BLUE = "0 0 1";
	private final static String SOLID = "0";
	private final static String SEMI_TRANSPARENT = "0.8";
	private final static String TRANSPARENT = "1";

	public static void showNeighbours(String outputPath,
			List<? extends Particle> particles) {
		List<String> header = getNeighboursHeader(particles.size());
		List<String> lines = new ArrayList<>(header);
		addBasicBody(particles, lines);
		writeFile(outputPath + ".xyz", lines);
	}

	private static List<String> getNeighboursHeader(int size) {
		List<String> header = new ArrayList<>();
		header.add(Integer.toString(size));
		header.add("ParticleId xCoordinate yCoordinate xDisplacement yDisplacement Radius R G B Transparency");
		return header;
	}

	private static void addBasicBody(List<? extends Particle> particles,
			List<String> lines) {
		for (Particle particle : particles) {
			lines.add(getParticleLine(particle));
		}
	}

	private static String getColor(Particle particle) {
		Bird bird = (Bird) particle;
		double angle = bird.getAngle();
		double x = (Math.cos(angle) + 1) / 2;
		double y = (Math.sin(angle) + 1) / 2;
		String color = x + " " + y + " 0";
		return color;
	}
	

	private static String getParticleLine(Particle p) {
		return p.getId() + " " + p.getPosition().x + " " + p.getPosition().y + " " + getDisplacement(p)
				+ " 0.1 " + getColor(p) + " " + SOLID;
	}

	private static String getDisplacement(Particle particle) {
		Bird bird = (Bird) particle;
		double xDisplacement = (bird.getVelocity() + 1)*Math.cos(bird.getAngle());
		double yDisplacement = (bird.getVelocity() + 1)*Math.sin(bird.getAngle());
		return xDisplacement + " " + yDisplacement;
	}

	private static void writeFile(String path, List<String> lines) {
		Path file = Paths.get(path);
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
