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

import model.Particle;

public class XYZFilesGenerator {

	private final static String RED = "1 0 0";
	private final static String GREEN = "0 1 0";
	private final static String BLUE = "0 0 1";
	private final static String SOLID = "0";
	private final static String SEMI_TRANSPARENT = "0.8";

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
		header.add("ParticleId xCoordinate yCoordinate Radius R G B Transparency");
		return header;
	}

	private static void addBasicBody(List<? extends Particle> particles,
			List<String> lines) {
		for (Particle particle : particles) {
			lines.add(getParticleLine(particle, GREEN));
		}
	}

	private static String getParticleLine(Particle p, String color) {
		return p.getId() + " " + p.getPosition().x + " " + p.getPosition().y
				+ " 0.1 " + color + " " + SOLID;
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
