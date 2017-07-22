package week3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fast {
	
	private final Point[] points;

	private Fast(Point[] points) {
		this.points = points;
	}

	private List<Point[]> findColinearPoints() {
		List<Point[]> result = new ArrayList<>();
		for (final Point p : points) {
			Point[] clone = points.clone();
			Arrays.sort(clone, p.SLOPE_ORDER);
			for (int i = 0; i < clone.length; i++) {
				Point[] sol = findColinear(p, clone, i);
				if (sol != null) result.add(sol);
			}
		}
		Brute.normalize(result);
		return result;
	}
	
	private Point[] findColinear(Point p, Point[] points, int i) {
		List<Point> result = new ArrayList<>();
		double slope = p.slopeTo(points[i]);
		for (int j = i+1; j < points.length; j++) {
			double nextSlope = p.slopeTo(points[j]);
			if (Brute.isSameSlope(slope, nextSlope)) {
				result.add(points[j]);
			} else {
				break;
			}
		}
		if (result.size() >= 2) {
			result.add(p);
			result.add(points[i]);
			return result.toArray(new Point[0]);
		}
		return null;
	}

	public static void main(String[] args) {
		Point[] points = Brute.readFile(args[0]);
		Fast fast = new Fast(points);
		List<Point[]> colinear = fast.findColinearPoints();
		Brute.printSolitions(colinear);
		Brute.drawSolutions(points, colinear);
	}
}