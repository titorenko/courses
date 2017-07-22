package week5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import common.Point2D;
import common.RectHV;

public class KdTree {
    
    private static class Node {
        Node left;
        Node right;
        Point2D p;
        
        private Node(Point2D p) {
            this.p = p;
        }
    }
    
    private Node root = null;
    private int size = 0;

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        root = insert(p, root, true);
    }

    private Node insert(Point2D p, Node node, boolean isX) {
        if (node == null) {
            size++;
            return new Node(p);
        }

        if (!node.p.equals(p)) {
            Comparator<Point2D> cmpr = isX ? Point2D.X_ORDER : Point2D.Y_ORDER;
            if (cmpr.compare(p, node.p) <= 0) {
                node.left = insert(p, node.left, !isX);
            } else {
                node.right = insert(p, node.right, !isX);
            }
        }
        return node;
    }

    public boolean contains(Point2D p) {
        return contains(p, root, true);
    }

    private boolean contains(Point2D p, Node node, boolean isX) {
        if (node == null) return false;
        if (node.p.equals(p)) return true;
        Comparator<Point2D> cmpr = isX ? Point2D.X_ORDER : Point2D.Y_ORDER;
        if (cmpr.compare(p, node.p) <= 0) {
            return contains(p, node.left, !isX);
        } else {
            return contains(p, node.right, !isX);
        }
    }

    public void draw() {
        draw(root);
    }

    private void draw(Node node) {
        if (node == null) return;
        node.p.draw();
        draw(node.left);
        draw(node.right);
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        final List<Point2D> result = new ArrayList<>();
        range(root, rect, true, result);
        return result;
    }

    private void range(Node node, RectHV rect, boolean isX, List<Point2D> result) {
        if (node == null) return;
        if (rect.contains(node.p)) result.add(node.p);
        if (isX) {
            if (rect.xmin() <= node.p.x()) {
                range(node.left, rect, !isX, result);
            }
            if (rect.xmax() >= node.p.x()) {
                range(node.right, rect, !isX, result);
            }
        } else {
            if (rect.ymin() <= node.p.y()) {
                range(node.left, rect, !isX, result);
            }
            if (rect.ymax() >= node.p.y()) {
                range(node.right, rect, !isX, result);
            }
        }
    }
    
    private class NearestPointSearchContext {
        private final Point2D p;
        private double min = Double.MAX_VALUE;
        private double minSquared = Double.MAX_VALUE;
        private Point2D best = null;

        private NearestPointSearchContext(Point2D p) {
            this.p = p;
        }

        public void update(Point2D other) {
            double dist = p.distanceSquaredTo(other);
            if (dist < minSquared) {
                min = Math.sqrt(dist);
                minSquared = dist;
                best = other;
            }
        }
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        NearestPointSearchContext ctx = new NearestPointSearchContext(p);
        nearest(root, ctx, true);
        return ctx.best;        
    }

    private void nearest(Node node, NearestPointSearchContext ctx, boolean isX) {
        if (node == null) return;
        ctx.update(node.p);
        Comparator<Point2D> cmpr = isX ? Point2D.X_ORDER : Point2D.Y_ORDER;
        if (cmpr.compare(ctx.p, node.p) <= 0) {
            nearest(node.left, ctx, !isX);
            if ((isX && ctx.min + ctx.p.x() >= node.p.x()) || (!isX && ctx.min + ctx.p.y() >= node.p.y())) {
                nearest(node.right, ctx, !isX);
            }
        } else {
            nearest(node.right, ctx, !isX);
            if ((isX && ctx.p.x() - ctx.min <= node.p.x()) || (!isX && ctx.p.y() - ctx.min <= node.p.y())) {
                nearest(node.left, ctx, !isX);    
            }
        }
    }
}