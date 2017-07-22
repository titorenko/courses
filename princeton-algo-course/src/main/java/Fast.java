import java.util.*;

public class Fast {

    private List<Point[]> findColinearPoints(Point[] points) {
        List<Point[]> result = new ArrayList<>();
        Map<Point, Set<Double>> exploredSlopes = new HashMap<>();
        Arrays.sort(points); //presort to make further sorting faster 
        for (int j = 0; j < points.length; j++) {
            findColinearToTheRight(points, result, j, exploredSlopes);
        }
        normalize(result);
        return result;
    }

    private void findColinearToTheRight(Point[] points, List<Point[]> result, int mainPointIndex, Map<Point, Set<Double>> exploredSlopes) {
        final Point p = points[mainPointIndex];
        final Point[] others = Arrays.copyOfRange(points, mainPointIndex+1, points.length);
        Arrays.sort(others, p.SLOPE_ORDER);
        Set<Double> pExploredSlopes = getExploredSlopes(p, exploredSlopes);
        for (int i = 0; i < others.length; i++) {
            double slope = p.slopeTo(others[i]);
            if (pExploredSlopes.contains(slope)) continue;
            Point[] sol = findColinear(p, others, i, slope);
            if (sol != null) {
                result.add(sol);
                i += sol.length - 2;
                markAsExplored(exploredSlopes, slope, sol);
            }
        }
    }

    private Set<Double> getExploredSlopes(Point p, Map<Point, Set<Double>> exploredSlopes) {
        Set<Double> pExploredSlopes = exploredSlopes.get(p);
        return (pExploredSlopes  == null) ? new HashSet<Double>() : pExploredSlopes;
    }

    private void markAsExplored(Map<Point, Set<Double>> exploredSlopes, double slope, Point[] sol) {
        for (Point point : sol) {
            if (!exploredSlopes.containsKey(point)) 
                exploredSlopes.put(point, new HashSet<Double>());
            exploredSlopes.get(point).add(slope);
        }
    }

    private Point[] findColinear(Point p, Point[] points, int i, double slope) {
        List<Point> result = new ArrayList<>();
        for (int j = i + 1; j < points.length; j++) {
            double nextSlope = p.slopeTo(points[j]);
            if (isSameSlope(slope, nextSlope)) {
                result.add(points[j]);
            } else {
                break;
            }
        }
        if (result.size() >= 2) { //we are interested in 4 or more colinear points
            result.add(p);
            result.add(points[i]);
            return result.toArray(new Point[0]);
        }
        return null;
    }
    
    private void normalize(List<Point[]> result) {
        for (Point[] points : result) {
            Arrays.sort(points);
        }
    }
    
    private boolean isSameSlope(double s1, double s2) {
        return s1 == s2 || Math.abs(s1 - s2) < 1E-10; 
    }
    
    public static void main(String[] args) {
        Point[] points = readFile(args[0]);
        Fast fast = new Fast();
        List<Point[]> colinear = fast.findColinearPoints(points);
        printSolitions(colinear);
        drawSolutions(points, colinear);
    }
    
    private static void drawSolutions(Point[] points, List<Point[]> colinear) {
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

    private static void printSolitions(List<Point[]> colinear) {
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

    private static Point[] readFile(String filename) {
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