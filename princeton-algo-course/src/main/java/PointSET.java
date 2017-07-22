

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {
    
    private final TreeSet<Point2D> points = new TreeSet<Point2D>();  

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        points.add(p);
    }

    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        final List<Point2D> result = new ArrayList<>();
        for (Point2D p : points) {
            if (rect.contains(p)) result.add(p);
        }
        return result;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        Point2D result = null;
        double minDist = Double.MAX_VALUE;
        for (Point2D other : points) {
            double dist = p.distanceTo(other);
            if (dist < minDist) {
                minDist = dist;
                result = other;
            }
        }
        return result;        
    }
}