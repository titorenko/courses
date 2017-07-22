package week10;

import java.util.Arrays;
import java.util.List;

import week5.WeightedDigraph;
import week5.WeightedDigraph.Edge;

/**
 * Bellman-Ford shortest path algorithm. Works for graphs with negative edges but without negatice cycles.
 * Negative cycles will be detected if they exist.
 * Some optimizations are implemented: 
 * -- Yen (1970) If a vertex v has a distance value that has not changed since the last time the edges out of v were relaxed, then there is no need to relax the edges out of v a second time.
 * Some optimizations that can be further implemented:
 * -- Yen (1970) Partition into some order and do two loops, did not help on my test cases   
 * -- Do random permutation, before doing previous as described in http://11011110.livejournal.com/215330.html
 */
public class BellmanFord {

    public static class NegaticeCycleFoundException extends Exception {
        private static final long serialVersionUID = -3214806239936804986L;
    }
    
    private final int PLUS_INFINITY = Integer.MAX_VALUE;

    private final WeightedDigraph g;
    private final int n;
    private final int sourceVertex;


    public BellmanFord(WeightedDigraph g, int sourceVertex) {
        this.g = g;
        this.n = g.getVertexCount();
        this.sourceVertex = sourceVertex;
    }
    
    public int[] find() throws NegaticeCycleFoundException {
        final int[] a = getInitialLengths(n);
        final int[] lastRelaxationLegth = new int[n];
        Arrays.fill(lastRelaxationLegth, PLUS_INFINITY);
        boolean hasRelaxed = false;
        for (int i = 0; i < n-1; i++) {
            hasRelaxed = relax(a, lastRelaxationLegth);
            if (!hasRelaxed) break;
        }
        if (hasRelaxed) 
            throw new NegaticeCycleFoundException();
        return a;
    }

    private boolean relax(final int[] a, int[] lastRelaxationLegth) {
        boolean hasRelaxed = false;
        final List<Edge>[] adj = g.getAdj();
        for (int from = 0; from < n; from++) {
            final int fromPath = a[from];
            if (fromPath == lastRelaxationLegth[from]) continue;
            lastRelaxationLegth[from] = fromPath;
            for (Edge e : adj[from]) {
                int relaxed = fromPath+e.weight;
                if (relaxed < a[e.to]) {
                    a[e.to] = relaxed;
                    hasRelaxed = true;
                }
            }
        }
        return hasRelaxed;
    }

    private int[] getInitialLengths(final int n) {
        int[] a = new int[n];
        Arrays.fill(a, PLUS_INFINITY);
        a[sourceVertex] = 0;
        return a;
    }
}
