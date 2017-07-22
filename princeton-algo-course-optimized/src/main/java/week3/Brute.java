package week3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import common.In;
import common.StdDraw;
import common.StdOut;

public class Brute {

	private final Point[] points;

	private Brute(Point[] points) {
		this.points = points;
	}

	private List<Point[]> findColinearPoints() {
		List<Point[]> result = new ArrayList<>();
		for (int i = 0; i < points.length; i++) {
			final Point p = points[i];
			for (int j = i + 1; j < points.length; j++) {
				final Point q = points[j];
				double s1 = p.slopeTo(q);
				for (int k = j + 1; k < points.length; k++) {
					final Point r = points[k];
					double s2 = q.slopeTo(r);
					if (!isSameSlope(s1, s2))
						continue;
					for (int l = k + 1; l < points.length; l++) {
						final Point s = points[l];
						double s3 = r.slopeTo(s);
						if (isSameSlope(s2, s3)) {
							result.add(new Point[] { p, q, r, s });
						}
					}
				}
			}
		}
		normalize(result);
		return result;
	}

	static void normalize(List<Point[]> result) {
		for (Point[] points : result) {
			Arrays.sort(points);
		}
		Comparator<Point[]> comparator = new Comparator<Point[]>() {
			@Override
			public int compare(Point[] a1, Point[] a2) {
				for (int i = 0; i < Math.min(a1.length, a2.length); i++) {
					int cmp = a1[i].compareTo(a2[i]);
					if (cmp != 0)
						return cmp;
				}
				return a1.length - a2.length;
			}
		};
		Collections.sort(result, comparator);
		Point[] prev = null;
		for (Iterator<Point[]> iterator = result.iterator(); iterator.hasNext();) {
			Point[] points = iterator.next();
			if (prev != null && comparator.compare(prev, points) == 0) {
				iterator.remove();//TODO: remove points with smaller size
			} else {
				prev = points;
			}
		}
	}

	static boolean isSameSlope(double s1, double s2) {
	    return s1 == s2 || Math.abs(s1 - s2) < 1E-10; 
	}

	public static void main(String[] args) {
		Point[] points = readFile(args[0]);
		Brute brute = new Brute(points);
		List<Point[]> colinear = brute.findColinearPoints();
		printSolitions(colinear);
		drawSolutions(points, colinear);
	}

	static void drawSolutions(Point[] points, List<Point[]> colinear) {
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.show(0);
		for (Point point : points) {
			point.draw();
		}
		for (Point[] sol : colinear) {
			sol[0].drawTo(sol[sol.length - 1]);
		}
		StdDraw.show(0);
	}

	static void printSolitions(List<Point[]> colinear) {
		for (Point[] c : colinear) {
			for (int i = 0; i < c.length; i++) {
				StdOut.print(c[i]);
				if (i != c.length - 1) {
					StdOut.print(" -> ");
				} else {
					StdOut.println();
				}
			}
		}
	}

	static Point[] readFile(String filename) {
		In in = new In(filename);
		int N = in.readInt();
		Point[] points = new Point[N];
		for (int i = 0; i < N; i++) {
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x, y);
		}
		return points;
	}
}