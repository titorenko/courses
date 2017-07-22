package week10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import week10.BellmanFord.NegaticeCycleFoundException;
import week5.DijkstraShortestPath;
import week5.DijkstraSolvers;
import week5.WeightedDigraph;
import week5.WeightedDigraph.Edge;

import com.google.common.primitives.Ints;
/**
 * In this assignment you will implement one or more algorithms for the all-pairs shortest-path problem. 
   Some of the edge lengths can be negative. Graphs may or may not have negative-cost cycles.
   Your task is to compute the "shortest shortest path". Precisely, you must first identify which, if any, of the three graphs have no negative cycles. For each such graph, you should compute all-pairs shortest paths and remember the smallest one (i.e., compute minu,v∈Vd(u,v), where d(u,v) denotes the shortest-path distance from u to v).
 */
public class JohnsonAPSP {

    private final WeightedDigraph g;
    private final int n;

    public JohnsonAPSP(WeightedDigraph g) {
        this.g = g;
        this.n = g.getVertexCount();
    }
    
    public int[][] solve() throws NegaticeCycleFoundException {
        Object[] reweightResult = reweight(g);
        final WeightedDigraph gr = (WeightedDigraph) reweightResult[0];
        final int[] p = (int[]) reweightResult[1];
        Stream<int[]> solutionStream = IntStream.range(0, n).
                boxed().
                parallel().
                map(i -> dijkstra(gr, i, p));
        return solutionStream.toArray(int[][]::new);
    }
    
    public int solveForMin() throws NegaticeCycleFoundException {
        Object[] reweightResult = reweight(g);
        final WeightedDigraph gr = (WeightedDigraph) reweightResult[0];
        final int[] p = (int[]) reweightResult[1];
        Stream<int[]> solutionStream = IntStream.range(0, n).
                filter(i -> hasNegativeOutgoingEdge(i)).
                boxed().
                parallel().
                map(i -> dijkstra(gr, i, p));
        return solutionStream.mapToInt(Ints::min).min().getAsInt();
    }
    
    
    private boolean hasNegativeOutgoingEdge(int i) {
        return g.getAdj()[i].stream().anyMatch(e -> e.weight < 0);
    }

    private int[] dijkstra(WeightedDigraph gr, int source, int[] p) {
        DijkstraShortestPath solver = DijkstraSolvers.BINARY.solverFor(gr, source);
        int[] result = solver.find();
        restoreWeights(result, source, p);
        return result;
    }
    
    private void restoreWeights(int[] dijkstraSolution, int source, int[] p) {
        for (int j = 0; j < dijkstraSolution.length; j++) {
            dijkstraSolution[j] = dijkstraSolution[j] - p[source] + p[j]; 
        }
    }

    private Object[] reweight(WeightedDigraph g) throws NegaticeCycleFoundException {
        WeightedDigraph g2 = attachVirtualSource();
        BellmanFord bf = new BellmanFord(g2, n);
        int[] p = bf.find();
        List<Edge>[] reweightedEdges = WeightedDigraph.newAdj(n);
        for (int i = 0; i < n; i++) {
            for (Edge e : g.getAdj()[i]) {
                reweightedEdges[i].add(new Edge(e.from, e.to, e.weight+p[e.from]-p[e.to]));
            }
        }
        WeightedDigraph result = new WeightedDigraph(reweightedEdges);
        assert result.getEdgeStream().allMatch(e -> e.weight >= 0);
        return new Object[] {result, p};
    }
    
    WeightedDigraph attachVirtualSource() {
        List<Edge>[] edges = Arrays.copyOf(g.getAdj(), g.getVertexCount()+1);
        List<Edge> sourceEdges = new ArrayList<Edge>();
        int sourceIdx = g.getVertexCount();
        for (int i = 0; i < g.getVertexCount(); i++) {
            sourceEdges.add(new Edge(sourceIdx, i, 0));
        }
        edges[sourceIdx] = sourceEdges;
        return new WeightedDigraph(edges);
    }

    public static void main(String[] args) throws NegaticeCycleFoundException {
        WeightedDigraph g = InputParser.assignment3();
        JohnsonAPSP alg = new JohnsonAPSP(g);
        long start = System.currentTimeMillis();
        int min = alg.solveForMin();
        long duration = System.currentTimeMillis() - start;
        System.out.println("Minimum is "+min+" computed in "+duration);
    }
}