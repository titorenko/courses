package week3;

import java.util.Comparator;

import common.StdDraw;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
		@Override
		public int compare(Point p1, Point p2) {
			double s1 = slopeTo(p1);
			double s2 = slopeTo(p2);
			if (isSameSlope(s1, s2)) return 0;
			return (int) Math.signum(s1 - s2);
		}
		
		private boolean isSameSlope(double s1, double s2) {
			return Math.abs(s1 - s2) < 1E-10;
		}
	};

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
    	if (x == that.x && y == that.y) return Double.NEGATIVE_INFINITY;
    	if (y == that.y) return 0;
    	if (x == that.x) return Double.POSITIVE_INFINITY;
    	return (double) (that.y - y) / (double) (that.x - x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        int signum = Integer.signum(y - that.y);
    	return signum == 0 ? Integer.signum(x - that.x) : signum;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}