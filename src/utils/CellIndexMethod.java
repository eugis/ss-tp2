package utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Particle;
import model.Point;

public class CellIndexMethod {

	Set<Particle>[][] matrix;
	private double cellLength;
	private List<Particle> particles;
	private boolean periodicBounds;
	private double rc;
	private double l;

	public CellIndexMethod(List<Particle> particles, double l, int m, double rc, boolean periodicBounds) {
		cellLength = l / m;
		this.l = l;
		this.periodicBounds = periodicBounds;
		this.rc = rc;
		this.particles = particles;
		if (!validProperties(particles, l, m, rc)) {
			throw new IllegalArgumentException();
		}
		fillMatrix(particles, m);
	}

	public Set<Particle>[][] getMatrix() {
		return matrix;
	}

	public boolean isPeriodicBounds() {
		return periodicBounds;
	}

	public double getRc() {
		return rc;
	}

	public double getL() {
		return l;
	}

	private Map<Particle, Set<Particle>> getNeighbours() {
		Map<Particle, Set<Particle>> neighbours = new HashMap<Particle, Set<Particle>>();
		for (Particle p : particles) {
			neighbours.put(p, new HashSet<Particle>());
		}
		int m = matrix.length;
		for (int x = 0; x < m; x++) {
			for (int y = 0; y < m; y++) {
				Set<Particle> particlesInCell = matrix[x][y];
				for (Particle p : particlesInCell) {
					int[] dx = { 0, 0, 1, 1, 1 };
					int[] dy = { 0, 1, 1, 0, -1 };
					for (int k = 0; k < 5; k++) {
						int xx = x + dx[k];
						int yy = y + dy[k];
						if (!periodicBounds) {
							if (xx >= m || yy < 0 || yy >= m) {
								continue;
							}
						}
						xx = (xx + m) % m;
						yy = (yy + m) % m;
						for (Particle q : matrix[xx][yy]) {
							addNeighbour(p, q, neighbours);
						}
					}
				}
			}
		}
		return neighbours;

	}

	private void addNeighbour(Particle p, Particle q, Map<Particle, Set<Particle>> neighbours) {
		if (p.equals(q)) {
			return;
		}

		Point pp = p.getPosition().clone();
		Point qq = q.getPosition().clone();

		if (periodicBounds) {

			if (pp.x < qq.x && pp.x + 2 * cellLength < qq.x) {
				pp.x += l;
			}

			if (qq.x < pp.x && qq.x + 2 * cellLength < pp.x) {
				qq.x += l;
			}

			if (pp.y < qq.y && pp.y + 2 * cellLength < qq.y) {
				pp.y += l;
			}

			if (qq.y < pp.y && qq.y + 2 * cellLength < pp.y) {
				qq.y += l;
			}
		}

		double dist2 = Point.dist2(pp, qq);
		double r = rc + p.getRadius() + q.getRadius();

		if (dist2 < r * r) {
			neighbours.get(p).add(q);
			neighbours.get(q).add(p);
		}
	}

	@SuppressWarnings("unchecked")
	private void fillMatrix(List<Particle> particles, int m) {
		matrix = new Set[m][m];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				matrix[i][j] = new HashSet<Particle>();
			}
		}
		for (Particle particle : particles) {
			Point p = particle.getPosition();
			matrix[(int) (p.x / cellLength)][(int) (p.y / cellLength)].add(particle);
		}
	}

	private boolean validProperties(List<Particle> particles, double l, int m, double rc) {
		double max1 = 0;
		double max2 = 0;
		for (Particle p : particles) {
			if (p.getRadius() >= max1) {
				max2 = max1;
				max1 = p.getRadius();
			} else {
				if (p.getRadius() >= max2) {
					max2 = p.getRadius();
				}
			}
		}
		return (cellLength > rc + max1 + max2);
	}
	
	/**
	 * 
	 * @param particle - particle before move to new position
	 * @param x - new x coordinate
	 * @param y - new y coordinate
	 */
	public void moveParticle(Particle particle, int x, int y){
		Point p = particle.getPosition();
		int prevX = (int) (p.x / cellLength);
		int prevY = (int) (p.y / cellLength);
		int postX = (int) (x / cellLength);
		int postY = (int) (y / cellLength);
		if(prevX != postX || prevY != postY){
			matrix[prevX][prevY].remove(particle);
			matrix[postX][postY].add(particle);
		}
	}
}
