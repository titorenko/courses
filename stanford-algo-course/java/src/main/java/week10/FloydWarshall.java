package week10;

import java.util.Arrays;
import java.util.stream.IntStream;

import week10.BellmanFord.NegaticeCycleFoundException;
import week5.WeightedDigraph;

/**
 * Cubic APSP algorithm that works with negative edges. Detects negative cycles. 
 */
public class FloydWarshall {
    private final int PLUS_INFINITY = Integer.MAX_VALUE;
    
    private final WeightedDigraph g;
    private final int n;

    public FloydWarshall(WeightedDigraph g) {
        this.g = g;
        this.n = g.getVertexCount();
    }
    
    public int[][] solve() throws NegaticeCycleFoundException {
        int[][] a = initialState();
        for (int k = 0; k < n; k++) {
            //compute shortest paths using only 0, 1, ..., k as intermediate vertices
            final int kf = k;
            IntStream.range(0, n).parallel().forEach(i -> {
                final int x = a[i][kf];
                if (x == PLUS_INFINITY) return;
                for (int j = 0; j < n; j++) {
                    final int y = a[kf][j];
                    if (y == PLUS_INFINITY) continue;
                    final int sum = x + y;
                    if (sum < a[i][j]) a[i][j] = sum;
                }
            });
            for (int i = 0; i < n; i++) {
                if (a[i][i] < 0) throw new NegaticeCycleFoundException(); 
            }
        }
        return a;
    }
    
    int solveForMin() throws NegaticeCycleFoundException {
        int[][] shortestPaths = solve();
        int min = shortestPaths[0][0];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (shortestPaths[i][j] < min) min = shortestPaths[i][j];
            }
        }
        return min;
    }

    private int[][] initialState() {
        int[][] result = new int[n][];
        for (int i = 0; i < n; i++) {
            result[i] = new int[n];
            Arrays.fill(result[i], PLUS_INFINITY);
            result[i][i] = 0;
            g.getAdj()[i].forEach(e -> result[e.from][e.to] = e.weight);
        }
        return result;
    }
    
    public static void main(String[] args) throws NegaticeCycleFoundException {
        WeightedDigraph g = InputParser.assignmentLarge();
        FloydWarshall alg = new FloydWarshall(g);
        long start = System.currentTimeMillis();
        int min = alg.solveForMin();
        long duration = System.currentTimeMillis() - start;
        System.out.println("Minimum is "+min+" computed in "+duration);
    }

}
